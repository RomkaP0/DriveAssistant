package com.romka_po.driveassistant.utils

import com.romka_po.driveassistant.R

class GetIcon {
    fun getCheckIcon(string: String): Int {
        return when (string){
            "Antifreeze"-> R.drawable.ic_antifreeze
            "Battery"-> R.drawable.ic_battery
            "Break"-> R.drawable.ic_break
            "Candle"-> R.drawable.ic_candle
            "Engine"-> R.drawable.ic_engine
            "Filter"-> R.drawable.ic_filter
            "Oil"-> R.drawable.ic_oil
            "Oil_filter"-> R.drawable.ic_oil_filter
            "Grm"-> R.drawable.ic_grm
            "Piston"-> R.drawable.ic_piston
            else -> { R.drawable.ic_battery}
        }
    }
}