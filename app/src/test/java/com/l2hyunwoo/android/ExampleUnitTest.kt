package com.l2hyunwoo.android

import com.l2hyunwoo.android.di.example.helloworld.DaggerHelloWorldComponent
import com.l2hyunwoo.android.di.example.model.PersonWithMember
import com.l2hyunwoo.android.di.example.model.SampleWrapper
import com.l2hyunwoo.android.di.example.person.DaggerPersonComponent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun `Provision Injection Test`() {
        val exampleComponent = DaggerHelloWorldComponent.create()
        assertEquals("Hello World!" == exampleComponent.getHelloWorld(), true)
    }

    @Test
    fun `Member-Injection Test`() {
        val sampleWrapper = SampleWrapper()
        DaggerHelloWorldComponent.create()
            .inject(sampleWrapper)
        assertEquals("Hello World!" == sampleWrapper.sampleString, true)
    }

    @Test
    fun `MembersInjector Test`() {
        val sampleWrapper = SampleWrapper()
        val injector = DaggerHelloWorldComponent.create()
            .getMemberInjector()
        injector.injectMembers(sampleWrapper)
        assertEquals("Hello World!" == sampleWrapper.sampleString, true)
    }

    @Test
    fun `PersonWithConstructor Test`() {
        val personComponent = DaggerPersonComponent.create()
        val person = personComponent.getPersonWithConstructor()
        assert(person.name == "Charles")
        assert(person.age == 100)
    }

    @Test
    fun `PersonWithMemberInjection Test`() {
        val personWithMember = PersonWithMember()
        val personComponent = DaggerPersonComponent.create()
        personComponent.inject(personWithMember)
        assert(personWithMember.name == "Charles")
        assert(personWithMember.age == 100)
    }

    sealed class Example(val data: String) {
        object SampleA : Example("A")
        object SampleB : Example("B")
        class SampleC: Example("C")
    }
    @Test
    fun `Object-Eqaulity`() {
        assert(Example.SampleA == Example.SampleA)
        // assert(Example.SampleA == Example.SampleB) 오류가 납니다

        assertTrue(checkEqual(Example.SampleA, Example.SampleA))
        assertFalse(checkEqual(Example.SampleA, Example.SampleB))
    }

    private fun checkEqual(old: Example, new: Example) = old == new
}
