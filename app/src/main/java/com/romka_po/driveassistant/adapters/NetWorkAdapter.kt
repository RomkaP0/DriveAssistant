package com.romka_po.driveassistant.adapters

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetWorkAdapter( private val context: Context) {
        fun isConnected(): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    val nw = connectivityManager.activeNetwork ?: return false
                    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                    when {
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
                else -> {
                    val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                    nwInfo.isConnected
                }
            }
        }
    }
