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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompetitorChannel
import com.example.data.model.ContentIdea
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun CompetitorScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val competitor by viewModel.activeCompetitor.collectAsState()
  var competitorInput by remember { mutableStateOf("@Fireship") }

  val activeComp = competitor ?: CompetitorChannel(
    name = "Fireship Media",
    handle = "@Fireship",
    subscribers = "3.2M",
    avgViews = "420K",
    uploadCadence = "2x / week",
    topThemes = listOf("High-speed code news", "100 seconds of tech", "Framework drama"),
    contentGaps = listOf(
      "Mobile developer workflows & on-device AI apps",
      "Beginner step-by-step implementations (they focus on speed overview)",
      "Zero-budget hosting & free open-source database setups"
    )
  )

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
          text = "Competitor Intelligence & Content Gaps",
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
        )
        Text(
          text = "Uncover untapped angles in your niche without copying",
          style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
        )
      }
    }

    // Input Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Enter Competitor YouTube Handle or URL",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = competitorInput,
            onValueChange = { competitorInput = it },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = ElectricViolet,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextWhite,
              unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(10.dp)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = { viewModel.analyzeCompetitor(competitorInput) },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.TravelExplore, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Analyze Competitor & Extract Gaps", fontSize = 13.sp)
          }
        }
      }
    }

    // Competitor Profile Card
    item {
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
            Column {
              Text(activeComp.name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite))
              Text(activeComp.handle, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
            }
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = ObsidianElevated
            ) {
              Text(
                text = "${activeComp.subscribers} Subscribers",
                style = MaterialTheme.typography.labelSmall.copy(color = TextWhite, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("Upload Cadence", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
              Text(activeComp.uploadCadence, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite))
            }
            Column {
              Text("Avg Views / Video", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
              Text(activeComp.avgViews, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = SuccessGreen))
            }
          }
        }
      }
    }

    // Top Themes
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Their Dominant Content Themes",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Spacer(modifier = Modifier.height(8.dp))
          activeComp.topThemes.forEach { theme ->
            Text(
              text = "• $theme",
              style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight),
              modifier = Modifier.padding(vertical = 3.dp)
            )
          }
        }
      }
    }

    // Content Gaps (Untapped Angles for YOU)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, NeonPurple.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Content Gaps (Original Angles for You)",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Topics their viewers frequently ask for in comments that this competitor does not cover:",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )

          Spacer(modifier = Modifier.height(12.dp))

          activeComp.contentGaps.forEach { gap ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = ObsidianElevated,
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text("💎", modifier = Modifier.padding(end = 8.dp))
                Text(
                  text = gap,
                  style = MaterialTheme.typography.bodyMedium.copy(color = TextWhite, fontWeight = FontWeight.SemiBold)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = {
              viewModel.generateScriptForIdea(
                ContentIdea(
                  id = "gap_" + System.currentTimeMillis(),
                  title = "The Tool ${activeComp.name.substringBefore(" ")} Missed (Full Setup)",
                  hook = "Everyone watched the 100-second video, but nobody showed how to actually install it for free.",
                  opportunityScore = 93,
                  format = "Shorts",
                  audience = "Developers & Builders",
                  difficulty = "Easy",
                  whyItFits = "Fills an acute audience content gap left by top competitors.",
                  tags = listOf("Competitor Gap", "Tutorial")
                )
              )
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Generate Content to Fill This Gap")
          }
        }
      }
    }
  }
}
