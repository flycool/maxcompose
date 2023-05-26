package com.example.maxcompose.compose2

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination

val images = listOf(
    "https://tse2-mm.cn.bing.net/th/id/OIP-C.7KW5GT7NQ8yUGlBbCHEm0gHaNK?pid=ImgDet&rs=1",
    "https://img.bizhizu.com/2015/1231/20151231030245752.jpg",
    "https://img.zcool.cn/community/01c4c05548e2f90000019ae9115bb2.jpg@1280w_1l_2o_100sh.jpg",
    "https://scpic.chinaz.net/files/pic/pic9/201806/bpic7031.jpg",
)


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun ImageViewPager() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp)
    ) { paddingValues ->

        val pageState = rememberPagerState()
        val matrix = remember {
            ColorMatrix()
        }
        HorizontalPager(
            state = pageState,
            pageCount = images.size,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { index ->

            val pageOffset = (pageState.currentPage - index) + pageState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300)
            )
            LaunchedEffect(imageSize) {
                if (pageOffset != 0f) {
                    matrix.setToSaturation(0f)
                } else {
                    matrix.setToSaturation(1f)
                }
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[index])
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "image",
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
        }
    }
}