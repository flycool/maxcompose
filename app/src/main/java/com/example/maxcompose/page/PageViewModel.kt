package com.example.maxcompose.page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maxcompose.model.ListItem29
import com.example.maxcompose.repository.FakeRepository
import kotlinx.coroutines.launch

class PageViewModel : ViewModel() {

    private val repository = FakeRepository()

    var state by mutableStateOf(ScreenState())

    private val paginator = DefaultPaginator<Int, ListItem29>(
        initialKey = 0,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getItems(nextPage, 20)
        },
        getNextKey = { state.page + 1 },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun reset() {
        viewModelScope.launch {
            paginator.reset()
            state = state.copy(
                items = emptyList(),
                page = 0
            )
            paginator.loadNextItems()
        }
    }
}

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<ListItem29> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
)