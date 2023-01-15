package com.polware.newsapiclient.data.models

data class APIResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
    )