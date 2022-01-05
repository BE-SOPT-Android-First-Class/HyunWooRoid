package com.l2hyunwoo.android.presentation.main.subscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingFragment
import com.l2hyunwoo.android.databinding.FragmentPersonalInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment :
    BindingFragment<FragmentPersonalInfoBinding>(R.layout.fragment_personal_info) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}
