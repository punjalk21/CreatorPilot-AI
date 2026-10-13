package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.TitleOption
import com.example.data.model.ContentIdea
import com.example.data.model.TrendTopic
import com.example.ui.components.IdeaItemCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun IdeasScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val ideas by viewModel.contentIdeas.collectAsState()
  var selectedTab by remember { mutableStateOf(0) } // 0: Content Ideas, 1: Title Generator, 2: Trending Now
  var formatFilter by remember { mutableStateOf("All") } // "All", "Shorts", "Long-form"

  val filteredIdeas = remember(ideas, formatFilter) {
    if (formatFilter == "All") ideas
    else ideas.filter { it.format.equals(formatFilter, ignoreCase = true) }
  }

  // Title generator state
  var topicQuery by remember { mutableStateOf("5 Free AI Tools in 2026") }
  var generatedTitles by remember { mutableStateOf<List<TitleOption>>(emptyList()) }

  val trendingTopics = remember {
    listOf(
      TrendTopic("DeepSeek vs Claude 3.7", "AI & Technology", "+310% Search", 95, "Benchmark test on real coding projects"),
      TrendTopic("Zero-Budget Faceless YouTube", "Creator Strategy", "+185% Search", 92, "Free open source workflow breakdown"),
      TrendTopic("Gemini Flash 3.8", "AI", "+140% Search", 89, "Top 5 hidden features nobody noticed"),
      TrendTopic("Shorts Algorithm 2026 Update", "Education", "+220% Search", 94, "Why looping hooks are mandatory now")
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    item {
      Column {
        Text(
          text = "Content Strategy & Ideas",
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
        )
        Text(
          text = "Actionable topics calibrated to your channel's performance history",
          style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted)
        )
      }
    }

    // Tab Selector
    item {
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = ObsidianCard,
        contentColor = NeonPurple,
        divider = { HorizontalDivider(color = ObsidianCardBorder) }
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("What to Create Next", fontWeight = FontWeight.SemiBold) },
          icon = { Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(18.dp)) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("Title Generator", fontWeight = FontWeight.SemiBold) },
          icon = { Icon(Icons.Default.TextFields, contentDescription = null, modifier = Modifier.size(18.dp)) }
        )
        Tab(
          selected = selectedTab == 2,
          onClick = { selectedTab = 2 },
          text = { Text("Trending Now", fontWeight = FontWeight.SemiBold) },
          icon = { Icon(Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(18.dp)) }
        )
      }
    }

    when (selectedTab) {
      0 -> {
        // Format filter chips
        item {
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            listOf("All", "Shorts", "Long-form").forEach { filter ->
              FilterChip(
                selected = formatFilter == filter,
                onClick = { formatFilter = filter },
                label = { Text(filter) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = ElectricViolet,
                  selectedLabelColor = TextWhite,
                  containerColor = ObsidianCard,
                  labelColor = TextGrayLight
                ),
                border = FilterChipDefaults.filterChipBorder(
                  enabled = true,
                  selected = formatFilter == filter,
                  borderColor = ObsidianCardBorder,
                  selectedBorderColor = ElectricViolet
                )
              )
            }
          }
        }

        items(filteredIdeas) { idea ->
          IdeaItemCard(
            idea = idea,
            onGenerateScript = { viewModel.generateScriptForIdea(idea) },
            onSave = { viewModel.saveIdeaToProject(idea) }
          )
        }
      }

      1 -> {
        // Title Generator Tab
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, ObsidianCardBorder, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard)
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = "Clickbait-Proof Title Generator",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Generates 5 distinct psychological angles with transparent CTR scores",
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
              )

              Spacer(modifier = Modifier.height(14.dp))

              OutlinedTextField(
                value = topicQuery,
                onValueChange = { topicQuery = it },
                label = { Text("Video Topic or Concept") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = ElectricViolet,
                  unfocusedBorderColor = ObsidianCardBorder,
                  focusedTextColor = TextWhite,
                  unfocusedTextColor = TextWhite,
                  focusedLabelColor = NeonPurple
                ),
                shape = RoundedCornerShape(10.dp)
              )

              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = {
                  generatedTitles = listOf(
                    TitleOption("Curiosity", "The 1 Tool Top YouTubers Secretly Rely On in 2026", 92, "High intrigue without misleading promises"),
                    TitleOption("Educational", "How to 10× Your YouTube Growth with AI (Step-by-Step)", 88, "Clear learning outcome, strong search intent"),
                    TitleOption("Emotional", "I Almost Quit YouTube Until I Discovered This Strategy", 85, "Relatable vulnerability, high community connection"),
                    TitleOption("SEO-Focused", "YouTube AI Tools 2026: Complete Growth & Script Guide", 90, "Optimized for high-intent search queries"),
                    TitleOption("Storytelling", "How a Zero-Subscriber Channel Reached 100K in 90 Days", 94, "Compelling narrative arc with concrete benchmark")
                  )
                  viewModel.showToast("Generated 5 high-converting title options!")
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generate & Score Titles")
              }
            }
          }
        }

        if (generatedTitles.isNotEmpty()) {
          items(generatedTitles) { titleOpt ->
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ObsidianCardBorder, RoundedCornerShape(12.dp)),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = ObsidianCard)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ObsidianElevated
                  ) {
                    Text(
                      text = titleOpt.style,
                      style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                  }

                  Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SuccessGreen.copy(alpha = 0.15f)
                  ) {
                    Text(
                      text = "CTR Score: ${titleOpt.score}/100",
                      style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = "\"${titleOpt.title}\"",
                  style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = titleOpt.explanation,
                  style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
                )
              }
            }
          }
        }
      }

      2 -> {
        // Trending Now Section
        item {
          Text(
            text = "Real-Time Topic Velocity",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
        }

        items(trendingTopics) { trend ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = trend.category,
                  style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted)
                )
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = SuccessGreen.copy(alpha = 0.15f)
                ) {
                  Text(
                    text = trend.searchGrowth,
                    style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = trend.topic,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
              )

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = "Recommended Angle: " + trend.recommendedAngle,
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
              )

              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = {
                  viewModel.generateScriptForIdea(
                    ContentIdea(
                      id = "trend_" + System.currentTimeMillis(),
                      title = trend.topic + ": What Creators Need to Know",
                      hook = "Stop what you're doing: ${trend.topic} just changed everything.",
                      opportunityScore = trend.opportunityScore,
                      format = "Shorts",
                      audience = "Tech Enthusiasts",
                      difficulty = "Easy",
                      whyItFits = trend.recommendedAngle,
                      tags = listOf("Trending", "AI")
                    )
                  )
                },
                colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
                modifier = Modifier.fillMaxWidth()
              ) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Make a Video on This", color = TextWhite, fontSize = 12.sp)
              }
            }
          }
        }
      }
    }
  }
}
