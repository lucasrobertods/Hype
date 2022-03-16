package br.com.hype.domain.usecase

import br.com.hype.data.repository.EventRepository
import br.com.hype.domain.model.Event
import java.lang.Exception

class GetEvent (
    private val eventRepository: EventRepository
): GetEventUseCase {

    override suspend fun invoke(): List<Event> = try {
        eventRepository.getEvents()
    } catch (e : Exception) {
        listOf()
    }

}

interface GetEventUseCase {
    suspend operator fun invoke(): List<Event>
}