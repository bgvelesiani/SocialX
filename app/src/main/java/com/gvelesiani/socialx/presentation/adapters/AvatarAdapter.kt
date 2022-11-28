package com.gvelesiani.socialx.presentation.adapters

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.databinding.AvatarItemBinding
import java.io.File


class AvatarAdapter(
    private val clickListener: (Pair<Bitmap?, Int>) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var avatars: List<Int> = arrayListOf(
        R.drawable.avatar_1,
        R.drawable.avatar_2,
        R.drawable.avatar_3,
        R.drawable.avatar_4,
        R.drawable.avatar_5,
        R.drawable.avatar_6,
        R.drawable.avatar_7,
        R.drawable.avatar_8,
        R.drawable.avatar_9,
        R.drawable.avatar_10,
        R.drawable.avatar_11
    )
    var binding: AvatarItemBinding? = null
    private var userKey: String = ""

    fun submitUserKey(key: String) {
        userKey = key
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AvatarItemBinding.inflate(inflater, parent, false)
        return AvatarViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = avatars[position]
        (viewHolder as AvatarViewHolder).bind(
            item,
            clickListener
        )
    }

    inner class AvatarViewHolder(private val binding: AvatarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            drawable: Int,
            clickListener: (Pair<Bitmap?, Int>) -> Unit
        ) {
            with(binding) {
                Glide.with(binding.root).load(drawable)
                    .placeholder(R.drawable.ic_launcher_background).into(binding.ivAvatar)

                val bitmap = ContextCompat.getDrawable(root.context, drawable)
                    ?.let { drawableToBitmap(it) }

                ivAvatar.setOnClickListener {
                    clickListener.invoke(Pair(bitmap, drawable))
                }
            }
        }
    }



    override fun getItemCount() = avatars.size
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    if(drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val width = drawable.intrinsicWidth
    val height = drawable.intrinsicHeight

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

