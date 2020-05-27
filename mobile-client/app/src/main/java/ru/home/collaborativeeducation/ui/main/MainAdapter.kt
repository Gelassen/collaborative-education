package ru.home.collaborativeeducation.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CategoryViewItem

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable {

    private val fullSource: MutableList<CategoryViewItem> = ArrayList()
    private val datasource: MutableList<CategoryViewItem> = ArrayList()

    private lateinit var clickListener: ClickListener

    interface ClickListener {
        fun onCategoryClick(item: CategoryViewItem)
    }

    fun setListener(listener: ClickListener) {
        this.clickListener = listener
    }

    fun addItem(data: CategoryViewItem) {
        datasource.add(data)
        notifyItemInserted(datasource.size - 1)
    }

    fun update(datasource: MutableList<CategoryViewItem>) {
        this.datasource.clear()
        this.fullSource.clear()
        this.datasource.addAll(datasource)
        this.fullSource.addAll(datasource)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = datasource.get(position)
        holder.title.text = item.title
        holder.itemView.setOnClickListener {
            clickListener.onCategoryClick(item)
        }
        // holder.offer.setTextColor(if (item.isSelected) res.getColor(R.color.selected_text) else res.getColor(R.color.unselected_text))
    }

    override fun getItemCount(): Int {
        return datasource.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView

        init {
            title = itemView.findViewById(R.id.title)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence): FilterResults {
                val result = FilterResults()
                val data = fullSource.filter { it -> it.title!!.toLowerCase().contains(constraint.toString().toLowerCase()) }
                result.values = data
                return result
            }

            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                datasource.clear()
                datasource.addAll(if (results.values == null) mutableListOf() else results.values as MutableList<CategoryViewItem>)
                notifyDataSetChanged()
            }
        }
    }

}