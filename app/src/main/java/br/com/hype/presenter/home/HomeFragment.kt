package br.com.hype.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.hype.databinding.FragmentHomeBinding
import br.com.hype.presenter.home.adapter.EventAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.events.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = false
            eventAdapter.setEventList(it)
        })

        setupView()

        return root
    }

    private fun setupView() {
        homeViewModel.getEvents()
        eventAdapter = EventAdapter().apply {
            onItemClick = { event ->
                findNavController().navigate(
                    HomeFragmentDirections.navigateToDetail(event)
                )
            }
        }
        binding.recyclerView.adapter = eventAdapter
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                isRefreshing = true
                homeViewModel.getEvents()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}