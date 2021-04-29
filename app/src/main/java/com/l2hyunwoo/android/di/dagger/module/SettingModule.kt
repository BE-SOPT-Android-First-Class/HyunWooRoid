package com.l2hyunwoo.android.di.dagger.module

import com.l2hyunwoo.android.di.dagger.ViewModelBuilder
import com.l2hyunwoo.android.presentation.main.subscreen.PersonalInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun personalInfoFragment(): PersonalInfoFragment
}