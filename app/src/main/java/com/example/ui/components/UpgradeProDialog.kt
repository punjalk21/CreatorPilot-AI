package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun UpgradeProDialog(
  onDismiss: () -> Unit,
  onUpgrade: (String) -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .border(2.dp, Brush.horizontalGradient(listOf(ElectricViolet, NeonPurple)), RoundedCornerShape(20.dp)),
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
            Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = NeonPurple)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Upgrade to Pro",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrayLight)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Unlock the complete AI YouTube Growth Engine for ₹499/month.",
          style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayLight)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val perks = listOf(
          "Unlimited High-Opportunity Content Ideas",
          "20 Scene-by-Scene AI Scripts per Month",
          "Full AI Channel Health & Opportunity Reports",
          "AI Performance Coach Outlier Analysis",
          "30-Day Growth Strategy Calendar",
          "1-Click Creator Pack Downloads & Exports"
        )

        perks.forEach { perk ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(perk, style = MaterialTheme.typography.bodySmall.copy(color = TextWhite))
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = { onUpgrade("Pro") },
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Upgrade to Pro — ₹499/mo", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        TextButton(
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Keep Current Plan", color = TextGrayMuted, fontSize = 12.sp)
        }
      }
    }
  }
}
