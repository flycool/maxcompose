package com.example.maxcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maxcompose.model.ListItem
import com.example.maxcompose.model.bottomItems
import com.example.maxcompose.model.composeItemList
import com.example.maxcompose.navigation.bottom.NavigationBottomBar
import com.example.maxcompose.navigation.bottom.NavigationForBottomSheet
import com.example.maxcompose.page.PageViewModel
import com.example.maxcompose.ui.theme.MaxcomposeTheme
import com.example.maxcompose.util.WindowInfo
import com.example.maxcompose.util.isPermanentlyDenied
import com.example.maxcompose.util.rememberWindowInfo
import com.example.maxcompose.util.toDp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaxcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(navigator: DestinationsNavigator) {
    LazyColumn() {
        items(composeItemList) { item ->
            Text(
                text = item.name,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navigator.navigate(item.destination)
                    }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun LazyVerticalGrid32() {
    Box(modifier = Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val state = rememberLazyGridState(
            initialFirstVisibleItemIndex = 99
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp), state = state
        ) {
            items(100) { i ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Cyan), contentAlignment = Alignment.Center
                ) {
                    Text(text = "item $i")
                }
            }
        }

        Button(
            onClick = {
                scope.launch {
                    state.animateScrollToItem(0, 0)
                }
            }, modifier = Modifier.padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun BottomSheet30() {
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed, animationSpec = tween(
            durationMillis = 300
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "bottom sheet")
            }
        }, sheetBackgroundColor = Color.Cyan
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {
                Text(text = "bottom sheet ${sheetState.progress.fraction}")
            }
        }

    }
}

@Destination
@Composable
fun PageList29() {
    val viewModel = viewModel<PageViewModel>()
    val state = viewModel.state
    var reIndex by remember {
        mutableStateOf(0)
    }
    if (reIndex >= state.items.size - 1 && !state.endReached && !state.isLoading) {
        viewModel.loadNextItems()
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(state.items) { index, item ->
            reIndex = index

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.description)
            }
        }
        if (state.isLoading) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    Button(onClick = {
        viewModel.reset()
    }) {
        Text(text = "reset")
    }
}


@Destination
@Composable
fun MotionLayout28() {
    Column {
        var progress by remember {
            mutableStateOf(0f)
        }
        ProfileHeader(progress = progress)
        Spacer(modifier = Modifier.height(16.dp))
        Slider(
            value = progress,
            onValueChange = {
                progress = it
            },
            modifier = Modifier.padding(
                horizontal = 32.dp
            )
        )
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
private fun ProfileHeader(progress: Float) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val properties = motionProperties(id = "profile_pic")
        val changeColor = properties.value.color("background")
        Box(
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxWidth()
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = changeColor,
                    shape = CircleShape
                )
                .layoutId("profile_pic")
        )
        Text(
            text = "max name",
            fontSize = 24.sp,
            modifier = Modifier
                .layoutId("username"),
            color = changeColor,
        )
    }
}

@Destination
@Composable
fun LayoutForAllScreenSize27() {
    val windowInfo = rememberWindowInfo()
    if (windowInfo.screenWidthType == WindowInfo.WindowInfoType.Compact) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(10) {
                Text(
                    text = "item $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .padding(16.dp),
                )
            }
            items(10) {
                Text(
                    text = "item $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Green)
                        .padding(16.dp),
                )
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                items(10) {
                    Text(
                        text = "item $it", modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(16.dp)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                items(10) {
                    Text(
                        text = "item $it", modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green)
                            .padding(16.dp)
                    )
                }
            }
        }

    }

}

