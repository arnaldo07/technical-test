package com.enmanuelbergling.technicaltest.ui.utils

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val LazyPagingItems<*>.isRefreshing: Boolean
    get() = loadState.refresh == LoadState.Loading

val LazyPagingItems<*>.isAppending: Boolean
    get() = loadState.append == LoadState.Loading

val LazyPagingItems<*>.isEmpty: Boolean
    get() = itemCount == 0