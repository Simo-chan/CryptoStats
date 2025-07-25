package com.simochan.cryptostats.crypto.presentation.coin_details.custom_graph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simochan.cryptostats.crypto.domain.CoinPrice
import com.simochan.cryptostats.ui.theme.CryptoStatsTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointsIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true,
) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )
    val visibleDataPoints = rememberSaveable(dataPoints, visibleDataPointsIndices) {
        dataPoints.slice(visibleDataPointsIndices)
    }
    val maxYValue = rememberSaveable(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }
    val minYValue = rememberSaveable(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    val measurer = rememberTextMeasurer()

    var xLabelWidth by rememberSaveable {
        mutableFloatStateOf(0f)
    }
    LaunchedEffect(key1 = xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    val selectedDataPointIndex = rememberSaveable(selectedDataPoint) {
        dataPoints.indexOf(selectedDataPoint)
    }
    var drawPoints by rememberSaveable {
        mutableStateOf(listOf<DataPoint>())
    }
    var isShowingDataPoints by rememberSaveable {
        mutableStateOf(selectedDataPoint != null)
    }

    //Graph's line specs
    val chartLinePath = remember { Path() }
    val pathMeasure = remember { PathMeasure() }
    val pathSegment = remember { Path() }
    val filledSegment = remember { Path() }
    val animationProgress = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = Unit) {
        animationProgress.animateTo(
            1f,
            animationSpec = tween(1000)
        )
    }
    val gradientColors = remember {
        listOf(
            style.chartLineColor.copy(alpha = 0.4f),
            Color.Transparent
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(drawPoints, xLabelWidth) {
                detectHorizontalDragGestures { change, _ ->
                    val newSelectedDataPointIndex = getSelectedDataPointIndex(
                        touchOffset = change.position.x,
                        triggerWidth = xLabelWidth,
                        drawPoints = drawPoints
                    )
                    isShowingDataPoints =
                        (newSelectedDataPointIndex + visibleDataPointsIndices.first) in visibleDataPointsIndices
                    if (isShowingDataPoints) {
                        onSelectedDataPoint(dataPoints[newSelectedDataPointIndex])
                    }
                }
            }
    ) {
        val minLabelSpacingY = style.minYLabelSpacing.toPx()
        val verticalPaddingPx = style.verticalPadding.toPx()
        val horizontalPaddingPx = style.horizontalPadding.toPx()
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.toPx()

        val xLabelTextLayoutResults = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }
        val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight = if (maxXLabelLineCount > 0) {
            maxXLabelHeight / maxXLabelLineCount
        } else {
            0
        }

        val viewPortHeightPx = size.height - (maxXLabelHeight + 2 * verticalPaddingPx
                + xLabelLineHeight + xAxisLabelSpacingPx)

        //Y LABEL CALCULATIONS
        val labelViewPortHeightPx = viewPortHeightPx + xLabelLineHeight
        val labelCountExcludingLastLabel =
            ((labelViewPortHeightPx / (minLabelSpacingY + xLabelLineHeight))).toInt()

        val valueIncrement = (maxYValue - minYValue) / labelCountExcludingLastLabel

        val yLabels = (0..labelCountExcludingLastLabel).map {
            ValueLabel(
                value = maxYValue - (valueIncrement * it),
                unit = unit
            )
        }

        val yLabelTextLayoutResults = yLabels.map {
            measurer.measure(
                text = it.formatted(),
                style = textStyle
            )
        }
        val maxYLabelWidth = yLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0

        //VIEWPORT CALCULATIONS
        val viewPortTopY = verticalPaddingPx + xLabelLineHeight + 10f
        val viewPortRightX = size.width
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeftX = 2f * horizontalPaddingPx + maxYLabelWidth

        //X LABEL CALCULATIONS
        xLabelWidth = maxXLabelWidth + xAxisLabelSpacingPx
        xLabelTextLayoutResults.forEachIndexed { index, result ->
            val x = viewPortLeftX + xAxisLabelSpacingPx / 2f +
                    xLabelWidth * index
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = x,
                    y = viewPortBottomY + xAxisLabelSpacingPx
                ),
                color = if (index == selectedDataPointIndex) {
                    style.selectedColor
                } else {
                    style.unselectedColor
                }
            )

            if (showHelperLines) {
                drawLine(
                    color = if (selectedDataPointIndex == index) {
                        style.selectedColor
                    } else {
                        style.unselectedColor
                    },
                    start = Offset(
                        x = x + result.size.width / 2f,
                        y = viewPortBottomY
                    ),
                    end = Offset(
                        x = x + result.size.width / 2f,
                        y = viewPortTopY
                    ),
                    strokeWidth = if (selectedDataPointIndex == index) {
                        style.helperLinesThicknessPx * 2f
                    } else {
                        style.helperLinesThicknessPx
                    }
                )
                if (selectedDataPointIndex == index) {
                    val valueLabel = ValueLabel(
                        value = visibleDataPoints[index].y,
                        unit = unit
                    )
                    val valueResult = measurer.measure(
                        text = valueLabel.formatted(),
                        style = textStyle.copy(
                            color = style.selectedColor
                        ),
                        maxLines = 1
                    )
                    val textPositionX =
                        if (selectedDataPointIndex == visibleDataPointsIndices.last) {
                            x - valueResult.size.width
                        } else {
                            x - valueResult.size.width / 2f
                        } + result.size.width / 2f
                    val isTextInVisibleRange =
                        (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()
                    if (isTextInVisibleRange) {
                        drawText(
                            textLayoutResult = valueResult,
                            topLeft = Offset(
                                x = textPositionX,
                                y = viewPortTopY - valueResult.size.height - 10f
                            )
                        )
                    }
                }
            }
        }

        val heightRequiredForLabels = xLabelLineHeight *
                (labelCountExcludingLastLabel + 1)
        val remainingHeightForLabels = labelViewPortHeightPx - heightRequiredForLabels
        val spaceBetweenLabels = remainingHeightForLabels / labelCountExcludingLastLabel

        yLabelTextLayoutResults.forEachIndexed { index, result ->
            val x = horizontalPaddingPx + maxYLabelWidth - result.size.width.toFloat()
            val y = viewPortTopY +
                    index * (xLabelLineHeight + spaceBetweenLabels) - xLabelLineHeight / 2f
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = x,
                    y = y
                ),
                color = style.unselectedColor
            )

            if (showHelperLines) {
                drawLine(
                    color = style.unselectedColor,
                    start = Offset(
                        x = viewPortLeftX,
                        y = y + result.size.height.toFloat() / 2f
                    ),
                    end = Offset(
                        x = viewPortRightX,
                        y = y + result.size.height.toFloat() / 2f
                    ),
                    strokeWidth = style.helperLinesThicknessPx
                )
            }
        }

        drawPoints = visibleDataPointsIndices.map {
            val x = viewPortLeftX + (it - visibleDataPointsIndices.first) *
                    xLabelWidth + xLabelWidth / 2f
            val ratio = (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
            val y = viewPortBottomY - (ratio * viewPortHeightPx)
            DataPoint(
                x = x,
                y = y,
                xLabel = dataPoints[it].xLabel
            )
        }

        val connectionPoint1 = mutableListOf<DataPoint>()
        val connectionPoint2 = mutableListOf<DataPoint>()
        for (i in 1 until drawPoints.size) {
            val p0 = drawPoints[i - 1]
            val p1 = drawPoints[i]

            val x = (p1.x + p0.x) / 2f
            val y1 = p0.y
            val y2 = p1.y

            connectionPoint1.add(DataPoint(x, y1, ""))
            connectionPoint2.add(DataPoint(x, y2, ""))
        }

        chartLinePath.reset()
        pathSegment.reset()
        filledSegment.reset()

        chartLinePath.apply {
            if (drawPoints.isNotEmpty()) {
                moveTo(drawPoints.first().x, drawPoints.first().y)

                for (i in 1 until drawPoints.size) {
                    cubicTo(
                        x1 = connectionPoint1[i - 1].x,
                        y1 = connectionPoint1[i - 1].y,
                        x2 = connectionPoint2[i - 1].x,
                        y2 = connectionPoint2[i - 1].y,
                        x3 = drawPoints[i].x,
                        y3 = drawPoints[i].y
                    )
                }
            }
        }

        pathMeasure.setPath(chartLinePath, false)
        pathMeasure.getSegment(0f, pathMeasure.length * animationProgress.value, pathSegment)

        if (drawPoints.isNotEmpty()) {
            val progress = animationProgress.value
            val animatedChartLinePath = Path()
            pathMeasure.getSegment(0f, pathMeasure.length * progress, animatedChartLinePath)

            filledSegment.addPath(animatedChartLinePath)
            if (animatedChartLinePath.isEmpty.not()) {
                val lastPointX =
                    if (progress == 1f) drawPoints.last().x else pathMeasure.getPosition(pathMeasure.length * progress).x
                filledSegment.lineTo(lastPointX, viewPortBottomY)
                filledSegment.lineTo(drawPoints.first().x, viewPortBottomY)
                filledSegment.close()
            }
        }

        drawPath(
            path = filledSegment,
            brush = Brush.verticalGradient(
                colors = gradientColors,
                startY = viewPortTopY,
                endY = viewPortBottomY
            )
        )

        drawPath(
            path = pathSegment,
            color = style.chartLineColor,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round,
            )
        )

        drawPoints.forEachIndexed { index, point ->
            if (isShowingDataPoints) {
                val circleOffset = Offset(
                    x = point.x,
                    y = point.y
                )
                drawCircle(
                    color = style.selectedColor,
                    radius = 8f,
                    center = circleOffset
                )

                if (selectedDataPointIndex == index) {
                    drawCircle(
                        color = Color.White,
                        radius = 10f,
                        center = circleOffset
                    )
                    drawCircle(
                        color = style.selectedColor,
                        radius = 10f,
                        center = circleOffset,
                        style = Stroke(
                            width = 1.5f
                        )
                    )
                }
            }
        }
    }
}

