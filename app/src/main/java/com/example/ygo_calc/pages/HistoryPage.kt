import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun C_HistoryPage(
    modifier: Modifier = Modifier,
    context: Context
) {
    val sharedPreferences = context.getSharedPreferences("game_results", Context.MODE_PRIVATE)
    var results by remember { mutableStateOf(sharedPreferences.all.values.toList()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (results.isEmpty()) {
            Text("No history available")
        } else {
            Button(
                onClick = {
                    sharedPreferences.edit().clear().apply()
                    results = emptyList()
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Clear History")
            }

            results.forEach { result ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Text(
                        text = result.toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}