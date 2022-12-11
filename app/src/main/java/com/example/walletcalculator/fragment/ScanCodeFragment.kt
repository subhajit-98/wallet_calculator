package com.example.walletcalculator.fragment

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.walletcalculator.R
import com.example.walletcalculator.activity.HomeActivity
import com.example.walletcalculator.activity.MainActivity
import com.example.walletcalculator.databinding.FragmentScanCodeBinding
import com.example.walletcalculator.utils.Constants

private const val ARG_PARAM1 = "param1"

class ScanCodeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    lateinit var binding: FragmentScanCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as HomeActivity?)!!.demo()
        binding = FragmentScanCodeBinding.inflate(inflater, container,false)
        /*binding.clickText.setOnClickListener {
            // (activity as HomeActivity)!!.test()
            // (activity as HomeActivity)!!.checkPermission()
            var resp = (activity as HomeActivity)!!.permissionAsk(Manifest.permission.CAMERA, Constants.CAMERA_PERMISSION_CODE)
            if(!resp){
                val alert: AlertDialog = (activity as MainActivity?)!!.alertMessage(true, "Permission Required", "Camera permission is required")
                alert.show()
                alert.findViewById<TextView>(R.id.cancel)?.setOnClickListener {
                    alert.dismiss()
                }
            }
        }*/
        return binding.root
        // return inflater.inflate(R.layout.fragment_scan_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.clickText.setOnClickListener {
            /*Toast.makeText(activity, "Clicked", Toast.LENGTH_LONG).show()
            val alert: AlertDialog = (activity as MainActivity?)!!.alertMessage(true)
            alert.show()
            alert.findViewById<TextView>(R.id.cancel)?.setOnClickListener {
                alert.dismiss()
            }*/
            // (activity as MainActivity)!!.toastMessage("From custom toast")
            /*var resp = (activity as HomeActivity)!!.permissionAsk(Manifest.permission.CAMERA, Constants.CAMERA_PERMISSION_CODE)
            if(!resp){
                val alert: AlertDialog = (activity as MainActivity?)!!.alertMessage(true, "Permission Required", "Camera permission is required")
                alert.show()
                alert.findViewById<TextView>(R.id.cancel)?.setOnClickListener {
                    alert.dismiss()
                }
            }*/

            // (activity as HomeActivity)!!.replaceFragment(ScanCodeFragment(), (activity as HomeActivity).findViewById<FrameLayout>(R.id.showContent).id, true)
            (activity as HomeActivity)!!.changeDrawerMenuIcon(false)
            (activity as HomeActivity)!!.changeFragment(ScanCodeFragment(), true, "demo", "Demo")
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ScanCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}