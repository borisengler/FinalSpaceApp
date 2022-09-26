package com.example.finalfinalspace.data.prefs

import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.times
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SettingStorageTest2 {
    lateinit var settingsStorage: SettingsStorage
    @Mock private lateinit var mockedSharedPref: SharedPreferences
    @Mock private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        mockedSharedPref = mock()
        editor = mock()
        whenever(mockedSharedPref.edit()).thenReturn(editor)
        whenever(editor.putBoolean(eq(SettingsStorage.AUTO_SYNC), anyBoolean())).thenReturn(editor)

        settingsStorage = SettingsStorage(mockedSharedPref)
    }

    @Test
    fun testGetAutoSyncWhenTrue() {
        whenever(mockedSharedPref.getBoolean(eq(SettingsStorage.AUTO_SYNC), eq(true))).thenReturn(true)

        Assert.assertEquals(true, settingsStorage.getAutoSync())
        verify(mockedSharedPref, times(1)).getBoolean(eq(SettingsStorage.AUTO_SYNC), eq(true))
    }

    @Test
    fun testGetAutoSyncWhenFalse() {
        whenever(mockedSharedPref.getBoolean(eq(SettingsStorage.AUTO_SYNC), eq(true))).thenReturn(false)

        Assert.assertEquals(false, settingsStorage.getAutoSync())
        verify(mockedSharedPref, times(1)).getBoolean(eq(SettingsStorage.AUTO_SYNC), eq(true))
    }

    @Test
    fun testSetAutoSyncWhenTrue() {
        whenever(mockedSharedPref.getBoolean(eq(SettingsStorage.AUTO_SYNC), eq(true))).thenReturn(true)

        settingsStorage.setAutoSync(true)

        Assert.assertEquals(true, settingsStorage.getAutoSync())
        verify(mockedSharedPref, times(1)).edit()
        verify(editor, times(1)).putBoolean(eq(SettingsStorage.AUTO_SYNC), eq(true))
        verify(editor, times(1)).apply()
    }

    @Test
    fun testSetAutoSyncWhenFalse() {
        whenever(mockedSharedPref.getBoolean(SettingsStorage.AUTO_SYNC, true)).thenReturn(false)

        settingsStorage.setAutoSync(false)

        Assert.assertEquals(settingsStorage.getAutoSync(), false)
        verify(mockedSharedPref, times(1)).edit()
        verify(editor, times(1)).putBoolean(eq(SettingsStorage.AUTO_SYNC), eq(false))
        verify(editor, times(1)).apply()
    }
}
