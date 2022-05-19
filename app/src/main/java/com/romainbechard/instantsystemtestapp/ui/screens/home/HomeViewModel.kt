package com.romainbechard.instantsystemtestapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romainbechard.instantsystemtestapp.data.NewsRepository
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.data.tools.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _articlesList: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
    val articlesList: StateFlow<List<Article>> = _articlesList

    private val _subjectList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val subjectList: StateFlow<List<String>> = _subjectList

    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList

    private val _errorState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = _errorState

    fun getLists() {
        viewModelScope.launch {
            _subjectList.emit(repository.getSubjects())
            when(val response = repository.getHeadlines()) {
                is Result.Success -> {
                    _articlesList.emit(response.data)
                }
                is Result.Error -> {
                    _errorState.emit(true)
                }
            }
        }
    }

    fun onItemClicked(articleIndex: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(articleIndex)) list.remove(articleIndex) else list.add(articleIndex)
        }
    }

}