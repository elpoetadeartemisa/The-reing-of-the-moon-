package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppRulesData
import com.example.model.Artwork
import com.example.model.SocialLinks
import com.example.ui.theme.*

@Composable
fun LunarTopBar(
    onOpenRules: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MidnightNavy,
        tonalElevation = 6.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(LunarGold, MidnightObsidian)
                            )
                        )
                        .border(1.5.dp, LunarGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌙", fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "THE REIGN OF THE MOON",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = LunarGold,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Citas & Arte Bajo la Luna",
                        style = MaterialTheme.typography.labelSmall,
                        color = MoonSilverMuted
                    )
                }
            }

            // Rules button with golden shield
            FilledTonalButton(
                onClick = onOpenRules,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MidnightCardLight,
                    contentColor = LunarGold
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("open_rules_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Reglas de la app",
                    tint = LunarGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Reglas",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CommunityRulesDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, LunarGold.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("community_rules_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MidnightCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📜", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "REGLAS DE LA APP",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = LunarGold
                        )
                        Text(
                            text = "THE REIGN OF THE MOON",
                            style = MaterialTheme.typography.bodySmall,
                            color = MoonSilverMuted,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MoonSilver)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Important charter note
                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RoseLoveSoft.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                        Text("🛡️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Por favor lee y respeta las normas de convivencia. Nuestro compromiso es garantizar un ambiente romántico, respetuoso y auténticamente artístico.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MoonSilver
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Rules List
                AppRulesData.RULES.forEach { rule ->
                    RuleItemCard(rule = rule)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LunarGold,
                        contentColor = MidnightObsidian
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("accept_rules_button")
                ) {
                    Text(
                        text = "Entiendo y Acepto las Reglas Lunares",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RuleItemCard(rule: com.example.model.CommunityRule) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(rule.highlightColorHex).copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(rule.iconEmoji, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${rule.number}. ${rule.title}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(rule.highlightColorHex)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = rule.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MoonSilverMuted,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = rule.description,
                style = MaterialTheme.typography.bodySmall,
                color = MoonSilver,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun SocialLinksRow(
    socialLinks: SocialLinks,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        socialLinks.instagram?.let { handle ->
            SocialBadge(
                label = "Instagram",
                handle = "@$handle",
                emoji = "📸",
                badgeColor = Color(0xFFE1306C),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/$handle"))
                    context.startActivity(Intent.createChooser(intent, "Abrir Instagram"))
                }
            )
        }

        socialLinks.spotify?.let { link ->
            SocialBadge(
                label = "Spotify",
                handle = "Música",
                emoji = "🎵",
                badgeColor = Color(0xFF1DB954),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/$link"))
                    context.startActivity(Intent.createChooser(intent, "Abrir Spotify"))
                }
            )
        }

        socialLinks.behance?.let { handle ->
            SocialBadge(
                label = "Behance",
                handle = "Portafolio",
                emoji = "🎨",
                badgeColor = Color(0xFF1769FF),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://behance.net/$handle"))
                    context.startActivity(Intent.createChooser(intent, "Abrir Behance"))
                }
            )
        }

        socialLinks.soundcloud?.let { handle ->
            SocialBadge(
                label = "SoundCloud",
                handle = "Acústico",
                emoji = "☁️",
                badgeColor = Color(0xFFFF5500),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://soundcloud.com/$handle"))
                    context.startActivity(Intent.createChooser(intent, "Abrir SoundCloud"))
                }
            )
        }

        socialLinks.twitter?.let { handle ->
            SocialBadge(
                label = "X / Twitter",
                handle = "@$handle",
                emoji = "𝕏",
                badgeColor = Color(0xFF1DA1F2),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://x.com/$handle"))
                    context.startActivity(Intent.createChooser(intent, "Abrir X"))
                }
            )
        }
    }
}

@Composable
fun SocialBadge(
    label: String,
    handle: String,
    emoji: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Surface(
        color = badgeColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, badgeColor.copy(alpha = 0.5f)),
        modifier = Modifier
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = handle,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MoonSilver
            )
        }
    }
}

@Composable
fun ArtworkViewerDialog(
    artwork: Artwork,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, LunarGold.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with discipline
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        color = MidnightCardLight,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${artwork.discipline.emoji} ${artwork.discipline.displayName}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LunarGold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MoonSilver)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = artwork.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MoonSilver
                )
                Text(
                    text = "${artwork.technique} • Año ${artwork.year}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MoonSilverMuted
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Poetry reader if poetryVerses present
                if (!artwork.poetryVerses.isNullOrBlank()) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MidnightCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, LunarGold.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("✒️", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Manuscrito Poético",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LunarGold
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = artwork.poetryVerses,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MoonSilver,
                                fontStyle = FontStyle.Italic,
                                fontFamily = FontFamily.Serif,
                                lineHeight = 24.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Audio preview if audioTitle present
                if (!artwork.audioTitle.isNullOrBlank()) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MidnightCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MysticVioletSoft.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MysticViolet),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Reproducir acústico", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = artwork.audioTitle,
                                    fontWeight = FontWeight.Bold,
                                    color = MoonSilver,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Acústico nocturno • ${artwork.audioDuration ?: "3:40 min"}",
                                    fontSize = 12.sp,
                                    color = MoonSilverMuted
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Artwork story / description
                Text(
                    text = "Historia & Inspiración Romántica",
                    fontWeight = FontWeight.Bold,
                    color = LunarGold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = artwork.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoonSilver,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = RoseLove, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("${artwork.likesCount} admiraciones", color = MoonSilverMuted, fontSize = 13.sp)
                    }

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MidnightCardLight,
                            contentColor = LunarGold
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}
