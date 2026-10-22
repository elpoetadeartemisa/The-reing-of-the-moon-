package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.ArtistProfile
import com.example.ui.theme.*

@Composable
fun MatchCelebrationDialog(
    matchedArtist: ArtistProfile,
    currentUser: ArtistProfile,
    onOpenChat: (ArtistProfile) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = androidx.compose.foundation.BorderStroke(2.dp, Brush.linearGradient(listOf(LunarGold, RoseLove))),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("match_celebration_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Moon celebration badge
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(listOf(LunarGold, RoseLove))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌕", fontSize = 36.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡LUNA LLENA!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = LunarGold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Atracción Artística & Romántica",
                    style = MaterialTheme.typography.titleSmall,
                    color = RoseLoveSoft,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dual avatar presentation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Current User
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MidnightCardLight)
                                .border(2.dp, LunarGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(currentUser.countryFlag, fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentUser.firstName,
                            color = MoonSilver,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Amor",
                        tint = RoseLove,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Matched Artist
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MidnightCardLight)
                                .border(2.dp, RoseLove, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(matchedArtist.countryFlag, fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = matchedArtist.firstName,
                            color = MoonSilver,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "«${matchedArtist.romanticQuote.ifBlank { "Tus obras y las mías conversan en la misma frecuencia cósmica." }}»",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoonSilverMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onOpenChat(matchedArtist) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RoseLove,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("open_private_chat_button")
                ) {
                    Text("Abrir Chat Privado con ${matchedArtist.firstName}", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(contentColor = MoonSilverMuted),
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Text("Seguir buscando almas bohemias")
                }
            }
        }
    }
}

@Composable
fun PublicRuleWarningDialog(
    warningMessage: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, WarningAmber),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(WarningAmber.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Advertencia de regla",
                        tint = WarningAmber,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "REGLA DE CONVIVENCIA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = WarningAmber
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = warningMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoonSilver,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Recuerda: Los chats públicos son para exhibir arte, poesía y reflexiones. La intimidad sexual solo se permite en chats privados consensuados.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoonSilverMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarningAmber,
                        contentColor = MidnightObsidian
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Text("Comprendido", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
