package com.l2hyunwoo.android

import com.l2hyunwoo.android.di.example.DaggerExampleComponent
import com.l2hyunwoo.android.di.example.SampleWrapper
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
    fun `Provision Injection Test`() {
        val exampleComponent = DaggerExampleComponent.create()
        assertEquals("Hello World!" == exampleComponent.getHelloWorld(), true)
    }

    @Test
    fun `Member-Injection Test`() {
        val sampleWrapper = SampleWrapper()
        DaggerExampleComponent.create()
            .inject(sampleWrapper)
        assertEquals("Hello World!" == sampleWrapper.sampleString, true)
    }
}