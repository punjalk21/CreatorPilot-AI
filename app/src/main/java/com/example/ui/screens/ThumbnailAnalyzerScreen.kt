package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.model.ThumbnailAnalysis
import com.example.ui.components.CircularScoreGauge
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun ThumbnailAnalyzerScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val thumbnailAnalysis by viewModel.activeThumbnail.collectAsState()
  var inputConcept by remember { mutableStateOf("Top 5 Free AI Tools in 2026 (Shocked face with purple glow)") }

  val analysis = thumbnailAnalysis ?: ThumbnailAnalysis("demo")

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
          text = "AI Thumbnail Studio & Audit",
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
        )
        Text(
          text = "Audits mobile viewport legibility, focal contrast, and curiosity appeal",
          style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
        )
      }
    }

    // Input Concept Card
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
            text = "Enter Thumbnail Concept / Framing",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = inputConcept,
            onValueChange = { inputConcept = it },
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
            onClick = { viewModel.analyzeThumbnail(inputConcept) },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.PhotoSizeSelectActual, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Analyze & Score Thumbnail", fontSize = 13.sp)
          }
        }
      }
    }

    // Overall Score Card
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
            .padding(18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          CircularScoreGauge(
            score = analysis.overallScore,
            size = 100.dp,
            strokeWidth = 9.dp
          )

          Spacer(modifier = Modifier.width(20.dp))

          Column {
            Text(
              text = "Overall Thumbnail Score",
              style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted)
            )
            Text(
              text = "${analysis.overallScore} / 100",
              style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = SuccessGreen.copy(alpha = 0.15f),
              modifier = Modifier.padding(top = 4.dp)
            ) {
              Text(
                text = "Strong Click-Through Potential",
                style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }

    // 7 Metric Breakdown Card (Mobile readability, Text size, Visual hierarchy, Subject clarity, Contrast, Curiosity, Composition)
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
            text = "Diagnostic Metrics Breakdown",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Spacer(modifier = Modifier.height(14.dp))

          ThumbnailMetricRow("Mobile Readability (120px scale)", analysis.mobileReadability)
          ThumbnailMetricRow("Text Size & Overlap Safety", analysis.textSize)
          ThumbnailMetricRow("Visual Hierarchy", analysis.visualHierarchy)
          ThumbnailMetricRow("Subject Clarity & Focal Point", analysis.subjectClarity)
          ThumbnailMetricRow("Foreground/Background Contrast", analysis.contrast)
          ThumbnailMetricRow("Curiosity & Intrigue Index", analysis.curiosity)
          ThumbnailMetricRow("Rule-of-Thirds Composition", analysis.composition)
        }
      }
    }

    // Specific, Actionable Critique
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
            Icon(Icons.Default.RateReview, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Specific Actionable Critique",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          analysis.critique.forEach { item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
              verticalAlignment = Alignment.Top
            ) {
              Text("•", color = WarningAmber, modifier = Modifier.padding(end = 8.dp))
              Text(
                text = item,
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight, lineHeight = 18.sp)
              )
            }
          }
        }
      }
    }

    // 3 Generated Concepts
    item {
      Text(
        text = "3 High-Converting Visual Concepts",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
      )
    }

    items(analysis.generatedConcepts) { concept ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = concept,
            style = MaterialTheme.typography.bodyMedium.copy(color = TextWhite, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp)
          )
        }
      }
    }
  }
}

@Composable
fun ThumbnailMetricRow(label: String, score: Int) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(label, style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight))
      Text("$score / 100", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = TextWhite))
    }
    Spacer(modifier = Modifier.height(3.dp))
    LinearProgressIndicator(
      progress = { score / 100f },
      color = if (score >= 85) SuccessGreen else if (score >= 75) ElectricViolet else WarningAmber,
      trackColor = ObsidianElevated,
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp))
    )
  }
}
