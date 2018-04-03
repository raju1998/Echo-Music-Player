package com.example.manish.echo.activities


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.manish.echo.R
import com.example.manish.echo.Songs
import com.example.manish.echo.activities.MainActivity.statified.drawerLayout
import com.example.manish.echo.adapters.NavigationDrawerAdapter
import com.example.manish.echo.fragments.MainScreenFragment
import com.example.manish.echo.fragments.SettingFragment
import com.example.manish.echo.fragments.SongPlayingFragment

class MainActivity : AppCompatActivity() {
    object statified {
        var drawerLayout: DrawerLayout? = null
        var notificationManager: NotificationManager?=null
    }
    var trackNotificationBar:Notification?=null
    var navigationDrawerIconList: ArrayList<String> = arrayListOf()
    var Images_for_drawer = intArrayOf(R.drawable.navigation_allsongs, R.drawable.navigation_favorites, R.drawable.navigation_settings,
            R.drawable.navigation_aboutus)



    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        MainActivity.statified.drawerLayout = findViewById(R.id.drawer_layout)
        navigationDrawerIconList.add("All Songs")
        navigationDrawerIconList.add("Favorites")
        navigationDrawerIconList.add("Settings")
        navigationDrawerIconList.add("About Us")

        val toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MainActivity.statified.drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()
        val mainScreenFragment = MainScreenFragment()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment, mainScreenFragment, "Main Screen Fragment")
                .commit()
        var _navigationAdapter = NavigationDrawerAdapter(navigationDrawerIconList, Images_for_drawer, this)
        _navigationAdapter.notifyDataSetChanged()

        var navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = _navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)

        val intent =Intent(this@MainActivity,MainActivity::class.java)
        val pintent=PendingIntent.getActivity(this@MainActivity,System.currentTimeMillis().toInt(),
                intent,0)
        trackNotificationBar=Notification.Builder(this)
                .setContentTitle("A track is playing in background")
                .setSmallIcon(R.drawable.echo_logo)
                .setContentIntent(pintent)
                .setOngoing(true)
                .setAutoCancel(true)

                .build()
        statified.notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    }

    override fun onStart() {
        super.onStart()
        try {
            statified.notificationManager?.cancel(1978)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onStop() {
        super.onStop()
        try {
            if (SongPlayingFragment.Statified.mediaplayer?.isPlaying as Boolean){
                statified.notificationManager?.notify(1978, trackNotificationBar)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            statified.notificationManager?.cancel(1978)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}
