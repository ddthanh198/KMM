package com.example.kmm_module.data.model

//import io.ktor.resources.*
import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
//@Resource("/api/breed//images")
data class DogImageUrlListResponse(
    val message: List<String>? = null,
    val status: String? = null
)