package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GeneratedScript
import com.example.data.model.ScriptScene
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun ScriptGeneratorScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val script by viewModel.activeScript.collectAsState()
  val clipboardManager = LocalClipboardManager.current

  val lengths = listOf("15 sec", "30 sec", "60 sec", "3 min", "5 min", "10 min")
  val tones = listOf("Educational", "Funny", "Storytelling", "Dramatic", "Professional", "Motivational")

  var selectedLength by remember { mutableStateOf(script?.length ?: "60 sec") }
  var selectedTone by remember { mutableStateOf(script?.tone ?: "Educational") }
  var activePreviewSceneIndex by remember { mutableStateOf(0) }
  var isPlayingPreview by remember { mutableStateOf(false) }

  val activeScript = script ?: return

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
            text = "AI Script & Asset Studio",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "High-retention structure with scene prompts and Creator Pack export",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }

        IconButton(onClick = { viewModel.setExportDialog(true) }) {
          Icon(Icons.Default.Inventory2, contentDescription = "Creator Pack", tint = NeonPurple)
        }
      }
    }

    // Title Card
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
            text = "Target Video Title",
            style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = activeScript.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
        }
      }
    }

    // Length Selector
    item {
      Column {
        Text("Target Length", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(lengths) { len ->
            FilterChip(
              selected = selectedLength == len,
              onClick = {
                selectedLength = len
                viewModel.regenerateScript(selectedLength, selectedTone)
              },
              label = { Text(len) },
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
    }

    // Tone Selector
    item {
      Column {
        Text("Pacing & Tone", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(tones) { tone ->
            FilterChip(
              selected = selectedTone == tone,
              onClick = {
                selectedTone = tone
                viewModel.regenerateScript(selectedLength, selectedTone)
              },
              label = { Text(tone) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = IndigoAccent,
                selectedLabelColor = TextWhite,
                containerColor = ObsidianCard,
                labelColor = TextGrayLight
              )
            )
          }
        }
      }
    }

    // Real-Time Script Score Card (Required feature)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, Brush.horizontalGradient(listOf(ElectricViolet.copy(alpha = 0.5f), ObsidianCardBorder)), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Script Scorecard",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = SuccessGreen.copy(alpha = 0.15f)
            ) {
              Text(
                text = "${activeScript.overallScore} / 100",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = SuccessGreen),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // 4 Score Metrics: Hook (92), Retention Potential (87), Clarity (94), CTA (81)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            ScriptScorePill("Hook", activeScript.hookScore, SuccessGreen, Modifier.weight(1f))
            ScriptScorePill("Retention", activeScript.retentionScore, NeonPurple, Modifier.weight(1f))
            ScriptScorePill("Clarity", activeScript.clarityScore, ElectricViolet, Modifier.weight(1f))
            ScriptScorePill("CTA", activeScript.ctaScore, WarningAmber, Modifier.weight(1f))
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Actionable advice to improve script
          Text(
            text = "AI Improvement Recommendation:",
            style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(4.dp))
          activeScript.improvementTips.forEach { tip ->
            Text(
              text = "• $tip",
              style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight, lineHeight = 18.sp),
              modifier = Modifier.padding(vertical = 2.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                viewModel.regenerateScript(selectedLength, selectedTone)
                viewModel.showToast("Applied AI retention enhancements!")
              },
              colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.AutoFixHigh, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Improve Script", fontSize = 12.sp)
            }

            OutlinedButton(
              onClick = {
                val fullText = "Title: ${activeScript.title}\n\nHook: ${activeScript.hook}\n\n" +
                  activeScript.scenes.joinToString("\n\n") { "[${it.timeRange}]\nVoiceover: ${it.voiceover}\nVisual: ${it.visualDescription}" } +
                  "\n\nCTA: ${activeScript.cta}"
                clipboardManager.setText(AnnotatedString(fullText))
                viewModel.showToast("Script copied to clipboard!")
              },
              shape = RoundedCornerShape(8.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGrayLight)
            ) {
              Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Copy", fontSize = 12.sp)
            }

            Button(
              onClick = { viewModel.saveCurrentScriptToProject() },
              colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
              shape = RoundedCornerShape(8.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder)
            ) {
              Icon(Icons.Default.Bookmark, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Save", color = TextWhite, fontSize = 12.sp)
            }
          }
        }
      }
    }

    // Video Export MVP — Interactive Scene Flow Preview Player
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.SmartDisplay, contentDescription = null, tint = YouTubeRed)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Scene-by-Scene Flow Preview",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = ObsidianElevated
            ) {
              Text(
                text = "Scene ${activePreviewSceneIndex + 1} of ${activeScript.scenes.size}",
                style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Simulated Video Canvas
          val currentScene = activeScript.scenes.getOrElse(activePreviewSceneIndex) { activeScript.scenes.first() }
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(170.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Brush.verticalGradient(listOf(ObsidianDark, ObsidianElevated)))
              .border(1.dp, ObsidianCardBorder, RoundedCornerShape(12.dp))
              .padding(14.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = ElectricViolet.copy(alpha = 0.2f),
                modifier = Modifier.padding(bottom = 8.dp)
              ) {
                Text(
                  text = "Timing: ${currentScene.timeRange}",
                  style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
              }

              Text(
                text = currentScene.caption,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextWhite,
                  textAlign = TextAlign.Center
                )
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "Voiceover: \"${currentScene.voiceover}\"",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextGrayLight,
                  textAlign = TextAlign.Center,
                  fontSize = 11.sp
                ),
                maxLines = 2
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Controls: Previous Scene, Play, Next Scene, Download Pack
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(
              onClick = {
                if (activePreviewSceneIndex > 0) activePreviewSceneIndex--
              },
              enabled = activePreviewSceneIndex > 0
            ) {
              Icon(Icons.Default.SkipPrevious, contentDescription = "Prev", tint = if (activePreviewSceneIndex > 0) TextWhite else TextGrayDark)
            }

            Button(
              onClick = {
                isPlayingPreview = !isPlayingPreview
                if (activePreviewSceneIndex < activeScript.scenes.size - 1) {
                  activePreviewSceneIndex++
                } else {
                  activePreviewSceneIndex = 0
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
              shape = RoundedCornerShape(20.dp)
            ) {
              Icon(if (isPlayingPreview) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(if (isPlayingPreview) "Next Scene" else "Play Flow")
            }

            IconButton(
              onClick = {
                if (activePreviewSceneIndex < activeScript.scenes.size - 1) activePreviewSceneIndex++
              },
              enabled = activePreviewSceneIndex < activeScript.scenes.size - 1
            ) {
              Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = if (activePreviewSceneIndex < activeScript.scenes.size - 1) TextWhite else TextGrayDark)
            }

            Button(
              onClick = { viewModel.setExportDialog(true) },
              colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Download, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Creator Pack", color = TextWhite, fontSize = 11.sp)
            }
          }
        }
      }
    }

    // Pattern Interrupts
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Pacing & Pattern Interrupts",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          activeScript.patternInterrupts.forEach { interrupt ->
            Text(
              text = "⚡ $interrupt",
              style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight),
              modifier = Modifier.padding(vertical = 3.dp)
            )
          }
        }
      }
    }

    // Full Scene Breakdown List
    item {
      Text(
        text = "Complete Scene Breakdown",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
      )
    }

    items(activeScript.scenes) { scene ->
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
            Text(
              text = "Scene ${scene.sceneNumber} (${scene.timeRange})",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = NeonPurple)
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = ObsidianElevated
            ) {
              Text(
                text = "SFX: ${scene.soundEffect}",
                style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Voiceover:",
            style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted, fontWeight = FontWeight.SemiBold)
          )
          Text(
            text = "\"${scene.voiceover}\"",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Visual Cue / AI Image Prompt:",
            style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted, fontWeight = FontWeight.SemiBold)
          )
          Text(
            text = scene.imagePrompt,
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
          )
        }
      }
    }

    // Call to Action
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SuccessGreen.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Natural Call to Action (CTA)",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = SuccessGreen)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "\"${activeScript.cta}\"",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite)
          )
        }
      }
    }
  }
}

@Composable
fun ScriptScorePill(label: String, score: Int, color: Color, modifier: Modifier = Modifier) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = ObsidianElevated,
    modifier = modifier
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted, fontSize = 10.sp))
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "$score",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = color)
      )
    }
  }
}
