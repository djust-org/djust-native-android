// DjustLiveView.kt
//
// Public Jetpack Compose entry point. LVN-IV PR-1: stub @Composable so
// downstream consumers can `import org.djust.native.DjustLiveView` and
// reference it immediately. The actual WebSocket transport, msgpack
// decoding, and patch applicator land in LVN-IV PRs 2-6 per
// djust-org/djust#1580.

package org.djust.native

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Native Jetpack Compose client for djust LiveView.
 *
 * Eventually:
 * ```kotlin
 * @Composable
 * fun ContentScreen() {
 *     DjustLiveView(url = "ws://127.0.0.1:8111/ws/live/")
 * }
 * ```
 *
 * Connects to the LiveView WebSocket with `?platform=compose`, consumes
 * the existing Patch opcode stream, and renders true Compose widgets
 * per the v1 vocabulary (see [widgetTags]).
 */
@Composable
fun DjustLiveView(url: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("djust-native-android")
        Text("Implementation in progress")
        Text("djust-org/djust#1580")
    }
}
