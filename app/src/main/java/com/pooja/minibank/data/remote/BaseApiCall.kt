package com.pooja.minibank.data.remote

import com.pooja.minibank.core.utils.ResponseState
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object BaseApicCall {
    suspend fun <T> safeApiCall(apiCall : suspend () -> Response<T>) : ResponseState<T>
    {
        return try {
            val response = apiCall()
            if(response.isSuccessful) {
                val body = response.body()
                if(body!=null) {
                    ResponseState.Success(body)
                } else{
                    ResponseState.Error("Empty response body")
                }
            } else{
//                val errorMessage = when(response.code()) {
//                    400 -> "Bad Request"
//                    401 -> "Unauthorized or Session expired. Please login again"
//                    403 -> "Access denied"
//                    404 -> "API not found"
//                    408 -> "Request timeout"
//                    429 -> "Too many requests"
//                    500 -> "Internal server error"
//                    503 -> "Service unavailable"
//                    else -> response.message()
//                }
                val errorBody = response.errorBody()?.string()

                val errorMessage = try {

                    if (!errorBody.isNullOrEmpty()) {
                        val json = JSONObject(errorBody)
                        json.optString("message", response.message())
                    } else {
                        response.message()
                    }

                } catch (e: Exception) {
                    response.message()
                }

                ResponseState.Error(errorMessage)
                ResponseState.Error(message = errorMessage)
            }
        } catch (e: UnknownHostException) {
            ResponseState.Error(message = "No internet connection")
        } catch (e: SocketTimeoutException) {
            ResponseState.Error(message = "Request Timeout")
        } catch (e: IOException) {
            ResponseState.Error(message = "Network Error")
        } catch (e: Exception) {
            ResponseState.Error(message = e.localizedMessage ?: "Unknown error")
        } as ResponseState<T>
    }
}