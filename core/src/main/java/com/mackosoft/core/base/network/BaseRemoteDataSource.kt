package com.mackosoft.core.base.network

import android.util.Log
import com.mackosoft.core.base.datasource.api.RetrofitClient
import retrofit2.Response
import java.io.IOException

open class BaseRemoteDataSource(private val retrofitClient: RetrofitClient) {

    private companion object {
        const val TAG = "BaseRemoteDataSource"
    }

    protected fun <Api> Class<Api>.buildApi(): Api = retrofitClient.instance.create(this)

    protected suspend fun <RemoteData> getRemoteData(
        apiCall: suspend () -> Response<RemoteData>,
        apiCallName: String,
    ): Result<RemoteData> {
        val result: Result<RemoteData> = try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                response.body()?.let { remoteData ->
                    Result.success(remoteData)
                } ?: run {
                    Log.e(TAG, "Response body is null for <$apiCallName>")
                    Result.failure(NullResponseBody(apiCallName))
                }
            } else {
                Result.failure(
                    UnsuccessfulApiCall(
                        apiCallName = apiCallName,
                        errorCode = response.code().toString(),
                    )
                )
            }
        } catch (ioe: IOException) {
            Log.e(TAG, "Failed to perform <$apiCallName>: ${ioe.message}", ioe)
            Result.failure(ioe)
        }

        return result
    }
}