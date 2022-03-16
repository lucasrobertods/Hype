package br.com.hype.data.api

import br.com.hype.data.response.EventResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface EventApi {

    @GET("events")
    suspend fun getEvents(): Response<List<EventResponse>>

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