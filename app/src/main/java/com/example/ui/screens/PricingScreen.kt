package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SubscriptionPlan
import com.example.ui.theme.*
import com.example.ui.viewmodel.CreatorPilotViewModel

@Composable
fun PricingScreen(
  viewModel: CreatorPilotViewModel,
  modifier: Modifier = Modifier
) {
  val user by viewModel.currentUser.collectAsState()

  val plans = remember {
    listOf(
      SubscriptionPlan(
        id = "free",
        name = "Free",
        priceInr = "₹0",
        billingPeriod = "/ month",
        description = "Essential diagnostics for aspiring creators",
        isPopular = false,
        features = listOf(
          "1 Channel Analysis",
          "5 Content Ideas / month",
          "2 Full AI Scripts / month",
          "Basic Channel Health Score"
        )
      ),
      SubscriptionPlan(
        id = "pro",
        name = "Pro",
        priceInr = "₹499",
        billingPeriod = "/ month",
        description = "The complete growth engine for dedicated creators",
        isPopular = true,
        features = listOf(
          "3 Channel Analyses",
          "Unlimited High-Opportunity Ideas",
          "20 Full AI Scripts / month",
          "Full AI Opportunity Report & Evidence",
          "AI Performance Coach",
          "30-Day Growth Strategy Calendar",
          "Download Creator Packs"
        )
      ),
      SubscriptionPlan(
        id = "creator",
        name = "Creator",
        priceInr = "₹999",
        billingPeriod = "/ month",
        description = "Maximum velocity for serious creators & agencies",
        isPopular = false,
        features = listOf(
          "Unlimited Channel Analyses",
          "Unlimited AI Scripts & Refinements",
          "Competitor Intelligence & Content Gaps",
          "AI Thumbnail Audit & Concept Studio",
          "Direct Creator Pack Bundle Exports",
          "Priority AI Generation Queue"
        )
      )
    )
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
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "Simple, Transparent Pricing",
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TextWhite)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Invest in growth, not guessing. Cancel anytime.",
          style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted)
        )
      }
    }

    items(plans) { plan ->
      val isCurrentPlan = user.tier.equals(plan.name, ignoreCase = true)

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(
            width = if (plan.isPopular) 2.dp else 1.dp,
            brush = if (plan.isPopular) Brush.horizontalGradient(listOf(ElectricViolet, NeonPurple)) else Brush.linearGradient(listOf(ObsidianCardBorder, ObsidianCardBorder)),
            shape = RoundedCornerShape(16.dp)
          ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard)
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = plan.name,
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )

            if (plan.isPopular) {
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = ElectricViolet.copy(alpha = 0.2f)
              ) {
                Text(
                  text = "MOST POPULAR",
                  style = MaterialTheme.typography.labelSmall.copy(color = NeonPurple, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
              }
            } else if (isCurrentPlan) {
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = SuccessGreen.copy(alpha = 0.2f)
              ) {
                Text(
                  text = "CURRENT PLAN",
                  style = MaterialTheme.typography.labelSmall.copy(color = SuccessGreen, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(verticalAlignment = Alignment.Bottom) {
            Text(
              text = plan.priceInr,
              style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold, color = TextWhite)
            )
            Text(
              text = plan.billingPeriod,
              style = MaterialTheme.typography.bodyMedium.copy(color = TextGrayMuted),
              modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = plan.description,
            style = MaterialTheme.typography.bodySmall.copy(color = TextGrayLight)
          )

          Spacer(modifier = Modifier.height(16.dp))

          HorizontalDivider(color = ObsidianCardBorder)

          Spacer(modifier = Modifier.height(14.dp))

          plan.features.forEach { feat ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Check, contentDescription = null, tint = if (plan.isPopular) NeonPurple else SuccessGreen, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(feat, style = MaterialTheme.typography.bodySmall.copy(color = TextWhite))
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = { viewModel.selectTier(plan.name) },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isCurrentPlan) ObsidianElevated else if (plan.isPopular) ElectricViolet else IndigoAccent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = if (isCurrentPlan) "Active Plan" else "Upgrade to ${plan.name}",
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
  }
}
