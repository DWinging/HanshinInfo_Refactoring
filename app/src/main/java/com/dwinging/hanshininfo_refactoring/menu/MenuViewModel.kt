package com.dwinging.hanshininfo_refactoring.menu

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.State
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

    private val _searchMode = mutableStateOf(false)
    val searchMode: State<Boolean> = _searchMode

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    // MainMenu 선택 여부
    fun mainMenuSelected(menu: MainMenuList) {
        _selectedMenu.value = menu
    }

    // Sub 메뉴 확장 여부
    fun toggleExpand(menu: MainMenuList) {
        _expandedGroup[menu] = _expandedGroup[menu] != true
    }
    
    fun delayedCollapse() {
        viewModelScope.launch {
            delay(100)
            collapseAll()
        }
    }

    fun collapseAll() {
        _expandedGroup.keys.forEach { key ->
            _expandedGroup[key] = false
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun openSearchMode() {
        _searchQuery.value = ""
        _searchMode.value = true
    }

    fun closeSearchMode() {
        _searchMode.value = false
        onSearchQueryChanged("")
    }

    fun onClearQuery() {
        onSearchQueryChanged("")
    }
}