package com.example.walletcalculator.activity

import android.Manifest
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.BackStackEntry
import com.example.walletcalculator.R
import com.example.walletcalculator.databinding.ActivityHomeBinding
import com.example.walletcalculator.fragment.AddBankAccountFragment
import com.example.walletcalculator.fragment.AddExpenseFragment
import com.example.walletcalculator.fragment.ScanCodeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : MainActivity(), NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var homeBinding: ActivityHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val toolBarTitle: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        toggle = ActionBarDrawerToggle(this, homeBinding.drawerLayout, homeBinding.toolBarInclude.toolBar, R.string.open, R.string.close)
        homeBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        /**
         * For custom icon menu drawer
         */
        toggle.isDrawerIndicatorEnabled = false
        // toggle.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        this.changeDrawerMenuIcon()
        toggle.setToolbarNavigationClickListener {
            openDrawer()
        }

        // toggle.drawerArrowDrawable = DrawerArrowDrawable(this) // Not worked

        homeBinding.navigationView.setNavigationItemSelectedListener(this)

        homeBinding.toolBarInclude.toolBar.inflateMenu(R.menu.tool_bar_option_menu)
        homeBinding.toolBarInclude.toolBar.setOnMenuItemClickListener(this)

        this.drawerMenuNotification()
        this.appBarOptionMenuNotification()

        /**
         * Bottom Navigation
         */
        homeBinding.bottomNav.setOnNavigationItemSelectedListener(this)

        //this.replaceFragment(ScanCodeFragment(), homeBinding.showContent.id, false)
        this.changeFragment(ScanCodeFragment(), false, "home_fragment", "Home fragment")
        this.toolBarTitle.add("Home fragment")

        this.permissionAskAll()


    }

    /**
     * Drawer menu notification number
     */
    private fun drawerMenuNotification(){
        val notify: TextView = MenuItemCompat.getActionView(homeBinding.navigationView.menu.findItem(R.id.notify)) as TextView
        notify.text = "40"
        notify.gravity = Gravity.CENTER_VERTICAL
        notify.setTextColor(resources.getColor(R.color.drawer_notify_color))
        notify.setTypeface(null, Typeface.BOLD)
    }

    /**
     * Appbar menu icon notification number
     */
    private fun appBarOptionMenuNotification(){
        homeBinding.toolBarInclude.toolBar.menu.findItem(R.id.action_cart).actionView?.findViewById<TextView>(R.id.cart_badge)?.text="5"
    }

    /**
     * Toolbar title
     */
    private fun setToolBarTitle(title: String){
        homeBinding.toolBarInclude.toolBar.title = title
        homeBinding.toolBarInclude.toolBar.setTitleTextColor(Color.WHITE)
        // homeBinding.toolBarInclude.toolBar.subtitle = "77"
    }

    /**
     * Drawer open
     */
    private fun openDrawer() {
        if(super.fragmentListCount() > 0) {
            this.onBackPressed()
        }
        else {
            homeBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * Drawer close
     */
    private fun closeDrawer() {
        homeBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    /**
     * Change drawer menu icon
     * @isMenuIcon == true => show hamburger
     */
    public fun changeDrawerMenuIcon(isMenuIcon:Boolean = true) {
        if(isMenuIcon) {
            toggle.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }
        else {
            toggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
    }

    public fun demo() {

    }

    /**
     * Drawer menu and Bottom Nav click listener
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /*if(item.itemId == R.id.notify){
            this.closeDrawer()
            Toast.makeText(applicationContext, "Notify", Toast.LENGTH_LONG).show()
            return true
        }*/
        when (item.itemId) {
            R.id.notify -> {
                this.closeDrawer()
                this.replaceFragment(ScanCodeFragment.newInstance("Test"), homeBinding.showContent.id, true, "test")
                Toast.makeText(applicationContext, "Notify", Toast.LENGTH_LONG).show()
            }

            R.id.bank -> {
                this.closeDrawer()
                this.changeFragment(AddBankAccountFragment(),true, "add_bank","Add bank account")
                this.changeDrawerMenuIcon(false)
            }

            R.id.myExpense -> {
                this.closeDrawer()
                this.changeFragment(AddExpenseFragment(),true, "add_bank","Add expense")
                this.changeDrawerMenuIcon(false)
            }

            R.id.product -> {
                Toast.makeText(applicationContext, "Product", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }

    /**
     * Option menu click listener
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.t1 -> {
                Toast.makeText(applicationContext, "Notify", Toast.LENGTH_LONG).show()
                return true
            }
        }
        return true
    }

    /**
     * Change fragment from fragment
     */
    public fun changeFragment(fragment: Fragment, backStackEntry: Boolean = false, backStackEntryName: String = "", fragmentTitle: String = ""){
        this.setToolBarTitle(fragmentTitle)
        if(backStackEntry)
            this.toolBarTitle.add(fragmentTitle)
        super.replaceFragment(fragment, homeBinding.showContent.id, backStackEntry, backStackEntryName)
    }

    /**
     * Bottom menu show
     */
    public fun showBottomMenu() {
        homeBinding.bottomNav.visibility = View.VISIBLE
    }

    /**
     * Bottom menu hide
     */
    public fun hideBottomMenu() {
        homeBinding.bottomNav.visibility = View.GONE
    }

    /**
     * Back button hit
     */
    override fun onBackPressed() {
        Toast.makeText(this, "Back hit 2...", Toast.LENGTH_LONG).show()
        super.onBackPressed()
        Log.i("aaaaaaaaaaaaaaa", super.fragmentID())
        Log.i("Count fragment", super.fragmentListCount().toString())
        if(super.fragmentListCount() > 0) {
            this.changeDrawerMenuIcon(false)
        }
        else {
            this.changeDrawerMenuIcon(true)
        }

        // Remove last title
        if(this.toolBarTitle.size > 1) {
            this.toolBarTitle.removeAt(this.toolBarTitle.size-1)
            this.setToolBarTitle(this.toolBarTitle[this.toolBarTitle.size-1])
        }
    }
}

//https://www.youtube.com/watch?v=zQh-QGGKPw0
// https://medium.com/android-news/android-adding-badge-or-count-to-the-navigation-drawer-84c93af1f4d9 /// drawer menu notify
// https://stackoverflow.com/questions/43194243/notification-badge-on-action-item-android // Cart badge
// https://www.youtube.com/watch?v=DAWmKXOqSyo // Custom Toast
// https://www.youtube.com/watch?v=__GRhyvf6oE // Custom Toast


// For help not implement
// https://www.youtube.com/watch?v=oh4YOj9VkVE
// https://www.youtube.com/watch?v=PTejW6z0szc
