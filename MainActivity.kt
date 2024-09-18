import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.eac12.ui.theme.EAC12Theme

// ViewModel para gestionar las etapas del ciclo de vida
class LifecycleViewModel : ViewModel() {
    private val _lifecycleEvents = mutableStateOf<List<String>>(emptyList())
    val lifecycleEvents: List<String> by _lifecycleEvents

    fun addEvent(event: String) {
        _lifecycleEvents.value = _lifecycleEvents.value + event
    }

    fun resetEvents() {
        _lifecycleEvents.value = emptyList()
    }
}

// Actividad principal que captura el ciclo de vida y actualiza el ViewModel
class MainActivity : ComponentActivity() {
    private val lifecycleViewModel: LifecycleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logAndAddEvent("onCreate")
        setContent {
            EAC12Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LifecycleMonitorScreen(lifecycleViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        logAndAddEvent("onStart")
    }

    override fun onResume() {
        super.onResume()
        logAndAddEvent("onResume")
    }

    override fun onPause() {
        super.onPause()
        logAndAddEvent("onPause")
    }

    override fun onStop() {
        super.onStop()
        logAndAddEvent("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        logAndAddEvent("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        logAndAddEvent("onRestart")
    }

    private fun logAndAddEvent(event: String) {
        Log.d("LifecycleEvent", event)
        lifecycleViewModel.addEvent(event)
    }
}

// Composable para mostrar la lista de eventos y el botón de reinicio
@Composable
fun LifecycleMonitorScreen(viewModel: LifecycleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(viewModel.lifecycleEvents) { event ->
                Text(text = event)
            }
        }

        Button(
            onClick = { viewModel.resetEvents() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = LifecycleViewModel()
    viewModel.addEvent("Preview Event 1")
    viewModel.addEvent("Preview Event 2")
    LifecycleMonitorScreen(viewModel)
}
