package com.example.nasaproject.datalayer.api
import com.example.nasaproject.utlities.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofitMoshi = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService{
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date") startDate:String?,
                             @Query("end_date") endDate:String?,
                             @Query("api_key") apiKey:String):String


    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String): PictureOfTheDayNetwork

}



object NasaApi{
    val retrofitServiceAs :NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }

   val retrofitMoshiPic :NasaApiService by lazy {
       retrofitMoshi.create(NasaApiService::class.java)
   }


}