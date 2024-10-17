package com.ru.malevich.unscramblegame.load.data.cloud

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CloudDataSource {
    suspend fun load(): List<String>
    class Base(
        private val service: WordsService,
        private val size: Int
    ) : CloudDataSource {
        override suspend fun load(): List<String> {
            val response = service.words(size).execute()
            if (response.isSuccessful) {
                val body = response.body()!!
                if (body.status in 200..299)
                    return body.words
                else
                    throw IllegalStateException(response.errorBody().toString())
            } else
                throw IllegalStateException(response.errorBody().toString())
        }
    }
}

class WordsResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val words: List<String>
)

interface WordsService {
    @GET("api")
    fun words(
        @Query("words") words: Int
    ): Call<WordsResponse>
}