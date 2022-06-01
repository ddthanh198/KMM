package com.example.kmm_module

import io.ktor.client.HttpClient

expect class HttpBaseClient() {
    val httpClient: HttpClient
}
