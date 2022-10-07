package com.kizitonwose.calendarsample.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendarcompose.HorizontalCalendar
import com.kizitonwose.calendarcompose.rememberCalendarState
import com.kizitonwose.calendarcore.CalendarDay
import com.kizitonwose.calendarcore.CalendarMonth
import com.kizitonwose.calendarcore.DayPosition
import com.kizitonwose.calendarcore.firstDayOfWeekFromLocale
import com.kizitonwose.calendarsample.R
import com.kizitonwose.calendarsample.displayText
import java.time.YearMonth

@Composable
fun Example4Page() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth }
    val endMonth = remember { currentMonth.plusMonths(500) }
    Column {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = firstDayOfWeekFromLocale(),
        )
        HorizontalCalendar(
            state = state,
            dayContent = { day -> Day(day) },
            monthHeader = { month -> MonthHeader(month) },
            calendarScrollPaged = false,
            contentPadding = PaddingValues(4.dp),
            monthContainer = { _, container ->
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp
                Box(modifier = Modifier
                    .width(screenWidth * 0.73f)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        color = colorResource(R.color.black),
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                ) {
                    container()
                }
            },
            monthContent = { _, content ->
                Box(modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.example_6_month_bg_color),
                            colorResource(R.color.example_6_month_bg_color3),
                        )
                    )
                )) {
                    content()
                }
            }
        )
    }
}

@Composable
private fun MonthHeader(calendarMonth: CalendarMonth) {
    val daysOfWeek = calendarMonth.weekDays.first().map { it.date.dayOfWeek }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = colorResource(R.color.example_6_month_bg_color2))
            .padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = calendarMonth.yearMonth.displayText(short = true),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    text = dayOfWeek.name.first().toString(),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        Divider(color = Color.Black)
    }

}

@Composable
private fun Day(day: CalendarDay) {
    BoxWithConstraints(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (day.position == DayPosition.MonthDate) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = day.date.dayOfMonth.toString(),
                color = Color.Black,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(heightDp = 600)
@Composable
private fun Example4Preview() {
    Example4Page()
}
