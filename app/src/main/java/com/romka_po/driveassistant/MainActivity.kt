package com.romka_po.driveassistant

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.DetectedActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.adapters.VKTools
import com.romka_po.driveassistant.databinding.ActivityMainBinding
import com.romka_po.driveassistant.repositories.FBRepository
import com.romka_po.driveassistant.ui.fe.service.BackgroundDetectedActivitiesService
import com.romka_po.driveassistant.ui.fe.utils.Constants


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    lateinit var broadcastReceiver: BroadcastReceiver



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FBRepository(FBTools(), VKTools()).registerVKAuthProvider(this)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_login ||
                destination.id == R.id.navigation_splash ||
                destination.id == R.id.navigation_register
            )
                navView.visibility = View.GONE
            else
                navView.visibility = View.VISIBLE
        }
//        navView.menu[2].c
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)











        requestActivityRecognitionPermission()

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                if (intent.action == Constants.BROADCAST_DETECTED_ACTIVITY) {
                    val type = intent.getIntExtra("type", -1)
                    val confidence = intent.getIntExtra("confidence", 0)
                    provideUserStateOutput(type, confidence)
                }
            }
        }


        binding.btnStartTracking.setOnClickListener {
            startTracking()
        }

        binding.btnStopTracking.setOnClickListener {
            stopTracking()
        }


    }




    private fun startTracking(){
        val intent = Intent(this, BackgroundDetectedActivitiesService::class.java)
        startService(intent)
    }

    private fun stopTracking(){
        val intent = Intent(this, BackgroundDetectedActivitiesService::class.java)
        stopService(intent)
    }

    private fun provideUserStateOutput(type: Int, confidence: Int){
        var label = getString(R.string.activity_unknown)

        when (type) {
            DetectedActivity.IN_VEHICLE -> {
                label = getString(R.string.activity_in_vehicle)
            }
            DetectedActivity.ON_BICYCLE -> {
                label = getString(R.string.activity_on_bicycle)
            }
            DetectedActivity.ON_FOOT -> {
                label = getString(R.string.activity_on_foot)
            }
            DetectedActivity.RUNNING -> {
                label = getString(R.string.activity_running)
            }
            DetectedActivity.STILL -> {
                label = getString(R.string.activity_still)
            }
            DetectedActivity.TILTING -> {
                label = getString(R.string.activity_tilting)
            }
            DetectedActivity.WALKING -> {
                label = getString(R.string.activity_walking)
            }
            DetectedActivity.UNKNOWN -> {
                label = getString(R.string.activity_unknown)
            }
        }

        Log.e("ActivityDetection", "User activity: $label, Confidence: $confidence")

        if(confidence > Constants.CONFIDENCE){
            Toast.makeText(applicationContext, label, Toast.LENGTH_SHORT).show()
//            binding.txtActivity.text = label
//            binding.txtConfidence.text ="Confidence : $confidence"
//            binding.imgActivity.setImageResource(icon)
        }
    }

    private fun requestActivityRecognitionPermission(){
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                android.Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), 101)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            101 -> {
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {
                        val permission = permissions[i]
                        if (android.Manifest.permission.ACTIVITY_RECOGNITION.equals(permission,
                                ignoreCase = true)) {
                            if (grantResults[i] === PackageManager.PERMISSION_GRANTED) {
                                // you now have permission
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
            IntentFilter(Constants.BROADCAST_DETECTED_ACTIVITY)
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
}


