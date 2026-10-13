package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.example.data.model.Channel
import com.example.data.model.ContentIdea
import com.example.data.model.Opportunity
import com.example.ui.components.CircularScoreGauge
import com.example.ui.components.IdeaItemCard
import com.example.ui.components.OpportunityHeroCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun DashboardScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val channel by viewModel.currentChannel.collectAsState()
  val user by viewModel.currentUser.collectAsState()
  val opportunity by viewModel.opportunity.collectAsState()
  val ideas by viewModel.contentIdeas.collectAsState()

  val activeChannel = channel ?: Channel()
  val health = activeChannel.healthScore

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Good morning, ${user.name.substringBefore(" ")} 👋",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Here's what your channel needs today.",
            style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted)
          )
        }

        Button(
          onClick = { viewModel.setAnalyzeChannelDialog(true) },
          colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
          Icon(Icons.Default.Add, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Analyze New Channel", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
      }
    }

    // Active Channel Pill & Stats Summary
    item {
      Surface(
        color = ObsidianCard,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(YouTubeRed),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.PlayArrow, contentDescription = null, tint = TextWhite, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(activeChannel.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite))
              Text(activeChannel.handle, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(horizontalAlignment = Alignment.End) {
              Text(activeChannel.subscriberCount, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite))
              Text("Subscribers", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
            }
            Column(horizontalAlignment = Alignment.End) {
              Text(activeChannel.viewCount, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = SuccessGreen))
              Text("Total Views", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
            }
          }
        }
      }
    }

    // Most Important MVP Card: Channel Health Card
    item {
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
            Text(
              text = "Channel Health",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
            )

            Surface(
              shape = RoundedCornerShape(20.dp),
              color = SuccessGreen.copy(alpha = 0.15f)
            ) {
              Text(
                text = health.status,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = SuccessGreen
                ),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            CircularScoreGauge(
              score = health.overall,
              size = 110.dp,
              strokeWidth = 10.dp
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              HealthSubMetricRow("Content Quality", health.contentScore)
              HealthSubMetricRow("Title Framing", health.titlesScore)
              HealthSubMetricRow("Thumbnails CTR", health.thumbnailsScore)
              HealthSubMetricRow("Upload Consistency", health.consistencyScore)
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = { viewModel.navigateTo(AppDestination.HEALTH_REPORT) },
            colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
            border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("View Full AI Health Report", color = TextWhite, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
          }
        }
      }
    }

    // AI Biggest Opportunity Card
    item {
      OpportunityHeroCard(
        opportunity = opportunity,
        onGenerateIdeas = { viewModel.navigateTo(AppDestination.IDEAS) },
        onWhyThisMatters = { viewModel.setExplainDialog(true) }
      )
    }

    // Section: What You Should Create Next
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "What You Should Create Next",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = TextWhite
            )
          )
          Text(
            text = "Tailored to your channel history & viral algorithm gaps",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }

        TextButton(onClick = { viewModel.navigateTo(AppDestination.IDEAS) }) {
          Text("View All (${ideas.size})", color = NeonPurple, fontWeight = FontWeight.SemiBold)
        }
      }
    }

    // 3 Highlighted Idea Cards
    items(ideas.take(3)) { idea ->
      IdeaItemCard(
        idea = idea,
        onGenerateScript = { viewModel.generateScriptForIdea(idea) },
        onSave = { viewModel.saveIdeaToProject(idea) }
      )
    }

    // Top Videos & AI Insights Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Top Videos
        Card(
          modifier = Modifier
            .weight(1f)
            .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Top Videos",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Spacer(modifier = Modifier.height(10.dp))
            health.topVideos.forEach { video ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = video.title,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = TextWhite),
                    maxLines = 1
                  )
                  Text(video.views, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
                }
                Surface(
                  color = SuccessGreen.copy(alpha = 0.15f),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = "${video.multiplier}×",
                    style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
              HorizontalDivider(color = ObsidianCardBorder, modifier = Modifier.padding(vertical = 4.dp))
            }
          }
        }

        // AI Insights
        Card(
          modifier = Modifier
            .weight(1f)
            .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "AI Insights",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Spacer(modifier = Modifier.height(10.dp))
            InsightBullet("Strongest Topic", "Practical AI Tools", SuccessGreen)
            InsightBullet("Strongest Format", "Shorts (< 45 sec)", NeonPurple)
            InsightBullet("Best Hook Style", "Problem Framing", WarningAmber)
            InsightBullet("Fastest Velocity", "Saturday 11 AM", ElectricViolet)
          }
        }
      }
    }

    // Channel Growth Minimal Aesthetic Chart preview
    item {
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
            Column {
              Text(
                text = "Channel Growth Momentum",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
              )
              Text("Views up +42% over previous 30-day baseline", style = MaterialTheme.typography.bodySmall.copy(color = SuccessGreen))
            }

            IconButton(onClick = { viewModel.navigateTo(AppDestination.ANALYTICS) }) {
              Icon(Icons.Default.Insights, contentDescription = null, tint = NeonPurple)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Clean Minimal Chart Canvas
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(90.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
              .padding(horizontal = 12.dp, vertical = 8.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxSize(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Bottom
            ) {
              val heights = listOf(0.35f, 0.42f, 0.38f, 0.55f, 0.48f, 0.65f, 0.72f, 0.68f, 0.88f, 0.95f)
              heights.forEachIndexed { index, h ->
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Bottom,
                  modifier = Modifier.fillMaxHeight()
                ) {
                  Box(
                    modifier = Modifier
                      .width(18.dp)
                      .fillMaxHeight(h)
                      .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                      .background(
                        if (index >= heights.size - 3) Brush.verticalGradient(listOf(NeonPurple, ElectricViolet))
                        else Brush.verticalGradient(listOf(ElectricViolet.copy(alpha = 0.5f), ObsidianMuted))
                      )
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun HealthSubMetricRow(label: String, score: Int) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(label, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
      Text("$score%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = TextWhite))
    }
    Spacer(modifier = Modifier.height(3.dp))
    LinearProgressIndicator(
      progress = { score / 100f },
      color = if (score >= 80) ElectricViolet else WarningAmber,
      trackColor = ObsidianElevated,
      modifier = Modifier
        .fillMaxWidth()
        .height(5.dp)
        .clip(RoundedCornerShape(3.dp))
    )
  }
}

@Composable
fun InsightBullet(label: String, value: String, accentColor: Color) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(8.dp)
        .clip(CircleShape)
        .background(accentColor)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
      Text(value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = TextWhite))
    }
  }
}
