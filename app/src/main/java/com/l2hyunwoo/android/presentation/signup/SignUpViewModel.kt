package com.l2hyunwoo.android.presentation.signup

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import com.l2hyunwoo.android.presentation.util.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {
    val inputId = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    private val inputIdLength = Transformations.map(inputId) { it.length }
    private val inputPasswordLength = Transformations.map(inputPassword) { it.length }

    val isSignUpButtonClickable = MediatorLiveData<Boolean>().apply {
        addSourceList(inputId, inputPassword) { canSignUp() }
    }

    private fun canSignUp() =
        ((inputIdLength.value ?: 0) > BLANK) &&
            ((inputPasswordLength.value ?: 0) > PASSWORD_MIN_LENGTH)

    companion object {
        const val BLANK = 0
        const val PASSWORD_MIN_LENGTH = 6
    }
}
