package com.example.howtoprotectsuperearth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import android.media.MediaPlayer
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

import com.example.howtoprotectsuperearth.model.Step
import com.example.howtoprotectsuperearth.data.StepDatasource

import com.example.howtoprotectsuperearth.ui.theme.HowToProtectSuperEarthTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowToProtectSuperEarthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SuperEarthAppWithBackgroundMusic()
                }
            }
        }
    }
}

@Composable
fun SuperEarthAppWithBackgroundMusic() {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current

    // Effect to create and release MediaPlayer
    DisposableEffect(key1 = context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.anthem).apply {
            isLooping = true

        }

        onDispose {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    // Effect to observe lifecycle and control MediaPlayer
    DisposableEffect(key1 = lifecycleOwner, key2 = mediaPlayer) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (mediaPlayer?.isPlaying == false) { // Start if not already playing
                        mediaPlayer?.start()
                    }
                }
                Lifecycle.Event.ON_PAUSE -> {
                    if (mediaPlayer?.isPlaying == true) { // Pause if playing
                        mediaPlayer?.pause()
                    }
                }
                // MediaPlayer is released in the outer onDispose
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            // The mediaPlayer is released by the outer DisposableEffect
        }
    }

    // Your main app content
    SuperEarthApp()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperEarthApp() {
    StepList(
        steps = StepDatasource().loadSteps()
    )
}

@Composable
fun StepList(steps: List<Step>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            SuperEarthTopAppBar()
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(steps) { step ->
                StepItem(
                    step = step,
                    modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun StepItem(
    step: Step,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                StepIcon(step.imageResourceId)
                StepInformation(step.title)
                Spacer(Modifier.weight(1f))
                ItemExpansionButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {

                StepDetails(
                    stepDetails = step.description,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
private fun ItemExpansionButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperEarthTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_superearth_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun StepIcon(
    @DrawableRes stepIconRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(stepIconRes),
        contentDescription = null
    )
}

@Composable
fun StepInformation(
    @StringRes stepNameRes: Int,

    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(stepNameRes),
            style = MaterialTheme.typography.titleLarge, // Or another appropriate style
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )

    }
}
@Composable
fun StepDetails(
    @StringRes stepDetails: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(stepDetails),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview
@Composable
fun HowToProtectSuperEarthPreview() {
    HowToProtectSuperEarthTheme(darkTheme = false) {
        SuperEarthApp()
    }
}


@Preview
@Composable
fun HowToProtectSuperEarthDarkThemePreview() {
    HowToProtectSuperEarthTheme(darkTheme = true) {
        SuperEarthApp()
    }
}