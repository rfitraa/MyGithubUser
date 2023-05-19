package com.dicoding.mygithubuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.mygithubuser.UI.Activity.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun clickSetting(){
        Espresso.openActionBarOverflowOrOptionsMenu(
            InstrumentationRegistry.getInstrumentation().getTargetContext()
        );

        Espresso.onView(ViewMatchers.withText("Setting")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.switch_theme))
            .perform(ViewActions.click())
    }
}