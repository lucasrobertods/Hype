package br.com.hype.data.api

import br.com.hype.data.response.EventResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {

    @GET("events")
    suspend fun getEvents(
        @Query("size") size: Int = 100,
        @Query("sort_by") sortBy: String = "asc",
        @Query("value") value: String = "dateTime"
    ): Response<List<EventResponse>>

    companion object {
        var retrofitService: EventApi? = null
        fun getInstance() : EventApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://event-services.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(EventApi::class.java)
            }
            return retrofitService!!
        }

    }

}