package com.example.stockmarketapp.data.remote.dto

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListing(
        @Query(value = "apikey") apikey:String
    ) : ResponseBody

    companion object {
        const val API_KEY = "PP4GL2J2GEW0YBP0"
        const val BASE_URL ="https://alphavantage.co"
    }
}