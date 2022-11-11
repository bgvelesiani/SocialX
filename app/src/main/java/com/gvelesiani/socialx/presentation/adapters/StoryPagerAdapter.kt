package com.gvelesiani.socialx.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.api.Story
import com.gvelesiani.socialx.databinding.FullStoryItemBinding

class StoryPagerAdapter(private val context: Context, private var storyList: ArrayList<Story>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return storyList.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            FullStoryItemBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)

        val story = binding.ivStory
        val userImage = binding.ivUserAvatar
        val name = binding.tvUserName

        storyList[position].let {
            Glide.with(context)
                .load(it.userImage?.url)
                .into(story)

            Glide.with(context)
                .load(it.userImage?.url)
                .into(userImage)

            name.text = it.userName
        }


        val vp = container as ViewPager
        vp.addView(binding.root, 0)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}