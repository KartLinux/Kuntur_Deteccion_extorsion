package com.denicks21.speechandtext

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.denicks21.speechandtext.navigation.NavGraph
import com.denicks21.speechandtext.ui.theme.SpeechAndTextTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

class MainActivity : ComponentActivity() {

    // Estado compartido con Compose
    val speechInput = mutableStateOf("")
    val listening   = mutableStateOf(false)

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent

    // Lanzador para pedir permiso de micrófono
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(this, "Permiso de micrófono denegado", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pedir permiso si no está concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        setupSpeechRecognizer()

        setContent {
            SpeechAndTextTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController     = navController,
                    speechInputState  = speechInput,
                    listeningState    = listening,
                    startListening    = { startListening() },
                    stopListening     = { stopListening() }
                )
            }
        }

        // Auto-guardado cada 3 segundos
        lifecycleScope.launch {
            while (isActive) {
                delay(3_000L)
                if (listening.value && speechInput.value.isNotBlank()) {
                    val ts = System.currentTimeMillis()
                    writeFile("speech_$ts.txt", speechInput.value)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.toast_auto_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onPartialResults(partial: Bundle?) {
                    partial
                        ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull()
                        ?.let { hypothesis ->
                            speechInput.value = hypothesis
                        }
                }

                override fun onResults(results: Bundle?) {
                    results
                        ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull()
                        ?.let { hypothesis ->
                            val prev = speechInput.value.trim()
                            speechInput.value =
                                if (prev.isNotEmpty()) "$prev $hypothesis" else hypothesis
                        }
                    if (listening.value) startListening()
                }

                override fun onEndOfSpeech() {
                    if (listening.value) startListening()
                }

                override fun onError(error: Int) {
                    if (listening.value) startListening()
                }

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }

    fun startListening() {
        listening.value = true
        speechRecognizer.startListening(recognizerIntent)
    }

    fun stopListening() {
        listening.value = false
        speechRecognizer.stopListening()
    }

    fun writeFile(fileName: String, text: String) {
        val dir = File(getExternalFilesDir("SpeechAndText"), "")
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, fileName)
        BufferedWriter(FileOutputStream(file).bufferedWriter()).use { it.write(text) }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}


