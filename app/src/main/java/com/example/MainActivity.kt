package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: CreatorPilotViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CreatorPilotTheme {
        CreatorPilotApp(viewModel = viewModel)
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorPilotApp(viewModel: CreatorPilotViewModel) {
  val destination by viewModel.currentDestination.collectAsState()
  val channel by viewModel.currentChannel.collectAsState()
  val user by viewModel.currentUser.collectAsState()

  // Modal dialog states
  val isAnalyzing by viewModel.isAnalyzingChannel.collectAsState()
  val analysisStep by viewModel.analysisStepIndex.collectAsState()
  val showExplain by viewModel.showExplainDialog.collectAsState()
  val showUpgrade by viewModel.showUpgradeDialog.collectAsState()
  val showExport by viewModel.showExportDialog.collectAsState()
  val showAnalyzeChannel by viewModel.showAnalyzeChannelDialog.collectAsState()
  val toastMessage by viewModel.toastMessage.collectAsState()

  val opportunity by viewModel.opportunity.collectAsState()
  val activeScript by viewModel.activeScript.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(toastMessage) {
    toastMessage?.let {
      snackbarHostState.showSnackbar(it)
      viewModel.clearToast()
    }
  }

  // Drawer / More Menu sheet state
  var showMoreSheet by remember { mutableStateOf(false) }

  val showTopAndBottomBars = destination !in listOf(AppDestination.LANDING, AppDestination.AUTH, AppDestination.ONBOARDING)

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      if (showTopAndBottomBars) {
        TopAppBar(
          title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(Brush.linearGradient(listOf(ElectricViolet, NeonPurple))),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = TextWhite, modifier = Modifier.size(18.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "CreatorPilot AI",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
                )
                Text(
                  text = channel?.handle ?: "@AlexCreatesAI",
                  style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted)
                )
              }
            }
          },
          actions = {
            // Plan Pill Button
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = ElectricViolet.copy(alpha = 0.2f),
              border = androidx.compose.foundation.BorderStroke(1.dp, ElectricViolet.copy(alpha = 0.5f)),
              modifier = Modifier
                .clickable { viewModel.setUpgradeDialog(true) }
                .padding(end = 6.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = user.tier,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = NeonPurple)
                )
              }
            }

            // More Hub Menu
            IconButton(onClick = { showMoreSheet = true }) {
              Icon(Icons.Default.Menu, contentDescription = "More Features", tint = TextWhite)
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(containerColor = ObsidianDark)
        )
      }
    },
    bottomBar = {
      if (showTopAndBottomBars) {
        NavigationBar(
          containerColor = ObsidianCard,
          contentColor = TextWhite
        ) {
          NavigationBarItem(
            selected = destination == AppDestination.DASHBOARD,
            onClick = { viewModel.navigateTo(AppDestination.DASHBOARD) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = NeonPurple,
              selectedTextColor = NeonPurple,
              indicatorColor = ElectricViolet.copy(alpha = 0.2f),
              unselectedIconColor = TextGrayMuted,
              unselectedTextColor = TextGrayMuted
            )
          )

          NavigationBarItem(
            selected = destination == AppDestination.IDEAS,
            onClick = { viewModel.navigateTo(AppDestination.IDEAS) },
            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Ideas") },
            label = { Text("Ideas") },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = NeonPurple,
              selectedTextColor = NeonPurple,
              indicatorColor = ElectricViolet.copy(alpha = 0.2f),
              unselectedIconColor = TextGrayMuted,
              unselectedTextColor = TextGrayMuted
            )
          )

          NavigationBarItem(
            selected = destination == AppDestination.SCRIPTS,
            onClick = { viewModel.navigateTo(AppDestination.SCRIPTS) },
            icon = { Icon(Icons.Default.EditNote, contentDescription = "Scripts") },
            label = { Text("Scripts") },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = NeonPurple,
              selectedTextColor = NeonPurple,
              indicatorColor = ElectricViolet.copy(alpha = 0.2f),
              unselectedIconColor = TextGrayMuted,
              unselectedTextColor = TextGrayMuted
            )
          )

          NavigationBarItem(
            selected = destination == AppDestination.STRATEGY,
            onClick = { viewModel.navigateTo(AppDestination.STRATEGY) },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Strategy") },
            label = { Text("Strategy") },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = NeonPurple,
              selectedTextColor = NeonPurple,
              indicatorColor = ElectricViolet.copy(alpha = 0.2f),
              unselectedIconColor = TextGrayMuted,
              unselectedTextColor = TextGrayMuted
            )
          )

          NavigationBarItem(
            selected = destination == AppDestination.ANALYTICS,
            onClick = { viewModel.navigateTo(AppDestination.ANALYTICS) },
            icon = { Icon(Icons.Default.Insights, contentDescription = "Analytics") },
            label = { Text("Analytics") },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = NeonPurple,
              selectedTextColor = NeonPurple,
              indicatorColor = ElectricViolet.copy(alpha = 0.2f),
              unselectedIconColor = TextGrayMuted,
              unselectedTextColor = TextGrayMuted
            )
          )
        }
      }
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(ObsidianDark)
    ) {
      when (destination) {
        AppDestination.LANDING -> LandingScreen(viewModel = viewModel)
        AppDestination.AUTH -> AuthScreen(viewModel = viewModel)
        AppDestination.ONBOARDING -> OnboardingScreen(viewModel = viewModel)
        AppDestination.DASHBOARD -> DashboardScreen(viewModel = viewModel)
        AppDestination.HEALTH_REPORT -> HealthReportScreen(viewModel = viewModel)
        AppDestination.IDEAS -> IdeasScreen(viewModel = viewModel)
        AppDestination.SCRIPTS -> ScriptGeneratorScreen(viewModel = viewModel)
        AppDestination.STRATEGY -> StrategyCalendarScreen(viewModel = viewModel)
        AppDestination.THUMBNAILS -> ThumbnailAnalyzerScreen(viewModel = viewModel)
        AppDestination.ANALYTICS -> AnalyticsScreen(viewModel = viewModel)
        AppDestination.COMPETITORS -> CompetitorScreen(viewModel = viewModel)
        AppDestination.PROJECTS -> ProjectsScreen(viewModel = viewModel)
        AppDestination.PRICING -> PricingScreen(viewModel = viewModel)
        AppDestination.PROFILE -> ProfileScreen(viewModel = viewModel)
      }
    }
  }

  // Multi-step Loading Experience Dialog
  if (isAnalyzing) {
    AnalysisLoadingDialog(
      stepIndex = analysisStep,
      onDismiss = {}
    )
  }

  // Explain Opportunity Dialog
  if (showExplain) {
    ExplainOpportunityDialog(
      opportunity = opportunity,
      onDismiss = { viewModel.setExplainDialog(false) }
    )
  }

  // Upgrade Pro Dialog
  if (showUpgrade) {
    UpgradeProDialog(
      onDismiss = { viewModel.setUpgradeDialog(false) },
      onUpgrade = { tier -> viewModel.selectTier(tier) }
    )
  }

  // Creator Pack Export Dialog
  if (showExport) {
    CreatorPackExportDialog(
      title = activeScript?.title ?: "CreatorPack",
      onDismiss = { viewModel.setExportDialog(false) }
    )
  }

  // Analyze Channel Modal
  if (showAnalyzeChannel) {
    AnalyzeChannelModal(
      onDismiss = { viewModel.setAnalyzeChannelDialog(false) },
      onAnalyze = { url, niche ->
        viewModel.analyzeChannel(url, niche)
      }
    )
  }

  // Hub Features Bottom Sheet (Access Thumbnails, Competitors, Projects, Pricing, Profile, Landing)
  if (showMoreSheet) {
    ModalBottomSheet(
      onDismissRequest = { showMoreSheet = false },
      containerColor = ObsidianCard,
      contentColor = TextWhite
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        Text(
          text = "Creator Studio Modules",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
        )
        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          HubGridButton(
            icon = Icons.Default.PhotoSizeSelectActual,
            title = "Thumbnails",
            subtitle = "AI Audit & Scores",
            onClick = {
              viewModel.navigateTo(AppDestination.THUMBNAILS)
              showMoreSheet = false
            },
            modifier = Modifier.weight(1f)
          )

          HubGridButton(
            icon = Icons.Default.TravelExplore,
            title = "Competitors",
            subtitle = "Gaps & Angles",
            onClick = {
              viewModel.navigateTo(AppDestination.COMPETITORS)
              showMoreSheet = false
            },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          HubGridButton(
            icon = Icons.Default.Folder,
            title = "Saved Projects",
            subtitle = "Packs & Scripts",
            onClick = {
              viewModel.navigateTo(AppDestination.PROJECTS)
              showMoreSheet = false
            },
            modifier = Modifier.weight(1f)
          )

          HubGridButton(
            icon = Icons.Default.HealthAndSafety,
            title = "Health Report",
            subtitle = "Diagnosis & Gaps",
            onClick = {
              viewModel.navigateTo(AppDestination.HEALTH_REPORT)
              showMoreSheet = false
            },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          HubGridButton(
            icon = Icons.Default.Diamond,
            title = "Pricing Plans",
            subtitle = "Free, Pro, Creator",
            onClick = {
              viewModel.navigateTo(AppDestination.PRICING)
              showMoreSheet = false
            },
            modifier = Modifier.weight(1f)
          )

          HubGridButton(
            icon = Icons.Default.Person,
            title = "Settings",
            subtitle = "Profile & Channel",
            onClick = {
              viewModel.navigateTo(AppDestination.PROFILE)
              showMoreSheet = false
            },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
          onClick = {
            viewModel.navigateTo(AppDestination.LANDING)
            showMoreSheet = false
          },
          border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGrayLight),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(Icons.Default.Language, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("View Web Landing Page")
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}

@Composable
fun HubGridButton(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .clickable { onClick() }
      .border(1.dp, ObsidianCardBorder, RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianElevated)
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(ElectricViolet.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite))
        Text(subtitle, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
      }
    }
  }
}
