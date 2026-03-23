package com.acpitzone.insight.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acpitzone.insight.ui.theme.*

enum class NavItem { Home, Track, Insights }

@Composable
fun BottomNavBar(
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Top divider
        Divider(thickness = 0.5.dp, color = Color(0xFFE5E5EA))

        // Single Row: Home | Track | Insights | [+]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Home
            NavTabItem(
                icon     = Icons.Default.Home,
                label    = "Home",
                selected = selectedItem == NavItem.Home,
                onClick  = { onItemSelected(NavItem.Home) }
            )

            // Track
            NavTabItem(
                icon     = Icons.Default.Schedule,
                label    = "Track",
                selected = selectedItem == NavItem.Track,
                onClick  = { onItemSelected(NavItem.Track) }
            )

            // Insights
            NavTabItem(
                icon     = Icons.Default.BarChart,
                label    = "Insights",
                selected = selectedItem == NavItem.Insights,
                onClick  = { onItemSelected(NavItem.Insights) }
            )

            // + FAB — circle, inline with nav items, right side
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Add,
                    contentDescription = "Add",
                    tint               = Color(0xFF1A1A2E),
                    modifier           = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun NavTabItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = label,
            tint               = if (selected) Color(0xFF1A1A2E) else Color(0xFFB0B0B0),
            modifier           = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text       = label,
            fontSize   = 10.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color      = if (selected) Color(0xFF1A1A2E) else Color(0xFFB0B0B0)
        )
    }
}






