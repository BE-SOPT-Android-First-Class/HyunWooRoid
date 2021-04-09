package com.l2hyunwoo.android.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2hyunwoo.android.domain.entity.UserInfo
import com.l2hyunwoo.android.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    val inputId = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    fun login() {
        viewModelScope.launch {
            val result = loginRepository.login(
                UserInfo(
                    id = inputId.value ?: "",
                    password = inputPassword.value ?: ""
                )
            )
            handleMessage(result.message)
        }
    }

    private fun handleMessage(message: String) {
        when (message) {
            "로그인 성공" -> _loginResult.value = Valid
            "회원정보가 일치하지 않습니다." -> _loginResult.value = Inconsistent
            "Id doesn't exist" -> _loginResult.value = NoneId
            else -> throw IllegalStateException("The corresponding state does not exist")
        }
    }
}

sealed class LoginResult
object Valid : LoginResult()
object NoneId : LoginResult()
object Inconsistent : LoginResult()
