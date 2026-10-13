package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.ContentIdea
import com.example.data.model.Opportunity
import com.example.ui.theme.*

@Composable
fun CircularScoreGauge(
  score: Int,
  modifier: Modifier = Modifier,
  strokeWidth: Dp = 10.dp,
  size: Dp = 100.dp,
  scoreColor: Color = ElectricViolet
) {
  val animatedProgress by animateFloatAsState(
    targetValue = score / 100f,
    animationSpec = tween(durationMillis = 1000),
    label = "scoreGauge"
  )

  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier.size(size)
  ) {
    Canvas(modifier = Modifier.size(size)) {
      // Track
      drawArc(
        color = Color(0xFF222538),
        startAngle = 140f,
        sweepAngle = 260f,
        useCenter = false,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
      )
      // Progress
      drawArc(
        brush = Brush.sweepGradient(
          listOf(ElectricViolet, VioletGlow, NeonPurple, ElectricViolet)
        ),
        startAngle = 140f,
        sweepAngle = 260f * animatedProgress,
        useCenter = false,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
      )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = "$score",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          color = TextWhite
        )
      )
      Text(
        text = "/ 100",
        style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted)
      )
    }
  }
}

@Composable
fun OpportunityHeroCard(
  opportunity: Opportunity,
  onGenerateIdeas: () -> Unit,
  onWhyThisMatters: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, Brush.horizontalGradient(listOf(ElectricViolet.copy(alpha = 0.6f), ObsidianCardBorder)), RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianCard)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(ElectricViolet.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.RocketLaunch,
              contentDescription = null,
              tint = NeonPurple,
              modifier = Modifier.size(18.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Your Biggest Opportunity",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TextWhite
            )
          )
        }

        Surface(
          shape = RoundedCornerShape(20.dp),
          color = ElectricViolet.copy(alpha = 0.2f)
        ) {
          Text(
            text = "${opportunity.multiplier}× Multiplier",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              color = NeonPurple
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = opportunity.title,
        style = MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.SemiBold,
          color = TextWhite,
          lineHeight = 22.sp
        )
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Recommended action: " + opportunity.recommendedAction,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = TextGrayLight,
          lineHeight = 20.sp
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onGenerateIdeas,
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Generate Ideas", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }

        OutlinedButton(
          onClick = onWhyThisMatters,
          border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.horizontalGradient(listOf(ObsidianCardBorder, ObsidianCardBorder))
          ),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGrayLight),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Outlined.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextGrayLight)
          Spacer(modifier = Modifier.width(6.dp))
          Text("Why This Matters", fontSize = 13.sp)
        }
      }
    }
  }
}

@Composable
fun IdeaItemCard(
  idea: ContentIdea,
  onGenerateScript: () -> Unit,
  onSave: () -> Unit,
  modifier: Modifier = Modifier
) {
  var expandedScore by remember { mutableStateOf(false) }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianCard)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Header: Tags + Opportunity Score Chip
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = if (idea.format == "Shorts") YouTubeRed.copy(alpha = 0.2f) else ElectricViolet.copy(alpha = 0.2f)
          ) {
            Text(
              text = idea.format,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (idea.format == "Shorts") Color(0xFFFF4D6D) else NeonPurple
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = ObsidianElevated
          ) {
            Text(
              text = idea.difficulty,
              style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        // Clickable Opportunity Score Pill
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = ObsidianElevated,
          modifier = Modifier.clickable { expandedScore = !expandedScore }
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(Icons.Default.Bolt, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Score: ${idea.opportunityScore}/100",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
            )
            Icon(
              imageVector = if (expandedScore) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
              contentDescription = null,
              tint = TextGrayMuted,
              modifier = Modifier.size(14.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = idea.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = TextWhite
        )
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "🪝 Hook: \"${idea.hook}\"",
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextGrayLight,
          lineHeight = 18.sp
        )
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "💡 Why this fits: ${idea.whyItFits}",
        style = MaterialTheme.typography.bodySmall.copy(
          color = NeonPurple,
          lineHeight = 17.sp
        )
      )

      // Transparent Score Breakdown Drawer if clicked
      if (expandedScore) {
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = ObsidianElevated,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text(
              text = "Opportunity Score Breakdown (0-100)",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("• Relevance: ${idea.breakdown.relevance}%", style = MaterialTheme.typography.bodySmall, color = TextGrayLight)
              Text("• Audience Fit: ${idea.breakdown.audienceFit}%", style = MaterialTheme.typography.bodySmall, color = TextGrayLight)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("• Historical Momentum: ${idea.breakdown.historicalPerformance}%", style = MaterialTheme.typography.bodySmall, color = TextGrayLight)
              Text("• Content Gap: ${idea.breakdown.contentGap}%", style = MaterialTheme.typography.bodySmall, color = TextGrayLight)
            }
            Text(
              text = "Transparent algorithmic score based on channel history, not guaranteed view predictions.",
              style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted),
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onGenerateScript,
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Generate Script", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }

        OutlinedButton(
          onClick = onSave,
          border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.horizontalGradient(listOf(ObsidianCardBorder, ObsidianCardBorder))
          ),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGrayLight),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(Icons.Outlined.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Save", fontSize = 12.sp)
        }
      }
    }
  }
}

