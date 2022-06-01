package com.example.kmm_module.remote


//import io.ktor.client.plugins.resources.*

import com.example.kmm_module.HttpBaseClient
import com.example.kmm_module.base.Either
import com.example.kmm_module.base.Failure
import com.example.kmm_module.base.Success
import com.example.kmm_module.data.model.DogImageUrlListResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.get

class BaseApiClass {

    //Create Http Client
    private var client = HttpBaseClient().httpClient

    @Throws(Exception::class)

    suspend fun getDogImageUrlList(breedName : String) : Either<Exception, DogImageUrlListResponse> {
        try {
            val response = client.get("/api/breed/${breedName}/images")
            val body = response.body<DogImageUrlListResponse>()
            return Success(body)
//            Success(response)
        } catch (e: Exception) {
            return Failure(e)
        }
    }
}