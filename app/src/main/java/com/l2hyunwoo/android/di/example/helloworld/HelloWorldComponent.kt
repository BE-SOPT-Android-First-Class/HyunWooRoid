package com.l2hyunwoo.android.di.example.helloworld

import com.l2hyunwoo.android.di.example.model.SampleWrapper
import dagger.Component
import dagger.MembersInjector

@Component(modules = [HelloWorldModule::class])
interface HelloWorldComponent {
    fun getHelloWorld(): String
    fun inject(sampleWrapper: SampleWrapper)
    fun getMemberInjector(): MembersInjector<SampleWrapper>
}
