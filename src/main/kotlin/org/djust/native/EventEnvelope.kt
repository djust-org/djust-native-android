// EventEnvelope.kt
//
// LVN-IV PR-5: Kotlin mirror of djust-native-ios#5. Client→server
// event payload encoding; matches LiveViewConsumer's expected shape.

package org.djust.native

import org.json.JSONObject

data class EventEnvelope(
    val event: String,
    val params: Map<String, String> = emptyMap(),
    val djId: String? = null,
) {
    val type: String = "event"

    fun encode(): String {
        val json = JSONObject()
        json.put("type", type)
        json.put("event", event)
        json.put("params", JSONObject(params))
        djId?.let { json.put("djId", it) }
        return json.toString()
    }
}
