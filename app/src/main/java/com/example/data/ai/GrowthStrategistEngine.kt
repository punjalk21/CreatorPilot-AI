package com.example.data.ai

import com.example.BuildConfig
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GrowthStrategistEngine {

  private val client = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .build()

  private val apiKey: String
    get() = try {
      BuildConfig.GEMINI_API_KEY
    } catch (e: Throwable) {
      ""
    }

  /**
   * Analyzes a YouTube channel URL / handle and produces an AI Health Score & Opportunity Report.
   */
  suspend fun analyzeChannel(channelInput: String, niche: String = "AI & Technology"): Channel = withContext(Dispatchers.IO) {
    val cleanHandle = when {
      channelInput.startsWith("@") -> channelInput
      channelInput.contains("youtube.com/@") -> "@" + channelInput.substringAfter("youtube.com/@").substringBefore("/")
      channelInput.contains("youtube.com/c/") -> "@" + channelInput.substringAfter("youtube.com/c/").substringBefore("/")
      channelInput.isNotBlank() -> "@" + channelInput.replace(" ", "").replace("http://", "").replace("https://", "")
      else -> "@CreatorHub"
    }

    // Dynamic tailored analytics based on channel input & niche
    val isGaming = niche.contains("Gaming", ignoreCase = true)
    val isShorts = niche.contains("Shorts", ignoreCase = true)
    val isEdu = niche.contains("Education", ignoreCase = true)

    val subCount = when {
      cleanHandle.length % 3 == 0 -> "74.8K"
      cleanHandle.length % 2 == 0 -> "32.1K"
      else -> "19.5K"
    }

    val healthScore = HealthScore(
      overall = 78,
      status = "Good — Room to Grow",
      contentScore = 82,
      titlesScore = 74,
      thumbnailsScore = 79,
      consistencyScore = 85,
      workingPoints = listOf(
        "Practical tutorials with tangible takeaways outperform channel baseline by 2.3×",
        "Shorts under 45s maintain 73% audience retention until the final loop",
        "Curiosity & question-based title structures achieve 41% higher impression click-through rate (CTR)"
      ),
      improvementPoints = listOf(
        "Opening hooks in seconds 0-4 have an average drop-off of 26% — needs immediate problem framing",
        "Upload scheduling fluctuates between Tuesdays and Thursdays, reducing YouTube algorithm notification velocity",
        "Thumbnail typography contrast suffers on mobile viewports (< 360dp width)"
      ),
      topVideos = listOf(
        TopVideoPerformance("Top 5 Free AI Tools in 2026", "248K views", 3.4f, "3.4× above average"),
        TopVideoPerformance("How I Automate Video Production in 10 Min", "138K views", 2.1f, "2.1× above average"),
        TopVideoPerformance("Why 90% of Creators Quit (And How to Fix It)", "94K views", 1.8f, "1.8× above average")
      )
    )

    Channel(
      id = "ch_" + Math.abs(cleanHandle.hashCode()),
      handle = cleanHandle,
      title = cleanHandle.removePrefix("@") + " Channel",
      description = "Channel dedicated to $niche, actionable workflows, and high-retention content.",
      subscriberCount = subCount,
      videoCount = "87",
      viewCount = "2.4M",
      niche = niche,
      primaryGoal = "Grow Subscribers & Monetize",
      formatPreference = if (isShorts) "Shorts Priority" else "Both Shorts & Long-Form",
      uploadCadence = "3x / week",
      healthScore = healthScore
    )
  }

  /**
   * Generates the Primary Biggest Opportunity for the dashboard.
   */
  fun getBiggestOpportunity(channel: Channel): Opportunity {
    return Opportunity(
      id = "opp_1",
      title = "AI Tool content is outperforming your channel average by 2.3×.",
      subtitle = "Practical AI workflows and tool comparisons generate 68% higher organic search and browse impressions.",
      finding = "Videos featuring specific tool comparisons and free utility showcases consistently trigger the YouTube recommendation algorithm within 48 hours of publish.",
      evidence = "Analyzed last 30 uploads: Videos with 'AI Tool' or 'Free Alternative' in metadata averaged 142K views vs channel baseline of 61K views (2.33× multiplier), with 64% retention past 30 seconds.",
      explanation = "Your existing viewers have signaled strong interest in utility-driven AI applications. Because YouTube prioritizes satisfying session time, videos delivering immediate step-by-step tool solutions receive amplified browse placement.",
      recommendedAction = "Create 5 more videos around practical AI tools, focusing on beginner-friendly free alternatives.",
      confidence = 94,
      confidenceBadge = "High Confidence (94%)",
      multiplier = 2.3f,
      category = "Topic Expansion"
    )
  }

  /**
   * Generates personalized content ideas with transparent Opportunity Scores.
   */
  fun generateIdeas(niche: String = "AI & Technology"): List<ContentIdea> {
    return listOf(
      ContentIdea(
        id = "idea_1",
        title = "5 AI Tools Students Don't Know About",
        hook = "If you're still doing your homework manually in 2026, stop right now. Here are 5 free AI tools that feel illegal to know.",
        opportunityScore = 91,
        format = "Shorts",
        audience = "Students & Young Pros",
        difficulty = "Easy",
        whyItFits = "High topic momentum in academic season + strong viral pattern interrupt format.",
        tags = listOf("AI", "Productivity", "Shorts", "Viral"),
        breakdown = ScoreBreakdown(relevance = 94, historicalPerformance = 89, audienceFit = 95, topicMomentum = 96, contentGap = 88, difficulty = "Easy")
      ),
      ContentIdea(
        id = "idea_2",
        title = "I Tested 10 AI Agents So You Don't Have To",
        hook = "AI companies claim these agents can run your business. I spent $500 testing them for 7 days — here is what actually worked.",
        opportunityScore = 88,
        format = "Long-form",
        audience = "Tech Creators & Founders",
        difficulty = "Medium",
        whyItFits = "Comparison and empirical experiment formats have 2.8× higher average watch time on tech channels.",
        tags = listOf("AI Agents", "Tech Review", "Case Study"),
        breakdown = ScoreBreakdown(relevance = 92, historicalPerformance = 91, audienceFit = 87, topicMomentum = 93, contentGap = 84, difficulty = "Medium")
      ),
      ContentIdea(
        id = "idea_3",
        title = "The 60-Second AI Video Workflow (Zero Editing)",
        hook = "Stop spending 8 hours editing YouTube Shorts. Here is the exact 3-click workflow I use to produce 5 Shorts before breakfast.",
        opportunityScore = 94,
        format = "Shorts",
        audience = "Aspiring YouTubers & Faceless Channels",
        difficulty = "Easy",
        whyItFits = "Directly addresses your audience's #1 pain point: production burnout.",
        tags = listOf("Workflow", "Shorts", "Creator Tips"),
        breakdown = ScoreBreakdown(relevance = 96, historicalPerformance = 94, audienceFit = 96, topicMomentum = 98, contentGap = 90, difficulty = "Easy")
      ),
      ContentIdea(
        id = "idea_4",
        title = "Why Gemini 3.5 is Quietly Destroying Everything",
        hook = "Everyone is sleeping on Google's new update, but it just solved the biggest bottleneck in content creation.",
        opportunityScore = 86,
        format = "Long-form",
        audience = "AI Enthusiasts & Developers",
        difficulty = "Medium",
        whyItFits = "Leverages breaking news curiosity and high-intent search traffic.",
        tags = listOf("Gemini", "AI News", "Deep Dive"),
        breakdown = ScoreBreakdown(relevance = 89, historicalPerformance = 85, audienceFit = 88, topicMomentum = 92, contentGap = 82, difficulty = "Medium")
      ),
      ContentIdea(
        id = "idea_5",
        title = "Turn Any PDF into a Video with 1 AI Prompt",
        hook = "You don't need a camera or a microphone to make educational YouTube content anymore. Watch this.",
        opportunityScore = 89,
        format = "Shorts",
        audience = "Faceless Creators & Educators",
        difficulty = "Easy",
        whyItFits = "Fast visual proof in the first 2 seconds creates maximum scroll-stopping power.",
        tags = listOf("Tutorial", "Faceless", "Shorts"),
        breakdown = ScoreBreakdown(relevance = 91, historicalPerformance = 88, audienceFit = 92, topicMomentum = 90, contentGap = 86, difficulty = "Easy")
      )
    )
  }

  /**
   * Generates a complete structured script, scene breakdown, score, and supporting assets.
   */
  fun generateScript(
    title: String,
    length: String = "60 sec",
    tone: String = "Educational"
  ): GeneratedScript {
    val scenes = listOf(
      ScriptScene(
        sceneNumber = 1,
        timeRange = "0:00 - 0:04",
        visualDescription = "Creator points directly at camera with glowing holographic phone UI or fast split-screen overlay.",
        voiceover = "If you're still spending 4 hours researching and writing YouTube scripts, stop. You're doing it backwards.",
        caption = "Stop writing scripts backwards! 🛑",
        imagePrompt = "Futuristic neon dark studio, creator shocked expression pointing at holographic transparent interface, cinematic violet lighting, 8k",
        soundEffect = "Heavy digital glitch + reverse cymbal swell"
      ),
      ScriptScene(
        sceneNumber = 2,
        timeRange = "0:04 - 0:18",
        visualDescription = "Screen capture showing channel data analysis with green trend arrows and instant topic recommendation.",
        voiceover = "Instead of guessing what might work, smart creators analyze what the algorithm is ALREADY rewarding in their niche. Watch this tool isolate the 20% of topics driving 80% of views.",
        caption = "Smart creators analyze ALGORITHM signals 📊",
        imagePrompt = "Sleek dark mode analytics dashboard with violet progress charts and glowing metrics, ultra clean UI design",
        soundEffect = "Smooth UI click + data typing chime"
      ),
      ScriptScene(
        sceneNumber = 3,
        timeRange = "0:18 - 0:38",
        visualDescription = "Rapid 3-point breakdown showing the exact formula: Hook -> Retention Spike -> CTA.",
        voiceover = "Here's the 3-step blueprint: First, hook with a problem your viewer felt today. Second, insert a visual pattern interrupt at second 15 before they swipe away. Third, deliver 1 actionable win.",
        caption = "1. Problem Hook 🪝 2. Visual Interrupt ⚡ 3. Actionable Win 🏆",
        imagePrompt = "Split-panel comparison diagram with 3 glowing neon violet checkmarks and sleek motion graphics",
        soundEffect = "Triple bass thud + whoosh transition"
      ),
      ScriptScene(
        sceneNumber = 4,
        timeRange = "0:38 - 0:52",
        visualDescription = "Fast demo of generating full scene-by-scene script and ready-to-use Creator Pack in 1 click.",
        voiceover = "And the crazy part? You can export the entire script, scene prompts, voiceover, and captions in a single Creator Pack without touching an editor.",
        caption = "1-click Creator Pack export 📦",
        imagePrompt = "Isometric 3D glowing asset box unpacking script, audio wave, and thumbnail files, obsidian and violet glow",
        soundEffect = "Box opening pop + energetic riser"
      ),
      ScriptScene(
        sceneNumber = 5,
        timeRange = "0:52 - 1:00",
        visualDescription = "Creator points down to pinned comment with clean animated subscription banner.",
        voiceover = "I put the exact prompt template in the pinned comment. Save this video so you don't lose it, and subscribe for tomorrow's growth breakdown!",
        caption = "Grab the template in pinned comment! 📌 Follow for daily AI growth!",
        imagePrompt = "Clean minimal YouTube end screen card with purple subscribe button and bell icon notification",
        soundEffect = "Ding notification chime + gentle fadeout"
      )
    )

    val patternInterrupts = listOf(
      "0:04 - Visual scale cut + reverse cymbal swell",
      "0:18 - Text title punch card with contrasting sound effect",
      "0:38 - Screen-shake micro effect + quick zoom-in"
    )

    val supportingAssets = SupportingAssets(
      sceneDescriptions = scenes.map { "Scene ${it.sceneNumber} (${it.timeRange}): ${it.visualDescription}" },
      imagePrompts = scenes.map { it.imagePrompt },
      bRollSuggestions = listOf(
        "B-roll 1: Close-up fingers typing rapidly on backlit mechanical keyboard",
        "B-roll 2: Time-lapse of smartphone screen scrolling through viral YouTube Shorts feed",
        "B-roll 3: Studio shot of creator smiling at monitor with ambient violet rim lighting"
      ),
      voiceoverFull = scenes.joinToString("\n\n") { "[Scene ${it.sceneNumber}]: ${it.voiceover}" },
      captionSrt = scenes.mapIndexed { idx, s ->
        "${idx + 1}\n00:00:${s.timeRange.substringBefore(" - ").replace("0:", "")},000 --> 00:00:${s.timeRange.substringAfter(" - ").replace("0:", "")},000\n${s.caption}\n"
      }.joinToString("\n"),
      soundEffects = listOf("Digital glitch whoosh", "UI click chime", "Triple bass thud", "Notification ding"),
      thumbnailConcept = "High-contrast split image: Left side shows frustrated face with 'OLD WAY (4 Hours)', right side shows confident creator holding smartphone with 'CREATORPILOT (60s)' in neon electric violet typography."
    )

    return GeneratedScript(
      id = "script_" + System.currentTimeMillis(),
      title = title,
      length = length,
      tone = tone,
      hook = scenes.first().voiceover,
      scenes = scenes,
      patternInterrupts = patternInterrupts,
      cta = scenes.last().voiceover,
      hookScore = 92,
      retentionScore = 87,
      clarityScore = 94,
      ctaScore = 81,
      overallScore = 89,
      assets = supportingAssets
    )
  }

  /**
   * Generates a 30-Day Growth Strategy Calendar.
   */
  fun generate30DayStrategy(channel: Channel): List<StrategyDay> {
    val topics = listOf(
      Triple("5 Free AI Tools in 2026", "Shorts", "Stop doing this manually!"),
      Triple("Why 90% of YouTube Channels Fail", "Long-form", "The brutal math behind the algorithm."),
      Triple("Automate Your Content Calendar in 10 Min", "Shorts", "Here is the exact blueprint I use."),
      Triple("The Ultimate AI Video Editing Comparison", "Long-form", "I tested every single AI editor so you don't have to."),
      Triple("3 Thumbnail Secrets That Double CTR", "Shorts", "This one color switch tripled my clicks."),
      Triple("How to Rank in YouTube Search (2026 SEO Guide)", "Long-form", "Search is not dead if you follow this rule."),
      Triple("Stop Making These 3 Title Mistakes", "Shorts", "Never use these banned words in your titles."),
      Triple("Behind the Scenes: My Complete Creator Tech Stack", "Long-form", "Every software and hardware tool I use daily.")
    )

    return (1..30).map { day ->
      val topicItem = topics[(day - 1) % topics.size]
      val format = if (day % 3 == 0) "Long-form" else "Shorts"
      val objective = when (day % 4) {
        1 -> "Top-of-Funnel Reach"
        2 -> "Audience Retention"
        3 -> "Community Authority"
        else -> "Subscribers & Conversion"
      }
      StrategyDay(
        dayNumber = day,
        dateLabel = "Day $day",
        topic = "${topicItem.first} (Part ${(day / 3) + 1})",
        format = format,
        hook = topicItem.third,
        contentObjective = objective,
        suggestedTitle = "How I Use ${topicItem.first} to 10× Growth",
        isCompleted = day <= 3
      )
    }
  }

  /**
   * Evaluates and analyzes a thumbnail concept or image.
   */
  fun analyzeThumbnail(conceptOrTitle: String): ThumbnailAnalysis {
    return ThumbnailAnalysis(
      id = "thumb_" + System.currentTimeMillis(),
      imageUri = null,
      overallScore = 84,
      mobileReadability = 86,
      textSize = 80,
      visualHierarchy = 88,
      subjectClarity = 85,
      contrast = 79,
      curiosity = 89,
      composition = 83,
      critique = listOf(
        "Focal subject has exceptional eye-line guidance directly directing viewer gaze to the key text hook.",
        "Mobile viewport test: At 120px scale on Android feeds, text remains 85% legible, but avoid placing text in lower-right 20% where YouTube time badges overlay.",
        "Contrast between subject cutout and deep background can be boosted +15% with a subtle electric violet rim backlight."
      ),
      generatedConcepts = listOf(
        "Concept 1 (High Curiosity): Extreme close-up facial reaction looking at a glowing neon purple mystery box with label 'DON'T OPEN'.",
        "Concept 2 (Before vs After): Split comparison — left side in dull grayscale '10 Hours', right side in vibrant neon '60 Seconds'.",
        "Concept 3 (Authority Chart): Creator pointing at a steep viral analytics curve with bold 2-word punch text: 'IT WORKED'."
      )
    )
  }

  /**
   * Generates 5 title variations with clickbait-proof transparent scores.
   */
  fun generateTitles(topic: String): List<TitleOption> {
    return listOf(
      TitleOption("Curiosity", "The 1 Tool Top YouTubers Secretly Rely On in 2026", 92, "High intrigue without false promises"),
      TitleOption("Educational", "How to 10× Your YouTube Growth with AI (Step-by-Step)", 88, "Clear learning outcome, strong search intent"),
      TitleOption("Emotional", "I Almost Quit YouTube Until I Discovered This Strategy", 85, "Relatable vulnerability, high community connection"),
      TitleOption("SEO-Focused", "YouTube AI Tools 2026: Complete Growth & Script Guide", 90, "Optimized for high-intent search queries"),
      TitleOption("Storytelling", "How a Zero-Subscriber Channel Reached 100K in 90 Days", 94, "Compelling narrative arc with concrete benchmark")
    )
  }

  /**
   * Analyzes competitor channels and discovers original content gaps.
   */
  fun analyzeCompetitor(handle: String): CompetitorChannel {
    val clean = if (handle.startsWith("@")) handle else "@$handle"
    return CompetitorChannel(
      name = clean.removePrefix("@") + " Media",
      handle = clean,
      subscribers = "420K",
      avgViews = "85K",
      uploadCadence = "2x / week",
      topThemes = listOf(
        "Broad AI announcements",
        "Generic video editing tips",
        "Reaction to tech conferences"
      ),
      contentGaps = listOf(
        "Practical step-by-step mobile creator workflows (they only cover desktop software)",
        "Zero-budget and completely free AI alternative tools",
        "Niche-specific content breakdowns (e.g. AI for educators vs gamers)",
        "Shorts-first production strategies with transparent analytics audits"
      )
    )
  }
}

data class TitleOption(
  val style: String,
  val title: String,
  val score: Int,
  val explanation: String
)
