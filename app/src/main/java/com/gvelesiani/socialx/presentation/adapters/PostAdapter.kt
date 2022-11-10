package com.gvelesiani.socialx.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.api.Post
import com.gvelesiani.socialx.databinding.PostItemBinding
import java.util.Date

class PostAdapter(
    private val clickListener: (Post) -> Unit,
    private val like: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var postList: List<Post> = arrayListOf()
    var binding: PostItemBinding? = null
    private var userId: Int = 0

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<Post>) {
        postList = data
        notifyDataSetChanged()
    }

    fun submitUserId(userId: Int){
        this.userId = userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = PostItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = postList[position]
        (viewHolder as PostViewHolder).bind(
            item,
            clickListener,
            like
        )
    }

    inner class PostViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: Post,
            clickListener: (Post) -> Unit,
            like: (Int) -> Unit
        ) {
            with(binding) {
                Glide.with(binding.root).load(post.userImage?.url)
                    .placeholder(R.drawable.ic_launcher_background).into(ivUserAvatar)
                tvUserName.text = post.userName
                tvCreatedAt.text = Date(post.created_at).toString()
                tvPostDescription.text = post.description
                tvLikes.text = post.likes.toString()

                llLikes.setOnClickListener {
                    like.invoke(post.id)
                }

                ivLike.setImageResource(if (post.userLikes.contains(userId)) R.drawable.ic_like else R.drawable.ic_not_liked)
                tvLikes.setTextColor(
                    if (post.userLikes.contains(userId)) {
                        ContextCompat.getColor(binding.root.context, R.color.seed)
                    } else {
                        ContextCompat.getColor(binding.root.context, R.color.md_theme_dark_shadow)
                    }
                )
            }
        }
    }

    override fun getItemCount() = postList.size
}