package com.example.maxcompose.compose2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.maxcompose.retrofit.AuthInterceptor
import com.example.maxcompose.retrofit.MyApi
import com.ramcosta.composedestinations.annotation.Destination
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Composable
@Destination
fun RetrofitAnnotation() {

    LaunchedEffect(Unit) {
        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getUser()
        api.getPost()
    }
}