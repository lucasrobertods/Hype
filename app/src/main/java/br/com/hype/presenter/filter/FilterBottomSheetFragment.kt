package br.com.hype.presenter.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.hype.databinding.FragmentFilterBottomSheetBinding
import br.com.hype.domain.model.Event
import br.com.hype.presenter.base.BaseBottomSheetFragment
import br.com.hype.presenter.filter.adapter.FilterAdapter
import br.com.hype.presenter.home.adapter.MenuFilter

class FilterBottomSheetFragment : BaseBottomSheetFragment() {

    private lateinit var binding: FragmentFilterBottomSheetBinding

    private var eventList = listOf<Event>()
    private lateinit var menuFilter: MenuFilter
    var onFiltered: ((eventList: List<Event>) -> Unit)? = null

    private lateinit var filterAdapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.tvTitle.text = menuFilter.value
        filterAdapter = FilterAdapter().apply {
            setEventList(eventList, menuFilter)
            onItemClick = { selectedEvent ->
                val listFiltered = eventList.filter {
                    when(menuFilter) {
                        MenuFilter.ARTIST -> it.artist == selectedEvent.artist
                        MenuFilter.LOCAL -> it.location == selectedEvent.location
                        MenuFilter.CITY -> it.city == selectedEvent.city
                        MenuFilter.DATE -> it.date == selectedEvent.date
                        MenuFilter.HOUR -> it.hour == selectedEvent.hour
                    }
                }
                onFiltered?.invoke(listFiltered)
                dismiss()
            }
        }
        binding.recyclerView.adapter = filterAdapter
    }

    companion object {
        fun newInstance(eventList: List<Event>, menuFilter: MenuFilter) =
            FilterBottomSheetFragment().apply {
                this.eventList = eventList
                this.menuFilter = menuFilter
            }
    }
}