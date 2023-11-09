package com.example.timetracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var tvSelectedDate: TextView
    lateinit var tvPassedTime: TextView
    lateinit var passedTimeTitle: TextView
    lateinit var datePickerBtn: Button
    lateinit var spinner: Spinner

    private var spinnerOptions = arrayOf("Click here", "Seconds", "Minutes", "Hours", "Days")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvPassedTime = findViewById(R.id.tvPassedTime)
        passedTimeTitle = findViewById(R.id.passedTimeTitle)
        datePickerBtn = findViewById(R.id.datePickerBtn)
        spinner = findViewById(R.id.spinner)

        val myToast = Toast.makeText(this, "Please Select an appropriate option", Toast.LENGTH_LONG)


        // Spinner Adapter codes Start from here

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            spinnerOptions)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener= object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinnerOptions[position] == "Seconds"){
                    passedTimeTitle.text = "Passed Seconds"
                    tvSelectedDate.text = ""
                    tvPassedTime.text = ""
                    datePickerBtn.setOnClickListener( View.OnClickListener {
                        timeCountInSeconds(view)
                    })
                }
                else if(spinnerOptions[position] == "Minutes"){
                    passedTimeTitle.text = "Passed Minutes"
                    tvSelectedDate.text = ""
                    tvPassedTime.text = ""
                    datePickerBtn.setOnClickListener(View.OnClickListener {
                        timeCountInMinutes(view)
                    })
                }
                else if(spinnerOptions[position] == "Hours"){
                    passedTimeTitle.text = "Passed Hours"
                    tvSelectedDate.text = ""
                    tvPassedTime.text = ""
                    datePickerBtn.setOnClickListener(View.OnClickListener {
                        timeCountInHours(view)
                    })
                }
                else if(spinnerOptions[position] == "Days"){
                    passedTimeTitle.setText("Passed Days")
                    tvSelectedDate.text = ""
                    tvPassedTime.text = ""
                    datePickerBtn.setOnClickListener(View.OnClickListener {
                        timeCountInDays(view)
                    })
                }
                else{
                    datePickerBtn.setOnClickListener(View.OnClickListener {
                        myToast.show()
                    })
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

//    ============================== Days, Hours, Minutes, Seconds =================================

    // ------------------------------ Counting passed dates by days --------------------------------
    private fun timeCountInDays(view: View?) {

        // set variables for calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // set DatePickerDialog
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                // selected date visibility structure
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate.setText(selectedDate)

                // format the date in standard
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val myDate = simpleDateFormat.parse(selectedDate)

                // calculate the days
                val selectedDateInDays = myDate!!.time/(60000*60*24)
                val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                val currentDateInDays = currentDate!!.time/(60000*60*24)

                // calculate the difference
                val dayDifferences = currentDateInDays - selectedDateInDays
                tvPassedTime.setText(dayDifferences.toString())

            },
            year,
            month,
            day)

        // set max date so users can selected then call the show method
        datePickerDialog.datePicker.maxDate = Date().time - 86400000
        datePickerDialog.show()
    }

    // ------------------------------ Counting passed dates by days --------------------------------
    private fun timeCountInHours(view: View?) {

        // set variables for calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog( this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate.text = selectedDate

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val myDate = simpleDateFormat.parse(selectedDate)

                val selectedDateInHours = myDate!!.time/(60000*60)
                val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                val currentDateInHours = currentDate!!.time/(60000*60)

                val hoursDifference = currentDateInHours - selectedDateInHours
                tvPassedTime.text = hoursDifference.toString()

            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.maxDate = Date().time - 86400000
        datePickerDialog.show()

    }

    private fun timeCountInMinutes(view: View?) {

        // set variables for calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate.text = selectedDate

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val myDate = simpleDateFormat.parse(selectedDate)

                val selectedDateInMinutes = myDate!!.time/60000
                val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                val currentDateInMinutes = currentDate!!.time/60000

                val minutesDifference = currentDateInMinutes - selectedDateInMinutes
                tvPassedTime.text = minutesDifference.toString()

            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.maxDate = Date().time - 86400000
        datePickerDialog.show()

    }

    private fun timeCountInSeconds(view: View?) {

        // set variables for calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                // selected date visibility structure
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate.text = selectedDate

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val myDate = simpleDateFormat.parse(selectedDate)

                val selectedDateInSeconds = myDate!!.time/1000
                val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                val currentDateInSeconds = currentDate!!.time/1000

                val secondsDifference = currentDateInSeconds - selectedDateInSeconds
                tvPassedTime.text = secondsDifference.toString()

            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = Date().time - 86400000
        datePickerDialog.show()
    }
}