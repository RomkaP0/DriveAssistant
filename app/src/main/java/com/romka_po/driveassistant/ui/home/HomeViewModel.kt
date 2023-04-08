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
    val brand = MutableLiveData<String>()

    fun sendBrand(text: String) {
        brand.value = text
    }

    fun getBrands() = viewModelScope.launch {
        brands.postValue(Resource.Loading())
        val responce = autoAPIRepository.getBrands()
        brands.postValue(handleInfoCarModelResponse(responce))
    }
    fun getModels(brand_name:String) = viewModelScope.launch {
        models.postValue(Resource.Loading())
        val responce = autoAPIRepository.getModels(brand_name)
        models.postValue(handleInfoCarResponse(responce))
    }

    private fun handleInfoCarModelResponse(responce: Response<List<Brand>>): Resource<List<Brand>> {
        if (responce.isSuccessful){
            responce.body()?.let { resultResponce ->
                return Resource.Success(resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }
    private fun handleInfoCarResponse(responce: Response<List<Model>>): Resource<List<Model>> {
        if (responce.isSuccessful){
            responce.body()?.let { resultResponce ->
                return Resource.Success(resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }


}