package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArtistProfile
import com.example.model.RomanticSpot
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MapRadarScreen(
    artists: List<ArtistProfile>,
    spots: List<RomanticSpot>,
    radiusKm: Float,
    isShowingArtists: Boolean,
    selectedArtist: ArtistProfile?,
    selectedSpot: RomanticSpot?,
    onRadiusChange: (Float) -> Unit,
    onToggleCategory: (Boolean) -> Unit,
    onSelectArtist: (ArtistProfile?) -> Unit,
    onSelectSpot: (RomanticSpot?) -> Unit,
    onOpenChatWithArtist: (ArtistProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightObsidian)
    ) {
        // Map Top Bar Controls
        Surface(
            color = MidnightSurface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                // Toggle row: Artistas vs Lugares Románticos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FilterChip(
                        selected = isShowingArtists,
                        onClick = { onToggleCategory(true) },
                        label = { Text("🎭 Artistas Cercanos (${artists.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LunarGold,
                            selectedLabelColor = MidnightObsidian,
                            containerColor = MidnightCard,
                            labelColor = MoonSilver
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    FilterChip(
                        selected = !isShowingArtists,
                        onClick = { onToggleCategory(false) },
                        label = { Text("🍷 Citas Románticas (${spots.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RoseLove,
                            selectedLabelColor = Color.White,
                            containerColor = MidnightCard,
                            labelColor = MoonSilver
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Radius Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Radar, contentDescription = null, tint = LunarGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Radio de búsqueda: ${radiusKm.toInt()} km",
                        fontSize = 12.sp,
                        color = MoonSilver,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Slider(
                        value = radiusKm,
                        onValueChange = onRadiusChange,
                        valueRange = 2f..30f,
                        steps = 5,
                        colors = SliderDefaults.colors(
                            thumbColor = LunarGold,
                            activeTrackColor = LunarGold,
                            inactiveTrackColor = MidnightCardLight
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Celestial Radar & Map Canvas Box
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MidnightNavy)
                .border(1.dp, Brush.radialGradient(listOf(LunarGold.copy(alpha = 0.3f), Color.Transparent)), RoundedCornerShape(24.dp))
        ) {
            // Radar Canvas with rings & compass
            RadarCanvas(
                radiusKm = radiusKm,
                modifier = Modifier.fillMaxSize()
            )

            // Center: My Location / Studio
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(LunarGold)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("📍", fontSize = 16.sp)
            }

            // Interactive markers for Artists
            if (isShowingArtists) {
                artists.forEachIndexed { index, artist ->
                    val angle = (index * 72.0 + 35.0) * Math.PI / 180.0
                    val distFraction = (artist.distanceKm.toFloat() / radiusKm).coerceIn(0.2f, 0.85f)
                    val xOffset = (cos(angle) * distFraction * 130).dp
                    val yOffset = (sin(angle) * distFraction * 130).dp

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = xOffset, y = yOffset)
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(if (selectedArtist?.id == artist.id) LunarGold else MidnightCardLight)
                            .border(
                                2.dp,
                                if (selectedArtist?.id == artist.id) Color.White else LunarGold.copy(alpha = 0.6f),
                                CircleShape
                            )
                            .clickable { onSelectArtist(artist) }
                            .testTag("map_marker_artist_${artist.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(artist.countryFlag, fontSize = 16.sp)
                            Text(artist.mainDiscipline.emoji, fontSize = 10.sp)
                        }
                    }
                }
            } else {
                // Interactive markers for Romantic Spots
                spots.forEachIndexed { index, spot ->
                    val angle = (index * 90.0 + 20.0) * Math.PI / 180.0
                    val distFraction = (spot.distanceKm.toFloat() / radiusKm).coerceIn(0.2f, 0.85f)
                    val xOffset = (cos(angle) * distFraction * 130).dp
                    val yOffset = (sin(angle) * distFraction * 130).dp

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = xOffset, y = yOffset)
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(if (selectedSpot?.id == spot.id) RoseLove else MidnightCard)
                            .border(
                                2.dp,
                                if (selectedSpot?.id == spot.id) Color.White else RoseLoveSoft.copy(alpha = 0.7f),
                                CircleShape
                            )
                            .clickable { onSelectSpot(spot) }
                            .testTag("map_marker_spot_${spot.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🍷", fontSize = 20.sp)
                    }
                }
            }

            // Google Maps direct launcher button in corner
            FloatingActionButton(
                onClick = {
                    val gmmIntentUri = Uri.parse("geo:40.4168,-3.7038?q=cafes+de+arte+galerias+madrid")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        // Fallback browser Google Maps
                        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/cafes+de+arte+galerias+madrid"))
                        context.startActivity(webIntent)
                    }
                },
                containerColor = MidnightCard,
                contentColor = LunarGold,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(48.dp)
                    .testTag("launch_google_maps_fab")
            ) {
                Icon(Icons.Default.Map, contentDescription = "Abrir Google Maps", modifier = Modifier.size(22.dp))
            }

            // Compass indicator
            Surface(
                color = MidnightObsidian.copy(alpha = 0.75f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = "🧭 N",
                    color = MoonSilverMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Bottom Selected Info Card (Animated)
        AnimatedVisibility(visible = selectedArtist != null || selectedSpot != null) {
            Surface(
                color = MidnightSurface,
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
                border = BorderStroke(1.dp, LunarCardOutline),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (selectedArtist != null) {
                    ArtistMapDetailCard(
                        artist = selectedArtist,
                        onClose = { onSelectArtist(null) },
                        onChat = { onOpenChatWithArtist(selectedArtist) }
                    )
                } else if (selectedSpot != null) {
                    SpotMapDetailCard(
                        spot = selectedSpot,
                        onClose = { onSelectSpot(null) },
                        onNavigate = {
                            val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode("${selectedSpot.name}, ${selectedSpot.address}")}")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RadarCanvas(
    radiusKm: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = minOf(size.width, size.height) * 0.44f

        // Draw radar rings
        for (i in 1..4) {
            val r = maxRadius * (i / 4f)
            drawCircle(
                color = Color(0x22FFD56B),
                radius = r,
                center = center,
                style = Stroke(width = 1.2f)
            )
        }

        // Crosshairs
        drawLine(
            color = Color(0x18FFD56B),
            start = Offset(center.x, center.y - maxRadius),
            end = Offset(center.x, center.y + maxRadius),
            strokeWidth = 1f
        )
        drawLine(
            color = Color(0x18FFD56B),
            start = Offset(center.x - maxRadius, center.y),
            end = Offset(center.x + maxRadius, center.y),
            strokeWidth = 1f
        )
    }
}

@Composable
fun ArtistMapDetailCard(
    artist: ArtistProfile,
    onClose: () -> Unit,
    onChat: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MidnightCardLight)
                        .border(1.5.dp, LunarGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(artist.countryFlag, fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${artist.fullName}, ${artist.age}",
                            fontWeight = FontWeight.Bold,
                            color = MoonSilver,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("🛡️ Sin IA", color = RealPhotoGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "${artist.mainDiscipline.emoji} ${artist.mainDiscipline.displayName} • A ${artist.distanceKm} km",
                        fontSize = 12.sp,
                        color = LunarGold
                    )
                }
            }

            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MoonSilverMuted)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "«${artist.romanticQuote}»",
            style = MaterialTheme.typography.bodySmall,
            color = MoonSilverMuted,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = onChat,
            colors = ButtonDefaults.buttonColors(containerColor = RoseLove, contentColor = Color.White),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("map_chat_button")
        ) {
            Icon(Icons.Default.ChatBubble, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Invitar a Cita / Chatear en Privado", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SpotMapDetailCard(
    spot: RomanticSpot,
    onClose: () -> Unit,
    onNavigate: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = spot.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = LunarGold
                )
                Text(
                    text = "🍷 ${spot.category} • ${spot.address}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MoonSilverMuted
                )
            }
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MoonSilverMuted)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = spot.romanticDescription,
            style = MaterialTheme.typography.bodySmall,
            color = MoonSilver,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = onNavigate,
            colors = ButtonDefaults.buttonColors(containerColor = LunarGold, contentColor = MidnightObsidian),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("navigate_google_maps_button")
        ) {
            Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Navegar con Google Maps", fontWeight = FontWeight.Bold)
        }
    }
}
