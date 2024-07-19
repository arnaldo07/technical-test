package com.enmanuelbergling.technicaltest.tests

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.enmanuelbergling.technicaltest.MainActivity
import com.enmanuelbergling.technicaltest.R
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.mock.FAKE_CONTACTS
import com.enmanuelbergling.technicaltest.ui.feature.contact.detail.ContactScreen
import com.enmanuelbergling.technicaltest.ui.feature.contact.detail.fullName
import com.enmanuelbergling.technicaltest.ui.feature.contact.detail.stringText
import com.enmanuelbergling.technicaltest.ui.feature.contact.detail.topBarText
import com.enmanuelbergling.technicaltest.ui.theme.ContactsTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ContactDetailsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var windowSizeClass: WindowSizeClass

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun assertContactFieldsAreDisplayed() {
        val contact = FAKE_CONTACTS.first()

        composeTestRule.activity.setContent {
            windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity)

            ContactsTheme {
                ContactScreen(contactState = contact, onLocate = {}) {}
            }
        }

        composeTestRule.apply {
            val heightLarger =
                ::windowSizeClass.isInitialized && windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact


            assertFieldsAreDisplayedInContactDetails(
                contact = contact,
                context = composeTestRule.activity,
                heightLarger = heightLarger
            )
        }
    }
}

fun SemanticsNodeInteractionsProvider.assertFieldsAreDisplayedInContactDetails(
    contact: Contact,
    context: Context,
    heightLarger: Boolean,
) {
    //assert that topBar and fullName are displayed
    onNodeWithTag(
        context.getString(R.string.contact_top_bar_test_tag), useUnmergedTree = true
    ).assert(
        hasText(contact.topBarText)
    )
    onNodeWithTag(
        context.getString(R.string.contact_full_name_test_tag), useUnmergedTree = true
    ).assert(
        hasText(contact.fullName)
    )

    //just when the height is large enough
    if (heightLarger) {
        onNodeWithText(
            contact.phone, useUnmergedTree = true
        ).assertExists()

        onNodeWithText(
            contact.dayOfBirth,
            useUnmergedTree = true
        ).assertExists()

        onNodeWithText(
            contact.gender, useUnmergedTree = true
        ).assertExists()

        onNodeWithText(
            contact.location.stringText, useUnmergedTree = true
        ).assertExists()

    }
}
