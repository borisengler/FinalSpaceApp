package com.example.finalfinalspace.data.prefs

import android.content.SharedPreferences
import com.example.finalfinalspace.data.prefs.SettingsStorage.Companion.AUTO_SYNC
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingStorageTest {
    lateinit var settingsStorage: SettingsStorage
    @MockK private lateinit var mockedSharedPref: SharedPreferences
    @MockK private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { mockedSharedPref.edit() } returns editor
        every { editor.apply() } returns Unit
        every { editor.putBoolean(AUTO_SYNC, any()) } returns editor

        settingsStorage = SettingsStorage(mockedSharedPref)
    }

    @Test
    fun testGetAutoSyncWhenTrue() {
        every { mockedSharedPref.getBoolean(AUTO_SYNC, true) } returns true

        assertEquals(true, settingsStorage.getAutoSync())
        verify(exactly = 1) { mockedSharedPref.getBoolean(AUTO_SYNC, true) }
    }

    @Test
    fun testGetAutoSyncWhenFalse() {
        every { mockedSharedPref.getBoolean(AUTO_SYNC, true) } returns false

        assertEquals(false, settingsStorage.getAutoSync())
        verify(exactly = 1) { mockedSharedPref.getBoolean(AUTO_SYNC, true) }
    }

    @Test
    fun testSetAutoSyncWhenTrue() {
        every { mockedSharedPref.getBoolean(AUTO_SYNC, true) } returns true

        settingsStorage.setAutoSync(true)

        assertEquals(true, settingsStorage.getAutoSync())
        verify(exactly = 1) { mockedSharedPref.edit() }
        verify(exactly = 1) { editor.putBoolean(AUTO_SYNC, true) }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun testSetAutoSyncWhenFalse() {
        every { mockedSharedPref.getBoolean(AUTO_SYNC, true) } returns false

        settingsStorage.setAutoSync(false)

        assertEquals(false, settingsStorage.getAutoSync())
        verify(exactly = 1) { mockedSharedPref.edit() }
        verify(exactly = 1) { editor.putBoolean(AUTO_SYNC, false) }
        verify(exactly = 1) { editor.apply() }
    }
}
