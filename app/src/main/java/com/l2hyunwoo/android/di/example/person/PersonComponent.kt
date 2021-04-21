package com.l2hyunwoo.android.di.example.person

import com.l2hyunwoo.android.di.example.model.PersonWithConstructor
import com.l2hyunwoo.android.di.example.model.PersonWithMember
import dagger.Component

@Component(modules = [PersonModule::class])
interface PersonComponent {
    fun getPersonWithConstructor(): PersonWithConstructor
    fun inject(personWithMember: PersonWithMember)
}