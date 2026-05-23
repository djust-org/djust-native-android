// WebSocketClientTest.kt

package org.djust.native

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class WebSocketClientTest {

    @Test
    fun `platform raw values match Python keys`() {
        assertEquals("swiftui", Platform.SWIFTUI.rawValue)
        assertEquals("compose", Platform.COMPOSE.rawValue)
    }

    @Test
    fun `connection URL appends platform`() {
        val client = WebSocketClient("ws://127.0.0.1:8111/ws/live/", Platform.COMPOSE)
        assertTrue(client.connectionURL.contains("platform=compose"))
    }

    @Test
    fun `connection URL preserves existing query`() {
        val client = WebSocketClient("ws://127.0.0.1:8111/ws/live/?session=abc", Platform.COMPOSE)
        val url = client.connectionURL
        assertTrue(url.contains("session=abc"))
        assertTrue(url.contains("platform=compose"))
    }

    @Test
    fun `frames throws decoder unimplemented until PR3`() {
        val client = WebSocketClient("ws://127.0.0.1:8111/ws/live/")
        assertFailsWith<DjustWSException> {
            runBlocking { client.frames().collect {} }
        }
    }
}
