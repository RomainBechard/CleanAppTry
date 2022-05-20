package com.romainbechard.instantsystemtestapp.ui.screens.home

import com.romainbechard.instantsystemtestapp.FakeRepository
import com.romainbechard.instantsystemtestapp.data.Repository
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = FakeRepository()
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getListFromSubject() {
        viewModel.getListFromSubject(null)
        assert(viewModel.selectedSubject.value == null)
        assert(viewModel.input.value.isNotEmpty())
        assert(!viewModel.errorState.value)

        viewModel.onSubjectPicked(0)
        viewModel.getListFromSubject("Politique")
        assert(viewModel.selectedSubject.value != null)
        assert(!viewModel.errorState.value)
    }

    @Test
    fun setInput() {
        val myInput = "ABC"
        viewModel.setInput(myInput)
        assert(viewModel.input.value == myInput)
    }

    @Test
    fun onSubjectPicked() {
        viewModel.onSubjectPicked(null)
        assert(viewModel.selectedSubject.value == null)

        val selectedId = 0
        viewModel.onSubjectPicked(selectedId)
        assert(viewModel.selectedSubject.value == selectedId)
    }

    @Test
    fun onItemClicked() {
        val articleIndex = 0
        if (!viewModel.expandedCardIdsList.value.contains(articleIndex)) {
            viewModel.onItemClicked(articleIndex)
            assert(viewModel.expandedCardIdsList.value.contains(articleIndex) || viewModel.errorState.value)
        } else {
            viewModel.onItemClicked(articleIndex)
            assert(!viewModel.expandedCardIdsList.value.contains(articleIndex))
        }

        viewModel.onItemClicked(viewModel.articlesList.value.lastIndex + 1)
        assert(viewModel.errorState.value)
    }
}