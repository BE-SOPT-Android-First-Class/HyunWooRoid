package com.l2hyunwoo.android.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide

@BindingAdapter("srcFromUrl")
fun ImageView.setSrcFromUrl(url: LiveData<String>) {
    Glide.with(this.context)
        .load(url.value ?: "")
        .into(this)
}
