package com.example.maxcompose.page

interface Paginator<Key, T> {
    suspend fun loadNextItems()
    fun reset()
}