package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun AuthScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  var email by remember { mutableStateOf("punjal2177@gmail.com") }
  var password by remember { mutableStateOf("password123") }
  var isSignUp by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianDark)
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
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
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(Brush.linearGradient(listOf(ElectricViolet, NeonPurple))),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = TextWhite, modifier = Modifier.size(28.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = if (isSignUp) "Join CreatorPilot AI" else "Welcome to CreatorPilot AI",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Grow your YouTube channel with AI",
          style = MaterialTheme.typography.bodySmall.copy(color = TextGrayMuted),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Google Sign In Button
        Button(
          onClick = { viewModel.loginGoogle() },
          colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianCardBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("G", fontWeight = FontWeight.ExtraBold, color = TextWhite, fontSize = 16.sp)
          Spacer(modifier = Modifier.width(10.dp))
          Text("Continue with Google", color = TextWhite, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          HorizontalDivider(modifier = Modifier.weight(1f), color = ObsidianCardBorder)
          Text("  or  ", style = MaterialTheme.typography.labelSmall.copy(color = TextGrayMuted))
          HorizontalDivider(modifier = Modifier.weight(1f), color = ObsidianCardBorder)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Email address") },
          leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = TextGrayMuted) },
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

        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Password") },
          leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TextGrayMuted) },
          visualTransformation = PasswordVisualTransformation(),
          modifier = Modifier.fillMaxWidth(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricViolet,
            unfocusedBorderColor = ObsidianCardBorder,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          ),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = { viewModel.loginEmail(email) },
          colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(if (isSignUp) "Create Account" else "Log In", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = { viewModel.guestLogin() }) {
          Text("Continue as Guest (Explore Demo)", color = NeonPurple)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { isSignUp = !isSignUp }) {
          Text(
            text = if (isSignUp) "Already have an account? Log in" else "Don't have an account? Sign up",
            color = TextGrayLight,
            fontSize = 12.sp
          )
        }
      }
    }
  }
}
