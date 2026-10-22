package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Artwork
import com.example.model.PublicChatMessage
import com.example.ui.theme.*

@Composable
fun PublicLoungeScreen(
    messages: List<PublicChatMessage>,
    onSendMessage: (text: String, topic: String, attachedArtwork: Artwork?) -> Boolean,
    onLikeMessage: (String) -> Unit,
    onViewArtwork: (Artwork) -> Unit,
    onOpenRules: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var selectedTopic by remember { mutableStateOf("Poema del Alma") }
    var filterTopic by remember { mutableStateOf("Todos") }
    var showAttachArtMenu by remember { mutableStateOf(false) }

    val topics = listOf("Todos", "Poema del Alma", "Música Nocturna", "Exhibición", "Reflexión Lunar", "Cita Bohemia")

    val displayedMessages = remember(messages, filterTopic) {
        if (filterTopic == "Todos") messages
        else messages.filter { it.topicTag == filterTopic }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightObsidian)
    ) {
        // Prominent Rule Header Banner
        Surface(
            color = MidnightCard,
            border = BorderStroke(1.dp, WarningAmber.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenRules)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(WarningAmber.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⚠️", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "REGLA DEL SALÓN PÚBLICO",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Black,
                        color = WarningAmber,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Prohibido contenido sexual. Expresa poesía, arte y romance cortés. Intimidad solo en chats privados.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoonSilver,
                        lineHeight = 16.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Ver reglas",
                    tint = LunarGold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Topics filter scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            topics.forEach { topic ->
                FilterChip(
                    selected = filterTopic == topic,
                    onClick = { filterTopic = topic },
                    label = { Text(topic) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LunarGold,
                        selectedLabelColor = MidnightObsidian,
                        containerColor = MidnightSurface,
                        labelColor = MoonSilver
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (filterTopic == topic) LunarGold else LunarCardOutline,
                        enabled = true,
                        selected = filterTopic == topic
                    )
                )
            }
        }

        // Message stream
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            reverseLayout = false,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }
            items(displayedMessages, key = { it.id }) { msg ->
                PublicMessageItem(
                    message = msg,
                    onLike = { onLikeMessage(msg.id) },
                    onViewArtwork = onViewArtwork
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Topic selector for new message
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MidnightNavy)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Tema:", fontSize = 11.sp, color = MoonSilverMuted, fontWeight = FontWeight.Bold)
            listOf("Poema del Alma", "Música Nocturna", "Exhibición", "Reflexión Lunar", "Cita Bohemia").forEach { topic ->
                Surface(
                    color = if (selectedTopic == topic) LunarGold.copy(alpha = 0.2f) else MidnightCard,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (selectedTopic == topic) LunarGold else Color.Transparent),
                    modifier = Modifier.clickable { selectedTopic = topic }
                ) {
                    Text(
                        text = topic,
                        fontSize = 11.sp,
                        color = if (selectedTopic == topic) LunarGold else MoonSilverMuted,
                        fontWeight = if (selectedTopic == topic) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Input bar
        Surface(
            color = MidnightNavy,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = "Expresa tu verso o pensamiento artístico...",
                            fontSize = 13.sp,
                            color = MoonSilverMuted
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MidnightSurface,
                        unfocusedContainerColor = MidnightSurface,
                        focusedBorderColor = LunarGold,
                        unfocusedBorderColor = LunarCardOutline,
                        focusedTextColor = MoonSilver,
                        unfocusedTextColor = MoonSilver
                    ),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("public_chat_input"),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val success = onSendMessage(inputText, selectedTopic, null)
                            if (success) {
                                inputText = ""
                            }
                        }
                    },
                    containerColor = LunarGold,
                    contentColor = MidnightObsidian,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("send_public_message_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar al Salón",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PublicMessageItem(
    message: PublicChatMessage,
    onLike: () -> Unit,
    onViewArtwork: (Artwork) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightSurface),
        border = BorderStroke(1.dp, MidnightCardLight),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("public_message_${message.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Sender info & Topic
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MidnightCardLight)
                            .border(1.dp, LunarGold.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(message.senderCountryFlag, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = message.senderName,
                                fontWeight = FontWeight.Bold,
                                color = MoonSilver,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("🛡️", fontSize = 10.sp)
                        }
                        Text(
                            text = "${message.senderDiscipline.emoji} ${message.senderDiscipline.displayName} • ${message.timestamp}",
                            fontSize = 11.sp,
                            color = MoonSilverMuted
                        )
                    }
                }

                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, LunarGold.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = message.topicTag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = LunarGold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Message text
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MoonSilver,
                lineHeight = 21.sp,
                fontFamily = if (message.topicTag.contains("Poema")) FontFamily.Serif else FontFamily.Default,
                fontStyle = if (message.topicTag.contains("Poema")) FontStyle.Italic else FontStyle.Normal
            )

            // Attached artwork preview if present
            message.attachedArtwork?.let { art ->
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, LunarGold.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onViewArtwork(art) }
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(art.discipline.emoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = art.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = LunarGold
                            )
                            Text(
                                text = "Obra adjunta • Toca para abrir",
                                fontSize = 11.sp,
                                color = MoonSilverMuted
                            )
                        }
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = LunarGold, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action row (Heart reactions)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (message.isLikedByMe) RoseLove.copy(alpha = 0.2f) else MidnightCard,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, if (message.isLikedByMe) RoseLove else Color.Transparent),
                    modifier = Modifier.clickable(onClick = onLike)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (message.isLikedByMe) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Me gusta",
                            tint = if (message.isLikedByMe) RoseLove else MoonSilverMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${message.heartsCount} aplausos",
                            fontSize = 11.sp,
                            color = if (message.isLikedByMe) RoseLove else MoonSilverMuted,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Text(
                    text = "🌙 Salón de la Luna",
                    fontSize = 10.sp,
                    color = MoonSilverMuted
                )
            }
        }
    }
}
