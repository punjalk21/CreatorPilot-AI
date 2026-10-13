package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun LandingScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    // SaaS Navbar
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(ElectricViolet, VioletGlow))),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = TextWhite, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "CreatorPilot AI",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          TextButton(onClick = { viewModel.navigateTo(AppDestination.AUTH) }) {
            Text("Log In", color = TextGrayLight)
          }
          Button(
            onClick = { viewModel.navigateTo(AppDestination.ONBOARDING) },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text("Try Free", fontSize = 12.sp)
          }
        }
      }
    }

    // Hero Section
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = ElectricViolet.copy(alpha = 0.2f),
          border = androidx.compose.foundation.BorderStroke(1.dp, ElectricViolet.copy(alpha = 0.5f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Bolt, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Grow your YouTube channel with AI",
              style = MaterialTheme.typography.labelMedium.copy(color = NeonPurple, fontWeight = FontWeight.SemiBold)
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
          text = "Stop Guessing.\nStart Growing.",
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = TextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 42.sp
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "CreatorPilot AI is an AI YouTube Growth Strategist that analyzes your channel and tells you exactly what to improve and what to create next.",
          style = MaterialTheme.typography.bodyLarge.copy(
            color = TextGrayLight,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
          ),
          modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = { viewModel.navigateTo(AppDestination.ONBOARDING) },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Analyze My Channel →", fontWeight = FontWeight.Bold, fontSize = 15.sp)
          }

          OutlinedButton(
            onClick = { viewModel.navigateTo(AppDestination.DASHBOARD) },
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGrayLight),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Explore Live Demo Dashboard")
          }
        }
      }
    }

    // Core Workflow Banner (Analyze -> Discover -> Plan -> Create -> Publish -> Track -> Improve)
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
            text = "The Closed-Loop Growth Workflow",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Spacer(modifier = Modifier.height(12.dp))
          val steps = listOf(
            "1. Analyze" to "Scan video patterns, retention drop-offs, and title CTRs.",
            "2. Discover" to "Uncover your biggest statistical growth opportunities.",
            "3. Plan" to "Build a 30-day calendar balanced for reach & authority.",
            "4. Create" to "Generate hook-first scripts with visual scene prompts.",
            "5. Export" to "1-click download Creator Pack with assets & SRT captions.",
            "6. Improve" to "AI Performance Coach adapts to your real audience."
          )
          steps.forEach { (title, desc) ->
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
              Text(title, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = NeonPurple), modifier = Modifier.width(90.dp))
              Text(desc, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
            }
          }
        }
      }
    }

    // Feature Highlights Grid
    item {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LandingFeatureCard(
          icon = Icons.Default.HealthAndSafety,
          title = "Channel Health Score",
          description = "Deep diagnostic score from 0-100 covering content, titles, thumbnails, and upload consistency."
        )
        LandingFeatureCard(
          icon = Icons.Default.RocketLaunch,
          title = "AI Opportunity Report",
          description = "Identifies outlier topics that outperform your baseline by 2x-3x with transparent algorithmic evidence."
        )
        LandingFeatureCard(
          icon = Icons.Default.AutoFixHigh,
          title = "High-Retention Script Studio",
          description = "Structured scene-by-scene scripts with pattern interrupts, scorecards, and ready-to-use visual prompts."
        )
        LandingFeatureCard(
          icon = Icons.Default.Download,
          title = "1-Click Creator Packs",
          description = "Export scripts, captions, voiceover cues, and thumbnail concepts straight to your production suite."
        )
      }
    }
  }
}

@Composable
fun LandingFeatureCard(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  description: String
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianCard)
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(ElectricViolet.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.width(14.dp))
      Column {
        Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite))
        Spacer(modifier = Modifier.height(4.dp))
        Text(description, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight, lineHeight = 18.sp))
      }
    }
  }
}
