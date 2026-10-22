package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.ScreenTab
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.MoonDatingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MoonAppRoot()
            }
        }
    }
}

@Composable
fun MoonAppRoot(viewModel: MoonDatingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle back button when sub-screen or conversation is open
    BackHandler(enabled = uiState.activeConversationId != null || uiState.currentTab != ScreenTab.DISCOVERY) {
        if (uiState.activeConversationId != null) {
            viewModel.closeActiveConversation()
        } else {
            viewModel.selectTab(ScreenTab.DISCOVERY)
        }
    }

    Scaffold(
        topBar = {
            LunarTopBar(
                onOpenRules = { viewModel.showRules(true) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MidnightNavy,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("main_bottom_nav")
            ) {
                // Tab 1: Galería & Citas (Discovery)
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.DISCOVERY,
                    onClick = { viewModel.selectTab(ScreenTab.DISCOVERY) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == ScreenTab.DISCOVERY) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Citas & Arte",
                            tint = if (uiState.currentTab == ScreenTab.DISCOVERY) RoseLove else MoonSilverMuted
                        )
                    },
                    label = {
                        Text(
                            text = "Citas",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == ScreenTab.DISCOVERY) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = RoseLove,
                        selectedTextColor = LunarGold,
                        unselectedIconColor = MoonSilverMuted,
                        unselectedTextColor = MoonSilverMuted,
                        indicatorColor = MidnightCardLight
                    ),
                    modifier = Modifier.testTag("nav_tab_discovery")
                )

                // Tab 2: Salón Público (Public Chat)
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.PUBLIC_LOUNGE,
                    onClick = { viewModel.selectTab(ScreenTab.PUBLIC_LOUNGE) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == ScreenTab.PUBLIC_LOUNGE) Icons.Filled.Forum else Icons.Outlined.Forum,
                            contentDescription = "Salón de la Luna",
                            tint = if (uiState.currentTab == ScreenTab.PUBLIC_LOUNGE) LunarGold else MoonSilverMuted
                        )
                    },
                    label = {
                        Text(
                            text = "Salón",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == ScreenTab.PUBLIC_LOUNGE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LunarGold,
                        selectedTextColor = LunarGold,
                        unselectedIconColor = MoonSilverMuted,
                        unselectedTextColor = MoonSilverMuted,
                        indicatorColor = MidnightCardLight
                    ),
                    modifier = Modifier.testTag("nav_tab_public_lounge")
                )

                // Tab 3: Google Maps & Radar
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.MAP_RADAR,
                    onClick = { viewModel.selectTab(ScreenTab.MAP_RADAR) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == ScreenTab.MAP_RADAR) Icons.Filled.LocationOn else Icons.Outlined.LocationOn,
                            contentDescription = "Mapa Google",
                            tint = if (uiState.currentTab == ScreenTab.MAP_RADAR) LunarGold else MoonSilverMuted
                        )
                    },
                    label = {
                        Text(
                            text = "Mapa",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == ScreenTab.MAP_RADAR) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LunarGold,
                        selectedTextColor = LunarGold,
                        unselectedIconColor = MoonSilverMuted,
                        unselectedTextColor = MoonSilverMuted,
                        indicatorColor = MidnightCardLight
                    ),
                    modifier = Modifier.testTag("nav_tab_map_radar")
                )

                // Tab 4: Santuario Privado (Private Chats)
                val totalUnread = uiState.privateConversations.sumOf { it.unreadCount }
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.PRIVATE_CHATS,
                    onClick = { viewModel.selectTab(ScreenTab.PRIVATE_CHATS) },
                    icon = {
                        BadgedBox(badge = {
                            if (totalUnread > 0) {
                                Badge(containerColor = RoseLove) {
                                    Text(totalUnread.toString(), color = Color.White)
                                }
                            }
                        }) {
                            Icon(
                                imageVector = if (uiState.currentTab == ScreenTab.PRIVATE_CHATS) Icons.Filled.Lock else Icons.Outlined.Lock,
                                contentDescription = "Chats Privados",
                                tint = if (uiState.currentTab == ScreenTab.PRIVATE_CHATS) RoseLoveSoft else MoonSilverMuted
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Privados",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == ScreenTab.PRIVATE_CHATS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = RoseLoveSoft,
                        selectedTextColor = LunarGold,
                        unselectedIconColor = MoonSilverMuted,
                        unselectedTextColor = MoonSilverMuted,
                        indicatorColor = MidnightCardLight
                    ),
                    modifier = Modifier.testTag("nav_tab_private_chats")
                )

                // Tab 5: Mi Perfil (Profile)
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.PROFILE,
                    onClick = { viewModel.selectTab(ScreenTab.PROFILE) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == ScreenTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Mi Perfil",
                            tint = if (uiState.currentTab == ScreenTab.PROFILE) LunarGold else MoonSilverMuted
                        )
                    },
                    label = {
                        Text(
                            text = "Perfil",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == ScreenTab.PROFILE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LunarGold,
                        selectedTextColor = LunarGold,
                        unselectedIconColor = MoonSilverMuted,
                        unselectedTextColor = MoonSilverMuted,
                        indicatorColor = MidnightCardLight
                    ),
                    modifier = Modifier.testTag("nav_tab_profile")
                )
            }
        },
        containerColor = MidnightObsidian,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                ScreenTab.DISCOVERY -> {
                    DiscoveryScreen(
                        artists = viewModel.getFilteredArtists(),
                        currentIndex = uiState.currentArtistIndex,
                        activeFilter = uiState.activeDisciplineFilter,
                        onFilterSelect = { viewModel.setDisciplineFilter(it) },
                        onLike = { viewModel.likeArtist(it) },
                        onPass = { viewModel.nextArtist() },
                        onViewArtwork = { viewModel.viewArtwork(it) }
                    )
                }

                ScreenTab.PUBLIC_LOUNGE -> {
                    PublicLoungeScreen(
                        messages = uiState.publicMessages,
                        onSendMessage = { text, topic, art -> viewModel.sendPublicMessage(text, topic, art) },
                        onLikeMessage = { viewModel.toggleLikePublicMessage(it) },
                        onViewArtwork = { viewModel.viewArtwork(it) },
                        onOpenRules = { viewModel.showRules(true) }
                    )
                }

                ScreenTab.MAP_RADAR -> {
                    MapRadarScreen(
                        artists = uiState.artists,
                        spots = uiState.romanticSpots,
                        radiusKm = uiState.radarRadiusKm,
                        isShowingArtists = uiState.isMapShowingArtists,
                        selectedArtist = uiState.selectedArtistForMap,
                        selectedSpot = uiState.selectedSpotForMap,
                        onRadiusChange = { viewModel.setRadarRadius(it) },
                        onToggleCategory = { viewModel.toggleMapCategory(it) },
                        onSelectArtist = { viewModel.selectArtistForMap(it) },
                        onSelectSpot = { viewModel.selectSpotForMap(it) },
                        onOpenChatWithArtist = { viewModel.openChatWithMatchedArtist(it) }
                    )
                }

                ScreenTab.PRIVATE_CHATS -> {
                    PrivateChatsScreen(
                        conversations = uiState.privateConversations,
                        activeConversationId = uiState.activeConversationId,
                        onOpenConversation = { viewModel.openConversation(it) },
                        onCloseConversation = { viewModel.closeActiveConversation() },
                        onSendMessage = { convId, text -> viewModel.sendPrivateMessage(convId, text) }
                    )
                }

                ScreenTab.PROFILE -> {
                    ProfileScreen(
                        currentUser = uiState.currentUser,
                        onUpdateProfile = { fn, ln, c, flag, age, bio, q, md, sd, soc, realPhoto ->
                            viewModel.updateUserProfile(fn, ln, c, flag, age, bio, q, md, sd, soc, realPhoto)
                        },
                        onAddArtwork = { viewModel.addArtworkToCurrentUser(it) },
                        onViewArtwork = { viewModel.viewArtwork(it) },
                        onOpenRules = { viewModel.showRules(true) }
                    )
                }
            }

            // Dialogs
            if (uiState.showRulesDialog) {
                CommunityRulesDialog(onDismiss = { viewModel.showRules(false) })
            }

            uiState.matchCelebrationArtist?.let { artist ->
                MatchCelebrationDialog(
                    matchedArtist = artist,
                    currentUser = uiState.currentUser,
                    onOpenChat = { viewModel.openChatWithMatchedArtist(it) },
                    onDismiss = { viewModel.dismissMatchCelebration() }
                )
            }

            uiState.selectedArtworkModal?.let { artwork ->
                ArtworkViewerDialog(
                    artwork = artwork,
                    onDismiss = { viewModel.viewArtwork(null) }
                )
            }

            uiState.publicChatRuleWarning?.let { warning ->
                PublicRuleWarningDialog(
                    warningMessage = warning,
                    onDismiss = { viewModel.dismissPublicWarning() }
                )
            }
        }
    }
}
