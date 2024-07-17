package com.enmanuelbergling.technicaltest.ui.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Send email with Intent
 *
 * @param emails List of emails for send a message
 * @param subject Subject of the email to send
 * @param message Message to send
 */
fun Context.sendEmail(
    emails: Array<String>,
    subject: String? = null,
    message: String? = null,
): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
        .setData(Uri.parse("mailto:"))
        .setType("message/rfc822")
        .putExtra(Intent.EXTRA_EMAIL, emails)
        .putExtra(Intent.EXTRA_SUBJECT, subject)
        .putExtra(Intent.EXTRA_TEXT, message)
    return if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        true
    } else false
}