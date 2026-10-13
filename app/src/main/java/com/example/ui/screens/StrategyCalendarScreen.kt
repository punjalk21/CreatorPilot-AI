package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.data.model.StrategyDay
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun StrategyCalendarScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val days by viewModel.strategyDays.collectAsState()
  var filterObjective by remember { mutableStateOf("All") }

  val objectives = listOf("All", "Reach", "Retention", "Authority", "Conversion")

  val filteredDays = remember(days, filterObjective) {
    if (filterObjective == "All") days
    else days.filter { it.contentObjective.contains(filterObjective, ignoreCase = true) }
  }

  val completedCount = remember(days) { days.count { it.isCompleted } }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
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
            text = "30-Day Growth Strategy",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "Structured cadence balanced across Reach, Retention, and Authority",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }

        IconButton(onClick = { viewModel.regenerateStrategy() }) {
          Icon(Icons.Default.Refresh, contentDescription = "Regenerate", tint = NeonPurple)
        }
      }
    }

    // Progress Bar Card
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
            Text(
              text = "Execution Progress",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Text(
              text = "$completedCount of ${days.size} Days Completed",
              style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { if (days.isNotEmpty()) completedCount.toFloat() / days.size else 0f },
            color = ElectricViolet,
            trackColor = ObsidianElevated,
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp))
          )

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { viewModel.saveStrategyToProjects() },
              colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Save Strategy", fontSize = 12.sp)
            }

            OutlinedButton(
              onClick = { viewModel.regenerateStrategy() },
              shape = RoundedCornerShape(8.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGrayLight)
            ) {
              Text("Regenerate Plan", fontSize = 12.sp)
            }
          }
        }
      }
    }

    // Objective Filter Chips
    item {
      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        objectives.forEach { obj ->
          FilterChip(
            selected = filterObjective == obj,
            onClick = { filterObjective = obj },
            label = { Text(obj) },
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

    // Days List
    items(filteredDays) { day ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(
            1.dp,
            if (day.isCompleted) SuccessGreen.copy(alpha = 0.4f) else ObsidianCardBorder,
            RoundedCornerShape(12.dp)
          ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (day.isCompleted) ObsidianCard.copy(alpha = 0.8f) else ObsidianCard
        )
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              IconButton(
                onClick = { viewModel.toggleStrategyDay(day.dayNumber) },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(
                  imageVector = if (day.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                  contentDescription = "Toggle Complete",
                  tint = if (day.isCompleted) SuccessGreen else TextGrayDark,
                  modifier = Modifier.size(22.dp)
                )
              }
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = day.dateLabel,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (day.isCompleted) SuccessGreen else TextWhite
                )
              )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (day.format == "Shorts") YouTubeRed.copy(alpha = 0.2f) else ElectricViolet.copy(alpha = 0.2f)
              ) {
                Text(
                  text = day.format,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (day.format == "Shorts") Color(0xFFFF4D6D) else NeonPurple
                  ),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }

              Surface(
                shape = RoundedCornerShape(6.dp),
                color = ObsidianElevated
              ) {
                Text(
                  text = day.contentObjective,
                  style = MaterialTheme.typography.labelSmall.copy(color = TextGrayLight),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = day.suggestedTitle,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Hook Angle: \"${day.hook}\"",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
          )
        }
      }
    }
  }
}
