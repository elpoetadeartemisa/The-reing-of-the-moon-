package com.example.model

enum class ArtDiscipline(val displayName: String, val emoji: String, val description: String) {
    PINTURA("Pintura", "🎨", "Óleo, Acrílico, Acuarela, Murales"),
    ESCRITOS("Escritos & Poesía", "🖋️", "Poesía, Novela, Relatos, Prosa Poética"),
    MUSICA("Música & Acústica", "🎵", "Composición, Guitarra, Cello, Voz"),
    ESCULTURA("Escultura & Cerámica", "🗿", "Mármol, Arcilla, Fundición, Relieve"),
    FOTOGRAFIA("Fotografía", "📷", "Analógica, Retrato, Nocturna, Paisaje"),
    ARTESANIAS("Artesanías & Joyería", "✨", "Filigrana, Orfebrería, Vidrio Soplado")
}

data class Artwork(
    val id: String,
    val title: String,
    val discipline: ArtDiscipline,
    val description: String,
    val poetryVerses: String? = null,
    val audioTitle: String? = null,
    val audioDuration: String? = null,
    val year: String = "2024",
    val technique: String = "Técnica Mixta",
    val likesCount: Int = 0,
    val isLiked: Boolean = false
)

data class SocialLinks(
    val instagram: String? = null,
    val spotify: String? = null,
    val soundcloud: String? = null,
    val tiktok: String? = null,
    val behance: String? = null,
    val twitter: String? = null,
    val portfolioUrl: String? = null
)

data class ArtistProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val countryFlag: String,
    val age: Int,
    val bio: String,
    val mainDiscipline: ArtDiscipline,
    val secondaryDiscipline: ArtDiscipline? = null,
    val isRealPhotoVerified: Boolean = true,
    val noAiCertified: Boolean = true,
    val portfolio: List<Artwork> = emptyList(),
    val socialLinks: SocialLinks = SocialLinks(),
    val city: String = "Madrid",
    val latitude: Double = 40.4168,
    val longitude: Double = -3.7038,
    val distanceKm: Double = 3.5,
    val isLiked: Boolean = false,
    val isMatched: Boolean = false,
    val romanticQuote: String = ""
) {
    val fullName: String get() = "$firstName $lastName"
}

data class PublicChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val senderCountryFlag: String,
    val senderDiscipline: ArtDiscipline,
    val text: String,
    val timestamp: String,
    val attachedArtwork: Artwork? = null,
    val topicTag: String = "Poema del Alma",
    val heartsCount: Int = 0,
    val isLikedByMe: Boolean = false
)

data class PrivateMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean
)

data class PrivateChatConversation(
    val id: String,
    val artist: ArtistProfile,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val messages: List<PrivateMessage> = emptyList()
)

data class RomanticSpot(
    val id: String,
    val name: String,
    val category: String,
    val address: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val distanceKm: Double,
    val romanticDescription: String,
    val vibe: String
)

enum class ScreenTab {
    DISCOVERY,
    PUBLIC_LOUNGE,
    MAP_RADAR,
    PRIVATE_CHATS,
    PROFILE
}
