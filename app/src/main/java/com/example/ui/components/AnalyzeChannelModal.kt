package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun AnalyzeChannelModal(
  onDismiss: () -> Unit,
  onAnalyze: (String, String) -> Unit
) {
  var handleInput by remember { mutableStateOf("@AlexCreatesAI") }
  var nicheInput by remember { mutableStateOf("AI & Technology") }
  val presets = listOf("@AlexCreatesAI", "@Fireship", "@AliAbdaal", "@Veritasium", "@MKBHD")
  val niches = listOf("AI & Technology", "Education", "Gaming", "Finance", "Productivity")

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
            Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = ElectricViolet)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Analyze YouTube Channel",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrayLight)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = handleInput,
          onValueChange = { handleInput = it },
          label = { Text("Channel URL or @Handle") },
          placeholder = { Text("e.g. @AlexCreatesAI or youtube.com/@alex") },
          modifier = Modifier.fillMaxWidth(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricViolet,
            unfocusedBorderColor = ObsidianCardBorder,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          ),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Popular Presets:", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          items(presets) { p ->
            FilterChip(
              selected = handleInput == p,
              onClick = { handleInput = p },
              label = { Text(p, fontSize = 11.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = ElectricViolet,
                selectedLabelColor = TextWhite,
                containerColor = ObsidianElevated,
                labelColor = TextGrayLight
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Select Niche:", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          items(niches) { n ->
            FilterChip(
              selected = nicheInput == n,
              onClick = { nicheInput = n },
              label = { Text(n, fontSize = 11.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = IndigoAccent,
                selectedLabelColor = TextWhite,
                containerColor = ObsidianElevated,
                labelColor = TextGrayLight
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = { onAnalyze(handleInput, nicheInput) },
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Run Deep AI Channel Audit →", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
