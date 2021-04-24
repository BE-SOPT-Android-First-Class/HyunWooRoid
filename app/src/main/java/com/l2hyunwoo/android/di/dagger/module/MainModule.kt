package com.l2hyunwoo.android.di.dagger.module

import com.l2hyunwoo.android.di.dagger.ViewModelBuilder
import com.l2hyunwoo.android.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class,
        HomeModule::class,
        SettingModule::class
    ])
    internal abstract fun homeActivity(): MainActivity
}