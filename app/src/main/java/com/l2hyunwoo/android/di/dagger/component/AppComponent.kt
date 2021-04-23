package com.l2hyunwoo.android.di.dagger.component

import android.content.Context
import com.l2hyunwoo.android.di.dagger.module.ApplicationModule
import com.l2hyunwoo.android.di.dagger.module.HomeModule
import com.l2hyunwoo.android.di.dagger.module.SignInModule
import com.l2hyunwoo.android.di.dagger.module.SignUpModule
import com.l2hyunwoo.android.presentation.GithubApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        SignInModule::class,
        SignUpModule::class,
        HomeModule::class
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