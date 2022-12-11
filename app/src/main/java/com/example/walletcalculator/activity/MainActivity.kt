package com.example.walletcalculator.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.walletcalculator.R
import com.example.walletcalculator.utils.Constants

open class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){

        }
    }

    /**
     * On back button pressed
     */
    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Back hit...", Toast.LENGTH_LONG).show()
    }

    /**
     * Replace fragment global
     */
    public fun replaceFragment(fragment: Fragment, frameLayout: Int, backStack: Boolean, backStackEntryName: String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(frameLayout, fragment, "scanCodeTag")
        if (backStack){
            fragmentTransaction.addToBackStack(backStackEntryName)
        }
        fragmentTransaction.commit()
    }

    /**
     * Custom Toast
     */
    public fun toastMessage(toastMessage: String){
        val toastView =layoutInflater.inflate(R.layout.custom_toast_design, findViewById(R.id.toastLayout))
        val toastMessageView = toastView.findViewById<TextView>(R.id.toastText)
        toastMessageView.text = toastMessage

        with(Toast(applicationContext)) {
            duration = Toast.LENGTH_LONG
            view = toastView
            // setGravity(Gravity.NO_GRAVITY, 0,0)
            show()
        }

    }

    /**
     * Custom Alert
     */
    public fun alertMessage(isCancelable: Boolean, title: String, content: String): AlertDialog{
        var alertDialog: AlertDialog?= null
        val alertDialogBuilder = AlertDialog.Builder(this)
        val alertDialogView = layoutInflater.inflate(R.layout.custom_alert_design, null)
        alertDialogView.findViewById<TextView>(R.id.title)!!.text = title
        alertDialogView.findViewById<TextView>(R.id.content)!!.text = content
        val positiveButton = alertDialogView.findViewById<TextView>(R.id.submit)
        alertDialogBuilder.setView(alertDialogView)
        alertDialogBuilder.setCancelable(isCancelable)
        alertDialog = alertDialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return alertDialog
    }

    // Custom snack bar

    // Send Notification

    /**
     * Permission request / ask
     */
    /*public fun checkPermission(){
        val isCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
        val permissionRequest: MutableList<String> = ArrayList()
        Log.i("Permission", isCameraPermission.toString())
        if(!isCameraPermission){
            permissionRequest.add(Manifest.permission.CAMERA)
        }

        if(permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
            val ss = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
            Toast.makeText(this, ss.toString(), Toast.LENGTH_LONG).show()
            // ActivityCompat.requestPermissions()
        }
    }*/

    /**
     * Permission all
     */
    public fun permissionAskAll(){
        val permissionRequest: MutableList<String> = ArrayList()
        if(!permissionCheck(Manifest.permission.CAMERA)){
            permissionRequest.add(Manifest.permission.CAMERA)
        }
        if(!permissionCheck(Manifest.permission.CALL_PHONE)){
            permissionRequest.add(Manifest.permission.CALL_PHONE)
        }

        if(permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }

    /**
     * Permission check
     */
    public fun permissionCheck(permission: String): Boolean{
        return ActivityCompat.checkSelfPermission(this, permission)==PackageManager.PERMISSION_GRANTED
    }


    /**
     * Permission ask
     */
    public fun permissionAsk(permission: String, permissionCode: Int): Boolean{
        if(!permissionCheck(permission))
            ActivityCompat.requestPermissions(this, arrayOf(permission), permissionCode)

        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
        // Toast.makeText(this, ss.toString(), Toast.LENGTH_LONG).show()
    }

    /**
     * Check fragment stack count
     */
    protected fun fragmentListCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    /**
     * Get fragment Id or Name
     */
    protected fun fragmentID(): String {
        // Log.i("IDDDDD", supportFragmentManager.getBackStackEntryAt(0).id.toString())
        return if(this.fragmentListCount() > 0)
            supportFragmentManager.getBackStackEntryAt(this.fragmentListCount()-1).name.toString()
        else
            ""
    }
}