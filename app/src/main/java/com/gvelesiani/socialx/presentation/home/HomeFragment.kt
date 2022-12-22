package com.gvelesiani.socialx.presentation.home

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.api.Story
import com.gvelesiani.socialx.base.BaseFragment
import com.gvelesiani.socialx.common.IMAGES_MICRO_BASE_URL
import com.gvelesiani.socialx.databinding.FragmentHomeBinding
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.presentation.adapters.PostAdapter
import com.gvelesiani.socialx.presentation.adapters.StoriesAdapter
import com.gvelesiani.socialx.presentation.comments.CommentsFragment
import com.gvelesiani.socialx.presentation.createpost.CreatePostFragment
import com.gvelesiani.socialx.presentation.post.PostFragment
import com.gvelesiani.socialx.presentation.search.SearchFragment
import com.gvelesiani.socialx.presentation.story.StoryFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeVM by activityViewModels()
    private lateinit var adapter: PostAdapter
    private lateinit var storyAdapter: StoriesAdapter
    private var userInfo: UserInfoResponseModel = UserInfoResponseModel()

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.getUserInfo()
        viewModel.getStories()
        setupPostRecyclerView()
        setupStoryRecyclerView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            btSearch.setOnClickListener {
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out,
                        R.anim.slide_in,
                        R.anim.slide_out
                    )
                        .add(R.id.container, SearchFragment.newInstance())
                        .addToBackStack(toString())
                }
            }

            binding.createPostBanner.root.setOnClickListener {
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_from_bot,
                        R.anim.slide_to_bot,
                        R.anim.slide_from_bot,
                        R.anim.slide_to_bot
                    )
                        .add(R.id.container, CreatePostFragment.newInstance(userInfo))
                        .addToBackStack(toString())
                }
            }
        }
    }

    private fun setupPostRecyclerView() {
        adapter = PostAdapter(
            clickListener = {
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_from_bot,
                        R.anim.slide_to_bot,
                        R.anim.slide_from_bot,
                        R.anim.slide_to_bot
                    )
//                        .add(R.id.container, CommentsFragment.newInstance(it.id, userImage = userInfo.avatar?.url ?: ""))
                        .add(
                            R.id.container,
                            CommentsFragment.newInstance(
                                it.key,
                                userInfo.avatar,
                                userInfo.key
                            )
                        )
                        .addToBackStack(toString())
                }
            },
            like = {
                viewModel.likeOrDislikePost(it)
            },
            postClick = {
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out,
                        R.anim.slide_in,
                        R.anim.slide_out
                    )
                        .add(R.id.container, PostFragment.newInstance(it, userInfo))
                        .addToBackStack(toString())
                }
            }
        )
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupStoryRecyclerView() {
        storyAdapter = StoriesAdapter(clickListener = {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    StoryFragment.newInstance(it.first as ArrayList<Story>, it.second)
                ).addToBackStack(toString())
                .commit()
        }, addNewStoryClickListener = {})
        binding.stories.rvStories.adapter = storyAdapter
        binding.stories.rvStories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun setupObservers() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiState.collect { uiState ->
                        when (val state = uiState) {
                            is HomeUiState.Empty -> {}
                            is HomeUiState.Error -> {
                                showLoader(false)
                            }
                            is HomeUiState.Loading -> {
                                showLoader(true)
                            }

                            is HomeUiState.PostSuccess -> {
                                showLoader(false)
                                adapter.submitData(state.posts)
                            }

                            is HomeUiState.UserInfoSuccess -> {
                                Glide.with(binding.createPostBanner.ivUserAvatar)
                                    .load("${IMAGES_MICRO_BASE_URL}${state.userInfo.avatar}")
                                    .into(binding.createPostBanner.ivUserAvatar)
                                userInfo = state.userInfo
                                setKey(userInfo.key)
                                viewModel.getPosts()
                            }

                            is HomeUiState.StoriesSuccess -> {
//                                storyAdapter.submitData(state.stories)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoader(loading: Boolean) {
        with(binding) {
            loader.isVisible = loading
            content.isVisible = !loading
        }
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

}