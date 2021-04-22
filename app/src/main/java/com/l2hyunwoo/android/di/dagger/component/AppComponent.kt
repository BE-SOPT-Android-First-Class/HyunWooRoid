package com.l2hyunwoo.android.di.dagger.component

import com.l2hyunwoo.android.presentation.GithubApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<GithubApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<GithubApplication>
}