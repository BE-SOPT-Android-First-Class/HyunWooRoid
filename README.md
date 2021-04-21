# ✨BE SOPT✨ Android Assignment
## What Did You Use?

- Kotlin
- ConstraintLayout
- Gradle Kotlin DSL
- Android Jetpack
    - Lifecycle
        - ViewModel
        - LiveData
        - LifeCycleObserver
    - DataStore
    - Dagger-Hilt [WEEK 1]
    - DataBinding
- Dagger2(AndroidDagger)
- Android KTX
- Coroutine
- Glide
- ktlint
- [Android Kotlin Official Guide](https://developer.android.com/kotlin/style-guide)
- MVVM Architecture

## 1주차 과제 (LEVEL 1, 2, 3 완료)

### 화면캡쳐
<img src="https://user-images.githubusercontent.com/54518925/114260620-0e4d4a80-9a11-11eb-9529-baaf746a73ff.gif" width="30%" />

### 생명주기를 Log로 호출하는 법 - LifeCycleObserver를 사용
- Activity/Fragment와 같은 Component의 Lifecycle은 Android Jetpack에서 제공하는 **LifeCycleObserver를 활용하여 구현하였다**
- Activity의 Lifecycle을 LifeCycleEventLogger에 집어넣어서 생명주기 Event가 변할 때마다 로그를 찍게 설정

```kotlin
protected inner class LifeCycleEventLogger(private val className: String) : LifecycleObserver {
    fun registerLogger(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun log() {
        Log.d("${className}LifeCycleEvent", "${lifecycle.currentState}")
    }
}
```

### ``registerForActivityResult``를 사용하여 회원가입하기
- startActivityForResult가 Deprecate가 되고 호출된 Activity에서 결과를 받기 위한 새로운 대안으로 registerForActivityResult가 제시되었다.
- registerForActivityResult는 StartActivityForResult뿐만 아니라 GetContent(MediaStore), RequestPermission 같은 다양한 요청을 만들 수 있다

```kotlin
    private fun setUIListener() {
        val signUpActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK)
                    toast("회원가입이 완료되었습니다.")
            }

        binding.btnSigninSignup.setOnClickListener {
            signUpActivityLauncher.launch(Intent(this, SignUpActivity::class.java))
        }
    }
```

### SignUpActivity에서 빈칸 인식
- MutableLiveData를 활용하여 EditText의 text를 실시간으로 가져옴
- 데이터 처리를 View에서 해주는 것이 아니라 ViewModel에서 해줌으로써 View의 기능을 분리시킴
    - 모든 View에서는 Listener, ViewModel의 Data subscribe, View Navigation만 실행
    - 아키텍처(MVVM)을 활용하여 각 클래스의 기능을 분리, 기능 추가/수정/삭제에 쉽게 대응 가능
    - Android에서 일반적으로 사용할 수 있는 MVC 아키텍처..라고 불리는 것은 모바일 환경 특성상
        - 화면에 결과를 보여주는 View와
        - 사용자의 입력을 받는 Controller
    가 한 Class에 존재할 수밖에 없어서 기능 분리하기가 서버 사이드 MVC보다 훨씬 어렵다
    - 실제로 Android에서 [Real-MVC](https://github.com/step4me/todo-mvc)를 구현하는 코드도 있긴 하다만...그럴 바에 MVP나 MVVM을 구현하는 게 더 낫지 않을까하는게 내 비루한 생각

```kotlin
    val inputId = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    private val inputIdLength = Transformations.map(inputId) { it.length }
    private val inputPasswordLength = Transformations.map(inputPassword) { it.length }

    // MediatorLiveData를 활용하여 ID, Password가 변경할 때마다 canSignUp 값 Check
    val isSignUpButtonClickable = MediatorLiveData<Boolean>().apply {
        addSourceList(inputIdLength, inputPasswordLength) { canSignUp() }
    }

    // canSignUp이 true일때만 SignUp 버튼 동작
    private fun canSignUp() =
        ((inputIdLength.value ?: 0) > BLANK) &&
            ((inputPasswordLength.value ?: 0) > PASSWORD_MIN_LENGTH)

    companion object {
        const val BLANK = 0
        const val PASSWORD_MIN_LENGTH = 6
    }
```

### 회원가입/로그인 로직 정리
- 회원가입을 하고 DataStore에 ID, Password를 저장
- 로그인을 할 떄 입력된 ID, Password 값이 DataStore에 저장되어 있는 값이 맞는 지 확인
- 로그인 성공 시 MainActivity로 아니면 Toast 띄움

```kotlin
    // SignUpViewModel
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

    // SignUpRepositoryImpl
    class SignUpRepositoryImpl @Inject constructor(
        private val dataStore: DataStore<Preferences>
    ) : SignUpRepository {
        override suspend fun signUp(userInfo: UserInfo) {
            dataStore.edit {
                it[KEY_USER_ID] = userInfo.id
                it[KEY_USER_PASSWORD] = userInfo.password
            }
        }
    }

    // LoginRepositoryImpl
    override suspend fun login(userInfo: UserInfo): BaseResponse<String> =
        withContext(Dispatchers.IO) {
            // 등록된 ID가 없으면
            if (!isIdExist()) {
                return@withContext BaseResponse<String>(
                    data = "FAILURE",
                    message = "Id doesn't exist",
                    status = 400,
                    success = false
                )
            }
            // 회원정보(ID, Password)와 일치하지 않으면
            if (!isValidUser(userInfo)) {
                return@withContext BaseResponse<String>(
                    data = "FAILURE",
                    message = "회원정보가 일치하지 않습니다.",
                    status = 400,
                    success = false
                )
            }
            // 로그인 성공 시
            return@withContext BaseResponse<String>(
                data = "SUCCESS",
                message = "로그인 성공",
                status = 200,
                success = true
            )
        }
```

### ConstraintLayout Chain 이용하기
- 각 뷰의 start, end의 constraint를 알아야 하고 chain 지정
```
        <Button
            android:id="@+id/btn_signin_login"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            style="@style/NoMarginButton"
            app:backgroundTint="@color/black"
            android:text="LOGIN"
            android:paddingHorizontal="16dp"
            android:onClick="@{() -> viewModel.login()}"
            app:layout_constraintTop_toTopOf="@+id/btn_signin_signup"
            app:layout_constraintStart_toEndOf="@+id/btn_signin_signup"
            app:layout_constraintHorizontal_bias="0.5"
            app:layout_constraintEnd_toEndOf="parent" />

        <Button
            android:id="@+id/btn_signin_theme_change"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            style="@style/NoMarginButton"
            app:backgroundTint="@color/black"
            android:text="THEME CHANGE"
            android:layout_marginTop="24dp"
            android:textSize="12sp"
            app:layout_constraintTop_toBottomOf="@+id/btn_signin_signup"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent" />
```

### Log 화면 캡쳐
<img src="https://user-images.githubusercontent.com/54518925/114260300-e4932400-9a0e-11eb-9696-02542c78bab0.png" />

