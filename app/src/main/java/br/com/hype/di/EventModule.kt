package br.com.hype.di

import br.com.hype.data.api.EventApi
import br.com.hype.data.repository.EventRepository
import br.com.hype.data.repository.EventRepositoryImpl
import br.com.hype.domain.usecase.GetEvent
import br.com.hype.domain.usecase.GetEventUseCase
import br.com.hype.presenter.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single { EventApi.getInstance() }
    factory<EventRepository> { EventRepositoryImpl(get()) }
    factory<GetEventUseCase> { GetEvent(get()) }
    viewModel { HomeViewModel(get()) }
}