package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun OnboardingScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  var currentStep by remember { mutableStateOf(1) } // 1, 2, 3

  // Step 1: Goals
  var selectedGoal by remember { mutableStateOf("Get more views") }
  val goals = listOf(
    "Get more views",
    "Get more subscribers",
    "Grow Shorts",
    "Grow long-form",
    "Monetize my channel",
    "Grow a new channel"
  )

  // Step 2: Channel Handle
  var channelHandleInput by remember { mutableStateOf("@AlexCreatesAI") }
  val channelPresets = listOf("@AlexCreatesAI", "@Fireship", "@AliAbdaal", "@Veritasium", "@MKBHD")

  // Step 3: Niche & Details
  var selectedNiche by remember { mutableStateOf("AI & Technology") }
  val niches = listOf("AI & Technology", "Education", "Gaming", "Finance & Wealth", "Productivity", "Shorts Entertainment", "Lifestyle")

  var uploadCadence by remember { mutableStateOf("3x / week") }
  val cadences = listOf("Daily", "3x / week", "1x / week", "Bi-weekly")

  var formatPreference by remember { mutableStateOf("Both Shorts & Long-Form") }
  val formats = listOf("Shorts Priority", "Long-Form Priority", "Both Shorts & Long-Form")

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(20.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    // Header & Step indicator
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (currentStep > 1) {
          IconButton(onClick = { currentStep-- }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
          }
        } else {
          Spacer(modifier = Modifier.width(48.dp))
        }

        Text(
          text = "Step $currentStep of 3",
          style = MaterialTheme.typography.labelLarge.copy(color = NeonPurple, fontWeight = FontWeight.Bold)
        )

        TextButton(onClick = { viewModel.navigateTo(AppDestination.DASHBOARD) }) {
          Text("Skip", color = TextGrayMuted)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      LinearProgressIndicator(
        progress = { currentStep / 3f },
        color = ElectricViolet,
        trackColor = ObsidianElevated,
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
      )
    }

    when (currentStep) {
      1 -> {
        item {
          Column {
            Text(
              text = "What's your primary YouTube goal?",
              style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "CreatorPilot tailors your recommendations and opportunity scores to this objective.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted)
            )
          }
        }

        items(goals) { goal ->
          val isSelected = selectedGoal == goal
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { selectedGoal = goal }
              .border(
                1.dp,
                if (isSelected) ElectricViolet else ObsidianCardBorder,
                RoundedCornerShape(14.dp)
              ),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isSelected) ElectricViolet.copy(alpha = 0.15f) else ObsidianCard
            )
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = goal,
                style = MaterialTheme.typography.bodyLarge.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) TextWhite else TextGrayLight
                )
              )
              if (isSelected) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NeonPurple)
              }
            }
          }
        }

        item {
          Button(
            onClick = { currentStep = 2 },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Next: Connect Channel", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
          }
        }
      }

      2 -> {
        item {
          Column {
            Text(
              text = "What's your YouTube channel?",
              style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Enter your YouTube handle or URL. We only read public signals — no private login required.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted)
            )
          }
        }

        item {
          OutlinedTextField(
            value = channelHandleInput,
            onValueChange = { channelHandleInput = it },
            label = { Text("Channel Handle or URL") },
            placeholder = { Text("e.g. @AlexCreatesAI or youtube.com/@channel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = ElectricViolet,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextWhite,
              unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(12.dp)
          )
        }

        item {
          Column {
            Text("Or select a demo preset:", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(channelPresets) { preset ->
                FilterChip(
                  selected = channelHandleInput == preset,
                  onClick = { channelHandleInput = preset },
                  label = { Text(preset) },
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

        item {
          Button(
            onClick = { currentStep = 3 },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Next: Channel Calibration", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
          }
        }
      }

      3 -> {
        item {
          Column {
            Text(
              text = "Fine-tune your channel profile",
              style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Confirm your niche and upload cadence so recommendations are laser-targeted.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted)
            )
          }
        }

        item {
          Text("Channel Niche", style = MaterialTheme.typography.titleSmall.copy(color = TextWhite, fontWeight = FontWeight.SemiBold))
          Spacer(modifier = Modifier.height(8.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(niches) { niche ->
              FilterChip(
                selected = selectedNiche == niche,
                onClick = { selectedNiche = niche },
                label = { Text(niche) },
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

        item {
          Text("Target Upload Frequency", style = MaterialTheme.typography.titleSmall.copy(color = TextWhite, fontWeight = FontWeight.SemiBold))
          Spacer(modifier = Modifier.height(8.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(cadences) { cad ->
              FilterChip(
                selected = uploadCadence == cad,
                onClick = { uploadCadence = cad },
                label = { Text(cad) },
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

        item {
          Text("Format Focus", style = MaterialTheme.typography.titleSmall.copy(color = TextWhite, fontWeight = FontWeight.SemiBold))
          Spacer(modifier = Modifier.height(8.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(formats) { fmt ->
              FilterChip(
                selected = formatPreference == fmt,
                onClick = { formatPreference = fmt },
                label = { Text(fmt) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = NeonPurple,
                  selectedLabelColor = ObsidianDark,
                  containerColor = ObsidianCard,
                  labelColor = TextGrayLight
                )
              )
            }
          }
        }

        item {
          Spacer(modifier = Modifier.height(10.dp))
          Button(
            onClick = {
              viewModel.analyzeChannel(channelHandleInput, selectedNiche)
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Analyze My Channel →", fontWeight = FontWeight.Bold, fontSize = 15.sp)
          }
        }
      }
    }
  }
}