@Destination
@Composable
fun MigrateToCompose24() {
    AndroidView(factory = {
        TextView(it)
    }) {
        it.apply {
            text = "This is XML in composable"
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun ComposePermission23() {
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionsState.permissions.forEach { perm ->
            when (perm.permission) {
                android.Manifest.permission.CAMERA -> {
                    when {
                        perm.hasPermission -> Text(text = "Camera permission granted")
                        perm.shouldShowRationale -> Text(text = "Camera permission show Rationale")
                        perm.isPermanentlyDenied() -> Text(
                            text = "Camera permission was permanently denied." +
                                    "you can enable it in th app settings"
                        )
                    }
                }
                android.Manifest.permission.RECORD_AUDIO -> {
                    when {
                        perm.hasPermission -> Text(text = "Record audio permission granted")
                        perm.shouldShowRationale -> Text(text = "Record audio permission show Rationale")
                        perm.isPermanentlyDenied() -> Text(
                            text = "Record audio was permanently denied." +
                                    "you can enable it in th app settings"
                        )
                    }
                }
            }
        }
    }

}


@Composable
@Destination
fun MultiSelected22() {
    var items by remember {
        mutableStateOf((1..20).map {
            ListItem("item $it", false)
        })
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items.size) { i ->
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        items = items.mapIndexed { index, item ->
                            if (i == index) {
                                item.copy(isSelected = !item.isSelected)
                            } else item
                        }
                    }
                    .padding(16.dp)) {
                Text(text = items[i].title)
                if (items[i].isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

}

@Composable
@Destination
fun MultiLayerParallaxScroll21() {
    val moonScrollSpeed = 0.08f
    val midBgScrollSpeed = 0.03f

    val imageHeight = (LocalConfiguration.current.screenWidthDp * (2f / 3)).dp
    val lazyListState = rememberLazyListState()

    var moonOffset by remember {
        mutableStateOf(0f)
    }
    var midBgOffset by remember {
        mutableStateOf(0f)
    }

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            val layoutInfo = lazyListState.layoutInfo
            if (lazyListState.firstVisibleItemIndex == 0 || layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1) {
                return Offset.Zero
            }

            moonOffset += delta * moonScrollSpeed
            midBgOffset += delta * midBgScrollSpeed
            return Offset.Zero
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        state = lazyListState,
    ) {
        items(10) {
            Text(
                text = "item sample", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .clipToBounds()
                    .fillMaxWidth()
                    .height(imageHeight + midBgOffset.toDp())
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xfff36b21), Color(0xfff9a521)
                            )
                        )
                    ),
            ) {
                Image(painter = painterResource(id = R.drawable.ic_moonbg),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            translationY = moonOffset
                        })
                Image(painter = painterResource(id = R.drawable.ic_midbg),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            translationY = midBgOffset
                        })
                Image(
                    painter = painterResource(id = R.drawable.ic_outerbg),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier.matchParentSize()
                )

            }
        }

        items(20) { item ->
            Text(
                text = "item sample", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun BottomSheetWithBadge20() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        NavigationBottomBar(
            items = bottomItems,
            navController = navController,
            onItemClick = {
                navController.navigate(it.route)
            },
        )
    }) {
        NavigationForBottomSheet(navController)
    }
}

@Destination
@Composable
fun Navigation19() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("main_screen") {
            Text(text = "main screen")
        }
    }
}

@Composable
fun SplashScreen(
    navController: NavController
) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(true) {
        scale.animateTo(targetValue = 0.3f, animationSpec = tween(durationMillis = 500, easing = {
            OvershootInterpolator(2f).getInterpolation(it)
        }))
        delay(3000)
        navController.navigate("main_screen")
    }
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.scale(scale.value)
        )
    }
}

@Destination
@Composable
fun DropDown16() {
    DropDown(text = "drop down") {
        Text(text = "drop down")
    }
}

@Composable
private fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    open: Boolean = true,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(open)
    }
    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f, animationSpec = tween(
            durationMillis = 300
        )
    )
    val ratateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f, animationSpec = tween(
            durationMillis = 300
        )
    )
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        isOpen = !isOpen
                    }
                    .scale(
                        scaleX = 1f, scaleY = if (isOpen) -1f else 1f
                    ),

                )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                transformOrigin = TransformOrigin(0.5f, 0f)
                rotationX = ratateX.value
            }
            .alpha(alpha.value)) {
            content()
        }


    }
}

