package com.amos_tech_code.travelapp.data

import com.amos_tech_code.travelapp.data.model.request.LoginRequest
import com.amos_tech_code.travelapp.data.model.request.RegisterRequest
import com.amos_tech_code.travelapp.data.model.response.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.io.IOException

class NetworkService(val httpClient: HttpClient) {

    private val BASE_URL = "https://travelapp-yght.onrender.com"


    suspend fun register(registerRequest: RegisterRequest): ResultWrapper<RegisterResponse> {
        return makeWebRequest<RegisterResponse>(
            url = "${BASE_URL}/users/register",
            method = HttpMethod.Post,
            body = registerRequest,
        )
    }


    suspend fun login(loginRequest: LoginRequest): ResultWrapper<RegisterResponse> {
        return makeWebRequest<RegisterResponse>(
            url = "${BASE_URL}/users/login",
            method = HttpMethod.Post,
            body = loginRequest,
        )
    }


    suspend inline fun <reified T> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): ResultWrapper<T> {
        return try {
            val response = httpClient.request(url) {
                this.method = method
                headers.forEach { (key, value) ->
                    this.headers.append(key, value)
                }
                parameters.forEach { (key, value) ->
                    this.parameter(key, value)
                }
                if (body != null) {
                    this.setBody(body)
                }
                contentType(ContentType.Application.Json)

            }.body<T>()

            ResultWrapper.Success(response)

        } catch (e: ClientRequestException) {
            ResultWrapper.Error(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Error(e)
        } catch (e: IOException) {
            ResultWrapper.Error(e)
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }


}



sealed class ResultWrapper<out T> {

    data class Success<out T> (val value: T): ResultWrapper<T>()

    data class Error(val e: Exception): ResultWrapper<Nothing> ()
}