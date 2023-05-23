package com.jroslar.listafacturasjetpackcomposev21.ui.screens.smartsolar


import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.*
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.titleFragment
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SmartSolarScreen() {
    Scaffold(
        topBar = { Toolbar() },
        content = { Content() },
    )
}

@Preview(showBackground = true)
@Composable
private fun Toolbar() {
    val context = LocalContext.current as Activity
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        Button(
            onClick = { context.finish() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            )
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        rememberAsyncImagePainter(ContextCompat.getDrawable(context, R.drawable.baseline_keyboard_arrow_left_24)),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = R.string.toolbarDetalleBackText.getResourceStringAndroid(context),
                        style = normalTextFragment,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content() {
    val context = LocalContext.current
    val list = listOf(
        R.string.tab_text_1.getResourceStringAndroid(context),
        R.string.tab_text_2.getResourceStringAndroid(context),
        R.string.tab_text_3.getResourceStringAndroid(context),
    )
    val pagerState = rememberPagerState(pageCount = list.size)
    Column {
        Text(
            text = R.string.tvTitleSmartSolarText.getResourceStringAndroid(context),
            style = titleFragment,
            modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)
        )
        Tabs(pagerState = pagerState, list)
        TabsContent(pagerState = pagerState)
    }
}

private fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState, list: List<String>) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(list.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        edgePadding = 0.dp,
        modifier = Modifier.padding(start = 5.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .customTabIndicatorOffset(
                        currentTabPosition = tabPositions[pagerState.currentPage],
                        tabWidth = tabWidths[pagerState.currentPage]
                    )
                    .clip(RoundedCornerShape(40.dp))
            )
        },
        divider = {
        }
    ) {
        list.forEachIndexed { index, tab ->
            Tab(
                text = {
                    Text(
                        text = tab,
                        style = normalTextFragment,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                        onTextLayout = { textLayoutResult ->
                            tabWidths[index] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        }
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> SmartSolarMiInstalacionScreen()
            1 -> SmartSolarEnergiaScreen()
            2 -> SmartSolarDetallesScreen()
        }
    }
}