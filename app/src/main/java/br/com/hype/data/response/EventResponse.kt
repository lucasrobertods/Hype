package br.com.hype.data.response

import br.com.hype.domain.model.Event

data class EventResponse(
    val name: String? = "",
    val artist: String? = "",
    val photo: String? = "",
    val description: String? = "",
    val city: String? = "",
    val location: String? = "",
    val link: String? = "",
    val date: String? = "",
    val hour: String? = "",
)

fun EventResponse.toModel() = Event(
    name = name.orEmpty(),
    artist = artist.orEmpty(),
    photo = photo.orEmpty(),
    description = description.orEmpty(),
    city = city.orEmpty(),
    location = location.orEmpty(),
    link = link.orEmpty(),
    date = date.orEmpty(),
    hour = hour.orEmpty()
)
