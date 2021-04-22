package com.l2hyunwoo.android.presentation

import com.l2hyunwoo.android.di.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GithubApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this.applicationContext)
            .build()
    }

}
