package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ArtDiscipline
import com.example.model.ArtistProfile
import com.example.model.Artwork
import com.example.ui.components.SocialLinksRow
import com.example.ui.theme.*

@Composable
fun DiscoveryScreen(
    artists: List<ArtistProfile>,
    currentIndex: Int,
    activeFilter: ArtDiscipline?,
    onFilterSelect: (ArtDiscipline?) -> Unit,
    onLike: (ArtistProfile) -> Unit,
    onPass: () -> Unit,
    onViewArtwork: (Artwork) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightObsidian)
    ) {
        // Discipline filter pills row
        DisciplineFilterBar(
            activeFilter = activeFilter,
            onFilterSelect = onFilterSelect
        )

        if (artists.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌙", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No hay artistas en esta disciplina por ahora",
                        style = MaterialTheme.typography.titleMedium,
                        color = MoonSilver,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onFilterSelect(null) },
                        colors = ButtonDefaults.buttonColors(containerColor = LunarGold, contentColor = MidnightObsidian)
                    ) {
                        Text("Ver Todas las Disciplinas")
                    }
                }
            }
        } else {
            val safeIndex = currentIndex.coerceIn(0, artists.size - 1)
            val currentArtist = artists[safeIndex]

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Artist Romantic Card
                ArtistCard(
                    artist = currentArtist,
                    onViewArtwork = onViewArtwork
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Bar (Pass, Like)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Pass Button
                    FloatingActionButton(
                        onClick = onPass,
                        containerColor = MidnightCard,
                        contentColor = MoonSilverMuted,
                        shape = CircleShape,
                        modifier = Modifier
                            .size(56.dp)
                            .testTag("pass_artist_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Pasar artista", modifier = Modifier.size(28.dp))
                    }

                    // Super Love / Poema directo
                    FloatingActionButton(
                        onClick = { onLike(currentArtist) },
                        containerColor = MidnightCardLight,
                        contentColor = LunarGold,
                        shape = CircleShape,
                        modifier = Modifier
                            .size(62.dp)
                            .border(1.5.dp, LunarGold, CircleShape)
                            .testTag("super_poem_button")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Star, contentDescription = "Dedicar verso", modifier = Modifier.size(24.dp))
                            Text("Verso", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Like Button (Me apasiona)
                    FloatingActionButton(
                        onClick = { onLike(currentArtist) },
                        containerColor = RoseLove,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .size(68.dp)
                            .testTag("like_artist_button")
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = "Me apasiona / Match", modifier = Modifier.size(34.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DisciplineFilterBar(
    activeFilter: ArtDiscipline?,
    onFilterSelect: (ArtDiscipline?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(
            selected = activeFilter == null,
            onClick = { onFilterSelect(null) },
            label = { Text("✨ Todas las Artes") },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = LunarGold,
                selectedLabelColor = MidnightObsidian,
                containerColor = MidnightCard,
                labelColor = MoonSilver
            ),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = if (activeFilter == null) LunarGold else LunarCardOutline,
                enabled = true,
                selected = activeFilter == null
            )
        )

        ArtDiscipline.values().forEach { disc ->
            FilterChip(
                selected = activeFilter == disc,
                onClick = { onFilterSelect(disc) },
                label = { Text("${disc.emoji} ${disc.displayName}") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LunarGold,
                    selectedLabelColor = MidnightObsidian,
                    containerColor = MidnightCard,
                    labelColor = MoonSilver
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = if (activeFilter == disc) LunarGold else LunarCardOutline,
                    enabled = true,
                    selected = activeFilter == disc
                )
            )
        }
    }
}

@Composable
fun ArtistCard(
    artist: ArtistProfile,
    onViewArtwork: (Artwork) -> Unit
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightSurface),
        border = BorderStroke(1.dp, Brush.verticalGradient(listOf(LunarGold.copy(alpha = 0.5f), MidnightCardLight))),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("artist_card_${artist.id}")
    ) {
        Column {
            // Header Image Box with artist avatar/art illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(MidnightCardLight, MidnightObsidian)
                        )
                    )
            ) {
                // Hero Background
                Image(
                    painter = painterResource(id = R.drawable.img_moon_hero_1792695305812),
                    contentDescription = "Atelier lunar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.45f
                )

                // Dark gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MidnightSurface)
                            )
                        )
                )

                // Verified Real Photo Badge & No-AI indicator (Requested Rule)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MidnightObsidian.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, RealPhotoGreen)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🛡️", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Foto Real Verificada • Sin IA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RealPhotoGreen
                            )
                        }
                    }
                }

                // Distance Badge
                Surface(
                    color = MidnightObsidian.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, LunarGold.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = LunarGold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${artist.distanceKm} km",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LunarGold
                        )
                    }
                }

                // Avatar and name at the bottom of header
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(MidnightCardLight, MidnightSurface))
                            )
                            .border(2.5.dp, LunarGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(artist.countryFlag, fontSize = 34.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${artist.fullName}, ${artist.age}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MoonSilver
                            )
                        }
                        Text(
                            text = "${artist.countryFlag} ${artist.city}, ${artist.country}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MoonSilverMuted
                        )
                    }
                }
            }

            // Body content
            Column(modifier = Modifier.padding(18.dp)) {
                // Disciplines chips
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MidnightCard,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, LunarGold.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = "${artist.mainDiscipline.emoji} ${artist.mainDiscipline.displayName}",
                            color = LunarGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    artist.secondaryDiscipline?.let { sec ->
                        Surface(
                            color = MidnightCard,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MysticVioletSoft.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "${sec.emoji} ${sec.displayName}",
                                color = MysticVioletSoft,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Romantic Quote
                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, RoseLoveSoft.copy(alpha = 0.25f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Text("«", color = LunarGold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = artist.romanticQuote,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MoonSilver,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.weight(1f)
                        )
                        Text("»", color = LunarGold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Bio
                Text(
                    text = "Sobre mi arte & anhelos",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = LunarGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = artist.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoonSilver,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Social Networks Section
                Text(
                    text = "Redes Sociales Integradas",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = LunarGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                SocialLinksRow(socialLinks = artist.socialLinks)

                Spacer(modifier = Modifier.height(18.dp))

                // Artworks Gallery
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Exhibición de Obras (${artist.portfolio.size})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MoonSilver
                    )
                    Text(
                        text = "Toca para abrir",
                        style = MaterialTheme.typography.labelSmall,
                        color = MoonSilverMuted
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(artist.portfolio) { artwork ->
                        ArtworkPreviewCard(
                            artwork = artwork,
                            onClick = { onViewArtwork(artwork) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArtworkPreviewCard(
    artwork: Artwork,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightCard),
        border = BorderStroke(1.dp, LunarOutline),
        modifier = Modifier
            .width(180.dp)
            .clickable(onClick = onClick)
            .testTag("artwork_preview_${artwork.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(artwork.discipline.emoji, fontSize = 20.sp)
                Text(
                    text = artwork.year,
                    fontSize = 11.sp,
                    color = MoonSilverMuted
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = artwork.title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MoonSilver,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = artwork.technique,
                fontSize = 11.sp,
                color = MoonSilverMuted,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                color = MidnightCardLight,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (!artwork.poetryVerses.isNullOrBlank()) "📖 Leer Poema" else if (!artwork.audioTitle.isNullOrBlank()) "🎵 Escuchar" else "👁️ Ver Obra",
                    color = LunarGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
