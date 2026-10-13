package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun ProfileScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val user by viewModel.currentUser.collectAsState()
  val channel by viewModel.currentChannel.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    item {
      Text(
        text = "Account & Settings",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
      )
    }

    // User Profile Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(ElectricViolet, IndigoAccent))),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = user.name.take(1).uppercase(),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
              Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
              )
              Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
              )
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = ElectricViolet.copy(alpha = 0.2f),
                modifier = Modifier.padding(top = 4.dp)
              ) {
                Text(
                  text = "${user.tier} Plan",
                  style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
              }
            }
          }
        }
      }
    }

    // Connected YouTube Channel Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Connected YouTube Channel", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite))
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(channel?.title ?: "Alex Tech & AI", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = TextWhite))
              Text(channel?.handle ?: "@AlexCreatesAI", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
            }
            Button(
              onClick = { viewModel.setAnalyzeChannelDialog(true) },
              colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("Switch Channel", fontSize = 12.sp, color = TextWhite)
            }
          }
        }
      }
    }

    // Actions
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          ProfileOptionRow(
            icon = Icons.Default.Upgrade,
            title = "Manage Subscription",
            subtitle = "Active plan: ${user.tier}",
            onClick = { viewModel.navigateTo(AppDestination.PRICING) }
          )
          HorizontalDivider(color = ObsidianCardBorder)
          ProfileOptionRow(
            icon = Icons.Default.Language,
            title = "CreatorPilot Web & Landing Page",
            subtitle = "View marketing site & value proposition",
            onClick = { viewModel.navigateTo(AppDestination.LANDING) }
          )
          HorizontalDivider(color = ObsidianCardBorder)
          ProfileOptionRow(
            icon = Icons.Default.PlayCircle,
            title = "Replay Onboarding Flow",
            subtitle = "Calibrate niche, goals, and format",
            onClick = { viewModel.navigateTo(AppDestination.ONBOARDING) }
          )
          HorizontalDivider(color = ObsidianCardBorder)
          ProfileOptionRow(
            icon = Icons.Default.Logout,
            title = "Log Out",
            subtitle = "Switch accounts or explore as guest",
            titleColor = ErrorRed,
            onClick = { viewModel.logout() }
          )
        }
      }
    }
  }
}

@Composable
fun ProfileOptionRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String,
  titleColor: Color = TextWhite,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp, horizontal = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(icon, contentDescription = null, tint = if (titleColor == ErrorRed) ErrorRed else NeonPurple, modifier = Modifier.size(22.dp))
    Spacer(modifier = Modifier.width(12.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = titleColor))
      Text(subtitle, style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
    }
    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGrayMuted, modifier = Modifier.size(18.dp))
  }
}
