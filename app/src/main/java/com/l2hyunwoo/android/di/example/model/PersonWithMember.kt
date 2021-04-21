package com.l2hyunwoo.android.di.example.model

import javax.inject.Inject
import javax.inject.Named

class PersonWithMember {
    @Inject
    lateinit var name: String
    @JvmField
    @field:[Inject Named("age")]
    var age: Int = 0
}