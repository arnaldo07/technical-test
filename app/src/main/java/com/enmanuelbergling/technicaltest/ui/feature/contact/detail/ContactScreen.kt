package com.enmanuelbergling.technicaltest.ui.feature.contact.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.enmanuelbergling.technicaltest.R
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.ui.components.LargerIconButton
import com.enmanuelbergling.technicaltest.ui.theme.DimensionTokens
import com.enmanuelbergling.technicaltest.ui.theme.ContactsTheme
import com.enmanuelbergling.technicaltest.ui.utils.sendEmail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactRoute(onBack: () -> Unit, onLocate: (Coordinates) -> Unit) {

    val viewModel = hiltViewModel<ContactScreenViewModel>()

    val contactState by viewModel.contactState

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${contactState.name}, ${contactState.age}")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "arrow back"
                        )
                    }
                }
            )
        }
    ) {
        ContactScreen(
            contactState = contactState,
            modifier = Modifier
                .padding(it)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            onLocate = onLocate
        )
    }
}

@Composable
private fun ContactScreen(
    contactState: Contact,
    modifier: Modifier = Modifier,
    onLocate: (Coordinates) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DimensionTokens.MediumSmall)
    ) {
        item {
            ContactHeader(picture = contactState.thumbPicture)
        }
        item {
            Text(
                text = "${contactState.name} ${contactState.lastname}",
                fontWeight = FontWeight.Light
            )
        }
        item {
            val context = LocalContext.current

            ContactActions(
                onMail = {
                    context.sendEmail(arrayOf(contactState.email))
                },
                onLocate = { onLocate(contactState.location.coordinates) },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Column {
                Text(
                    text = stringResource(id = R.string.about, contactState.name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(all = DimensionTokens.MediumSmall)
                )

                HorizontalDivider(Modifier.fillMaxWidth())
            }
        }

        item {
            AboutItemText(
                imageVector = Icons.Rounded.Call,
                information = contactState.phone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DimensionTokens.MediumSmall)
            )
        }

        item {
            AboutItemText(
                imageVector = Icons.Rounded.DateRange,
                information = contactState.dayOfBirth.take(10),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DimensionTokens.MediumSmall)
            )
        }

        item {
            AboutItemCard(
                label = stringResource(id = R.string.gender),
                information = contactState.gender,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DimensionTokens.MediumSmall)
            )
        }

        item {
            AboutItemCard(
                label = stringResource(id = R.string.location),
                information = "${contactState.location.city}, ${contactState.location.state}, ${contactState.location.country}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DimensionTokens.MediumSmall),
            )
        }
    }
}

@Composable
private fun Modifier.biggerPicture() = this
    .size(DimensionTokens.BiggerPictureSize)
    .background(MaterialTheme.colorScheme.background, CircleShape)
    .padding(DimensionTokens.VerySmall)
    .clip(CircleShape)

@Composable
fun ContactHeader(picture: String, modifier: Modifier = Modifier) {

    val isPreviewEnabled = LocalInspectionMode.current

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.world_map),
            contentDescription = "world map",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = DimensionTokens.BiggerPictureSize / 2)
                .height(DimensionTokens.BiggerPictureSize + DimensionTokens.Small)
        )

        if (isPreviewEnabled) {
            Image(
                painter = painterResource(id = R.drawable.mr_bean),
                contentDescription = "contact picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .biggerPicture()
                    .align(Alignment.BottomCenter)
            )
        } else {
            AsyncImage(
                model = picture,
                contentDescription = "contact picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .biggerPicture()
                    .align(Alignment.BottomCenter),
                placeholder = painterResource(id = R.drawable.mr_bean),
            )
        }
    }
}

@Preview
@Composable
private fun ContactHeaderPrev() {
    ContactsTheme {
        ContactHeader(
            ""
        )
    }
}

@Composable
fun ContactActions(
    onMail: () -> Unit,
    onLocate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        LargerIconButton(imageVector = Icons.Rounded.Email, onClick = onMail)

        LargerIconButton(imageVector = Icons.Rounded.LocationOn, onClick = onLocate)
    }
}

@Composable
fun AboutItemCard(
    label: String,
    information: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(DimensionTokens.MediumSmall),
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(Modifier.padding(contentPadding)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = information,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun AboutItemCardPrev() {
    ContactsTheme {
        AboutItemCard(
            "Gender",
            "Female",
            Modifier.padding(DimensionTokens.Small),
            contentPadding = PaddingValues(DimensionTokens.Small)
        )
    }
}

@Composable
fun AboutItemText(
    imageVector: ImageVector,
    information: String,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageVector.toString(),
            modifier = Modifier.size(DimensionTokens.TextIconSize),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(DimensionTokens.Small))
        Text(
            text = information,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun AboutItemTextPrev() {
    ContactsTheme {
        AboutItemText(Icons.Rounded.LocationOn, "Santa Clara")
    }
}

