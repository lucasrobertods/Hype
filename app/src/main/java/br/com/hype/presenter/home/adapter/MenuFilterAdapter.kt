package br.com.hype.presenter.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.hype.R
import br.com.hype.databinding.ItemMenuFilterBinding

class MenuFilterAdapter : RecyclerView.Adapter<MenuFilterAdapter.EventViewHolder>() {

    private var menuFilterList = listOf(
        MenuFilter.ARTIST,
        MenuFilter.LOCAL,
        MenuFilter.CITY,
        MenuFilter.DATE,
        MenuFilter.HOUR
    )

    var onItemClick : ((menuFilter: MenuFilter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_filter, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(menuFilterList[position])
    }

    override fun getItemCount() = menuFilterList.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMenuFilterBinding.bind(itemView)
        fun bind(item: MenuFilter) {
            binding.tvFilter.text = item.value
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

}

enum class MenuFilter(val value: String) {
    ARTIST("Artista"),
    LOCAL("Local"),
    CITY("Cidade"),
    DATE("Data"),
    HOUR("Hora")
}
