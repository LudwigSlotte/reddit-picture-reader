package se.slotte.ludwig.redditpicturereader.picture_feed.data.service


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.reddit.com"

object ServiceFactory {

    //Add Interceptor to every response that we make to the server
    private val defaultHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                        .addHeader("Content-type", "application/json").build()
                chain.proceed(request)
            }.build()


    fun createRetrofitService(endPoint: String): Retrofit {

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(endPoint).client(defaultHttpClient)
                .build()
    }
}