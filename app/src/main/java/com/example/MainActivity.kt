package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BatchEngineScreen
import com.example.ui.components.EnrichmentStudioScreen
import com.example.ui.components.PipelineOverviewScreen
import com.example.ui.components.StandardsExplorerScreen
import com.example.ui.components.UnilogBottomNav
import com.example.ui.components.UnilogTopHeader
import com.example.ui.theme.BrandBackground
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSurface
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ProductIntelligenceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ProductIntelligenceApp()
            }
        }
    }
}

@Composable
fun ProductIntelligenceApp(
    viewModel: ProductIntelligenceViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("unilog_main_scaffold"),
        topBar = {
            UnilogTopHeader(
                onTriggerBatch = { viewModel.runBatchSimulation() }
            )
        },
        bottomBar = {
            UnilogBottomNav(
                selectedTab = state.selectedTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = BrandSurface,
                    contentColor = BrandPrimary
                )
            }
        },
        containerColor = BrandBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = state.selectedTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "unilog_tab_transition"
            ) { tabIndex ->
                when (tabIndex) {
                    0 -> PipelineOverviewScreen(state = state, viewModel = viewModel)
                    1 -> EnrichmentStudioScreen(state = state, viewModel = viewModel)
                    2 -> BatchEngineScreen(state = state, viewModel = viewModel)
                    3 -> StandardsExplorerScreen(state = state, viewModel = viewModel)
                }
            }
        }
    }
}
