package br.com.hype.presenter.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hype.R
import br.com.hype.databinding.ItemEventBinding
import br.com.hype.domain.model.Event
import com.bumptech.glide.Glide

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var eventList = listOf<Event>()
    var onItemClick : ((event: Event) -> Unit)? = null
    var onFilterClick : ((eventList: List<Event>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount() = eventList.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemEventBinding.bind(itemView)
        fun bind(event: Event) {

            Glide.with(itemView.context).load(event.photo).into(binding.imgEvent)
            binding.tvName.text = event.name
            binding.tvDate.text = event.date
            binding.tvHour.text = event.hour
            binding.tvLocation.text = event.location

            itemView.setOnClickListener {
                onItemClick?.invoke(event)
            }
        }
    }

    fun setEventList(eventList: List<Event>) {
        this.eventList = eventList
        notifyDataSetChanged()
    }

    fun getEventList() = this.eventList

}
