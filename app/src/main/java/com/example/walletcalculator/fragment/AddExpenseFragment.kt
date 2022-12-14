package com.example.walletcalculator.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.walletcalculator.R
import com.example.walletcalculator.activity.HomeActivity
import com.example.walletcalculator.activity.MainActivity
import com.example.walletcalculator.databinding.FragmentAddExpenseBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddExpenseFragment : Fragment() {

    private lateinit var addExpenseBinding: FragmentAddExpenseBinding
    private val debitedArray = arrayOf("PayTm UPI", "G Pay UPI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as HomeActivity).hideBottomMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addExpenseBinding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return addExpenseBinding.root
        // return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addExpenseBinding.selectDate.showSoftInputOnFocus= false
        addExpenseBinding.selectDate.isEnabled = false
        // addExpenseBinding.selectDateLayout.error = "aaaaaaaaaaa"
        addExpenseBinding.selectTime.isEnabled = false
        addExpenseBinding.selectDateLayout.setEndIconOnClickListener {
            dateDialog()?.show()
        }

        addExpenseBinding.selectTimeLayout.setEndIconOnClickListener {
            timeDialog().show()
        }

        // Spinner
        val spinnerList = activity?.let {
                                        ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, debitedArray)
                                    }
        addExpenseBinding.fromDebit.setAdapter(spinnerList)

        /**
         * Search product
         */
        addExpenseBinding.searchProductText.isEnabled = false
        addExpenseBinding.searchProductTextLayout.setEndIconOnClickListener {
            if(!(activity as MainActivity).permissionCheck(Manifest.permission.CAMERA)) {
                if(!(activity as MainActivity).permissionAsk(Manifest.permission.CAMERA, 1)) {
                    val respAlert = (activity as MainActivity).alertMessage(false, "Permission Requirment", "Need to camera permission mandatory")
                    respAlert.show()
                    respAlert.findViewById<TextView>(R.id.submit)?.setOnClickListener {
                        // Toast.makeText(activity, "Scan Code", Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", (activity as MainActivity).packageName, null)
                        intent.data = uri
                        startActivity(intent)
                        respAlert.cancel()
                    }
                    respAlert.findViewById<TextView>(R.id.cancel)?.setOnClickListener {
                        respAlert.cancel()
                    }
                }
            }
        }
    }

    /**
     * DatePickerDialog
     */
    private fun dateDialog(): DatePickerDialog? {
        val c = Calendar.getInstance()
        if(!addExpenseBinding.selectDate.text.isNullOrEmpty()){
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
                val date = LocalDate.parse(addExpenseBinding.selectDate.text.toString(), sdf)
                c.set(date.year, date.monthValue-1, date.dayOfMonth)
            }*/
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val date: Date = sdf.parse(addExpenseBinding.selectDate.text.toString())
            c.time = date
        }
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = activity?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    addExpenseBinding.selectDate.setText(""+dayOfMonth+"/"+(month+1)+"/"+year)
                },
                year,month, day
            )
        }
        return datePickerDialog
    }

    /**
     * TimePicker
     */
    private fun timeDialog(): TimePickerDialog{
        val mcurrentTime = Calendar.getInstance()
        if(!addExpenseBinding.selectTime.text.isNullOrEmpty()){
            val sdf = SimpleDateFormat("hh:mm")
            val date: Date = sdf.parse(addExpenseBinding.selectTime.text.toString())
            mcurrentTime.time = date
        }
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        val mTimePicker = TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                addExpenseBinding.selectTime.setText(""+hourOfDay+":"+minute)
            },
            hour, minute, false
        )

        return mTimePicker
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as HomeActivity).showBottomMenu()
    }
}