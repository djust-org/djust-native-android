// WebSocketClient.kt
//
// LVN-IV PR-2: WS transport via OkHttp. Decoder + receive loop are
// stubs until PR-3 wires msgpack.

package org.djust.native

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DjustWSException(message: String) : Exception(message)

enum class Platform(val rawValue: String) {
    SWIFTUI("swiftui"),
    COMPOSE("compose"),
}

/**
 * djust LiveView WebSocket transport.
 *
 * Eventually:
 * ```kotlin
 * val client = WebSocketClient(url = "ws://127.0.0.1:8111/ws/live/", Platform.COMPOSE)
 * client.connect()
 * client.frames().collect { frame -> /* apply patches */ }
 * ```
 */
class WebSocketClient(
    val url: String,
    val platform: Platform = Platform.COMPOSE,
) {
    /** Append `?platform=compose` to the configured URL. */
    val connectionURL: String
        get() {
            val sep = if (url.contains("?")) "&" else "?"
            return "$url${sep}platform=${platform.rawValue}"
        }

    fun connect() {
        // PR-3: open OkHttp WebSocket against connectionURL.
    }

    /**
     * Async sequence of decoded frames. Stub: actual msgpack decoding
     * lands in LVN-IV PR-3.
     */
    fun frames(): Flow<PatchFrame> = flow {
        throw DjustWSException(
            "msgpack decoder ships in djust-native-android PR-3 (djust#1580)"
        )
    }

    /**
     * Send an event payload back to the server. Matches the browser's
     * WS event encoding so the same `@event_handler` Python decorators
     * fire across all three clients.
     */
    suspend fun sendEvent(name: String, params: Map<String, String>) {
        // PR-5: msgpack-encode + send via OkHttp.
        throw DjustWSException(
            "event sender ships in djust-native-android PR-5 (djust#1580)"
        )
    }

    fun disconnect() {
        // PR-3: cancel OkHttp WebSocket task.
    }
}
