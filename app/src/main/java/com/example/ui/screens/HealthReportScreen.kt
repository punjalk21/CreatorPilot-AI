package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Opportunity
import com.example.ui.components.CircularScoreGauge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun HealthReportScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val channel by viewModel.currentChannel.collectAsState()
  val activeChannel = channel ?: return
  val health = activeChannel.healthScore

  val opportunities = remember {
    listOf(
      Opportunity(
        id = "opp_1",
        title = "AI Tool content is outperforming your channel average by 2.3×",
        subtitle = "Practical tutorials deliver highest retention",
        finding = "Specific tool workflows and free alternatives consistently trigger YouTube's browse algorithm within 48 hours.",
        evidence = "Last 30 uploads: Videos featuring 'AI Tools' averaged 142K views vs 61K channel baseline (2.33× multiplier) with 64% retention past 30s.",
        explanation = "Viewers are actively searching for utility-driven AI applications that save them time. High completion rate signals high satisfaction.",
        recommendedAction = "Create 5 more videos around practical AI tools, focusing on beginner-friendly free alternatives.",
        confidence = 94,
        confidenceBadge = "High Confidence (94%)",
        multiplier = 2.3f,
        category = "Topic Expansion"
      ),
      Opportunity(
        id = "opp_2",
        title = "YouTube Shorts under 45s have 78% loop completion rate",
        subtitle = "Micro-content driving 71% of all new channel subscribers",
        finding = "Shorts ending with seamless looping audio retain viewers for 1.4× full playback cycles.",
        evidence = "Your last 12 Shorts under 45s had an Average Percentage Viewed (APV) of 108%, generating 3.2K subscribers.",
        explanation = "The YouTube Shorts algorithm aggressively pushes content where viewers watch until the very last frame or loop.",
        recommendedAction = "Produce 3 dedicated 30-second Shorts weekly with script-matched looping hooks.",
        confidence = 91,
        confidenceBadge = "High Confidence (91%)",
        multiplier = 1.9f,
        category = "Format Optimization"
      ),
      Opportunity(
        id = "opp_3",
        title = "Curiosity-framed question titles boost CTR by +41%",
        subtitle = "Intrigue framing without deceptive clickbait",
        finding = "Titles structured as curiosity questions or contrarian statements outperform declarative titles by 41% CTR.",
        evidence = "'Why 90% of Creators Quit' achieved 8.4% CTR vs 'How to Create Good Videos' at 4.2% CTR.",
        explanation = "Human psychology responds to cognitive gaps: posing an unanswered dilemma prompts the click without needing exaggerated claims.",
        recommendedAction = "A/B test curiosity titles across your next 3 long-form video uploads.",
        confidence = 88,
        confidenceBadge = "Medium-High Confidence (88%)",
        multiplier = 1.6f,
        category = "Packaging & CTR"
      )
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header with back navigation
    item {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(onClick = { viewModel.navigateTo(AppDestination.DASHBOARD) }) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
          Text(
            text = "AI Channel Health Report",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "${activeChannel.handle} • Diagnostic Analysis",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }
      }
    }

    // Health Score Overview Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          CircularScoreGauge(
            score = health.overall,
            size = 100.dp,
            strokeWidth = 9.dp
          )

          Spacer(modifier = Modifier.width(20.dp))

          Column {
            Text(
              text = "Overall Health Score",
              style = MaterialTheme.typography.labelMedium.copy(color = TextGrayMuted)
            )
            Text(
              text = "${health.overall} / 100",
              style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = SuccessGreen.copy(alpha = 0.15f),
              modifier = Modifier.padding(top = 4.dp)
            ) {
              Text(
                text = health.status,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SuccessGreen),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }

    // What's Working Section
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SuccessGreen.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "What's Working",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          health.workingPoints.forEach { point ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.Top
            ) {
              Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
              Text(
                text = point,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight, lineHeight = 20.sp)
              )
            }
          }
        }
      }
    }

    // What Needs Improvement Section
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WarningAmber.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "What Needs Improvement",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          health.improvementPoints.forEach { point ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.Top
            ) {
              Text("⚠", color = WarningAmber, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
              Text(
                text = point,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight, lineHeight = 20.sp)
              )
            }
          }
        }
      }
    }

    // Section Header: Biggest Opportunities
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Biggest Opportunities",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "Actionable algorithmic findings with evidence & confidence ratings",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }
      }
    }

    // Detailed Opportunity Cards
    items(opportunities) { opp ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = ElectricViolet.copy(alpha = 0.2f)
            ) {
              Text(
                text = opp.category,
                style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            Surface(
              shape = RoundedCornerShape(20.dp),
              color = SuccessGreen.copy(alpha = 0.15f)
            ) {
              Text(
                text = opp.confidenceBadge,
                style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = opp.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Finding
          Surface(
            color = ObsidianElevated,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("Finding", style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold))
              Text(opp.finding, style = MaterialTheme.typography.bodySmall.copy(color = TextWhite))
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Evidence
          Surface(
            color = ObsidianElevated,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("Evidence", style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold))
              Text(opp.evidence, style = MaterialTheme.typography.bodySmall.copy(color = TextWhite))
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Explanation
          Surface(
            color = ObsidianElevated,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("Why It Matters", style = MaterialTheme.typography.labelSmall.copy(color = WarningAmber, fontWeight = FontWeight.Bold))
              Text(opp.explanation, style = MaterialTheme.typography.bodySmall.copy(color = TextWhite))
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "💡 Action: " + opp.recommendedAction,
            style = MaterialTheme.typography.bodySmall.copy(color = NeonPurple, fontWeight = FontWeight.SemiBold)
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { viewModel.navigateTo(AppDestination.IDEAS) },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Generate Content from Opportunity", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}
