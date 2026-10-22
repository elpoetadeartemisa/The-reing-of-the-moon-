package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PrivateChatConversation
import com.example.model.PrivateMessage
import com.example.ui.theme.*

@Composable
fun PrivateChatsScreen(
    conversations: List<PrivateChatConversation>,
    activeConversationId: String?,
    onOpenConversation: (String) -> Unit,
    onCloseConversation: () -> Unit,
    onSendMessage: (conversationId: String, text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (activeConversationId != null) {
        val conversation = conversations.find { it.id == activeConversationId }
        if (conversation != null) {
            BackHandler {
                onCloseConversation()
            }
            ConversationDetailView(
                conversation = conversation,
                onBack = onCloseConversation,
                onSendMessage = { text -> onSendMessage(conversation.id, text) },
                modifier = modifier
            )
            return
        }
    }

    // Conversations list view
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightObsidian)
    ) {
        // Header info banner reminding of the App's Rule #2
        Surface(
            color = MidnightCard,
            border = BorderStroke(1.dp, RoseLoveSoft.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🔐", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "SANTUARIO PRIVADO • REGLA #2",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = RoseLoveSoft,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Conversaciones 1 a 1 confidenciales. La intimidad, erotismo y pasión pertenecen a este espacio seguro con consentimiento.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoonSilver,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Text(
            text = "Conexiones & Afinidades (${conversations.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = LunarGold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )

        if (conversations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌕", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aún no tienes conexiones privadas",
                        style = MaterialTheme.typography.titleMedium,
                        color = MoonSilver,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Da 'Me apasiona' en la galería a los artistas que te inspiren para iniciar una conversación íntima.",
                        color = MoonSilverMuted,
                        fontSize = 13.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(conversations, key = { it.id }) { conv ->
                    ConversationItemCard(
                        conversation = conv,
                        onClick = { onOpenConversation(conv.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ConversationItemCard(
    conversation: PrivateChatConversation,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightSurface),
        border = BorderStroke(1.dp, MidnightCardLight),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("conversation_item_${conversation.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Artist Avatar
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MidnightCardLight)
                    .border(1.5.dp, LunarGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(conversation.artist.countryFlag, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = conversation.artist.fullName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MoonSilver
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("🛡️", fontSize = 10.sp)
                    }
                    Text(
                        text = conversation.lastMessageTime,
                        fontSize = 11.sp,
                        color = MoonSilverMuted
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${conversation.artist.mainDiscipline.emoji} ${conversation.artist.mainDiscipline.displayName}",
                    fontSize = 11.sp,
                    color = LunarGold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = conversation.lastMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (conversation.unreadCount > 0) MoonSilver else MoonSilverMuted,
                    fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            }

            if (conversation.unreadCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(RoseLove),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = conversation.unreadCount.toString(),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ConversationDetailView(
    conversation: PrivateChatConversation,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    val romanticIcebreakers = listOf(
        "¿Qué música inspiró tu última obra?",
        "Léeme tu verso favorito bajo la luna...",
        "¿Tomamos un vino en el café bohemio?",
        "Tus ojos tienen la luz de una noche despejada."
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightObsidian)
    ) {
        // Conversation Top Bar
        Surface(
            color = MidnightNavy,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = MoonSilver)
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MidnightCardLight)
                        .border(1.5.dp, LunarGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(conversation.artist.countryFlag, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = conversation.artist.fullName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MoonSilver
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("🛡️", fontSize = 10.sp)
                    }
                    Text(
                        text = "En línea bajo la luna • ${conversation.artist.city}",
                        style = MaterialTheme.typography.labelSmall,
                        color = RealPhotoGreen
                    )
                }
            }
        }

        // Intimacy safe space micro-banner
        Surface(
            color = MidnightCard.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = RoseLoveSoft, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Chat Privado: Se permite intimidad consentida y confidencial.",
                    fontSize = 11.sp,
                    color = RoseLoveSoft,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(conversation.messages, key = { it.id }) { msg ->
                PrivateMessageBubble(msg = msg)
            }
        }

        // Icebreakers suggestion chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            romanticIcebreakers.forEach { ice ->
                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, LunarOutline),
                    modifier = Modifier.clickable { inputText = ice }
                ) {
                    Text(
                        text = ice,
                        fontSize = 11.sp,
                        color = LunarGold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Text input bar
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
                        Text("Escribe un mensaje íntimo o poético...", fontSize = 13.sp, color = MoonSilverMuted)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MidnightSurface,
                        unfocusedContainerColor = MidnightSurface,
                        focusedBorderColor = RoseLove,
                        unfocusedBorderColor = LunarCardOutline,
                        focusedTextColor = MoonSilver,
                        unfocusedTextColor = MoonSilver
                    ),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("private_message_input"),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                    containerColor = RoseLove,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("send_private_message_button")
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar mensaje privado", modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun PrivateMessageBubble(msg: PrivateMessage) {
    val isMe = msg.isFromMe

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (isMe) MidnightCardLight else MidnightSurface,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isMe) 16.dp else 4.dp,
                bottomEnd = if (isMe) 4.dp else 16.dp
            ),
            border = BorderStroke(1.dp, if (isMe) RoseLove.copy(alpha = 0.5f) else LunarOutline),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = msg.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoonSilver,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = msg.timestamp,
                    fontSize = 10.sp,
                    color = MoonSilverMuted,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
