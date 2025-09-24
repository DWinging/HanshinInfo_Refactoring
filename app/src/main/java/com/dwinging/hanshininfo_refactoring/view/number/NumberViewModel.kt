package com.dwinging.hanshininfo_refactoring.view.number

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwinging.hanshininfo_refactoring.data.entities.NumberDetail
import com.dwinging.hanshininfo_refactoring.data.entities.NumberList
import com.dwinging.hanshininfo_refactoring.data.entities.NumberType
import kotlinx.coroutines.launch

class NumberViewModel: ViewModel() {
    private val _sheetData = mutableStateOf(NumberDataSheet(false, -1))
    val sheetData: State<NumberDataSheet> = _sheetData
    private val _numberList = mutableStateOf<List<NumberList>>(emptyList())
    val numberList: MutableState<List<NumberList>> = _numberList
    private val _confirmQuery = mutableStateOf("")
    val confirmQuery: State<String> = _confirmQuery
    private val _selectedDetail = mutableStateOf<NumberDetail?>(null)
    val selectedDetail: State<NumberDetail?> = _selectedDetail

    fun showSheet(id: Int, type: NumberType, context: Context) {
        _sheetData.value = NumberDataSheet(true, id)
        viewModelScope.launch {
            _selectedDetail.value = getNumberDetail(id, type, context)
        }
    }

    fun onDismissSheet() {
        _sheetData.value = NumberDataSheet(false, -1)
        _selectedDetail.value = null
    }

    suspend fun loadNumbers(type: NumberType, context: Context) {
        _numberList.value = searchNumbers(type, _confirmQuery.value, context)
    }

    fun onSearchConfirmed(query: String) {
        _confirmQuery.value = query
        Log.d("searchNumber", query)
    }

    fun resetConfirmQuery() {
        onSearchConfirmed("")
    }
}