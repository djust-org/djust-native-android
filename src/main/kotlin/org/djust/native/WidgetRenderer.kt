// WidgetRenderer.kt
//
// LVN-IV PR-4: Compose @Composable that renders a VNode into the
// corresponding widget per the v1 vocabulary. Mirror of
// djust-native-ios#4 (WidgetRenderer.swift).

package org.djust.native

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

typealias EventCallback = (name: String, params: Map<String, String>) -> Unit

@Composable
fun WidgetRenderer(node: VNode, onEvent: EventCallback = { _, _ -> }) {
    when (node.tag) {
        "Stack" -> Column { Children(node, onEvent) }
        "HStack" -> Row { Children(node, onEvent) }
        "ZStack" -> Box { Children(node, onEvent) }
        "Text" -> Text(node.text)
        "Button" -> Button(onClick = { fireTap(node, onEvent) }) {
            Children(node, onEvent)
        }
        "TextField" -> TextField(
            value = node.text,
            onValueChange = { /* PR-5: dj-input event */ },
            placeholder = { Text(node.attrs["placeholder"] ?: "") },
        )
        "Toggle" -> Switch(
            checked = node.attrs["isOn"] == "true",
            onCheckedChange = { /* PR-5: dj-change event */ },
        )
        "List" -> LazyColumn {
            items(node.children) { child -> WidgetRenderer(child, onEvent) }
        }
        "Image" -> Text("[image:${node.attrs["src"] ?: "?"}]") // placeholder
        "ScrollView" -> Column(Modifier.verticalScroll(rememberScrollState())) {
            Children(node, onEvent)
        }
        "Spacer" -> Spacer(Modifier.padding(4.dp))
        "NavigationView" -> Box { Children(node, onEvent) } // simplified; NavHost in PR-7
        else -> Text("⚠ unknown widget: ${node.tag}", color = Color.Red)
    }
}

@Composable
private fun Children(node: VNode, onEvent: EventCallback) {
    node.children.forEach { WidgetRenderer(it, onEvent) }
}

private fun fireTap(node: VNode, onEvent: EventCallback) {
    node.attrs["dj-tap"]?.let { onEvent(it, emptyMap()) }
}
