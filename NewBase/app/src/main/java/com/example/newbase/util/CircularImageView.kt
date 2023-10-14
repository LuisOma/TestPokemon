package com.example.newbase.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.newbase.R

class CircularImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView
    private var placeholder: Drawable? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.circular_image_view, this, true)
        imageView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)
        clipToOutline = true
    }

    fun loadImageOrText(url: String?, cadena: String?, placeholder: Drawable) {
        this.placeholder = placeholder

        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
                .listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setInitialsOrPlaceholder(cadena)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageView.visibility = View.VISIBLE
                        textView.visibility = View.GONE
                        if (resource != null) imageView.setImageDrawable(resource)
                        return false
                    }

                })
                .submit()
        } else {
            setInitialsOrPlaceholder(cadena)
        }
    }

    private fun setInitialsOrPlaceholder(cadena: String?) {
        if (!cadena.isNullOrEmpty()) {
            val words = cadena.split(" ")
            var initials = ""

            if (words.size >= 2) {
                initials = "${words[0][0]}${words[1][0]}"
            } else {
                initials = "${words[0][0]}"
            }

            if (!initials[0].isLetter()) {
                imageView.setImageDrawable(placeholder)
                imageView.visibility = View.VISIBLE
                textView.visibility = View.GONE
            } else {
                textView.text = initials.toUpperCase()
                textView.visibility = View.VISIBLE
                imageView.visibility = View.GONE
            }
        } else {
            imageView.setImageDrawable(placeholder)
            imageView.visibility = View.VISIBLE
            textView.visibility = View.GONE
        }
    }
}
