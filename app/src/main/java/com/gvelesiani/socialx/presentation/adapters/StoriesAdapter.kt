package com.gvelesiani.socialx.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.api.Story
import com.gvelesiani.socialx.databinding.NewStoryItemBinding
import com.gvelesiani.socialx.databinding.StoryItemBinding

class StoriesAdapter(
    private val clickListener: (Pair<List<Story>, Int>) -> Unit,
    private val addNewStoryClickListener: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var storyList: List<Story> = listOf()
    private var binding: StoryItemBinding? = null
    private var newStoryItemBinding: NewStoryItemBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<Story>) {
        storyList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = StoryItemBinding.inflate(inflater, parent, false)
        newStoryItemBinding = NewStoryItemBinding.inflate(inflater, parent, false)

        return if (viewType == 1) {
            NewStoryViewHolder(newStoryItemBinding!!)
        } else {
            StoryViewHolder(binding!!)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> {
                (viewHolder as NewStoryViewHolder).bind(addNewStoryClickListener)
            }
            else -> {
                val item = storyList[position - 1]
                (viewHolder as StoryViewHolder).bind(
                    position,
                    item,
                    clickListener,
                )
            }
        }

    }

    inner class NewStoryViewHolder(private val binding: NewStoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            addNewStoryClickListener: () -> Unit
        ) {
            with(binding) {
                root.setOnClickListener {
                    addNewStoryClickListener.invoke()
                }
            }
        }
    }

    inner class StoryViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            story: Story,
            clickListener: (Pair<List<Story>, Int>) -> Unit,
        ) {
            with(binding) {
                Glide.with(binding.root).load(story.userImage?.url)
                    .placeholder(R.drawable.ic_launcher_background).into(ivUserAvatar)
                tvUserName.text = story.userName
                root.setOnClickListener {
                    clickListener.invoke(Pair(storyList, position - 1))
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ADD_NEW_STORY_VIEW_TYPE
            else -> {
                STORY_VIEW_TYPE
            }
        }
    }


    override fun getItemCount() = storyList.size + 1

    companion object {
        const val ADD_NEW_STORY_VIEW_TYPE = 1
        const val STORY_VIEW_TYPE = 2
    }
}