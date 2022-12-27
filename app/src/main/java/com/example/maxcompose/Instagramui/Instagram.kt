package com.example.maxcompose.Instagramui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxcompose.R
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ProfileScreen17() {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(name = "max 24", modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection()
        Spacer(modifier = Modifier.height(25.dp))
        ButtonSection(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        HighlightSection(
            highlights = highlights,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        PostTabView(imageWithTexts = postsViews, onTabSelected = {selectedTabIndex = it})
        when (selectedTabIndex) {
            0-> PostSection(postIds = posts, modifier = Modifier.fillMaxWidth())
        }

    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    name: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_bell),
            contentDescription = "back",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_dotmenu),
            contentDescription = "back",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            RoundImage(
                painter = painterResource(id = R.drawable.profile_pic),
                modifier = modifier
                    .size(100.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f))
        }
        ProfileDescription(
            name = "Programing Mentor",
            description = "10 years of coding experience\n " +
                    "Want me to make your app? Send me an email:\n" +
                    "Subscribe to my YouTube channel!",
            url = "https://youtube.com/c/Philipplackner",
            followedBy = listOf("codinginflow", "miakhalifa"),
            otherCount = 17,
        )

    }
}

@Composable
fun RoundImage(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = Color.LightGray
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileStat(numberText = "601", text = "Posts")
        ProfileStat(numberText = "100k", text = "Followers")
        ProfileStat(numberText = "72", text = "Following")
    }
}

@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = numberText, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
fun ProfileDescription(
    name: String,
    description: String,
    url: String,
    followedBy: List<String>,
    otherCount: Int,
) {
    val letterSpace = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            letterSpacing = letterSpace,
            lineHeight = lineHeight,
        )
        Text(
            text = description,
            letterSpacing = letterSpace,
            lineHeight = lineHeight,
        )
        Text(
            text = url,
            color = Color(0xff3d3d91),
            letterSpacing = letterSpace,
            lineHeight = lineHeight,
        )
        if (followedBy.isNotEmpty()) {
            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    append("Followed by ")
                    followedBy.forEachIndexed { index, name ->
                        pushStyle(boldStyle)
                        append(name)
                        pop()
                        if (index < followedBy.size - 1) {
                            append(",")
                        }
                    }
                    if (otherCount > 2) {
                        append(" and ")
                        pushStyle(boldStyle)
                        append("$otherCount others")
                    }
                },
                letterSpacing = letterSpace,
                lineHeight = lineHeight
            )
        }

    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier
) {
    val minWidth = 95.dp
    val height = 30.dp
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        ActionButton(
            text = "Following",
            icon = Icons.Default.KeyboardArrowDown,
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        ActionButton(
            text = "Message",
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        ActionButton(
            text = "Email",
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        ActionButton(
            icon = Icons.Default.KeyboardArrowDown,
            modifier = Modifier.size(height)
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: ImageVector? = null,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(6.dp)
    ) {
        if (text != null) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun HighlightSection(
    modifier: Modifier = Modifier,
    highlights: List<ImageWithText>,
) {
    LazyRow(modifier = modifier) {
        items(highlights) { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(end = 15.dp)
            ) {
                RoundImage(
                    painter = painterResource(id = item.imageId),
                    modifier = Modifier.size(70.dp)
                )
                Text(
                    text = item.text,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    imageWithTexts: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit,
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val inactivityColor = Color(0xff777777)

    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier,
    ) {
        imageWithTexts.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color.Black,
                unselectedContentColor = inactivityColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Icon(
                    painter = painterResource(id = item.imageId),
                    contentDescription = null,
                    tint = if (selectedTabIndex == index) Color.Black else inactivityColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
fun PostSection(
    modifier: Modifier = Modifier,
    postIds: List<Int>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.scale(1.01f)
    ) {
        items(postIds) { id ->
            Image(
                painter = painterResource(id = id),
                contentDescription = null,
                contentScale = Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(width = 1.dp, color = Color.White)
            )
        }
    }
}

@Preview
@Composable
fun InstagramUIPreview() {
    ProfileScreen17()
}
