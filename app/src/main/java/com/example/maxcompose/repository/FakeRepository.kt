package com.example.maxcompose.repository

import com.example.maxcompose.model.ListItem29
import kotlinx.coroutines.delay

class FakeRepository {

    private val remoteDataSource = (0..100).map {
        ListItem29(
            title = "item $it",
            description = "description $it",
        )
    }

    suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItem29>> {
        delay(2000)
        val startingIndex = page* pageSize
        return if (startingIndex + pageSize <= remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        } else {
            Result.success(emptyList())
        }
    }
}