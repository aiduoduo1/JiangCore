package com.jj.ui.dialog

class JSingleChoiceState<T>(
    val items: List<T>,
    selectedIndex: Int = -1,
) {

    var selectedIndex: Int = selectedIndex
        private set

    val selectedItem: T?
        get() = items.getOrNull(selectedIndex)

    fun select(index: Int) {
        require(index in items.indices) { "Selected index is out of bounds." }
        selectedIndex = index
    }
}

class JMultiChoiceState<T>(
    val items: List<T>,
    selectedIndexes: Set<Int> = emptySet(),
) {

    private val mutableSelectedIndexes = selectedIndexes.toMutableSet()

    val selectedIndexes: Set<Int>
        get() = mutableSelectedIndexes.toSet()

    val selectedItems: List<T>
        get() = mutableSelectedIndexes.sorted().mapNotNull(items::getOrNull)

    fun isSelected(index: Int): Boolean {
        return index in mutableSelectedIndexes
    }

    fun toggle(index: Int) {
        require(index in items.indices) { "Selected index is out of bounds." }
        if (!mutableSelectedIndexes.add(index)) {
            mutableSelectedIndexes.remove(index)
        }
    }
}
