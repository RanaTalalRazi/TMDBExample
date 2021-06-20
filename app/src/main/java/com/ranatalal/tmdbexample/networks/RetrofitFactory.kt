package  com.ranatalal.tmdbexample.networks

import android.util.Log
import com.google.gson.GsonBuilder
import com.ranatalal.tmdbexample.BuildConfig
import com.ranatalal.tmdbexample.networks.apiInterface.RetrofitService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    var BASE_URL = "https://api.themoviedb.org/3/"

    fun getClient(): RetrofitService {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val httpClient: OkHttpClient? = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/json")
                builder.addHeader("Accept", "application/json")

                chain.proceed(builder.build())
            })
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            ).client(httpClient!!)
            .build()
        return retrofit.create(RetrofitService::class.java)
    }

}