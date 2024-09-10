package com.arl.localwebsocet.util

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by AralBenli on 10.09.2024.
 */

infix fun ImageView.setImageUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}