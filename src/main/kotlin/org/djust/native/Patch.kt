// Patch.kt
//
// Kotlin mirror of djust's Rust Patch enum + VNode struct
// (djust-org/djust crates/djust_vdom/src/lib.rs:432-532). Wire
// format identical for HTML + native targets — only tag vocabulary
// differs.
//
// LVN-IV PR-2 (djust#1580): type declarations. Concrete decode +
// applicator logic in PRs 3-4.

package org.djust.native

/**
 * A node in the server-side VDOM. Server emits widget-shaped tag
 * names (`"Stack"`, `"Text"`, ...) when `?platform=compose`.
 */
data class VNode(
    val id: String,
    val tag: String,
    val attrs: Map<String, String> = emptyMap(),
    val text: String = "",
    val children: List<VNode> = emptyList(),
)

/** One mutation in the patch stream. Matches the Rust Patch enum. */
sealed class Patch {
    data class Replace(val path: List<Int>, val node: VNode) : Patch()
    data class SetText(val path: List<Int>, val djId: String?, val text: String) : Patch()
    data class SetAttr(val path: List<Int>, val djId: String?, val key: String, val value: String) : Patch()
    data class RemoveAttr(val path: List<Int>, val djId: String?, val key: String) : Patch()
    data class InsertChild(val path: List<Int>, val djId: String?, val refDjId: String?, val node: VNode) : Patch()
    data class RemoveChild(val path: List<Int>, val djId: String?, val index: Int) : Patch()
    data class MoveChild(val path: List<Int>, val djId: String?, val fromIndex: Int, val toIndex: Int) : Patch()
    data class RemoveSubtree(val path: List<Int>, val djId: String?) : Patch()
    data class InsertSubtree(val path: List<Int>, val djId: String?, val node: VNode) : Patch()
}

/** Frame shape LiveViewConsumer sends over the WS. */
data class PatchFrame(
    val type: String,
    val patches: List<Patch>,
    val version: Int,
)
