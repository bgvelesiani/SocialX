package com.gvelesiani.socialx.presentation.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.api.Story
import com.gvelesiani.socialx.common.applyBundle
import com.gvelesiani.socialx.databinding.FragmentStoryBinding
import com.gvelesiani.socialx.presentation.adapters.StoryPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    lateinit var viewPagerAdapter: StoryPagerAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryBinding
        get() = FragmentStoryBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        val stories = arguments?.getParcelableArrayList<Story>(STORIES_ARGUMENT)
        val storyPosition = arguments?.getInt(STORY_POSITION)

        stories?.let {
            viewPagerAdapter = StoryPagerAdapter(requireContext(), it)
            binding.viewpager.adapter = viewPagerAdapter
            binding.viewpager.currentItem = storyPosition ?: 0
        }
    }

    override fun setupObservers() {
    }

    companion object {
        private const val STORIES_ARGUMENT = "STORIES_ARGUMENT"
        private const val STORY_POSITION = "STORY_POSITION"

        fun newInstance(story: ArrayList<Story>, position: Int): StoryFragment =
            StoryFragment().applyBundle {
                putParcelableArrayList(STORIES_ARGUMENT, story)
                putInt(STORY_POSITION, position)
            }
    }
}