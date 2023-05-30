package com.denobaba.composeretrofit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denobaba.composeretrofit.model.CryptoModel
import com.denobaba.composeretrofit.service.CryptoApi
import com.denobaba.composeretrofit.ui.theme.ComposeRetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.addAll

//alacağımız link https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRetrofitTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(){
    var cryptomodels= remember { mutableStateListOf<CryptoModel>() }




    val BASE_URL="https://raw.githubusercontent.com/"
    //var cryptomodels : ArrayList<CryptoModel>? = null We cant  do that like this, we must use State Hoisting

    val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build() //retrofiti build ettik

    val service = retrofit.create(CryptoApi::class.java) //serviceyle retrofiti bağladık

    val call =service.GetData()
    call.enqueue(object: Callback<List<CryptoModel>>{
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful){
                response.body()?.let {responseB ->
                    cryptomodels.clear()
                    cryptomodels.addAll(responseB)

                    for (cryptoModel: CryptoModel in cryptomodels){
                        println(cryptoModel.currency)
                        println(cryptoModel.price)
                    }
                }
            }
        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }
    })

    Scaffold(topBar = {DenoBar()}) {
        Mycryptolist(cryptos = cryptomodels)


    }
}

@Composable
fun Mycryptolist(cryptos: List<CryptoModel>){
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cryptos){crypto ->
            CryptoRow(crypto = crypto)



        }
    }
}

@Composable
fun CryptoRow(crypto: CryptoModel){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.surface)) {
        Text(text = crypto.currency, fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Text(text = crypto.price, fontSize = 20.sp, fontWeight = FontWeight.Bold)

    }

}



@Composable
fun DenoBar(){

    TopAppBar {
        Text(text = "Denobababurada")


    }


}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeRetrofitTheme {
        CryptoRow(crypto = CryptoModel("BTC","1232"))
    }
}