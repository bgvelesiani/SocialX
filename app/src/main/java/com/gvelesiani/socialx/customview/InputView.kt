package com.gvelesiani.socialx.customview

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.gvelesiani.socialx.R

class InputView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    lateinit var input: EditText

    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.input_view, this, true)

    init {
        var checked = false
        val inputImage = view.findViewById(R.id.ivDrawable) as ImageView
        val inputTitle = view.findViewById(R.id.tvInputTitle) as TextView
        input = view.findViewById(R.id.etInput) as EditText
        val transformImage = view.findViewById(R.id.ivTransformImage) as ImageView

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.InputView, 0, 0)

        try {
            val image =
                typedArray.getDrawable(R.styleable.InputView_setImage)
            val title = typedArray.getString(R.styleable.InputView_setTitle)
            val hint = typedArray.getString(R.styleable.InputView_setHint)
            val isPasswordInput =
                typedArray.getBoolean(R.styleable.InputView_isPasswordInput, false)

            inputImage.setImageDrawable(image)
            inputTitle.text = title
            input.hint = hint

            transformImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.password_hidden
                )
            )
            if (isPasswordInput) {
                input.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                input.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }

            transformImage.setOnClickListener {
                if (isPasswordInput) {
                    checked = !checked
                    if (checked) {
                        input.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    } else {
                        input.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    input.setSelection(input.text.length)
                    transformImage.setImageDrawable(
                        if (checked) ContextCompat.getDrawable(
                            context,
                            R.drawable.password_shown
                        ) else ContextCompat.getDrawable(context, R.drawable.password_hidden)
                    )
                }
            }

            transformImage.isVisible = isPasswordInput
        } finally {
            typedArray.recycle()
        }
    }

    fun getInputText(): String = input.text.toString()
}