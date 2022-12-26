package com.example.maxcompose.meditation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxcompose.R
import com.example.maxcompose.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MeditationScreen14() {
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        Column {
            GreetingSection(name = "max")
            ChipSection(chips = listOf("Sweet sleep", "Insomnia", "Depression"))
            CurrentMeditation()
            FeatureSection(features)
        }
        BottomNavigationContent(
            items = bottomItems,
                modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun GreetingSection(
    name: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Good morning,$name",
                color = Color.White
                //style = MaterialTheme.typography.h2
            )
            Text(
                text = "We wish you have a good day!",
                color = Color.White
                //style = MaterialTheme.typography.body1
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_bubble),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ChipSection(
    chips: List<String>
) {
    var selectedIndex by remember { mutableStateOf(0) }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        itemsIndexed(chips) { index, item ->
            Box(modifier = Modifier
                .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                .clickable {
                    selectedIndex = index
                }
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (selectedIndex == index) ButtonBlue else DarkerButtonBlue
                )
                .padding(15.dp)
            ) {
                Text(text = item, color = TextWhite)
            }

        }
    }
}

@Composable
fun CurrentMeditation(
    color: Color = LightRed,
) {
    Row(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Daily Though", color = TextWhite)
            Text(text = "Meditation 3-10 min", color = TextWhite)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ButtonBlue)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                tint = Color.White,
                modifier = Modifier.size(16.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun FeatureSection(
    featureList: List<Feature>
) {
    Column(
    ) {
        Text(
            text = "Featured",
            color = TextWhite,
            modifier = Modifier
                .padding(15.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
        ) {
            items(featureList.size) {
                FeatureItem(feature = featureList[it])
            }
        }
    }
}

@Composable
fun FeatureItem(
    feature: Feature,
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        //medium colored path
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
//            lineTo()
            close()
        }

        //light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
//            lineTo()
            close()
        }
        Canvas(
            modifier = Modifier.fillMaxWidth()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = feature.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = feature.lightColor
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                color = TextWhite,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Icon(
                painter = painterResource(id = feature.iconId),
                modifier = Modifier.align(Alignment.BottomEnd),
                tint = Color.White,
                contentDescription = null
            )
            Text(
                text = "Start",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { }
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationContent(
    modifier: Modifier = Modifier,
    items: List<BottomItem>,
    activityHighlightColor: Color = ButtonBlue,
    activityTextColor: Color = Color.White,
    inactivityTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0,
) {
    var selectedItemIndex by remember { mutableStateOf(initialSelectedItemIndex) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(15.dp),

        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            BottomItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activityHighlightColor = activityHighlightColor,
                activityTextColor = activityTextColor,
                inactivityTextColor = inactivityTextColor,
                onItemClick = { selectedItemIndex = index })
        }
    }
}

@Composable
fun BottomItem(
    item: BottomItem,
    isSelected: Boolean,
    activityHighlightColor: Color = ButtonBlue,
    activityTextColor: Color = Color.White,
    inactivityTextColor: Color = AquaBlue,
    onItemClick: (BottomItem) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick(item)
        },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) activityHighlightColor else Color.Transparent
                )
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null,
                tint = if (isSelected) activityTextColor else inactivityTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.name,
            color = if (isSelected) activityTextColor else inactivityTextColor,
        )

    }
}

@Preview
@Composable
fun MeditationScreenPreview() {
    MeditationScreen14()

}