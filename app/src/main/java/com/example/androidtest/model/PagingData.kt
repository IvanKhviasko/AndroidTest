package com.example.androidtest.model

sealed class PagingData<out T> {
    data class Content<T>(val data: T) : PagingData<T>()

    object Loading : PagingData<Nothing>()
}