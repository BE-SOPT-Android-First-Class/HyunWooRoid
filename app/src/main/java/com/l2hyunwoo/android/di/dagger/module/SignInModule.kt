package com.l2hyunwoo.android.di.dagger.module

import androidx.lifecycle.ViewModel
import com.l2hyunwoo.android.di.dagger.ViewModelBuilder
import com.l2hyunwoo.android.di.dagger.ViewModelKey
import com.l2hyunwoo.android.presentation.signin.SignInActivity
import com.l2hyunwoo.android.presentation.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SignInModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun signInActivity(): SignInActivity

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindViewModel(viewModel: SignInViewModel): ViewModel
}