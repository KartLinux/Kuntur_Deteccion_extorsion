package com.denicks21.speechandtext.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.denicks21.speechandtext.R

// -----------------------
// Constantes de configuración
// -----------------------
private const val MAIN_CARD_ALPHA = 0.3f            // Transparencia de la tarjeta principal
private const val METRIC_CARD_ALPHA = 0.3f          // Transparencia de las tarjetas de métrica

private val MAIN_CARD_CORNER = 16.dp                // Radio de esquina de la tarjeta principal
private val METRIC_CARD_CORNER = 12.dp              // Radio de esquina de las métricas

private val MAIN_CARD_HEIGHT = 200.dp               // Altura de la tarjeta principal
private val METRIC_CARD_HEIGHT = 100.dp             // Altura de cada tarjeta de métrica

private val LOCATION_ICON_SIZE = 20.dp              // Tamaño del icono de ubicación
private val LOCATION_SPACING = 8.dp                 // Espacio entre icono y texto

// Función que devuelve una ubicación de prueba
private fun getFakeLocation(): String = "Quito, Ecuador"

@Composable
fun HomePage(
    speechInput:       String,
    isListening:       Boolean,
    onToggleListening: () -> Unit
) {
    // Fondo degradado horizontal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ubicación con icono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter            = painterResource(id = R.drawable.ic_ubication),
                    contentDescription = "Ubicación",
                    tint               = MaterialTheme.colors.onPrimary,
                    modifier           = Modifier.size(LOCATION_ICON_SIZE)
                )
                Spacer(modifier = Modifier.width(LOCATION_SPACING))
                Text(
                    text  = getFakeLocation(),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary
                )
            }

            // ——— Tarjeta principal de estado ———
            // …

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MAIN_CARD_HEIGHT)
                    .clip(RoundedCornerShape(MAIN_CARD_CORNER)),
                shape           = RoundedCornerShape(MAIN_CARD_CORNER),
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = MAIN_CARD_ALPHA),
                elevation       = 0.dp
            ) {
                Column(
                    modifier            = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Aquí cambiamos el icono por tus drawables ic_kuntur_on / ic_kuntur_off:
                    Icon(
                        painter           = painterResource(
                            id = if (isListening)
                                R.drawable.ic_kuntur_on
                            else
                                R.drawable.ic_kuntur_off
                        ),
                        contentDescription = null,
                        modifier           = Modifier.size(64.dp),
                        tint               = Color.Unspecified    // preserva los colores originales del drawable
                    )

                    Text(
                        text  = if (isListening) "Kuntur a la escucha" else "Kuntur apagado",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                    Button(
                        onClick = onToggleListening,
                        shape   = RoundedCornerShape(50),
                        colors  = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor    = MaterialTheme.colors.onSecondary
                        )
                    ) {
                        Text(
                            text  = if (isListening) "Apagar Kuntur" else "Activar Kuntur",
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Texto transcrito en tiempo real
            Text(
                text     = if (speechInput.isNotBlank()) speechInput else "Aquí aparecerá tu texto...",
                style    = MaterialTheme.typography.body1,
                color    = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        // ——— Métricas inferiores ———
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                modifier   = Modifier
                    .weight(1f)
                    .height(METRIC_CARD_HEIGHT)
                    .clip(RoundedCornerShape(METRIC_CARD_CORNER)),
                icon       = Icons.Default.Notifications,
                label      = "Última alerta",
                value      = "21:39",
                cardAlpha  = METRIC_CARD_ALPHA,
                cornerSize = METRIC_CARD_CORNER,
                elevation  = 0.dp
            )
            MetricCard(
                modifier   = Modifier
                    .weight(1f)
                    .height(METRIC_CARD_HEIGHT)
                    .clip(RoundedCornerShape(METRIC_CARD_CORNER)),
                icon       = Icons.Default.BarChart,
                label      = "Incidencias",
                value      = "20",
                cardAlpha  = METRIC_CARD_ALPHA,
                cornerSize = METRIC_CARD_CORNER,
                elevation  = 0.dp
            )
        }
    }
}

@Composable
private fun MetricCard(
    modifier:    Modifier = Modifier,
    icon:        androidx.compose.ui.graphics.vector.ImageVector,
    label:       String,
    value:       String,
    cardAlpha:   Float,
    cornerSize:  Dp,
    elevation:   Dp
) {
    Card(
        modifier        = modifier,
        shape           = RoundedCornerShape(cornerSize),
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = cardAlpha),
        elevation       = elevation
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = MaterialTheme.colors.onSurface
            )
            Text(
                text  = label,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text  = value,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}



