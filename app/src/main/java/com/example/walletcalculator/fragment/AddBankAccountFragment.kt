package com.example.walletcalculator.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walletcalculator.R
import com.example.walletcalculator.activity.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddBankAccountFragment : Fragment() {

    // private lateinit var bottomNavigationView: BottomNavigationView // Working

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bottomNavigationView = requireActivity().findViewById(R.id.bottomNav) // Working
        // bottomNavigationView?.visibility = View.GONE // Working
        (activity as HomeActivity).hideBottomMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank_account, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        // bottomNavigationView?.visibility = View.VISIBLE // Working
        (activity as HomeActivity).showBottomMenu()
    }
}