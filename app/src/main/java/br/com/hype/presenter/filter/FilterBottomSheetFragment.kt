package br.com.hype.presenter.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.hype.databinding.FragmentFilterBottomSheetBinding
import br.com.hype.domain.model.Event
import br.com.hype.presenter.base.BaseBottomSheetFragment
import br.com.hype.presenter.filter.adapter.FilterAdapter

class FilterBottomSheetFragment : BaseBottomSheetFragment() {

    private lateinit var binding: FragmentFilterBottomSheetBinding

    private var eventList = listOf<Event>()
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
        filterAdapter = FilterAdapter().apply {
            setEventList(eventList)
            onItemClick = { selectedEvent ->
                val listFiltered = eventList.filter {
                    it.artist == selectedEvent.artist
                }
                onFiltered?.invoke(listFiltered)
                dismiss()
            }
        }
        binding.recyclerView.adapter = filterAdapter
    }

    companion object {
        fun newInstance(eventList: List<Event>) =
            FilterBottomSheetFragment().apply {
                this.eventList = eventList
            }
    }
}