package br.com.hype.presenter.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hype.R
import br.com.hype.databinding.ItemFilterBinding
import br.com.hype.domain.model.Event
import br.com.hype.presenter.home.adapter.MenuFilter

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var eventList = listOf<Event>()
    private lateinit var menuFilter: MenuFilter
    var onItemClick: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount() = eventList.size

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFilterBinding.bind(itemView)
        fun bind(event: Event) {
            when(menuFilter) {
                MenuFilter.ARTIST -> binding.tvFilter.text = event.artist
                MenuFilter.LOCAL -> binding.tvFilter.text = event.location
                MenuFilter.CITY -> binding.tvFilter.text = event.city
                MenuFilter.DATE -> binding.tvFilter.text = event.date
                MenuFilter.HOUR -> binding.tvFilter.text = event.hour
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(event)
            }
        }
    }

    fun setEventList(eventList: List<Event>, menuFilter: MenuFilter) {
        this.eventList = eventList
        this.menuFilter = menuFilter
        notifyDataSetChanged()
    }

}
