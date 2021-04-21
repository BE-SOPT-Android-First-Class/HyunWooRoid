package com.l2hyunwoo.android.di

import dagger.Component

@Component(modules = [ExampleModule::class])
interface ExampleComponent {
    fun getHelloWorld(): String
}
