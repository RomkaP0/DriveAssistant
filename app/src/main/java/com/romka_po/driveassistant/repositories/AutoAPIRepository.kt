package com.romka_po.driveassistant.repositories

import  com.romka_po.driveassistant.adapters.APIAdapter
import com.romka_po.driveassistant.model.Brand

class AutoAPIRepository() {


    suspend fun getBrands() = APIAdapter.apiClient.getBrands()

    suspend fun getModels(brand_name:String) = APIAdapter.apiClient.getMarks(brand_name)
}