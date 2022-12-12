package com.gvelesiani.socialx.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.common.IMAGES_MICRO_BASE_URL
import com.gvelesiani.socialx.databinding.CommentItemBinding
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.posts.PostModel

class CommentAdapter(
    private val clickListener: (PostModel) -> Unit,
    private val like: (String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var comments: List<CommentModel> = arrayListOf()
    var binding: CommentItemBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<CommentModel>) {
        comments = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CommentItemBinding.inflate(inflater, parent, false)
        return CommentViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = comments[position]
        (viewHolder as CommentViewHolder).bind(
            item,
            like
        )
    }

    inner class CommentViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            comment: CommentModel,
            like: (String) -> Unit
        ) {
            with(binding) {
                with(comment) {
                    Glide.with(binding.root).load("$IMAGES_MICRO_BASE_URL${userAvatar}")
                        .placeholder(R.drawable.ic_launcher_background).into(ivUserAvatar)
                    tvUserName.text = userName
                    tvComment.text = text
                    tvCreatedAt.text = createdAt
                    btLike.setOnClickListener { like(key) }
                    btLike.setTextColor(
                        ContextCompat.getColor(
                            btLike.context,
                            if (likedByCurrentUser) R.color.liked else R.color.comment_like_txt
                        )
                    )
                    btLike.text = if (likedByCurrentUser) "Liked" else "Like"
                    tvLikes.text = likes.size.toString()
                }
            }
        }
    }

    override fun getItemCount() = comments.size
}