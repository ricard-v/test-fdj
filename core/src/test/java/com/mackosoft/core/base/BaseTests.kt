package com.mackosoft.core.base

import androidx.annotation.CallSuper
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.After
import org.junit.AfterClass

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTests {
    companion object {
        /**
         * Is called whenever a test class is done with its tests.
         *
         * This ensures that all mocked objects, services, classes, etc,
         * are cleared so that it does not pollute any further test classes.
         */
        @AfterClass
        @JvmStatic
        fun cleanUpAfterTests() {
            unmockkAll()
        }
    }

    /**
     * To be used for any test on suspendable functions that require the use of Kotlin's
     * coroutines and [CoroutineDispatcher]s.
     *
     * Example:
     * ```
     *     testScope.runTest {
     *         ... // your test code
     *     }
     * ```
     *
     * @see: <a href="https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test">Kotlinx Coroutines Test</a>
     */
    protected val testScope = TestScope()

    /**
     * Is called whenever a test class is done with each of its tests.
     *
     * This ensures that all mocked objects, services, classes, etc, have all their mocked responses
     * to methods, properties, etc, cleared so that it does not pollute any further tests
     * within the same test class.
     */
    @After
    @CallSuper
    open fun tearDownTest() {
        clearAllMocks()
    }
}