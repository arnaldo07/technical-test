package com.enmanuelbergling.technicaltest

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import com.enmanuelbergling.technicaltest.mock.FAKE_CONTACTS
import com.enmanuelbergling.technicaltest.ui.navigation.AppNavHost
import com.enmanuelbergling.technicaltest.ui.rememberAppState
import com.enmanuelbergling.technicaltest.ui.theme.ContactsTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EndToEndTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun firstThreeContactsAreCorrectlyDisplay() {

        composeTestRule.activity.setContent {
            ContactsTheme {
                AppNavHost(
                    appState = rememberAppState(windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity))
                )
            }
        }

        composeTestRule.apply {
            onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
            FAKE_CONTACTS.take(3).forEach { contact ->
                onAllNodesWithTag(
                    activity.getString(R.string.contact_name_test_tag),
                    useUnmergedTree = true
                ).assertAny(
                    hasText(contact.name)
                )
                
                onAllNodesWithTag(
                    activity.getString(R.string.contact_email_test_tag),
                    useUnmergedTree = true
                ).assertAny(
                    hasText(contact.email)
                )
            }
        }
    }
}