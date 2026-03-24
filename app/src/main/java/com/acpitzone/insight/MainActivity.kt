package com.acpitzone.insight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.acpitzone.insight.ui.screen.InsightsScreen
import com.acpitzone.insight.ui.theme.InsightsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsightsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    InsightsScreen()
                }
            }
        }
    }
}
