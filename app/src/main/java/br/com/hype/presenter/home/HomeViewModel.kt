package br.com.hype.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hype.domain.model.Event
import br.com.hype.domain.usecase.GetEventUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getEventUseCase: GetEventUseCase
) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun getEvents() {
        viewModelScope.launch {
            val eventList = getEventUseCase()

            _events.value = eventList.map { event ->
                event.toUi()
            }
        }
    }

}