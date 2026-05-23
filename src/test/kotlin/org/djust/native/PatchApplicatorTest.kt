package org.djust.native

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class PatchApplicatorTest {

    @Test
    fun `initial version is negative`() {
        val app = PatchApplicator()
        assertEquals(-1, app.version)
        assertNull(app.root)
    }

    @Test
    fun `replace updates root`() {
        val app = PatchApplicator()
        val node = VNode(id = "a", tag = "Stack")
        app.apply(PatchFrame(
            type = "patch",
            patches = listOf(Patch.Replace(path = emptyList(), node = node)),
            version = 0,
        ))
        assertEquals(0, app.version)
        assertEquals(node, app.root)
        assertEquals(node, app.nodesByDjId["a"])
    }

    @Test
    fun `version regression throws`() {
        val app = PatchApplicator()
        val node = VNode(id = "a", tag = "Stack")
        app.apply(PatchFrame(
            type = "patch",
            patches = listOf(Patch.Replace(path = emptyList(), node = node)),
            version = 5,
        ))
        val exc = assertFailsWith<PatchException.VersionRegression> {
            app.apply(PatchFrame(type = "patch", patches = emptyList(), version = 4))
        }
        assertEquals(5, exc.have)
        assertEquals(4, exc.got)
    }
}
