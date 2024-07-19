package com.enmanuelbergling.technicaltest.ui.feature.contact.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.enmanuelbergling.technicaltest.R
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.ui.components.SimpleLottieAnimation
import com.enmanuelbergling.technicaltest.ui.theme.ContactsTheme
import com.enmanuelbergling.technicaltest.ui.theme.DimensionTokens
import com.enmanuelbergling.technicaltest.ui.utils.isAppending
import com.enmanuelbergling.technicaltest.ui.utils.isEmpty
import com.enmanuelbergling.technicaltest.ui.utils.isRefreshing
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeRoute(onDetails: (Contact) -> Unit) {

    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val contacts = viewModel.contacts.collectAsLazyPagingItems()

    HomeScreen(contacts = contacts, onDetails)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(contacts: LazyPagingItems<Contact>, onDetails: (Contact) -> Unit) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) {

        if (!contacts.isEmpty) {
            ContactsList(
                contacts = contacts,
                modifier = Modifier
                    .padding(it)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                onDetails = onDetails
            )
        } else if (contacts.isRefreshing) {
            ContactsShimmer(
                modifier = Modifier
                    .padding(it)
            )
        } else {
            LowInternetConnection(
                modifier = Modifier
                    .padding(it),
                onRetry = contacts::refresh
            )
        }
    }
}

@Composable
fun LowInternetConnection(modifier: Modifier, onRetry: () -> Unit) {

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SimpleLottieAnimation(
            rawResLottie = R.raw.not_internet_connection,
            modifier = Modifier.fillMaxWidth(.7f)
        )

        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@Composable
private fun ContactsShimmer(modifier: Modifier = Modifier) {
    Column(modifier) {
        repeat(10) { index ->
            Column {
                ContactItemPlaceholder(Modifier.shimmer())
                if (index < 9) {
                    val dividerStartPadding =
                        DimensionTokens.PictureSize + DimensionTokens.Small.times(3)
                    HorizontalDivider(
                        Modifier
                            .padding(start = dividerStartPadding)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactsList(
    contacts: LazyPagingItems<Contact>,
    modifier: Modifier = Modifier,
    onDetails: (Contact) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(contacts.itemCount, { index -> index }) { index ->
            contacts[index]?.let { contact ->
                Column {
                    ContactItem(
                        name = contact.name,
                        email = contact.email,
                        thumbPicture = contact.thumbPicture
                    ) {
                        onDetails(contact)
                    }

                    if (index < contacts.itemCount - 1) {
                        val dividerStartPadding =
                            DimensionTokens.PictureSize + DimensionTokens.Small.times(3)
                        HorizontalDivider(
                            Modifier
                                .padding(start = dividerStartPadding)
                                .fillMaxWidth(),
                        )
                    }
                }
            }
        }

        if (contacts.isAppending) {
            item {
                LinearProgressIndicator(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = DimensionTokens.SuperSmall)
                )
            }
        }
    }
}


private fun Modifier.contactPicture() = this
    .size(DimensionTokens.PictureSize)
    .clip(CircleShape)

@Composable
fun ContactItem(
    name: String,
    email: String,
    thumbPicture: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val isPreviewEnabled = LocalInspectionMode.current

    Row(
        Modifier
            .clickable { onClick() }
            .padding(start = DimensionTokens.VerySmall)
            .padding(all = DimensionTokens.Small) then modifier) {
        if (isPreviewEnabled) {
            Image(
                painter = painterResource(id = R.drawable.user_placeholder),
                contentDescription = "contact picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.contactPicture()
            )
        } else {
            AsyncImage(
                model = thumbPicture,
                contentDescription = "contact picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.contactPicture(),
                placeholder = painterResource(id = R.drawable.user_placeholder),
                error = painterResource(id = R.drawable.user_placeholder),
            )
        }

        Spacer(modifier = Modifier.width(DimensionTokens.Small))

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(DimensionTokens.VerySmall)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag(stringResource(id = R.string.contact_name_test_tag))
            )

            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag(stringResource(id = R.string.contact_email_test_tag))
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ContactItemPrev() {
    ContactsTheme {
        ContactItem(
            name = "Mr. Bean",
            email = "mrbean@gmail.com",
            thumbPicture = "",
            modifier = Modifier.fillMaxWidth()
        ) {}
    }
}

@Composable
fun ContactItemPlaceholder(modifier: Modifier = Modifier) {
    Row(
        Modifier
            .padding(start = DimensionTokens.VerySmall)
            .padding(all = DimensionTokens.Small) then modifier
    ) {
        Box(
            modifier = Modifier
                .contactPicture()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Spacer(modifier = Modifier.width(DimensionTokens.Small))

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(DimensionTokens.Small)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(DimensionTokens.TitleTextHeight)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.extraSmall
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(DimensionTokens.BodyTextHeight)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.extraSmall
                    )
            )
        }
    }
}

@Preview
@Composable
private fun ContactPlaceholderPrev() {
    ContactsTheme {
        ContactItemPlaceholder(Modifier.fillMaxWidth())
    }
}