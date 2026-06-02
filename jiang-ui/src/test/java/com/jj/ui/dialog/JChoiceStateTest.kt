package com.jj.ui.dialog

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class JChoiceStateTest {

    @Test
    fun singleChoiceSelectsOnlyOneItem() {
        val state = JSingleChoiceState(
            items = listOf("A", "B", "C"),
            selectedIndex = 0,
        )

        state.select(2)

        assertEquals(2, state.selectedIndex)
        assertEquals("C", state.selectedItem)
    }

    @Test
    fun multiChoiceTogglesItems() {
        val state = JMultiChoiceState(
            items = listOf("A", "B", "C"),
            selectedIndexes = setOf(0),
        )

        state.toggle(1)
        state.toggle(0)

        assertFalse(state.isSelected(0))
        assertTrue(state.isSelected(1))
        assertEquals(listOf("B"), state.selectedItems)
    }
}
