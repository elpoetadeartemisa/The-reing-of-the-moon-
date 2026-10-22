package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppRulesData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("The Reign of the Moon", appName)
  }

  @Test
  fun `test public chat sexual content moderation`() {
    val (validPass, _) = AppRulesData.validatePublicChatContent("Un hermoso poema para la luna llena")
    assertTrue("Artistic message should be valid", validPass)

    val (validFail, error) = AppRulesData.validatePublicChatContent("Enviando contenido sexual y pornografico")
    assertFalse("Sexual content in public lounge must be blocked", validFail)
    assertTrue("Error should reference Rule #1", error?.contains("Regla #1") == true)
  }
}
