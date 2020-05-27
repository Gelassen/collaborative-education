package ru.home.collaborativeeducation.ui.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CourseViewItem

class CourseAdapter :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>(), Filterable {

    interface ClickListener {
        fun onItemClick(item: CourseViewItem)
    }

    private lateinit var clickListener: ClickListener

    private val fullSource: MutableList<CourseViewItem> = ArrayList()
    private val datasource: MutableList<CourseViewItem> = ArrayList()

    fun addItem(data: CourseViewItem) {
        datasource.add(data)
        notifyItemInserted(datasource.size - 1)
    }

    fun update(datasource: MutableList<CourseViewItem>) {
        this.datasource.clear()
        this.fullSource.clear()
        this.datasource.addAll(datasource)
        this.fullSource.addAll(datasource)
        notifyDataSetChanged()
    }

    fun setListener(listener: ClickListener) {
        this.clickListener = listener
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
            if (clickListener == null) return@setOnClickListener

            clickListener.onItemClick(item)
        }
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
                if (results.values == null) return

                datasource.clear()
                datasource.addAll(results.values as MutableList<CourseViewItem>)
                notifyDataSetChanged()
            }
        }
    }

}