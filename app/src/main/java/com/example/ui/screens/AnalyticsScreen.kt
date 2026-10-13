package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.ContentIdea
import com.example.data.model.TopVideoPerformance
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun AnalyticsScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTimeRange by remember { mutableStateOf("30 Days") }
  val timeRanges = listOf("7 Days", "30 Days", "90 Days")

  val topVideos = remember {
    listOf(
      TopVideoPerformance("Top 5 Free AI Tools in 2026", "248K views", 3.4f, "3.4× above channel average"),
      TopVideoPerformance("How I Build Apps in 10 Minutes with AI", "142K views", 2.1f, "2.1× above channel average"),
      TopVideoPerformance("Gemini vs Claude: The Brutal Truth", "98K views", 1.8f, "1.8× above channel average")
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
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Analytics & Growth Coach",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "Focus on growth leverage rather than raw vanity numbers",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }
      }
    }

    // Time Range Selector
    item {
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        timeRanges.forEach { range ->
          FilterChip(
            selected = selectedTimeRange == range,
            onClick = { selectedTimeRange = range },
            label = { Text(range) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = ElectricViolet,
              selectedLabelColor = TextWhite,
              containerColor = ObsidianCard,
              labelColor = TextGrayLight
            )
          )
        }
      }
    }

    // Key Metrics Grid
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          AnalyticsStatCard("Views", "1.42M", "+38%", SuccessGreen, Modifier.weight(1f))
          AnalyticsStatCard("Watch Hours", "48.6K", "+24%", SuccessGreen, Modifier.weight(1f))
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          AnalyticsStatCard("Subscribers", "+3,420", "+52%", NeonPurple, Modifier.weight(1f))
          AnalyticsStatCard("Impressions CTR", "7.4%", "+1.2%", SuccessGreen, Modifier.weight(1f))
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          AnalyticsStatCard("Avg View Duration", "3m 42s", "+18s", ElectricViolet, Modifier.weight(1f))
          AnalyticsStatCard("Upload Frequency", "3.2 / wk", "Consistent", TextGrayLight, Modifier.weight(1f))
        }
      }
    }

    // Top Videos with Multipliers Card
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
            text = "Outlier Performers vs Channel Baseline",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "Videos that broke through the YouTube recommendation ceiling",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )

          Spacer(modifier = Modifier.height(14.dp))

          topVideos.forEachIndexed { index, video ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "${index + 1}. ${video.title}",
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
                )
                Text(video.views, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
              }

              Surface(
                shape = RoundedCornerShape(20.dp),
                color = SuccessGreen.copy(alpha = 0.15f)
              ) {
                Text(
                  text = "${video.multiplier}× Multiplier",
                  style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
              }
            }
            if (index < topVideos.size - 1) {
              HorizontalDivider(color = ObsidianCardBorder)
            }
          }
        }
      }
    }

    // AI Performance Coach Card (Required MVP feature)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, Brush.horizontalGradient(listOf(ElectricViolet.copy(alpha = 0.6f), ObsidianCardBorder)), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Psychology, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "AI Performance Coach",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // 1. What Happened?
          Surface(
            color = ObsidianElevated,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text("1. What Happened?", style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold))
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                "Your video 'Top 5 Free AI Tools in 2026' generated 248K views, which is 3.4× above your channel's standard upload benchmark.",
                style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 18.sp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // 2. Why might it have happened?
          Surface(
            color = ObsidianElevated,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text("2. Why Might It Have Happened?", style = MaterialTheme.typography.labelSmall.copy(color = WarningAmber, fontWeight = FontWeight.Bold))
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                "The opening hook framed an immediate cost-saving problem in second 2 ('stop paying $20/mo'). This drove an exceptional 68% retention rate past the 30-second drop-off mark, prompting YouTube browse promotion.",
                style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 18.sp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // 3. What should you do next?
          Surface(
            color = ObsidianElevated,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text("3. What Should You Do Next?", style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold))
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                "Do not switch topics to generic tech news. Immediately follow up with a Part 2: '5 More Free AI Tools for Students & Creators' using the identical thumbnail framing and pacing formula.",
                style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 18.sp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              viewModel.generateScriptForIdea(
                ContentIdea(
                  id = "followup_" + System.currentTimeMillis(),
                  title = "5 Free AI Tools Nobody Knows (Part 2)",
                  hook = "Last week I showed you 5 AI tools that saved 10 hours. Today, here are 5 even crazier free tools that just released.",
                  opportunityScore = 96,
                  format = "Shorts",
                  audience = "Productivity Seekers",
                  difficulty = "Easy",
                  whyItFits = "Direct sequel to your 3.4× multiplier outlier video.",
                  tags = listOf("AI Tools", "Part 2", "Viral Sequel")
                )
              )
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.AddCircleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Create More Like This (Auto-Generate Script)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}

@Composable
fun AnalyticsStatCard(
  title: String,
  value: String,
  change: String,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.border(1.dp, ObsidianCardBorder, RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianCard)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(title, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = change,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = accentColor)
      )
    }
  }
}
