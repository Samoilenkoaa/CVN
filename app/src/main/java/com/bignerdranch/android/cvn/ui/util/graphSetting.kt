package com.bignerdranch.android.cvn.ui.util

import android.graphics.Color
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

fun graphSetting(
    array: Array<DataPoint>,
    titles: Array<String>,
    graph: GraphView,
    graphTitle: String
) {
    val series = BarGraphSeries<DataPoint>(array);
    series.setSpacing(50)
    graph.getViewport().setMinX(0.0)
    graph.getViewport().setMaxX(4.0)
    graph.getViewport().setXAxisBoundsManual(true)
    val staticLabelsFormatter = StaticLabelsFormatter(graph)
    staticLabelsFormatter.setHorizontalLabels(arrayOf("") + titles + arrayOf(""))
    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter)
    graph.addSeries(series)
    graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
    graph.viewport.setDrawBorder(true)
    graph.gridLabelRenderer.isVerticalLabelsVisible = false
    graph.title = graphTitle
    series.color = Color.MAGENTA

}