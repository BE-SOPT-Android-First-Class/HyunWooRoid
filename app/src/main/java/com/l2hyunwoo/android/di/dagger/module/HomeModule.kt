package com.l2hyunwoo.android.di.dagger.module

import androidx.lifecycle.ViewModel
import com.l2hyunwoo.android.di.dagger.ViewModelBuilder
import com.l2hyunwoo.android.di.dagger.ViewModelKey
import com.l2hyunwoo.android.presentation.main.HomeViewModel
import com.l2hyunwoo.android.presentation.main.subscreen.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}