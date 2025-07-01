package com.enmanuelbergling.technicaltest.tests

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.enmanuelbergling.technicaltest.ContactsActivity
import com.enmanuelbergling.technicaltest.R
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
    val composeTestRule = createAndroidComposeRule<ContactsActivity>()

    private lateinit var windowSizeClass: WindowSizeClass

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun criticalFlow() {

        composeTestRule.activity.setContent {
            ContactsTheme {
                AppNavHost(appState = rememberAppState(
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity)
                ).also {
                    windowSizeClass = it.windowSizeClass
                })
            }
        }

        composeTestRule.apply {
            //assert that title is displayed
            onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()

            //assert that contacts are displayed
            FAKE_CONTACTS.take(5).forEach { contact ->
                onAllNodesWithTag(
                    activity.getString(R.string.contact_name_test_tag), useUnmergedTree = true
                ).assertAny(
                    hasText(contact.name)
                )

                onAllNodesWithTag(
                    activity.getString(R.string.contact_email_test_tag), useUnmergedTree = true
                ).assertAny(
                    hasText(contact.email)
                )
            }

            //navigate to details
            val firstContact = FAKE_CONTACTS.first()
            onAllNodesWithTag(
                activity.getString(R.string.contact_email_test_tag), useUnmergedTree = true
            ).filterToOne(
                hasText(firstContact.email)
            ).performClick()

            //assert that details are displayed
            val heightLarger =
                ::windowSizeClass.isInitialized && windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

            assertFieldsAreDisplayedInContactDetails(
                contact = firstContact,
                context = activity,
                heightLarger = heightLarger
            )
        }
    }
}