@Destination
@Composable
fun MusicKnob13() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(1.dp, Color.Cyan, RoundedCornerShape(10.dp))
                .padding(30.dp)
        ) {
            var volume by remember { mutableStateOf(0f) }
            val barCount = 20
            MusicKnob(
                modifier = Modifier.size(100.dp),
                onValueChange = {
                    volume = it
                },
            )
            Spacer(modifier = Modifier.width(20.dp))
            VolumeBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                activityBar = (barCount * volume).roundToInt(),
                barCount = barCount
            )
        }
    }
}

@Composable
private fun VolumeBar(
    modifier: Modifier = Modifier,
    activityBar: Int = 0,
    barCount: Int = 10,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }
        Canvas(modifier = modifier) {
            for (i in 0 until barCount) {
                drawRoundRect(
                    color = if (i in 0 until activityBar) Color.Green else Color.DarkGray,
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(barWidth, constraints.maxHeight.toFloat()),
                    cornerRadius = CornerRadius(0f)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit,
) {
    var rotation by remember { mutableStateOf(limitingAngle) }
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }
    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }

    Image(
        painter = painterResource(id = R.drawable.profile_pic),
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2
                centerY = windowBounds.size.height / 2
            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()
                when (event.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitingAngle..limitingAngle) {
                            val fixedAngle = if (angle in -180f..-limitingAngle) {
                                360f + angle
                            } else {
                                angle
                            }
                            rotation = fixedAngle

                            val percent = (fixedAngle - limitingAngle) / (360 - 2 * limitingAngle)
                            onValueChange(percent)
                            true
                        } else false
                    }
                    else -> false
                }
            }
            .rotate(rotation)
    )
}


@Destination
@Composable
fun CircularProgressBar12() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressBar(percentage = 0.7f, number = 100)
    }
}


@Composable
private fun CircularProgressBar(
    percentage: Float,
    number: Int,
    radius: Dp = 50.dp,
    strokeWidth: Dp = 10.dp,
    color: Color = Color.Green,
    fontSize: TextUnit = 29.sp,
    duration: Int = 1000,
    delay: Int = 0,
) {
    var animationState by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationState) percentage else 0f, animationSpec = tween(
            durationMillis = duration, delayMillis = delay
        )
    )

    LaunchedEffect(true) {
        animationState = true
    }

    Box(
        modifier = Modifier.size(radius * 2), contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            modifier = Modifier.clickable {
                animationState = !animationState
            })
    }
}

@Destination
@Composable
fun SimpleAnimation11() {
    var sizeState by remember { mutableStateOf(200.dp) }
    val size by animateDpAsState(
        targetValue = sizeState,
        animationSpec = tween(
            durationMillis = 2000, easing = LinearOutSlowInEasing
        ),
    )

    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red, targetValue = Color.Green, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000), repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .size(size)
            .background(color), contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            sizeState += 50.dp
        }) {
            Text(text = "Increase Size")
        }
    }
}

@Destination
@Composable
fun ConstraintLayout9() {
    val constraintSet = ConstraintSet {
        val greenbox = createRefFor("greenbox")
        val redbox = createRefFor("redbox")
        val guildLine = createGuidelineFromTop(0.5f)

        constrain(greenbox) {
            top.linkTo(guildLine)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redbox) {
            top.linkTo(parent.top)
            start.linkTo(greenbox.end)
            end.linkTo(parent.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        createHorizontalChain(greenbox, redbox, chainStyle = ChainStyle.Packed)
    }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(), constraintSet = constraintSet
    ) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenbox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redbox")
        )
    }
}

@Destination
@Composable
fun List8() {
    LazyColumn {
        items(
            listOf("this", "is", "jetpack", "Compose")
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp),
                text = it,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SimpleUI7() {
    val scaffoldState = rememberScaffoldState()
    var textFieldState by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = textFieldState,
                label = {
                    Text(text = "Enter your name")
                },
                onValueChange = {
                    textFieldState = it
                },

                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(textFieldState)
                    }
                },
            ) {
                Text(text = "Pls greet me")
            }
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    SimpleUI7()
}



















