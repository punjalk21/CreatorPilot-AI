package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ProjectEntity
import com.example.data.model.*
import com.example.data.repository.CreatorPilotRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppDestination(val label: String, val route: String) {
  LANDING("Landing", "landing"),
  AUTH("Log In", "auth"),
  ONBOARDING("Onboarding", "onboarding"),
  DASHBOARD("Dashboard", "dashboard"),
  HEALTH_REPORT("Health Report", "health_report"),
  IDEAS("Ideas", "ideas"),
  SCRIPTS("Scripts", "scripts"),
  STRATEGY("Strategy", "strategy"),
  THUMBNAILS("Thumbnails", "thumbnails"),
  ANALYTICS("Analytics", "analytics"),
  COMPETITORS("Competitors", "competitors"),
  PROJECTS("Projects", "projects"),
  PRICING("Pricing", "pricing"),
  PROFILE("Profile", "profile")
}

class CreatorPilotViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = CreatorPilotRepository(application)

  val currentChannel = repository.currentChannel
  val currentUser = repository.currentUser
  val contentIdeas = repository.currentIdeas
  val strategyDays = repository.currentStrategy

  val savedProjects: StateFlow<List<ProjectEntity>> = repository.getAllProjects()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _currentDestination = MutableStateFlow(AppDestination.DASHBOARD)
  val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

  private val _opportunity = MutableStateFlow(repository.getBiggestOpportunity())
  val opportunity: StateFlow<Opportunity> = _opportunity.asStateFlow()

  private val _activeScript = MutableStateFlow<GeneratedScript?>(null)
  val activeScript: StateFlow<GeneratedScript?> = _activeScript.asStateFlow()

  private val _activeThumbnail = MutableStateFlow<ThumbnailAnalysis?>(null)
  val activeThumbnail: StateFlow<ThumbnailAnalysis?> = _activeThumbnail.asStateFlow()

  private val _activeCompetitor = MutableStateFlow<CompetitorChannel?>(null)
  val activeCompetitor: StateFlow<CompetitorChannel?> = _activeCompetitor.asStateFlow()

  // Multi-step Loading State
  private val _isAnalyzingChannel = MutableStateFlow(false)
  val isAnalyzingChannel: StateFlow<Boolean> = _isAnalyzingChannel.asStateFlow()

  private val _analysisStepIndex = MutableStateFlow(0)
  val analysisStepIndex: StateFlow<Int> = _analysisStepIndex.asStateFlow()

  // Dialog & Modal flags
  private val _showExplainDialog = MutableStateFlow(false)
  val showExplainDialog: StateFlow<Boolean> = _showExplainDialog.asStateFlow()

  private val _showUpgradeDialog = MutableStateFlow(false)
  val showUpgradeDialog: StateFlow<Boolean> = _showUpgradeDialog.asStateFlow()

  private val _showExportDialog = MutableStateFlow(false)
  val showExportDialog: StateFlow<Boolean> = _showExportDialog.asStateFlow()

  private val _showAnalyzeChannelDialog = MutableStateFlow(false)
  val showAnalyzeChannelDialog: StateFlow<Boolean> = _showAnalyzeChannelDialog.asStateFlow()

  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  init {
    // Generate initial default script so Script Generator is immediately usable
    _activeScript.value = repository.generateScript("5 AI Tools Students Don't Know About", "60 sec", "Educational")
    _activeThumbnail.value = repository.analyzeThumbnail("5 AI Tools Students Don't Know About")
    _activeCompetitor.value = repository.analyzeCompetitor("@Fireship")
  }

  fun navigateTo(destination: AppDestination) {
    _currentDestination.value = destination
  }

  fun setExplainDialog(visible: Boolean) {
    _showExplainDialog.value = visible
  }

  fun setUpgradeDialog(visible: Boolean) {
    _showUpgradeDialog.value = visible
  }

  fun setExportDialog(visible: Boolean) {
    _showExportDialog.value = visible
  }

  fun setAnalyzeChannelDialog(visible: Boolean) {
    _showAnalyzeChannelDialog.value = visible
  }

  fun clearToast() {
    _toastMessage.value = null
  }

  fun showToast(msg: String) {
    _toastMessage.value = msg
  }

  /**
   * Runs the channel analysis with the required multi-step loading experience:
   * ✓ Finding videos
   * ✓ Studying content patterns
   * ✓ Analyzing titles
   * ✓ Comparing performance
   * ● Finding opportunities
   * ○ Building recommendations
   */
  fun analyzeChannel(urlOrHandle: String, niche: String = "AI & Technology") {
    viewModelScope.launch {
      _isAnalyzingChannel.value = true
      _analysisStepIndex.value = 0

      // Step-by-step progress
      for (step in 0..5) {
        _analysisStepIndex.value = step
        delay(600)
      }

      val result = repository.analyzeChannel(urlOrHandle, niche)
      _opportunity.value = repository.getBiggestOpportunity()
      _isAnalyzingChannel.value = false
      _showAnalyzeChannelDialog.value = false
      _currentDestination.value = AppDestination.DASHBOARD
      _toastMessage.value = "Channel analyzed successfully! Health score: ${result.healthScore.overall}/100"
    }
  }

  fun generateScriptForIdea(idea: ContentIdea, length: String = "60 sec", tone: String = "Educational") {
    val script = repository.generateScript(idea.title, length, tone)
    _activeScript.value = script
    _currentDestination.value = AppDestination.SCRIPTS
  }

  fun regenerateScript(length: String, tone: String) {
    val current = _activeScript.value ?: return
    _activeScript.value = repository.generateScript(current.title, length, tone)
    _toastMessage.value = "Script regenerated for $length ($tone)!"
  }

  fun saveCurrentScriptToProject() {
    val script = _activeScript.value ?: return
    viewModelScope.launch {
      repository.saveProject(
        title = script.title,
        type = "Script",
        content = script.scenes.joinToString("\n\n") { "[${it.timeRange}] ${it.voiceover}" },
        hook = script.hook,
        format = script.length,
        score = script.overallScore,
        status = "Ready"
      )
      _toastMessage.value = "Script saved to Projects!"
    }
  }

  fun saveIdeaToProject(idea: ContentIdea) {
    viewModelScope.launch {
      repository.saveProject(
        title = idea.title,
        type = "Idea",
        content = "${idea.whyItFits}\n\nHook: ${idea.hook}",
        hook = idea.hook,
        format = idea.format,
        score = idea.opportunityScore,
        status = "Draft"
      )
      _toastMessage.value = "Idea saved to Projects!"
    }
  }

  fun saveStrategyToProjects() {
    viewModelScope.launch {
      val days = strategyDays.value
      val summary = days.take(7).joinToString("\n") { "${it.dateLabel}: ${it.suggestedTitle} (${it.format})" }
      repository.saveProject(
        title = "30-Day Growth Strategy Calendar",
        type = "Strategy",
        content = summary,
        hook = "Day 1 Hook: ${days.firstOrNull()?.hook ?: ""}",
        format = "30 Days",
        score = 92,
        status = "In Progress"
      )
      _toastMessage.value = "Growth strategy saved to Projects!"
    }
  }

  fun deleteProject(id: Long) {
    viewModelScope.launch {
      repository.deleteProject(id)
      _toastMessage.value = "Project removed"
    }
  }

  fun updateProjectStatus(project: ProjectEntity, newStatus: String) {
    viewModelScope.launch {
      repository.updateProjectStatus(project, newStatus)
    }
  }

  fun toggleStrategyDay(dayNumber: Int) {
    repository.toggleStrategyDayCompleted(dayNumber)
  }

  fun regenerateStrategy() {
    repository.regenerateStrategy()
    _toastMessage.value = "Growth Strategy refreshed with latest channel trends!"
  }

  fun analyzeThumbnail(query: String) {
    _activeThumbnail.value = repository.analyzeThumbnail(query)
    _toastMessage.value = "Thumbnail analyzed! Score: 84/100"
  }

  fun analyzeCompetitor(handle: String) {
    _activeCompetitor.value = repository.analyzeCompetitor(handle)
    _toastMessage.value = "Competitor channels analyzed! Found 4 content gaps."
  }

  fun selectTier(tier: String) {
    repository.updateUserTier(tier)
    _showUpgradeDialog.value = false
    _toastMessage.value = "Plan updated to $tier! Premium features unlocked."
  }

  fun loginGoogle() {
    repository.loginUser("punjal2177@gmail.com", "Alex Rivera")
    _currentDestination.value = AppDestination.DASHBOARD
    _toastMessage.value = "Welcome back, Alex!"
  }

  fun loginEmail(email: String) {
    val name = email.substringBefore("@").replace(".", " ").capitalize()
    repository.loginUser(email, name.ifBlank { "Creator" })
    _currentDestination.value = AppDestination.DASHBOARD
    _toastMessage.value = "Logged in successfully!"
  }

  fun guestLogin() {
    repository.setGuestMode(true)
    _currentDestination.value = AppDestination.DASHBOARD
    _toastMessage.value = "Exploring as Guest. Create an account to save projects permanently."
  }

  fun logout() {
    repository.logout()
    _currentDestination.value = AppDestination.LANDING
    _toastMessage.value = "Logged out successfully"
  }
}
