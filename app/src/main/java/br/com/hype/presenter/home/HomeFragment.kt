package br.com.hype.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.hype.R
import br.com.hype.databinding.FragmentHomeBinding
import br.com.hype.domain.model.Event
import br.com.hype.presenter.base.extensions.show
import br.com.hype.presenter.filter.FilterBottomSheetFragment
import br.com.hype.presenter.home.adapter.EventAdapter
import br.com.hype.presenter.home.adapter.MenuFilter
import br.com.hype.presenter.home.adapter.MenuFilterAdapter
import br.com.hype.presenter.home.viewmodel.HomeViewModel
import br.com.hype.presenter.login.SessionManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var eventList = listOf<Event>()

    private lateinit var eventAdapter: EventAdapter
    private lateinit var menuFilterAdapter: MenuFilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()

        return root
    }

    private fun handleObservers() {
        homeViewModel.events.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            eventList = it
            updateEventList(it)
        }
    }

    private fun setupView() {
        setupUser()
        handleObservers()
        setupAdapters()
        setupRefresh()
        setupListeners()
    }

    private fun setupUser() {
        SessionManager.user?.let {
            binding.tvWelcome.text = getString(R.string.home_fragment_welcome_name, it.name.split(" ")[0])
        }
    }

    private fun setupListeners() {
        binding.imgClearFilter.setOnClickListener {
            updateEventList(eventList)
        }
    }

    private fun showFilterDialog(menuFilter: MenuFilter) {
        FilterBottomSheetFragment.newInstance(eventList, menuFilter).apply {
            onFiltered = {
                updateEventList(it)
            }
        }.show(childFragmentManager)
    }

    private fun updateEventList(list: List<Event>) {
        eventAdapter.setEventList(list)
    }

    private fun setupRefresh() {
        refreshEvents()
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                isRefreshing = true
                refreshEvents()
            }
        }
    }

    private fun setupAdapters() {
        eventAdapter = EventAdapter().apply {
            onItemClick = { event ->
                findNavController().navigate(
                    HomeFragmentDirections.navigateToDetail(event)
                )
            }
        }
        binding.recyclerView.adapter = eventAdapter

        menuFilterAdapter = MenuFilterAdapter().apply {
            onItemClick = { menuFilter ->
                showFilterDialog(menuFilter)
            }
        }
        binding.recyclerViewFilter.adapter = menuFilterAdapter
    }

    private fun refreshEvents() {
        homeViewModel.getEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}