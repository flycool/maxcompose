package com.example.maxcompose.page

class DefaultPaginator<Key, T>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<T>>,
    private inline val getNextKey: suspend (List<T>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<T>, newKey: Key) -> Unit,
) : Paginator<Key, T> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if(isMakingRequest) return
        isMakingRequest = true

        onLoadUpdated(true)
        val items = onRequest(currentKey).getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        isMakingRequest = false
        currentKey = getNextKey(items)

        onSuccess(items, currentKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}