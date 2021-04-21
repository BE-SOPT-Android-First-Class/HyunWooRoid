package com.l2hyunwoo.android

import com.l2hyunwoo.android.di.DaggerExampleComponent
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `의존성 주입 Test`() {
        val exampleComponent = DaggerExampleComponent.create()
        assertEquals("Hello World!" == exampleComponent.getHelloWorld(), true)
    }
}