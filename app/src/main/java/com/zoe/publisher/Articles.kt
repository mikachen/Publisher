package com.zoe.publisher

data class Articles(
    val author: Map<String, Any>,
    val category: String,
    val content: String,
    val createdTime: Long,
    val id: String,
    val title: String,
)

data class Users(
    val email: String,
    val userId: String,
    val nickName: String,
    val password: String,
    val token: String
)

data class Author(
    val email: String,
    val id: String,
    val name: String,
)
