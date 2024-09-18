// MainActivity.kt
package com.example.eac12

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.eac12.ui.theme.EAC12Theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf

// ViewModel para gestionar las etapas del ciclo de vida
class LifecycleViewModel : ViewModel() {
    // Llista mutable observable
    private val _lifecycleEvents = mutableStateListOf<String>()
    val lifecycleEvents: List<String> get() = _lifecycleEvents

    // Funció per afegir una nova etapa
    fun addEvent(event: String) {
        _lifecycleEvents.add(event)
    }

    // Funció per resetejar les etapes
    fun resetEvents() {
        _lifecycleEvents.clear()
    }
}

class MainActivity : ComponentActivity() {
    private val lifecycleViewModel: LifecycleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logAndAddEvent("onCreate")
        setContent {
            EAC12Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LifecycleMonitorScreen(viewModel = lifecycleViewModel)
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

// Composable per mostrar la llista d'esdeveniments i el botó de reinici
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
    EAC12Theme {
        // Crea un ViewModel fictici per a la vista prèvia
        val viewModel = LifecycleViewModel().apply {
            addEvent("Preview Event 1")
            addEvent("Preview Event 2")
        }
        LifecycleMonitorScreen(viewModel)
    }
}
