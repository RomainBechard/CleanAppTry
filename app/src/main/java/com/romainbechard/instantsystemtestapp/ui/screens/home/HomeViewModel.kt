package com.romainbechard.instantsystemtestapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romainbechard.instantsystemtestapp.data.NewsRepository
import com.romainbechard.instantsystemtestapp.data.Repository
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.data.tools.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _articlesList: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
    val articlesList: StateFlow<List<Article>> = _articlesList

    private val _subjectList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val subjectList: StateFlow<List<String>> = _subjectList

    private val _selectedSubject: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedSubject: StateFlow<Int?> = _selectedSubject

    private val _input: MutableStateFlow<String> = MutableStateFlow("France")
    val input: StateFlow<String> = _input

    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList

    private val _errorState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = _errorState

    fun getDefaultList() {
        viewModelScope.launch {
            val subjects = repository.getSubjects()
            _subjectList.emit(subjects)
            when (val response = repository.getHeadlines()) {
                is Result.Success -> {
                    _articlesList.emit(response.data)
                }
                is Result.Error -> {
                    _errorState.emit(true)
                }
            }
        }
    }

    fun getListFromSubject(subject: String?) {
        viewModelScope.launch {
            if (subject != null) {
                when (val response = repository.getSearchResult(subject)) {
                    is Result.Success -> _articlesList.emit(response.data)
                    is Result.Error -> _errorState.emit(true)
                }
            } else {
                if (input.value.isNotEmpty()) {
                    _selectedSubject.value = null
                    when (val response = repository.getSearchResult(input.value)) {
                        is Result.Success -> _articlesList.emit(response.data)
                        is Result.Error -> _errorState.emit(true)
                    }
                }
            }
        }
    }

    fun setInput(text: String) {
        _input.value = text
    }

    fun onSubjectPicked(index: Int?) {
        _selectedSubject.value = index
    }

    fun onItemClicked(articleIndex: Int) {
        if (articleIndex <= expandedCardIdsList.value.lastIndex) {
            _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
                if (list.contains(articleIndex)) list.remove(articleIndex) else list.add(articleIndex)
            }
        } else {
            _errorState.value = true
        }
    }

}