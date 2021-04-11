package com.baymax.quotable.api

data class ApiResponse<T>(
    val count: Int,
    val lastItemIndex: Int,
    val results: List<T>,
    val totalCount: Int
)