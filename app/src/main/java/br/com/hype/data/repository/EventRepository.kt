package br.com.hype.data.repository

import br.com.hype.data.api.EventApi
import br.com.hype.data.response.toModel
import br.com.hype.domain.model.Event
import java.lang.Exception

class EventRepositoryImpl(
    private val eventApi: EventApi
) : EventRepository {

    override suspend fun getEvents(): List<Event> {
        val result = eventApi.getEvents()

        return if(result.isSuccessful) {
            result.body()?.map {
                it.toModel()
            } ?: throw Exception()
        } else {
            throw Exception()
        }
    }

}

interface EventRepository {
    suspend fun getEvents(): List<Event>
}