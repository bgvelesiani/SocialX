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
import com.gvelesiani.socialx.databinding.PostItemBinding
import com.gvelesiani.socialx.domain.model.posts.PostModel
import java.util.*

class PostAdapter(
    private val clickListener: (PostModel) -> Unit,
    private val like: (String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var postList: List<PostModel> = arrayListOf()
    var binding: PostItemBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<PostModel>) {
        postList = data
        notifyDataSetChanged()
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
            post: PostModel,
            clickListener: (PostModel) -> Unit,
            like: (String) -> Unit
        ) {
            with(binding) {
                cvPostImage.visibility = if (post.image != "") View.VISIBLE else View.GONE

                Glide.with(root).load("$IMAGES_MICRO_BASE_URL${post.userImage}")
                    .placeholder(R.drawable.ic_launcher_background).into(ivUserAvatar)

                Glide.with(root).load("$IMAGES_MICRO_BASE_URL${post.image}").into(ivPostImage)

                tvUserName.text = post.userName
                tvCreatedAt.text = Date(post.createdAt).toString()
                tvPostDescription.text = post.description
                with(userInteractions) {
                    tvLikes.text = post.likes.size.toString()

                    llLikes.setOnClickListener {
                        like.invoke(post.key)
                    }

                    ivLike.setImageResource(if (post.likedByCurrentUser) R.drawable.ic_like else R.drawable.ic_not_liked)
                    tvLikes.setTextColor(
                        if (post.likedByCurrentUser) {
                            ContextCompat.getColor(root.context, R.color.seed)
                        } else {
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.md_theme_dark_shadow
                            )
                        }
                    )

                    llComments.setOnClickListener {
                        clickListener.invoke(post)
                    }

                    tvComments.text = post.comments.size.toString()
                }
            }
        }
    }

    override fun getItemCount() = postList.size
}