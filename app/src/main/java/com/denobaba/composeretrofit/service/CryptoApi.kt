package com.denobaba.composeretrofit.service

import com.denobaba.composeretrofit.model.CryptoModel
import retrofit2.http.GET

//alacağımız link https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

interface CryptoApi {
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun GetData(): retrofit2.Call<List<CryptoModel>>
}