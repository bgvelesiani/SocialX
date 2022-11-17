package com.gvelesiani.socialx.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.api.Comment
import com.gvelesiani.socialx.databinding.CommentItemBinding
import com.gvelesiani.socialx.domain.model.posts.PostModel

class CommentAdapter(
    private val clickListener: (PostModel) -> Unit,
    private val like: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var comments: List<Comment> = arrayListOf()
    var binding: CommentItemBinding? = null
    private var userId: Int = 0

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<Comment>) {
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
            comment: Comment,
            like: (Int) -> Unit
        ) {
            with(binding) {
                Glide.with(binding.root).load(comment.userAvatar)
                    .placeholder(R.drawable.ic_launcher_background).into(ivUserAvatar)
                tvUserName.text = comment.userName
                tvComment.text = comment.text
            }
        }
    }

    override fun getItemCount() = comments.size
}