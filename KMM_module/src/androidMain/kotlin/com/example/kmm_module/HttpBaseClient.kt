package com.example.kmm_module

import com.example.kmm_module.base.CustomException
import com.example.kmm_module.base.ErrorResponse
import com.example.kmm_module.utils.*

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


actual class HttpBaseClient {
    actual val httpClient: HttpClient = HttpClient {
        install(DefaultRequest)
        install(Resources)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        defaultRequest {
            host = BASE_URL
            port = 8080
            url { protocol = URLProtocol.HTTP }
//            contentType(ContentType.Application.Json)
        }
        // Validate Response
        expectSuccess = false
        // JSON Deserializer

//        install(JsonFeature) {
//            val json = kotlinx.serialization.json.Json {
//                ignoreUnknownKeys = true
//                coerceInputValues = true
//            }
//            serializer = KotlinxSerializer(json)
//        }
        HttpResponseValidator {
            validateResponse { response ->
                when (response.status.value) {
                    in REDIRECT_RESPONSE_EXCEPTION_RANGE -> throw RedirectResponseException(response, "")
                    in CLIENT_REQUEST_EXCEPTION_RANGE -> throw ClientRequestException(response, "")
                    in SERVER_RESPONSE_EXCEPTION_RANGE -> throw ServerResponseException(response, "")
                }
                if (response.status.value >= RESPONSE_EXCEPTION_CODE) {
                    throw ResponseException(response, "")
                }
            }
            handleResponseException { cause ->
                var error = ErrorResponse()
                when (cause) {
                    is ResponseException -> {
                        error = cause.response.body()
                        error.statusCode = cause.response.status.value
                    }
                    is java.net.UnknownHostException -> {
                        error = CustomException.getNoInternetError()
                    }
                    else -> CustomException.getDefaultError(cause.message)
                }
                throw CustomException(error)
            }
        }
    }
}