package com.cs4520.assignment4.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.cs4520.assignment4.util.constants.Api
import com.cs4520.assignment4.data.api.ProductApi
import com.cs4520.assignment4.data.api.ProductClient
import com.cs4520.assignment4.data.local.ProductDatabase
import com.cs4520.assignment4.data.models.Product
import com.cs4520.assignment4.data.repository.ProductRepository
import com.cs4520.assignment4.data.repository.ProductRepositoryImpl
import com.cs4520.assignment4.util.filterList
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    private val productClient: ProductClient = ProductClient(productApi)

    private val db = Room.databaseBuilder(
        application.applicationContext,
        ProductDatabase::class.java,
        "product_table"
    ).build()

    private val productDao = db.productDao()

    private val repository: ProductRepository = ProductRepositoryImpl(productClient, productDao)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _productListLiveData = MutableLiveData<List<Product>?>()
    val productListLiveData: LiveData<List<Product>?>
        get() = _productListLiveData

    private var _errorMessageLiveData = MutableLiveData<String?>(null)
    val errorMessageLiveData: LiveData<String?>
        get() = _errorMessageLiveData

    private var _pageNumberLiveData = MutableLiveData(1)

    val pageNumberLiveData: LiveData<Int>
        get() = _pageNumberLiveData

    /**
     * fetch products from the repository using coroutine
     * if successful, post product live to live data and add to room database
     * if unsuccessful, display error message
     */
    fun fetchProducts(page: Int) {
        _isLoading.postValue(true)
        _productListLiveData.postValue(null)
        viewModelScope.launch {
            try {
                val response = repository.getProduct(page)

                response?.let {
                    _productListLiveData.postValue(it.filterList())
                } ?: run {
                    _errorMessageLiveData.postValue("Random error occurred in API response")
                }
            } catch (e: Exception){
                _errorMessageLiveData.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun onNextPageClick() {
        _pageNumberLiveData.postValue(pageNumberLiveData.value?.plus(1))
    }

    fun onBackPageClick() {
        _pageNumberLiveData.postValue(_pageNumberLiveData.value?.minus(1))
    }
}