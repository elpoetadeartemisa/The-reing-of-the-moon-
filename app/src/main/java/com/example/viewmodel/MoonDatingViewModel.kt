package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MoonUiState(
    val currentTab: ScreenTab = ScreenTab.DISCOVERY,
    val artists: List<ArtistProfile> = SampleData.ARTISTS,
    val currentArtistIndex: Int = 0,
    val activeDisciplineFilter: ArtDiscipline? = null,
    val matchCelebrationArtist: ArtistProfile? = null,
    val selectedArtworkModal: Artwork? = null,
    val showRulesDialog: Boolean = false,
    val publicMessages: List<PublicChatMessage> = SampleData.INITIAL_PUBLIC_MESSAGES,
    val publicChatRuleWarning: String? = null,
    val privateConversations: List<PrivateChatConversation> = SampleData.INITIAL_PRIVATE_CONVERSATIONS,
    val activeConversationId: String? = null,
    val currentUser: ArtistProfile = SampleData.CURRENT_USER,
    val radarRadiusKm: Float = 10f,
    val selectedSpotForMap: RomanticSpot? = null,
    val selectedArtistForMap: ArtistProfile? = null,
    val romanticSpots: List<RomanticSpot> = SampleData.ROMANTIC_SPOTS,
    val isMapShowingArtists: Boolean = true // toggle between artists & romantic rendezvous spots
)

class MoonDatingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MoonUiState())
    val uiState: StateFlow<MoonUiState> = _uiState.asStateFlow()

    fun selectTab(tab: ScreenTab) {
        _uiState.update { it.copy(currentTab = tab, activeConversationId = null) }
    }

    fun setDisciplineFilter(discipline: ArtDiscipline?) {
        _uiState.update { it.copy(activeDisciplineFilter = discipline, currentArtistIndex = 0) }
    }

    fun getFilteredArtists(): List<ArtistProfile> {
        val filter = _uiState.value.activeDisciplineFilter
        return if (filter == null) {
            _uiState.value.artists
        } else {
            _uiState.value.artists.filter {
                it.mainDiscipline == filter || it.secondaryDiscipline == filter
            }
        }
    }

    fun nextArtist() {
        val list = getFilteredArtists()
        if (list.isNotEmpty()) {
            _uiState.update {
                it.copy(currentArtistIndex = (it.currentArtistIndex + 1) % list.size)
            }
        }
    }

    fun prevArtist() {
        val list = getFilteredArtists()
        if (list.isNotEmpty()) {
            _uiState.update {
                val newIndex = if (it.currentArtistIndex - 1 < 0) list.size - 1 else it.currentArtistIndex - 1
                it.copy(currentArtistIndex = newIndex)
            }
        }
    }

    fun likeArtist(artist: ArtistProfile) {
        // Trigger match celebration
        val updatedArtists = _uiState.value.artists.map {
            if (it.id == artist.id) it.copy(isLiked = true, isMatched = true) else it
        }

        // Also ensure a private conversation exists for this matched artist
        val currentConvs = _uiState.value.privateConversations.toMutableList()
        val existingIndex = currentConvs.indexOfFirst { it.artist.id == artist.id }
        if (existingIndex < 0) {
            val newConv = PrivateChatConversation(
                id = "conv_${artist.id}",
                artist = artist.copy(isLiked = true, isMatched = true),
                lastMessage = "¡Ambos habéis coincidido bajo la luna! Empieza a chatear...",
                lastMessageTime = "Ahora",
                unreadCount = 1,
                messages = listOf(
                    PrivateMessage(
                        id = "pm_init_${System.currentTimeMillis()}",
                        senderId = artist.id,
                        text = "¡Hola! Vi tus obras de arte y me cautivó tu sensibilidad artística. ¿Cuál es la inspiración detrás de tu última creación?",
                        timestamp = "Ahora",
                        isFromMe = false
                    )
                )
            )
            currentConvs.add(0, newConv)
        }

        _uiState.update {
            it.copy(
                artists = updatedArtists,
                privateConversations = currentConvs,
                matchCelebrationArtist = artist
            )
        }
    }

    fun dismissMatchCelebration() {
        _uiState.update { it.copy(matchCelebrationArtist = null) }
        nextArtist()
    }

    fun openChatWithMatchedArtist(artist: ArtistProfile) {
        _uiState.update {
            it.copy(
                matchCelebrationArtist = null,
                currentTab = ScreenTab.PRIVATE_CHATS,
                activeConversationId = "conv_${artist.id}"
            )
        }
    }

    fun showRules(show: Boolean) {
        _uiState.update { it.copy(showRulesDialog = show) }
    }

    fun viewArtwork(artwork: Artwork?) {
        _uiState.update { it.copy(selectedArtworkModal = artwork) }
    }

    fun sendPublicMessage(text: String, topic: String, attachedArtwork: Artwork? = null): Boolean {
        if (text.isBlank()) return false

        // Moderation validation against Rule #1
        val (isValid, errorMessage) = AppRulesData.validatePublicChatContent(text)
        if (!isValid) {
            _uiState.update { it.copy(publicChatRuleWarning = errorMessage) }
            return false
        }

        val currentUser = _uiState.value.currentUser
        val newMsg = PublicChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderId = currentUser.id,
            senderName = currentUser.fullName,
            senderCountryFlag = currentUser.countryFlag,
            senderDiscipline = currentUser.mainDiscipline,
            text = text.trim(),
            timestamp = "Justo ahora",
            topicTag = topic,
            heartsCount = 1,
            isLikedByMe = true,
            attachedArtwork = attachedArtwork
        )

        _uiState.update {
            it.copy(
                publicMessages = listOf(newMsg) + it.publicMessages,
                publicChatRuleWarning = null
            )
        }
        return true
    }

    fun dismissPublicWarning() {
        _uiState.update { it.copy(publicChatRuleWarning = null) }
    }

    fun toggleLikePublicMessage(messageId: String) {
        _uiState.update { state ->
            val updated = state.publicMessages.map { msg ->
                if (msg.id == messageId) {
                    val newLiked = !msg.isLikedByMe
                    val count = if (newLiked) msg.heartsCount + 1 else maxOf(0, msg.heartsCount - 1)
                    msg.copy(isLikedByMe = newLiked, heartsCount = count)
                } else msg
            }
            state.copy(publicMessages = updated)
        }
    }

    fun openConversation(conversationId: String) {
        _uiState.update { state ->
            val updatedConvs = state.privateConversations.map {
                if (it.id == conversationId) it.copy(unreadCount = 0) else it
            }
            state.copy(
                privateConversations = updatedConvs,
                activeConversationId = conversationId
            )
        }
    }

    fun closeActiveConversation() {
        _uiState.update { it.copy(activeConversationId = null) }
    }

    fun sendPrivateMessage(conversationId: String, text: String) {
        if (text.isBlank()) return
        val newMsg = PrivateMessage(
            id = "pm_${System.currentTimeMillis()}",
            senderId = _uiState.value.currentUser.id,
            text = text.trim(),
            timestamp = "Ahora",
            isFromMe = true
        )

        _uiState.update { state ->
            val updatedConvs = state.privateConversations.map { conv ->
                if (conv.id == conversationId) {
                    conv.copy(
                        lastMessage = text.trim(),
                        lastMessageTime = "Ahora",
                        messages = conv.messages + newMsg
                    )
                } else conv
            }
            state.copy(privateConversations = updatedConvs)
        }
    }

    fun updateUserProfile(
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
    ) {
        _uiState.update { state ->
            val updated = state.currentUser.copy(
                firstName = firstName,
                lastName = lastName,
                country = country,
                countryFlag = countryFlag,
                age = age,
                bio = bio,
                romanticQuote = quote,
                mainDiscipline = mainDiscipline,
                secondaryDiscipline = secondaryDiscipline,
                socialLinks = socials,
                isRealPhotoVerified = isRealPhoto,
                noAiCertified = isRealPhoto
            )
            state.copy(currentUser = updated)
        }
    }

    fun addArtworkToCurrentUser(artwork: Artwork) {
        _uiState.update { state ->
            val updatedUser = state.currentUser.copy(
                portfolio = state.currentUser.portfolio + artwork
            )
            state.copy(currentUser = updatedUser)
        }
    }

    fun setRadarRadius(km: Float) {
        _uiState.update { it.copy(radarRadiusKm = km) }
    }

    fun toggleMapCategory(showArtists: Boolean) {
        _uiState.update {
            it.copy(
                isMapShowingArtists = showArtists,
                selectedArtistForMap = null,
                selectedSpotForMap = null
            )
        }
    }

    fun selectSpotForMap(spot: RomanticSpot?) {
        _uiState.update { it.copy(selectedSpotForMap = spot, selectedArtistForMap = null) }
    }

    fun selectArtistForMap(artist: ArtistProfile?) {
        _uiState.update { it.copy(selectedArtistForMap = artist, selectedSpotForMap = null) }
    }
}
