    package com.pruebatecnica.idigitalstudio.data.model

    data class Pokemon(
        val id: Int,
        val name: String,
        val url: String,
        val abilities: List<String>,
        val imageUrl: String
    )