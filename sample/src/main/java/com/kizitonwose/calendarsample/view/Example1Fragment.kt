package com.kizitonwose.calendarsample.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.kizitonwose.calendarcore.CalendarDay
import com.kizitonwose.calendarcore.DayPosition
import com.kizitonwose.calendarcore.daysOfWeek
import com.kizitonwose.calendarsample.R
import com.kizitonwose.calendarsample.databinding.Example1CalendarDayBinding
import com.kizitonwose.calendarsample.databinding.Example1FragmentBinding
import com.kizitonwose.calendarsample.displayText
import com.kizitonwose.calendarview.MonthDayBinder
import com.kizitonwose.calendarview.ViewContainer
import java.time.LocalDate
import java.time.YearMonth

class Example1Fragment : BaseFragment(R.layout.example_1_fragment), HasToolbar {

    override val toolbar: Toolbar?
        get() = null

    override val titleRes: Int = R.string.example_1_title

    private lateinit var binding: Example1FragmentBinding

    private val selectedDates = mutableSetOf<LocalDate>()
    private val today = LocalDate.now()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = Example1FragmentBinding.bind(view)
        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].displayText()
                setTextColorRes(R.color.example_1_white_light)
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        binding.exOneCalendar.setup(startMonth, endMonth, daysOfWeek.first())
        binding.exOneCalendar.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = Example1CalendarDayBinding.bind(view).exOneDayText

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDates.contains(day.date)) {
                            selectedDates.remove(day.date)
                        } else {
                            selectedDates.add(day.date)
                        }
                        binding.exOneCalendar.notifyDayChanged(day)
                    }
                }
            }
        }

        binding.exOneCalendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.textView
                textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    when {
                        selectedDates.contains(data.date) -> {
                            textView.setTextColorRes(R.color.example_1_bg)
                            textView.setBackgroundResource(R.drawable.example_1_selected_bg)
                        }
                        today == data.date -> {
                            textView.setTextColorRes(R.color.example_1_white)
                            textView.setBackgroundResource(R.drawable.example_1_today_bg)
                        }
                        else -> {
                            textView.setTextColorRes(R.color.example_1_white)
                            textView.background = null
                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.example_1_white_light)
                    textView.background = null
                }
            }
        }

        binding.exOneCalendar.monthScrollListener = {
            binding.exOneYearText.text = it.yearMonth.year.toString()
            binding.exOneMonthText.text = it.yearMonth.month.displayText(short = false)
//            if (binding.exOneCalendar.maxRowCount == 6) {
//                binding.exOneYearText.text = it.yearMonth.year.toString()
//                binding.exOneMonthText.text = monthTitleFormatter.format(it.yearMonth)
//            } else {
//                // In week mode, we show the header a bit differently.
//                // We show indices with dates from different months since
//                // dates overflow and cells in one index can belong to different
//                // months/years.
//                val firstDate = it.weekDays.first().first().date
//                val lastDate = it.weekDays.last().last().date
//                if (firstDate.yearMonth == lastDate.yearMonth) {
//                    binding.exOneYearText.text = firstDate.yearMonth.year.toString()
//                    binding.exOneMonthText.text = monthTitleFormatter.format(firstDate)
//                } else {
//                    binding.exOneMonthText.text =
//                        "${monthTitleFormatter.format(firstDate)} - ${monthTitleFormatter.format(lastDate)}"
//                    if (firstDate.year == lastDate.year) {
//                        binding.exOneYearText.text = firstDate.yearMonth.year.toString()
//                    } else {
//                        binding.exOneYearText.text = "${firstDate.yearMonth.year} - ${lastDate.yearMonth.year}"
//                    }
//                }
//            }
        }

        binding.weekModeCheckBox.setOnCheckedChangeListener { _, monthToWeek ->
//            val firstDate = binding.exOneCalendar.findFirstVisibleDay()?.date ?: return@setOnCheckedChangeListener
//            val lastDate = binding.exOneCalendar.findLastVisibleDay()?.date ?: return@setOnCheckedChangeListener
//
//            val oneWeekHeight = binding.exOneCalendar.daySize.height
//            val oneMonthHeight = oneWeekHeight * 6
//
//            val oldHeight = if (monthToWeek) oneMonthHeight else oneWeekHeight
//            val newHeight = if (monthToWeek) oneWeekHeight else oneMonthHeight
//
//            // Animate calendar height changes.
//            val animator = ValueAnimator.ofInt(oldHeight, newHeight)
//            animator.addUpdateListener { animator ->
//                binding.exOneCalendar.updateLayoutParams {
//                    height = animator.animatedValue as Int
//                }
//            }

            // When changing from month to week mode, we change the calendar's
            // config at the end of the animation(doOnEnd) but when changing
            // from week to month mode, we change the calendar's config at
            // the start of the animation(doOnStart). This is so that the change
            // in height is visible. You can do this whichever way you prefer.

//            animator.doOnStart {
//                if (!monthToWeek) {
//                    binding.exOneCalendar.updateMonthConfiguration(
//                        inDateStyle = InDateStyle.ALL_MONTHS,
//                        maxRowCount = 6,
//                        hasBoundaries = true
//                    )
//                }
//            }
//            animator.doOnEnd {
//                if (monthToWeek) {
//                    binding.exOneCalendar.updateMonthConfiguration(
//                        inDateStyle = InDateStyle.FIRST_MONTH,
//                        maxRowCount = 1,
//                        hasBoundaries = false
//                    )
//                }
//
//                if (monthToWeek) {
//                    // We want the first visible day to remain
//                    // visible when we change to week mode.
//                    binding.exOneCalendar.scrollToDate(firstDate)
//                } else {
//                    // When changing to month mode, we choose current
//                    // month if it is the only one in the current frame.
//                    // if we have multiple months in one frame, we prefer
//                    // the second one unless it's an outDate in the last index.
//                    if (firstDate.yearMonth == lastDate.yearMonth) {
//                        binding.exOneCalendar.scrollToMonth(firstDate.yearMonth)
//                    } else {
//                        // We compare the next with the last month on the calendar so we don't go over.
//                        binding.exOneCalendar.scrollToMonth(minOf(firstDate.yearMonth.next, endMonth))
//                    }
//                }
//            }
//            animator.duration = 250
//            animator.start()
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.example_1_bg_light)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
    }
}
