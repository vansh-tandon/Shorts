package io.project.shorts.player

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.project.shorts.databinding.ItemThumbnailBinding
import io.project.shorts.models.current.PostsItem
import io.project.shorts.util.showImage

class ThumbnailAdapter(
    var context: Context,
    var thumbnail: List<PostsItem?>?): RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>() {

    inner class ThumbnailViewHolder(val binding: ItemThumbnailBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        return ThumbnailViewHolder(
            ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun getItemCount(): Int {
        return thumbnail!!.size
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.binding.itemImg.showImage(thumbnail!![position]?.submission?.thumbnail)
//        holder.binding.itemImg.showImage(model!!.submission!!.thumbnail)
        holder.binding.root.setOnClickListener {
            val mIntent = Intent(context, MainActivity2::class.java)
            mIntent.putExtra("ANAME", position)
            context.startActivity(mIntent)
        }
    }
}