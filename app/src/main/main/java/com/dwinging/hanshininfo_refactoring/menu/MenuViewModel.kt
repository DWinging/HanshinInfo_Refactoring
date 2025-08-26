package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel: ViewModel() {
    private val _selectedMenu = MutableStateFlow(MainMenuList.CAMPUS_MAP)
    val selectedMenu: StateFlow<MainMenuList> = _selectedMenu.asStateFlow()

    private val _expandedGroup = mutableStateMapOf<MainMenuList, Boolean>()
    val expandedMap: SnapshotStateMap<MainMenuList, Boolean> get() = _expandedGroup

    fun mainMenuSelected(menu: MainMenuList) {
        _selectedMenu.value = menu
    }

    fun toggleExpand(menu: MainMenuList) {
        _expandedGroup[menu] = _expandedGroup[menu] != true
    }

    fun delayedCollapse() {
        viewModelScope.launch {
            delay(100) // or 100~200ms 정도도 충분함
            collapseAll()
        }
    }

    fun collapseAll() {
        _expandedGroup.keys.forEach { key ->
            _expandedGroup[key] = false
        }
    }
}