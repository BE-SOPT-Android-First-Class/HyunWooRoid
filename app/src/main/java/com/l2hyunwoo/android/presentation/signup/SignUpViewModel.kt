package com.l2hyunwoo.android.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2hyunwoo.android.domain.entity.UserInfo
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import com.l2hyunwoo.android.presentation.util.SingleLiveEvent
import com.l2hyunwoo.android.presentation.util.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {
    val inputId = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    private val inputIdLength = Transformations.map(inputId) { it.length }
    private val inputPasswordLength = Transformations.map(inputPassword) { it.length }
    private val _signUpEvent = SingleLiveEvent<Unit>()
    val signUpEvent: LiveData<Unit>
        get() = _signUpEvent

    val isSignUpButtonClickable = MediatorLiveData<Boolean>().apply {
        addSourceList(inputIdLength, inputPasswordLength) { canSignUp() }
    }

    fun signUp() {
        viewModelScope.launch {
            signUpRepository.signUp(
                UserInfo(
                    id = inputId.value ?: "",
                    password = inputPassword.value ?: ""
                )
            )
            _signUpEvent.call()
        }
    }

    private fun canSignUp() =
        ((inputIdLength.value ?: 0) > BLANK) &&
            ((inputPasswordLength.value ?: 0) > PASSWORD_MIN_LENGTH)

    companion object {
        const val BLANK = 0
        const val PASSWORD_MIN_LENGTH = 6
    }
}
