// PatchApplicator.kt
//
// LVN-IV PR-3: Kotlin mirror of djust-native-ios#3's PatchApplicator.
// Four-phase ordering (Remove desc → Move → Insert → other) per ADR-013.

package org.djust.native

sealed class PatchException(message: String) : Exception(message) {
    class UnknownTag(tag: String) : PatchException("unknown widget tag: $tag")
    class MissingNode(val djId: String?, val path: List<Int>) :
        PatchException("missing node djId=$djId path=$path")
    class VersionRegression(val have: Int, val got: Int) :
        PatchException("version regression: have=$have got=$got")
}

class PatchApplicator {
    var root: VNode? = null
        private set

    var version: Int = -1
        private set

    val nodesByDjId: MutableMap<String, VNode> = mutableMapOf()

    fun apply(frame: PatchFrame): VNode? {
        if (frame.version <= version) {
            throw PatchException.VersionRegression(version, frame.version)
        }
        val removes = mutableListOf<Patch>()
        val moves = mutableListOf<Patch>()
        val inserts = mutableListOf<Patch>()
        val other = mutableListOf<Patch>()
        for (p in frame.patches) {
            when (p) {
                is Patch.RemoveChild, is Patch.RemoveSubtree -> removes.add(p)
                is Patch.MoveChild -> moves.add(p)
                is Patch.InsertChild, is Patch.InsertSubtree -> inserts.add(p)
                else -> other.add(p)
            }
        }
        removes.reverse()
        listOf(removes, moves, inserts, other).forEach { batch ->
            batch.forEach { applyOne(it) }
        }
        rebuildIndex()
        version = frame.version
        return root
    }

    private fun applyOne(patch: Patch) {
        when (patch) {
            is Patch.Replace -> root = patch.node
            else -> {
                // PR-4: per-op mutation + Compose MutableState binding.
            }
        }
    }

    private fun rebuildIndex() {
        nodesByDjId.clear()
        root?.let { indexSubtree(it) }
    }

    private fun indexSubtree(node: VNode) {
        nodesByDjId[node.id] = node
        node.children.forEach { indexSubtree(it) }
    }
}
