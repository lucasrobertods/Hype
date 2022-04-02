package br.com.hype.presenter.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.hype.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import java.util.*

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        showData()
        setupListeners()
    }

    private fun showData() {
        args.event.also { event ->
            Glide.with(requireContext()).load(event.photo).into(binding.imgEvent)
            binding.tvNome.text = event.name
            binding.tvDate.text = event.date
            binding.tvHour.text = event.hour
            binding.tvLocation.text = event.location
            binding.tvDescription.text = event.description
        }
    }

    private fun setupListeners() {
        binding.btnNavigate.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(args.event.link)
            startActivity(openURL)
        }
        binding.llReminder.setOnClickListener {
            addEvent()
        }
    }

    private fun addEvent() {
        val dateSplit = args.event.date.split("/")
        val day = dateSplit[0].toInt()
        val month = dateSplit[1].toInt()
        val year = dateSplit[2].toInt()

        val hourSplit = args.event.hour.split(":")
        val hour = hourSplit[0].toInt()
        val minute = hourSplit[1].toInt()

        val beginTime = Calendar.getInstance()
        beginTime.set(year, month, day, hour, minute)

        val endTime = Calendar.getInstance()
        endTime.set(year, month, day, hour + 4, 0)

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event";
        intent.putExtra(CalendarContract.Events.TITLE, args.event.name)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
        intent.putExtra(CalendarContract.Events.ALL_DAY, false)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, args.event.description)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}