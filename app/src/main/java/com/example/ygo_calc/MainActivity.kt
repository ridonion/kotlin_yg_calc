package com.example.ygo_calc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ygo_calc.ui.theme.YGO_CALCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YGO_CALCTheme {
                ManagePages()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YGO_CALCTheme {
        ManagePages()
    }
}