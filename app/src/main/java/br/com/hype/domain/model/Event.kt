package br.com.hype.domain.model

import java.io.Serializable

data class Event(
    val name: String,
    val artist: String,
    val photo: String,
    val description: String,
    val city: String,
    val location: String,
    val link: String,
    val date: String,
    val hour: String,
) : Serializable {
    fun toUi() = this
}
