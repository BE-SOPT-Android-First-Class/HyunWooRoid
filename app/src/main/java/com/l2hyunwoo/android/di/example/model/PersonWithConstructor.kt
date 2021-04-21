package com.l2hyunwoo.android.di.example.model

import javax.inject.Inject
import javax.inject.Named

class PersonWithConstructor @Inject constructor(
    val name: String,
    @Named("age")
    val age: Int
)