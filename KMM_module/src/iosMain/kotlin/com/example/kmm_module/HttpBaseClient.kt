package com.example.kmm_module


import com.example.kmm_module.base.CustomException
import com.example.kmm_module.base.ErrorResponse
import com.example.kmm_module.utils.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.ios.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.http.*


actual class HttpBaseClient {
    actual val httpClient: HttpClient = HttpClient(Ios) {
        defaultRequest {
            host = BASE_URL
            contentType(ContentType.Application.Json)
        }

        engine {
            // this: IosClientEngineConfig
            configureRequest {
//                setHTTPShouldUsePipelining(true)
            }
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
                    is IosHttpRequestException -> {
                        CustomException.getNoInternetError()
                    }
                    else -> CustomException.getDefaultError(cause.message)
                }
                throw CustomException(error)
            }
        }
    }
}