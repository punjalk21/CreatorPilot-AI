package com.example.data.model

data class Channel(
  val id: String = "ch_demo",
  val handle: String = "@AlexCreatesAI",
  val title: String = "Alex Tech & AI",
  val description: String = "Exploring artificial intelligence tools, productivity hacks, and modern tech tutorials.",
  val avatarUrl: String = "",
  val subscriberCount: String = "48.2K",
  val videoCount: String = "142",
  val viewCount: String = "3.8M",
  val niche: String = "AI & Technology",
  val primaryGoal: String = "Grow Subscribers & Monetize",
  val formatPreference: String = "Both Shorts & Long-Form",
  val uploadCadence: String = "3x / week",
  val healthScore: HealthScore = sampleHealthScore()
)

data class HealthScore(
  val overall: Int = 78,
  val status: String = "Good — Room to Grow",
  val contentScore: Int = 82,
  val titlesScore: Int = 74,
  val thumbnailsScore: Int = 79,
  val consistencyScore: Int = 85,
  val workingPoints: List<String> = listOf(
    "AI tool review videos outperform channel average by 2.3×",
    "Short videos (< 60s) demonstrate 18% higher audience retention",
    "Curiosity-framed question titles achieve 41% higher click-through rate"
  ),
  val improvementPoints: List<String> = listOf(
    "Opening hooks in first 3-5 seconds lose ~24% of viewers before the premise",
    "Inconsistent Thursday upload timings reduce early recommendation velocity",
    "Thumbnail text subject contrast is below threshold on mobile screens (< 360dp)"
  ),
  val topVideos: List<TopVideoPerformance> = listOf(
    TopVideoPerformance("Top 5 Free AI Tools in 2026", "248K views", 3.4f, "3.4× above average"),
    TopVideoPerformance("How I Build Apps in 10 Minutes with AI", "142K views", 2.1f, "2.1× above average"),
    TopVideoPerformance("Gemini vs Claude: The Brutal Truth", "98K views", 1.8f, "1.8× above average")
  )
)

data class TopVideoPerformance(
  val title: String,
  val views: String,
  val multiplier: Float,
  val multiplierLabel: String
)

data class Opportunity(
  val id: String,
  val title: String,
  val subtitle: String,
  val finding: String,
  val evidence: String,
  val explanation: String,
  val recommendedAction: String,
  val confidence: Int = 94,
  val confidenceBadge: String = "High Confidence",
  val multiplier: Float = 2.3f,
  val category: String = "Topic Expansion"
)

data class ScoreBreakdown(
  val relevance: Int = 94,
  val historicalPerformance: Int = 89,
  val audienceFit: Int = 92,
  val topicMomentum: Int = 95,
  val contentGap: Int = 86,
  val difficulty: String = "Easy"
)

data class ContentIdea(
  val id: String,
  val title: String,
  val hook: String,
  val opportunityScore: Int,
  val format: String, // "Shorts" or "Long-form"
  val audience: String,
  val difficulty: String,
  val whyItFits: String,
  val tags: List<String>,
  val breakdown: ScoreBreakdown = ScoreBreakdown(),
  val isSaved: Boolean = false
)

data class ScriptScene(
  val sceneNumber: Int,
  val timeRange: String,
  val visualDescription: String,
  val voiceover: String,
  val caption: String,
  val imagePrompt: String,
  val soundEffect: String
)

data class SupportingAssets(
  val sceneDescriptions: List<String> = emptyList(),
  val imagePrompts: List<String> = emptyList(),
  val bRollSuggestions: List<String> = emptyList(),
  val voiceoverFull: String = "",
  val captionSrt: String = "",
  val soundEffects: List<String> = emptyList(),
  val thumbnailConcept: String = ""
)

data class GeneratedScript(
  val id: String,
  val title: String,
  val length: String,
  val tone: String,
  val hook: String,
  val scenes: List<ScriptScene>,
  val patternInterrupts: List<String>,
  val cta: String,
  val hookScore: Int = 92,
  val retentionScore: Int = 87,
  val clarityScore: Int = 94,
  val ctaScore: Int = 81,
  val overallScore: Int = 89,
  val improvementTips: List<String> = listOf(
    "Elevate the first 2 seconds with a visual teaser before stating the problem.",
    "Add a high-energy pattern interrupt at 0:25 mark (e.g. quick zoom + whoosh SFX).",
    "Strengthen the call-to-action by offering a specific pinned-comment download."
  ),
  val assets: SupportingAssets = SupportingAssets()
)

data class StrategyDay(
  val dayNumber: Int,
  val dateLabel: String,
  val topic: String,
  val format: String,
  val hook: String,
  val contentObjective: String,
  val suggestedTitle: String,
  val isCompleted: Boolean = false
)

data class ThumbnailAnalysis(
  val id: String,
  val imageUri: String? = null,
  val overallScore: Int = 84,
  val mobileReadability: Int = 86,
  val textSize: Int = 80,
  val visualHierarchy: Int = 88,
  val subjectClarity: Int = 85,
  val contrast: Int = 79,
  val curiosity: Int = 89,
  val composition: Int = 83,
  val critique: List<String> = listOf(
    "Strong eye-contact on face subject creates immediate visual anchor.",
    "Text size is good, but bottom-right corner risks being obscured by YouTube timestamp badge (0:59).",
    "Increase contrast between foreground cut-out and background by +15% for mobile feeds."
  ),
  val generatedConcepts: List<String> = listOf(
    "Concept A: Split-screen comparison (Bad vs Good) with glowing electric violet dividing laser.",
    "Concept B: Close-up extreme reaction holding futuristic holographic AI smartphone.",
    "Concept C: Bold 3-word curiosity test ('DON'T USE THIS') with warning tape graphic."
  )
)

data class CompetitorChannel(
  val name: String,
  val handle: String,
  val subscribers: String,
  val avgViews: String,
  val uploadCadence: String,
  val topThemes: List<String>,
  val contentGaps: List<String>
)

data class TrendTopic(
  val topic: String,
  val category: String,
  val searchGrowth: String,
  val opportunityScore: Int,
  val recommendedAngle: String
)

data class SubscriptionPlan(
  val id: String,
  val name: String,
  val priceInr: String,
  val billingPeriod: String,
  val description: String,
  val isPopular: Boolean = false,
  val features: List<String>
)

data class UserProfile(
  val name: String = "Alex Rivera",
  val email: String = "alex.creator@gmail.com",
  val isGuest: Boolean = false,
  val tier: String = "Pro", // "Free", "Pro", "Creator"
  val activeChannelHandle: String = "@AlexCreatesAI"
)

fun sampleHealthScore(): HealthScore = HealthScore()
