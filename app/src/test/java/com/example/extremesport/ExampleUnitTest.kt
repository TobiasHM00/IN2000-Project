package com.example.extremesport

import com.example.extremesport.data.DataSource
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun sunrise_isCorrect() {
        val dataSource = DataSource()
        val sunrisetest = runBlocking { dataSource.getSunrise() }
        assertEquals("MET Norway", sunrisetest.copyright)
    }
}