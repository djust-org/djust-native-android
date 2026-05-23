// WidgetTagsTest.kt
//
// Pin the v1 vocabulary mirror against the Python source of truth in
// djust-org/djust. If Python's WIDGET_TAGS adds/removes entries, this
// test fails and forces the Kotlin mirror to update.

package org.djust.native

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WidgetTagsTest {

    @Test
    fun `widget vocabulary is exactly twelve`() {
        assertEquals(12, widgetTags.size)
    }

    @Test
    fun `widget vocabulary mirrors Python source`() {
        val expected = setOf(
            "Stack", "HStack", "ZStack",
            "Text", "Button", "TextField", "Toggle", "List", "Image",
            "ScrollView", "Spacer", "NavigationView",
        )
        assertEquals(expected, widgetTags)
    }

    @Test
    fun `event attrs mirror Python source`() {
        assertEquals(setOf("dj-tap", "dj-change", "dj-input"), eventAttrs)
    }

    @Test
    fun `style attrs mirror Python source`() {
        assertEquals(
            setOf("padding", "spacing", "alignment", "foregroundColor", "font"),
            styleAttrs,
        )
    }

    @Test
    fun `isWidgetTag works`() {
        assertTrue(isWidgetTag("Stack"))
        assertTrue(isWidgetTag("Button"))
        assertFalse(isWidgetTag("div"))
        assertFalse(isWidgetTag(""))
    }
}
