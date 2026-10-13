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
import com.example.data.local.ProjectEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun ProjectsScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val projects by viewModel.savedProjects.collectAsState()
  var selectedStatus by remember { mutableStateOf("All") }

  val statuses = listOf("All", "Ready", "In Progress", "Draft", "Published")

  val filteredProjects = remember(projects, selectedStatus) {
    if (selectedStatus == "All") projects
    else projects.filter { it.status.equals(selectedStatus, ignoreCase = true) }
  }

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
            text = "Saved Projects & Creator Packs",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
          )
          Text(
            text = "${projects.size} saved scripts, strategies, and content packs",
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
          )
        }
      }
    }

    // Status Filter Chips
    item {
      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        statuses.forEach { status ->
          FilterChip(
            selected = selectedStatus == status,
            onClick = { selectedStatus = status },
            label = { Text(status) },
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

    if (filteredProjects.isEmpty()) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
            .border(1.dp, ObsidianCardBorder, RoundedCornerShape(16.dp)),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.FolderOpen, contentDescription = null, tint = TextGrayMuted, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No projects in this category",
              style = MaterialTheme.typography.titleMedium.copy(color = TextWhite, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Generate a script or strategy and click 'Save to Projects'",
              style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted)
            )
          }
        }
      }
    } else {
      items(filteredProjects) { project ->
        ProjectItemCard(
          project = project,
          onDelete = { viewModel.deleteProject(project.id) },
          onExportPack = { viewModel.setExportDialog(true) },
          onStatusChange = { newStatus -> viewModel.updateProjectStatus(project, newStatus) }
        )
      }
    }
  }
}

@Composable
fun ProjectItemCard(
  project: ProjectEntity,
  onDelete: () -> Unit,
  onExportPack: () -> Unit,
  onStatusChange: (String) -> Unit
) {
  var showMenu by remember { mutableStateOf(false) }

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
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = ObsidianElevated
          ) {
            Text(
              text = project.type,
              style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = when (project.status) {
              "Ready" -> SuccessGreen.copy(alpha = 0.15f)
              "In Progress" -> WarningAmber.copy(alpha = 0.15f)
              "Published" -> ElectricViolet.copy(alpha = 0.15f)
              else -> ObsidianElevated
            }
          ) {
            Text(
              text = project.status,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = when (project.status) {
                  "Ready" -> SuccessGreen
                  "In Progress" -> WarningAmber
                  "Published" -> NeonPurple
                  else -> TextGrayLight
                }
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Box {
          IconButton(onClick = { showMenu = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = TextGrayLight)
          }
          DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier.background(ObsidianCard)
          ) {
            DropdownMenuItem(
              text = { Text("Mark as In Progress", color = TextWhite) },
              onClick = {
                onStatusChange("In Progress")
                showMenu = false
              }
            )
            DropdownMenuItem(
              text = { Text("Mark as Ready", color = TextWhite) },
              onClick = {
                onStatusChange("Ready")
                showMenu = false
              }
            )
            DropdownMenuItem(
              text = { Text("Mark as Published", color = TextWhite) },
              onClick = {
                onStatusChange("Published")
                showMenu = false
              }
            )
            HorizontalDivider(color = ObsidianCardBorder)
            DropdownMenuItem(
              text = { Text("Delete Project", color = ErrorRed) },
              onClick = {
                onDelete()
                showMenu = false
              }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = project.title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextWhite)
      )

      if (project.hook.isNotBlank()) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "🪝 Hook: \"${project.hook}\"",
          style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight),
          maxLines = 2
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Format: ${project.format} • Score: ${project.score}/100",
          style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted)
        )

        Button(
          onClick = onExportPack,
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Creator Pack", fontSize = 11.sp)
        }
      }
    }
  }
}
