# ✨BE SOPT✨ Android Assignment

## What Did You Use?

- Kotlin
- [Android Kotlin Official Guide](https://developer.android.com/kotlin/style-guide)
- MVVM Architecture
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
  - Navigation Component
- Dagger2(AndroidDagger)
- Android KTX
- Coroutine
- Glide
- ktlint
- Gson
- OkHttp
  - Retrofit
- Android NDK
  - jni

### 화면캡쳐

<p align="center">
    <img src="https://user-images.githubusercontent.com/54518925/116030360-58cbf980-a696-11eb-9843-9a66bccbc431.gif" width="30%" />
    <img src="https://user-images.githubusercontent.com/54518925/116030364-59fd2680-a696-11eb-8683-c6c66acddf50.gif" width="30%" />
</p>


## 1주차 과제 (LEVEL 1, 2, 3 완료)

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

## 2주차 과제 (LEVEL 1, LEVEl 2-2 구현)

### Dagger2로 Dependency Injection 구현

- Hilt의 선조 격인 Dagger2로 Dependency Injection을 해보면서 Hilt 내부에서는 어떠한 일들이 일어나는 지 확인해보고자 Dagger를 이용해 봄

```kotlin
// Dagger Component
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        SignInModule::class,
        SignUpModule::class,
        MainModule::class
    ]
)
interface AppComponent : AndroidInjector<GithubApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Context): Builder
        fun build(): AppComponent
    }
}

// App Module
@Module(includes = [ApplicationModuleBinds::class])
class ApplicationModule() {
    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("GithubDataStore") }
}

// Interface는 @Binds로 Injection
@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    abstract fun bindSignUpRepository(repository: SignUpRepositoryImpl): SignUpRepository

    @Singleton
    @Binds
    abstract fun bindGithubDataSource(dataSource: MockGithubDataSource): GithubDataSource

    @Singleton
    @Binds
    abstract fun bindUserReposRepository(userReposRepository: UserReposRepositoryImpl): UserReposRepository
}

// ViewModelProvider.Factory를 Inject 시켜주는 Provider 클래스
class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}

// ViewModelBuilder 모듈
@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory
}
```

### Sealed Class와 Abstract Class로 행동 추상화

- sealed class로 보여지는 View Type을 Header와 Item으로 분기
- 각 케이스에 해당하는 Binding 객체를 ``getItemViewType`` 함수와 ``onCreateViewHolder`` 함수를 통해 만들어줌
- ViewHolder를 abstract class로 추상화시켜 ViewHolder의 책임을 명시

```kotlin
sealed class UIModel {
    object Header : UIModel()
    class Repository(val githubRepository: GithubRepoInfo) : UIModel()
}

abstract class RepositoryViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(uiModel: UIModel)
}

class RepositoryItemViewHolder(private val binding: ItemMainRepoBinding) :
    RepositoryViewHolder(binding) {
    override fun onBind(uiModel: UIModel) {
        binding.repository = (uiModel as UIModel.Repository).githubRepository
    }
}

class RepositoryHeaderViewHolder(private val binding: ItemMainRepoHeaderBinding) :
    RepositoryViewHolder(binding) {
    override fun onBind(uiModel: UIModel) {}
}

class RepositoryListAdapter : RecyclerView.Adapter<RepositoryViewHolder>() {
    private val repositoryList = mutableListOf<UIModel>()

    override fun getItemViewType(position: Int): Int {
        return when (repositoryList[position]) {
            is UIModel.Header -> HEADER
            is UIModel.Repository -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            HEADER -> DataBindingUtil.inflate<ItemMainRepoHeaderBinding>(
                layoutInflater,
                R.layout.item_main_repo_header,
                parent,
                false
            )
            ITEM -> DataBindingUtil.inflate<ItemMainRepoBinding>(
                layoutInflater,
                R.layout.item_main_repo,
                parent,
                false
            )
            else -> throw IllegalArgumentException("Error View Type: $viewType")
        }
        return when(binding) {
            is ItemMainRepoHeaderBinding -> RepositoryHeaderViewHolder(binding)
            is ItemMainRepoBinding -> RepositoryItemViewHolder(binding)
            else -> throw IllegalArgumentException("Error Binding Type: ${binding.javaClass}")
        }
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(repositoryList[position])
    }
}
```

### 로컬 asset json 파일을 가져와서 item 넣어주기

- asset 폴더의 json 파일을 가져와서 Gson으로 deserializing하여 실제 서버통신을 하는 로직을 비슷하게 Mocking함

```kotlin
class MockGithubDataSource @Inject constructor(
    private val context: Context
) : GithubDataSource {
    override suspend fun fetchRepoList(githubId: String): List<ResponseGithubRepository> {
        return withContext(Dispatchers.IO) {
            val repoListJsonFile = runCatching {
                context.assets
                    .open("fake_repo_list.json")
                    .bufferedReader()
                    .use { it.readText() }
            }
            Gson().fromJson(repoListJsonFile.getOrNull(), typeOf<List<ResponseGithubRepository>>())
        }
    }
}
```

### ItemDecoration으로 아이템 간 간격 주기

- RecyclerView.ItemDecoration을 상속받은 VerticalItemDecorator 클래스를 활용하여 개발자가 원하는 만큼의 vertical margin을 줄 수 있도록 설계
- 첫 포지션에서는 top, bottom 모두, 이외의 포지션에서는 bottom만 마진을 줄 수 있게하여 아이템 간 간격을 동일하게 주었음

```kotlin
class VerticaltemDecorator(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when(parent.getChildAdapterPosition(view)) {
            0 -> {
                with(outRect) {
                    top = padding
                    bottom = padding
                }
            }
            else -> outRect.bottom = padding
        }
    }
}
```

## 4주차 과제(Level 1 완료)

<p align="center">
  <img src="https://user-images.githubusercontent.com/54518925/118400966-c6b87f00-b69e-11eb-924f-7873ac7473c0.png"/>
  <br/>
  <img src="https://user-images.githubusercontent.com/54518925/118400992-e64fa780-b69e-11eb-8896-984780be8b3b.png"/>
</p>

### JNI로 API Key 보호하기

- Android에서 C/C++ 파일은 [디컴파일 하기 어렵다는 아티클](https://medium.com/programming-lite/securing-api-keys-in-android-app-using-ndk-native-development-kit-7aaa6c0176be) 을 보고 자극을 받아 API 키를 NDK를 활용하여 보관하는 방법을 시도
- 일전에 작성해 두었던 [Android NDK 사용법](https://velog.io/@l2hyunwoo/NDK-Tutorial)을 기반으로 코드를 작성, Dagger를 활용하여 프로덕트 코드 단에서 API 키를 고려하지 않도록 코드 작성

```c++
#define SOPT_BASE_URL "http://cherishserver.com/"
#define GITHUB_BASE_URL "https://api.github.com/"
Java_com_l2hyunwoo_android_data_util_UrlStoreImpl_getConstant(
            JNIEnv *jEnv,
            jobject thiz,
            jstring key
) {
    const char *c_key = jEnv->GetStringUTFChars(key, 0);

    if (is_same_string(c_key, "SOPT_BASE_URL")) {
        return jEnv->NewStringUTF(SOPT_BASE_URL);
    }
    if (is_same_string(c_key, "GITHUB_BASE_URL")) {
        return jEnv->NewStringUTF(GITHUB_BASE_URL);
    }

    return jEnv->NewStringUTF("");
}
```

```kotlin
interface UrlStore {
    fun getSoptBaseUrl(): String
    fun getGithubBaseUrl(): String
}

class UrlStoreImpl @Inject constructor() : UrlStore {
    init {
        System.loadLibrary("native")
    }

    override fun getSoptBaseUrl(): String {
        return getConstant("SOPT_BASE_URL")
    }

    override fun getGithubBaseUrl(): String {
        return getConstant("GITHUB_BASE_URL")
    }

    private external fun getConstant(key: String): String
}

    @Singleton
    @Provides
    @Named("SOPT_BASE_URL")
    fun provideSoptRetrofit(urlStore: UrlStore): Retrofit {
        return Retrofit.Builder().baseUrl(urlStore.getSoptBaseUrl()).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    @Named("GITHUB_BASE_URL")
    fun provideGithubRetrofit(urlStore: UrlStore): Retrofit {
        return Retrofit.Builder().baseUrl(urlStore.getGithubBaseUrl()).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
```

### 굉장히 빠른 서버통신 Migration

- 일전에 작성했던 코드들은 Data-Domain-Presentation Layer로 나누어 실상 코드가 바뀐 부분은 Data Layer에서 한정되게 코드 Migration을 할 수 있어서 효율적으로 코드를 작성할 수 있었음
- 바뀐 부분은 주석처리하고 추가된 부분만 코드 작성

```kotlin
class SignUpRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val encryptedDataStore: EncryptedDataStore,
    private val soptApi: SoptApi
) : SignUpRepository {
    override suspend fun signUp(userInfo: UserInfo): BaseResponse<String> {
        // with(encryptedDataStore) {
        //     set(KEY_USER_ID, userInfo.id)
        //     set(KEY_USER_PASSWORD, userInfo.password)
        // }
        // dataStore.edit {
        //     it[KEY_USER_ID] = userInfo.id
        //     it[KEY_USER_PASSWORD] = userInfo.password
        // }
        return soptApi.signUp(userInfo.toSignUpDto()).toBaseResponse()
    }
}

@Parcelize
data class UserInfo(
    val id: String,
    val password: String
) : Parcelable {
    // 회원가입 로직에 맞는 요청 형태
    fun toSignUpDto(): RequestSignUp {
        return RequestSignUp(
            email = id,
            password = password
        )
    }
    // 로그인 로직에 맞는 요청 형태
    // 기존에 Data Class로 로직을 짰던 것과는 달리
    // HashMap으로도 key-value를 지정할 수 있다는 것을 보여주고 싶었음
    fun toSignInDto(): HashMap<String, String> =
        hashMapOf(
            "email" to id,
            "password" to password
        )
}

// SignUpViewModel
    fun signUp() {
        viewModelScope.launch {
            // runCatching을 활용한 아름다운 error handling
            // Retrofit 자체에서 coroutine을 사용할 때 Call Wrapper를 벗겨주어서 result receive
            runCatching {
                signUpRepository.signUp(
                    UserInfo(
                        id = inputId.value ?: "",
                        password = inputPassword.value ?: ""
                    )
                )
            }.onSuccess {
                if (it.success) _signUpEvent.call()
            }.onFailure { it.printStackTrace() }
        }
    }
```

- 개인적으로 Retrofit의 Call wrapper는 콜백 지옥을 만들어버리기에 딱 좋은 데이터 구조라 생각하였다.
- Rx도 좋은 대안이라 생각하지만 CompositeDisposable을 활용한 BaseViewModel을 만들어야 한다는 단점(안 그러면 Memory Leak이 일어남), Call 처럼 데이터가 오는 양식을 지정해두고 이를 subscribe해서 사용하기에 코드가 길어진다는 단점이 있어서
- 일반 동기식 코드처럼 비동기 처리(라고 하기에는 좀 그렇지만 여기서는 그렇게 쓰자)를 할 수 있는 Coroutine을 활용했다.
- 그리고 API 키(baseUrl)를 잘 지키는 방법을 최대한 고안을 많이 했고 일전에 눈여겨뒀던 방법인 jni를 활용한 방법을 이번에 적용해봤다
    - 깔끔하면서도 보안 강도를 올리는 방법을 새롭게 올릴 수 있어서 좋은 방법이라고 생각한다.

