package com.l2hyunwoo.android.di.example

import dagger.Component

@Component(modules = [ExampleModule::class])
interface ExampleComponent {
    fun getHelloWorld(): String
    fun inject(sampleWrapper: SampleWrapper)
}