private fun getSelectedDataPointIndex(
    touchOffset: Float,
    triggerWidth: Float,
    drawPoints: List<DataPoint>,
): Int {
    val triggerRangeLeft = touchOffset - triggerWidth / 2f
    val triggerRangeRight = touchOffset + triggerWidth / 2f
    return drawPoints.indexOfFirst {
        it.x in triggerRangeLeft..triggerRangeRight
    }
}

@Preview(widthDp = 1000)
@Composable
private fun LineChartPreview() {
    CryptoStatsTheme(true) {
        val coinHistoryMock = remember {
            (1..20).map {
                CoinPrice(
                    priceUsd = Random.nextFloat() * 1000.0,
                    time = ZonedDateTime.now().plusHours(it.toLong())
                )
            }
        }
        val style = ChartStyle(
            chartLineColor = Color.Black,
            unselectedColor = Color(0xFF7C7C7C),
            selectedColor = Color.Black,
            helperLinesThicknessPx = 1f,
            axisLinesThicknessPx = 5f,
            labelFontSize = 14.sp,
            minYLabelSpacing = 25.dp,
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            xAxisLabelSpacing = 8.dp
        )
        val dataPoints = remember {
            coinHistoryMock.map {
                DataPoint(
                    x = it.time.hour.toFloat(),
                    y = it.priceUsd.toFloat(),
                    xLabel = DateTimeFormatter.ofPattern("ha\nM/d").format(it.time)
                )
            }
        }
        Surface {
            LineChart(
                dataPoints = dataPoints,
                style = style,
                visibleDataPointsIndices = 0..19,
                unit = "$",
                modifier = Modifier
                    .width(700.dp)
                    .height(300.dp)
                    .background(Color.White),
                selectedDataPoint = dataPoints[1],
            )
        }
    }
}