@Composable
fun AnalysisLoadingDialog(
  stepIndex: Int,
  onDismiss: () -> Unit
) {
  val steps = listOf(
    "Finding videos & metadata",
    "Studying content patterns & cadence",
    "Analyzing titles & curiosity hooks",
    "Comparing performance vs niche average",
    "Finding highest-leverage growth opportunities",
    "Building personalized recommendations"
  )

  Dialog(onDismissRequest = {}) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .border(1.dp, ElectricViolet.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianCard)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        CircularProgressIndicator(
          color = ElectricViolet,
          strokeWidth = 3.dp,
          modifier = Modifier.size(44.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Analyzing your channel...",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = TextWhite
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "CreatorPilot AI is inspecting public signals & performance",
          style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          steps.forEachIndexed { index, stepName ->
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth()
            ) {
              when {
                index < stepIndex -> {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(18.dp)
                  )
                }
                index == stepIndex -> {
                  CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = NeonPurple
                  )
                }
                else -> {
                  Box(
                    modifier = Modifier
                      .size(18.dp)
                      .clip(CircleShape)
                      .background(ObsidianElevated),
                    contentAlignment = Alignment.Center
                  ) {
                    Box(
                      modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(TextGrayDark)
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.width(10.dp))

              Text(
                text = stepName,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = when {
                    index < stepIndex -> TextWhite
                    index == stepIndex -> NeonPurple
                    else -> TextGrayDark
                  },
                  fontWeight = if (index == stepIndex) FontWeight.SemiBold else FontWeight.Normal
                )
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun ExplainOpportunityDialog(
  opportunity: Opportunity,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianCard)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Psychology, contentDescription = null, tint = ElectricViolet, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Why This Matters",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrayLight)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
          color = ObsidianElevated,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text("Finding", style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(2.dp))
            Text(opportunity.finding, style = MaterialTheme.typography.bodyMedium.copy(color = TextWhite))
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
          color = ObsidianElevated,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text("Data Evidence", style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(2.dp))
            Text(opportunity.evidence, style = MaterialTheme.typography.bodyMedium.copy(color = TextWhite))
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
          color = ObsidianElevated,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text("Algorithmic Explanation", style = MaterialTheme.typography.labelSmall.copy(color = WarningAmber, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(2.dp))
            Text(opportunity.explanation, style = MaterialTheme.typography.bodyMedium.copy(color = TextWhite))
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Confidence: ${opportunity.confidenceBadge}",
            style = MaterialTheme.typography.labelMedium.copy(color = NeonPurple, fontWeight = FontWeight.SemiBold)
          )
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text("Got It")
          }
        }
      }
    }
  }
}

@Composable
fun CreatorPackExportDialog(
  title: String,
  onDismiss: () -> Unit
) {
  val clipboardManager = LocalClipboardManager.current
  var copied by remember { mutableStateOf(false) }

  val packTree = """
Project/
├── script.txt (Full Scene Breakdown & Pacing)
├── voiceover.mp3 (Synthesized Audio Preview Cue)
├── captions.srt (Synchronized Subtitle Tracks)
├── thumbnail-concept.png (High-CTR Framing)
├── scene-prompts.txt (AI Visual Prompts)
├── visual-assets/ (B-Roll & Pattern Interrupts)
└── content-plan.pdf (Distribution Checklist)
  """.trimIndent()

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianCard)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Inventory2, contentDescription = null, tint = ElectricViolet)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Download Creator Pack",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrayLight)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Complete production bundle ready for YouTube publication:",
          style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
          color = ObsidianDark,
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = packTree,
            style = MaterialTheme.typography.bodySmall.copy(
              color = NeonPurple,
              fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
              fontSize = 11.sp,
              lineHeight = 16.sp
            ),
            modifier = Modifier.padding(12.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            clipboardManager.setText(AnnotatedString(packTree))
            copied = true
          },
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(if (copied) Icons.Default.Check else Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(if (copied) "Bundle Copied to Clipboard!" else "Export & Copy Creator Pack")
        }
      }
    }
  }
}
