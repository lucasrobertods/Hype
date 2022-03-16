package br.com.hype.presenter.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.hype.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        args.event.also { event ->
            Glide.with(requireContext()).load(event.photo).into(binding.imgEvent)
            binding.tvNome.text = event.name
            binding.tvDate.text = event.date
            binding.tvHour.text = event.hour
            binding.tvLocation.text = event.location
            binding.tvDescription.text = event.description

            binding.btnNavigate.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(event.link)
                startActivity(openURL)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}