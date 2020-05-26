package ru.home.collaborativeeducation.ui.course.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.*
import ru.home.collaborativeeducation.storage.Cache

class CourseDetailsAdapter : RecyclerView.Adapter<CourseDetailsAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemClick(item: CourseWithMetadataAndComments)
        fun onLikeClick(item: CourseWithMetadataAndComments)
    }

    private lateinit var clickListener: ClickListener

    private val model: MutableList<CourseWithMetadataAndComments> = mutableListOf()

    private lateinit var cache: Cache

    fun addItem(item: CourseWithMetadataAndComments) {
        this.model.add(item)
        notifyDataSetChanged()
    }

    fun update(data: List<CourseWithMetadataAndComments>) {
        model.clear()
        model.addAll(data)
        notifyDataSetChanged()
    }

    fun updateItem(item: CourseWithMetadataAndComments) {
        val index = model.indexOf(item)
        model.mapIndexed { idx, it -> if (it.source.uid == item.source.uid) item else it}
        model.find { it -> it.source.uid == item.source.uid }
        notifyItemChanged(index)
    }

    fun setListener(listener: ClickListener) {
        this.clickListener = listener
    }

    fun onInit(context: Context) {
        cache = Cache(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.course_details_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = model.get(position)
//        val item = data.source
//        val likes = data.metadata.likes
        holder.title.text = data.source.title
        holder.desc.text = data.source.source
        holder.commentsCounter.setText(data.metadata.comments.size.toString())
        updateLikes(holder, data.metadata.likes)
        updateAuthor(holder, data.source)
        holder.itemView.setOnClickListener {
            if (clickListener == null) return@setOnClickListener

            clickListener.onItemClick(data)
        }
        holder.likeCounter.setOnClickListener {
            updateModel(data.metadata.likes)
            updateLikes(holder, data.metadata.likes)
            Toast.makeText(holder.itemView.context, if (isThisUserMadeLike(data.metadata.likes)) "You like it!" else "You like it less", Toast.LENGTH_SHORT).show()

            clickListener.onLikeClick(data)
        }
        holder.commentsCounter.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Comments click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateAuthor(holder: ViewHolder, item: CourseSourceItem) {
        if (isThisUserAuthor(item)) {
            holder.author.setText(holder.itemView.context.getString(R.string.label_author_origin))
        } else {
            holder.author.setText(holder.itemView.context.getString(R.string.label_author_alternative))
        }
    }

    private fun updateModel(likes: Likes) {
        if (isThisUserMadeLike(likes)) {
            val id = likes.users!!.indexOf(cache.getUuid())
            likes.users!!.removeAt(id)
            likes.counter = likes.counter!! - 1
        } else {
            likes.users!!.add(cache.getUuid())
            likes.counter = likes.counter!! + 1
        }
    }

    private fun updateLikes(holder: ViewHolder, likes: Likes) {
        if (likes.counter!! < 0L) likes.counter = 0
        holder.likeCounter.setText(likes.counter.toString())
        holder.likeCounter.compoundDrawables.get(0).setLevel(if (isThisUserMadeLike(likes)) 1 else 0)
    }

    private fun isThisUserMadeLike(likes: Likes): Boolean {
        val testOnly = cache.getUuid()
        return likes.users!!.contains(cache.getUuid())
    }

    private fun isThisUserAuthor(source: CourseSourceItem): Boolean {
        return source.users.contains(cache.getUuid())
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val desc: TextView
        val likeCounter: TextView
        val commentsCounter: TextView
        val author: TextView

        init {
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            likeCounter = itemView.findViewById(R.id.likeCounter)
            commentsCounter = itemView.findViewById(R.id.commentsCounter)
            author = itemView.findViewById(R.id.author)
        }
    }

}