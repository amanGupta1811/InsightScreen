


package com.acpitzone.insight.ui.screen

//import android.text.Layout.Alignment
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acpitzone.insight.ui.components.*
import com.acpitzone.insight.ui.theme.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon


@Composable
fun InsightsScreen() {
    var selectedNav by remember { mutableStateOf(NavItem.Insights) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    PurpleLighter.copy(alpha = 0.5f),
                                    PinkLight.copy(alpha = 0.25f),
                                    BackgroundLight
                                ),
                                radius = 900f
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    InsightsTopBar()
                }

                Spacer(modifier = Modifier.height(8.dp))

                SectionHeader("Stability Summary")
                SectionCard { StabilitySummaryCard() }

                Spacer(modifier = Modifier.height(20.dp))

                SectionHeader("Cycle Trends")
                SectionCard { CycleTrendsCard() }

                Spacer(modifier = Modifier.height(20.dp))

                SectionHeader("Body & Metabolic Trends")
                SectionCard { BodyMetabolicCard() }

                Spacer(modifier = Modifier.height(20.dp))

                SectionHeader("Body Signals")
                SectionCard { SymptomTrendsCard() }

                Spacer(modifier = Modifier.height(20.dp))

                SectionHeader("Lifestyle Impact")
                SectionCard { LifestyleImpactCard() }

                Spacer(modifier = Modifier.height(16.dp))
            }

            BottomNavBar(
                selectedItem = selectedNav,
                onItemSelected = { selectedNav = it }
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp)
                .offset(y = (-25).dp)
                .size(64.dp)
                .shadow(16.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color(0xFF1A1A2E),
                modifier = Modifier.size(28.dp)
            )
        }

    }
}
        @Composable
        fun InsightsTopBar() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppLogoIcon()
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Insights",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(32.dp))
            }
        }

        @Composable
        fun AppLogoIcon() {
            val colors = listOf(PurplePrimary, PurpleLight, GreenCycle, RedMenstruation)
            Box(modifier = Modifier.size(32.dp)) {
                val positions = listOf(
                    Pair(0.dp, 0.dp),
                    Pair(14.dp, 0.dp),
                    Pair(0.dp, 14.dp),
                    Pair(14.dp, 14.dp),
                )
                positions.forEachIndexed { i, (x, y) ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .offset(x = x, y = y)
                            .clip(CircleShape)
                            .background(colors[i])
                    )
                }
            }
        }

        @Composable
        fun SectionHeader(title: String) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        @Composable
        fun SectionCard(content: @Composable () -> Unit) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceWhite)
            ) {
                content()
            }
        }
