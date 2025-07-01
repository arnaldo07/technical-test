package com.enmanuelbergling.technicaltest.ui.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SimpleLottieAnimation(
    @RawRes rawResLottie: Int,
    modifier: Modifier = Modifier,
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(rawResLottie)
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
    )
}