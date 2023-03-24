package com.romka_po.driveassistant.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romka_po.driveassistant.model.Brand
import com.romka_po.driveassistant.model.Model
import com.romka_po.driveassistant.model.Resource
import com.romka_po.driveassistant.repositories.AutoAPIRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val autoAPIRepository: AutoAPIRepository) : ViewModel() {
    val brands:MutableLiveData<Resource<List<Brand>>> = MutableLiveData()
    val models:MutableLiveData<Resource<List<Model>>> = MutableLiveData()

    fun getBrands() = viewModelScope.launch {
        brands.postValue(Resource.Loading())
        val responce = autoAPIRepository.getBrands()
        brands.postValue(handleInfoCarResponse(responce))
    }
//    fun getModels(brand_name:String) = viewModelScope.launch {
//        models.postValue(Resource.Loading())
//        val responce = autoAPIRepository.getModels(brand_name)
//        models.postValue(handleInfoCarResponse(responce) as Resource<List<Model>>)
//    }

    private fun handleInfoCarResponse(responce: Response<List<Brand>>): Resource<List<Brand>> {
        if (responce.isSuccessful){
            responce.body()?.let { resultResponce ->
                return Resource.Success(resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }


}