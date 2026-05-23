// WidgetTags.kt
//
// Mirror of djust-org/djust's `python/djust/renderers/widgets.py` —
// the frozen v1 widget vocabulary (12 widgets, SwiftUI ∩ Compose
// intersection). Authoritative source is the Python module; this
// Kotlin constant moves in lockstep with it (see ADR-019 §SemVer).
//
// LVN-IV PR-1 (djust-org/djust#1580): vocabulary mirror. PR-2 onward
// adds widget composables that map each tag to a Jetpack Compose
// @Composable function.
//
// See:
//   - https://github.com/djust-org/djust/blob/main/docs/adr/019-liveview-native.md
//   - https://github.com/djust-org/djust/blob/main/docs/native-widget-vocabulary.md
//   - https://github.com/djust-org/djust/blob/main/python/djust/renderers/widgets.py

package org.djust.native

/**
 * The frozen v1 widget vocabulary. Mirrors `WIDGET_TAGS` in the djust
 * Python package. A `NativeRenderer`-emitted VNode whose tag is not in
 * this set is a bug — the client should fail loudly so the author
 * catches the typo early.
 */
val widgetTags: Set<String> = setOf(
    // Layout containers
    "Stack",          // → Compose Column
    "HStack",         // → Compose Row
    "ZStack",         // → Compose Box
    // Leaf widgets
    "Text",           // → Compose Text
    "Button",         // → Compose Button
    "TextField",      // → Compose TextField
    "Toggle",         // → Compose Switch
    "List",           // → Compose LazyColumn
    "Image",          // → Compose Image
    // Layout helpers
    "ScrollView",     // → Compose Modifier.verticalScroll
    "Spacer",         // → Compose Spacer
    "NavigationView", // → Compose NavHost
)

/**
 * Event-handler attribute names a native template uses. Mirror of
 * the djust Python `EVENT_ATTRS` frozenset.
 */
val eventAttrs: Set<String> = setOf(
    "dj-tap",
    "dj-change",
    "dj-input",
)

/**
 * Style attribute names this client honors in v1. Mirror of the
 * djust Python `STYLE_ATTRS` frozenset.
 */
val styleAttrs: Set<String> = setOf(
    "padding",
    "spacing",
    "alignment",
    "foregroundColor",
    "font",
)

/** True iff [tag] is in the frozen v1 widget vocabulary. */
fun isWidgetTag(tag: String): Boolean = tag in widgetTags
