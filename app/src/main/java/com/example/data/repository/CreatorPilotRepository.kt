package com.example.data.repository

import android.content.Context
import com.example.data.ai.GrowthStrategistEngine
import com.example.data.local.AppDatabase
import com.example.data.local.ProjectEntity
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatorPilotRepository(context: Context) {

  private val database = AppDatabase.getDatabase(context)
  private val projectDao = database.projectDao()
  private val aiEngine = GrowthStrategistEngine()

  private val _currentChannel = MutableStateFlow<Channel?>(
    // Default demo channel loaded so users can immediately explore the dashboard
    Channel(
      id = "ch_demo",
      handle = "@AlexCreatesAI",
      title = "Alex Tech & AI",
      description = "Practical AI tools, YouTube automation, and creator growth workflows.",
      subscriberCount = "48.2K",
      videoCount = "142",
      viewCount = "3.8M",
      niche = "AI & Technology",
      primaryGoal = "Grow Subscribers & Monetize",
      formatPreference = "Both Shorts & Long-Form",
      uploadCadence = "3x / week",
      healthScore = sampleHealthScore()
    )
  )
  val currentChannel: StateFlow<Channel?> = _currentChannel.asStateFlow()

  private val _currentUser = MutableStateFlow(
    UserProfile(
      name = "Alex Rivera",
      email = "alex.creator@gmail.com",
      isGuest = false,
      tier = "Pro",
      activeChannelHandle = "@AlexCreatesAI"
    )
  )
  val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

  private val _currentIdeas = MutableStateFlow<List<ContentIdea>>(aiEngine.generateIdeas())
  val currentIdeas: StateFlow<List<ContentIdea>> = _currentIdeas.asStateFlow()

  private val _currentStrategy = MutableStateFlow<List<StrategyDay>>(
    aiEngine.generate30DayStrategy(_currentChannel.value ?: Channel())
  )
  val currentStrategy: StateFlow<List<StrategyDay>> = _currentStrategy.asStateFlow()

  // Room projects flow
  fun getAllProjects(): Flow<List<ProjectEntity>> = projectDao.getAllProjects()

  suspend fun saveProject(
    title: String,
    type: String,
    content: String,
    hook: String = "",
    format: String = "Shorts",
    score: Int = 89,
    status: String = "Ready"
  ): Long {
    val entity = ProjectEntity(
      title = title,
      type = type,
      status = status,
      hook = hook,
      content = content,
      format = format,
      score = score,
      createdAt = System.currentTimeMillis()
    )
    return projectDao.insertProject(entity)
  }

  suspend fun deleteProject(id: Long) {
    projectDao.deleteProjectById(id)
  }

  suspend fun updateProjectStatus(project: ProjectEntity, newStatus: String) {
    projectDao.updateProject(project.copy(status = newStatus))
  }

  suspend fun analyzeChannel(channelUrl: String, niche: String): Channel {
    val result = aiEngine.analyzeChannel(channelUrl, niche)
    _currentChannel.value = result
    _currentIdeas.value = aiEngine.generateIdeas(niche)
    _currentStrategy.value = aiEngine.generate30DayStrategy(result)
    _currentUser.value = _currentUser.value.copy(activeChannelHandle = result.handle)
    return result
  }

  fun getBiggestOpportunity(): Opportunity {
    val channel = _currentChannel.value ?: Channel()
    return aiEngine.getBiggestOpportunity(channel)
  }

  fun generateScript(title: String, length: String, tone: String): GeneratedScript {
    return aiEngine.generateScript(title, length, tone)
  }

  fun analyzeThumbnail(conceptOrTitle: String): ThumbnailAnalysis {
    return aiEngine.analyzeThumbnail(conceptOrTitle)
  }

  fun generateTitles(topic: String) = aiEngine.generateTitles(topic)

  fun analyzeCompetitor(handle: String) = aiEngine.analyzeCompetitor(handle)

  fun regenerateStrategy() {
    val channel = _currentChannel.value ?: Channel()
    _currentStrategy.value = aiEngine.generate30DayStrategy(channel)
  }

  fun toggleStrategyDayCompleted(dayNumber: Int) {
    val current = _currentStrategy.value
    _currentStrategy.value = current.map {
      if (it.dayNumber == dayNumber) it.copy(isCompleted = !it.isCompleted) else it
    }
  }

  fun updateUserTier(tier: String) {
    _currentUser.value = _currentUser.value.copy(tier = tier)
  }

  fun setGuestMode(isGuest: Boolean) {
    _currentUser.value = _currentUser.value.copy(
      isGuest = isGuest,
      name = if (isGuest) "Guest Creator" else "Alex Rivera",
      tier = if (isGuest) "Free" else "Pro"
    )
  }

  fun loginUser(email: String, name: String) {
    _currentUser.value = UserProfile(
      name = name,
      email = email,
      isGuest = false,
      tier = "Pro",
      activeChannelHandle = _currentChannel.value?.handle ?: "@CreatorPilot"
    )
  }

  fun logout() {
    _currentUser.value = UserProfile(
      name = "Guest Creator",
      email = "guest@creatorpilot.ai",
      isGuest = true,
      tier = "Free",
      activeChannelHandle = ""
    )
  }
}
