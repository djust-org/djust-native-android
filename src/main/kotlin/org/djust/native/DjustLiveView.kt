// DjustLiveView.kt — wired (LVN-IV PR-6, djust#1580)
//
// Composes WebSocketClient + PatchApplicator + WidgetRenderer +
// EventEnvelope into the public Composable entry point.

package org.djust.native

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun DjustLiveView(url: String) {
    val client = remember { WebSocketClient(url, Platform.COMPOSE) }
    val applicator = remember { PatchApplicator() }
    var root by remember { mutableStateOf<VNode?>(null) }

    LaunchedEffect(url) {
        client.connect()
        client.frames()
            .catch { /* Stream throws decoder-unimplemented until PR-3 ships */ }
            .collect { frame ->
                applicator.apply(frame)
                root = applicator.root
            }
    }

    val current = root
    if (current != null) {
        WidgetRenderer(current) { name, params ->
            // Fire-and-forget event send via a scope owned by Compose
            CoroutineScope(Dispatchers.Main).launch {
                runCatching { client.sendEvent(name, params) }
            }
        }
    } else {
        Column(Modifier.padding(16.dp)) {
            CircularProgressIndicator()
            Text("Connecting to $url…")
        }
    }
}
