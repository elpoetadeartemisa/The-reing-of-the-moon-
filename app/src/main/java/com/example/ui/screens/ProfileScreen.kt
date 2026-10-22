package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.*
import com.example.ui.components.SocialLinksRow
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    currentUser: ArtistProfile,
    onUpdateProfile: (
        firstName: String,
        lastName: String,
        country: String,
        countryFlag: String,
        age: Int,
        bio: String,
        quote: String,
        mainDiscipline: ArtDiscipline,
        secondaryDiscipline: ArtDiscipline?,
        socials: SocialLinks,
        isRealPhoto: Boolean
    ) -> Unit,
    onAddArtwork: (Artwork) -> Unit,
    onViewArtwork: (Artwork) -> Unit,
    onOpenRules: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var showAddArtworkDialog by remember { mutableStateOf(false) }

    // Edit states
    var editFirstName by remember(currentUser) { mutableStateOf(currentUser.firstName) }
    var editLastName by remember(currentUser) { mutableStateOf(currentUser.lastName) }
    var editCountry by remember(currentUser) { mutableStateOf(currentUser.country) }
    var editCountryFlag by remember(currentUser) { mutableStateOf(currentUser.countryFlag) }
    var editAge by remember(currentUser) { mutableIntStateOf(currentUser.age) }
    var editBio by remember(currentUser) { mutableStateOf(currentUser.bio) }
    var editQuote by remember(currentUser) { mutableStateOf(currentUser.romanticQuote) }
    var editMainDiscipline by remember(currentUser) { mutableStateOf(currentUser.mainDiscipline) }
    var editIsRealPhoto by remember(currentUser) { mutableStateOf(currentUser.isRealPhotoVerified) }

    // Socials
    var editInstagram by remember(currentUser) { mutableStateOf(currentUser.socialLinks.instagram ?: "") }
    var editSpotify by remember(currentUser) { mutableStateOf(currentUser.socialLinks.spotify ?: "") }
    var editSoundCloud by remember(currentUser) { mutableStateOf(currentUser.socialLinks.soundcloud ?: "") }
    var editBehance by remember(currentUser) { mutableStateOf(currentUser.socialLinks.behance ?: "") }
    var editTwitter by remember(currentUser) { mutableStateOf(currentUser.socialLinks.twitter ?: "") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightObsidian)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Profile Header Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(LunarGold.copy(alpha = 0.5f), MidnightCardLight))),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Photo Avatar
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(listOf(MidnightCardLight, MidnightObsidian))
                        )
                        .border(3.dp, LunarGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(currentUser.countryFlag, fontSize = 48.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Name & Age
                Text(
                    text = "${currentUser.fullName}, ${currentUser.age}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MoonSilver
                )
                Text(
                    text = "${currentUser.countryFlag} ${currentUser.country} • ${currentUser.city}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoonSilverMuted
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Mandatory Real Photo / No IA badge
                Surface(
                    color = if (currentUser.isRealPhotoVerified) RealPhotoGreen.copy(alpha = 0.15f) else WarningAmber.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, if (currentUser.isRealPhotoVerified) RealPhotoGreen else WarningAmber)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (currentUser.isRealPhotoVerified) "🛡️" else "⚠️", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentUser.isRealPhotoVerified) "Foto Real Certificada • Tolerancia Cero a IA" else "Pendiente Verificación Real",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (currentUser.isRealPhotoVerified) RealPhotoGreen else WarningAmber
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Discipline
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = MidnightCard,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, LunarOutline)
                    ) {
                        Text(
                            text = "${currentUser.mainDiscipline.emoji} ${currentUser.mainDiscipline.displayName}",
                            color = LunarGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Romantic quote
                Surface(
                    color = MidnightCard,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "«${currentUser.romanticQuote}»",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoonSilverMuted,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Edit Button
                OutlinedButton(
                    onClick = { isEditing = !isEditing },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = LunarGold),
                    border = BorderStroke(1.dp, LunarGold),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("edit_profile_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Default.Close else Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isEditing) "Cancelar Edición" else "Editar Perfil & Redes Sociales")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // If editing is active, show the form
        if (isEditing) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MidnightSurface),
                border = BorderStroke(1.dp, LunarGold.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "EDITAR DATOS DE PERFIL",
                        fontWeight = FontWeight.Bold,
                        color = LunarGold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = editFirstName,
                        onValueChange = { editFirstName = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editLastName,
                        onValueChange = { editLastName = it },
                        label = { Text("Apellido") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = editCountry,
                            onValueChange = { editCountry = it },
                            label = { Text("País") },
                            modifier = Modifier.weight(2f),
                            colors = outlinedColors()
                        )
                        OutlinedTextField(
                            value = editCountryFlag,
                            onValueChange = { editCountryFlag = it },
                            label = { Text("Bandera") },
                            modifier = Modifier.weight(1f),
                            colors = outlinedColors()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editAge.toString(),
                        onValueChange = { it.toIntOrNull()?.let { a -> editAge = a } },
                        label = { Text("Edad (Años)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editBio,
                        onValueChange = { editBio = it },
                        label = { Text("Biografía Artística & Romántica") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4,
                        colors = outlinedColors()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editQuote,
                        onValueChange = { editQuote = it },
                        label = { Text("Frase / Declaración Romántica") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Redes Sociales Integradas:", fontWeight = FontWeight.Bold, color = LunarGold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editInstagram,
                        onValueChange = { editInstagram = it },
                        label = { Text("Instagram (sin @)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editSpotify,
                        onValueChange = { editSpotify = it },
                        label = { Text("Spotify (usuario o ID de artista)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editBehance,
                        onValueChange = { editBehance = it },
                        label = { Text("Behance / Portafolio") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Real Photo certification checkbox (Requested rule)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { editIsRealPhoto = !editIsRealPhoto }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = editIsRealPhoto,
                            onCheckedChange = { editIsRealPhoto = it },
                            colors = CheckboxDefaults.colors(checkedColor = RealPhotoGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Certifico que mi foto de perfil es 100% REAL y NO fue generada por Inteligencia Artificial (Acepto la regla de expulsión si es IA).",
                            style = MaterialTheme.typography.bodySmall,
                            color = MoonSilver,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onUpdateProfile(
                                editFirstName,
                                editLastName,
                                editCountry,
                                editCountryFlag,
                                editAge,
                                editBio,
                                editQuote,
                                editMainDiscipline,
                                currentUser.secondaryDiscipline,
                                SocialLinks(
                                    instagram = editInstagram.ifBlank { null },
                                    spotify = editSpotify.ifBlank { null },
                                    soundcloud = editSoundCloud.ifBlank { null },
                                    behance = editBehance.ifBlank { null },
                                    twitter = editTwitter.ifBlank { null }
                                ),
                                editIsRealPhoto
                            )
                            isEditing = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = LunarGold, contentColor = MidnightObsidian),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("save_profile_button")
                    ) {
                        Text("Guardar Cambios de Perfil", fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Social networks section (View mode)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = BorderStroke(1.dp, MidnightCardLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Mis Redes Sociales Conectadas",
                    fontWeight = FontWeight.Bold,
                    color = LunarGold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                SocialLinksRow(socialLinks = currentUser.socialLinks)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // My Artworks section
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = BorderStroke(1.dp, MidnightCardLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mis Obras Exhibidas (${currentUser.portfolio.size})",
                        fontWeight = FontWeight.Bold,
                        color = MoonSilver,
                        fontSize = 14.sp
                    )

                    Button(
                        onClick = { showAddArtworkDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MidnightCardLight, contentColor = LunarGold),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("add_artwork_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Añadir Obra", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(currentUser.portfolio) { art ->
                        ArtworkPreviewCard(artwork = art, onClick = { onViewArtwork(art) })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Community Rules footer button
        Surface(
            color = MidnightCard,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, WarningAmber.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onOpenRules)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📜", fontSize = 22.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Código de Honor & Reglas Lunares",
                        fontWeight = FontWeight.Bold,
                        color = LunarGold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "No sexo en público • Intimidad en privado • Solo fotos reales",
                        color = MoonSilverMuted,
                        fontSize = 11.sp
                    )
                }
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = LunarGold, modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Add Artwork Dialog
    if (showAddArtworkDialog) {
        AddArtworkModalDialog(
            onDismiss = { showAddArtworkDialog = false },
            onSave = { newArt ->
                onAddArtwork(newArt)
                showAddArtworkDialog = false
            }
        )
    }
}

@Composable
fun AddArtworkModalDialog(
    onDismiss: () -> Unit,
    onSave: (Artwork) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var discipline by remember { mutableStateOf(ArtDiscipline.PINTURA) }
    var description by remember { mutableStateOf("") }
    var technique by remember { mutableStateOf("Óleo sobre lienzo") }
    var poemText by remember { mutableStateOf("") }
    var audioTitle by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MidnightSurface),
            border = BorderStroke(1.5.dp, LunarGold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "EXHIBIR NUEVA OBRA DE ARTE",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = LunarGold
                )
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título de la Obra") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedColors()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Disciplina Artística:", fontSize = 12.sp, color = MoonSilver, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(ArtDiscipline.PINTURA, ArtDiscipline.ESCRITOS, ArtDiscipline.MUSICA, ArtDiscipline.ESCULTURA).forEach { d ->
                        Surface(
                            color = if (discipline == d) LunarGold else MidnightCard,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.clickable { discipline = d }
                        ) {
                            Text(
                                text = d.emoji,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = technique,
                    onValueChange = { technique = it },
                    label = { Text("Técnica / Instrumento / Estilo") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedColors()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Historia & Sentimiento Romántico") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = outlinedColors()
                )

                if (discipline == ArtDiscipline.ESCRITOS) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = poemText,
                        onValueChange = { poemText = it },
                        label = { Text("Versos del Poema o Prosa") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4,
                        colors = outlinedColors()
                    )
                }

                if (discipline == ArtDiscipline.MUSICA) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = audioTitle,
                        onValueChange = { audioTitle = it },
                        label = { Text("Título de la Pieza Acústica") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = MoonSilverMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                onSave(
                                    Artwork(
                                        id = "art_user_${System.currentTimeMillis()}",
                                        title = title.trim(),
                                        discipline = discipline,
                                        description = description.trim(),
                                        technique = technique.trim(),
                                        poetryVerses = if (discipline == ArtDiscipline.ESCRITOS) poemText.trim() else null,
                                        audioTitle = if (discipline == ArtDiscipline.MUSICA) audioTitle.trim() else null,
                                        year = "2024"
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = LunarGold, contentColor = MidnightObsidian),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Publicar en mi Galería", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun outlinedColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MidnightCard,
    unfocusedContainerColor = MidnightCard,
    focusedBorderColor = LunarGold,
    unfocusedBorderColor = LunarCardOutline,
    focusedTextColor = MoonSilver,
    unfocusedTextColor = MoonSilver,
    focusedLabelColor = LunarGold,
    unfocusedLabelColor = MoonSilverMuted
)
