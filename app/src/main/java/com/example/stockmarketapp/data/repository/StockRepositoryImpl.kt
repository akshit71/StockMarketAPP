package com.example.stockmarketapp.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.data.mapper.toCompanyInfo
import com.example.stockmarketapp.data.mapper.toCompanyListing
import com.example.stockmarketapp.data.mapper.toCompanyListingEntity
import com.example.stockmarketapp.data.remote.StockApi
import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.model.IntraDayInfo
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api : StockApi,
    private val db :StockDatabase,
    private val companyListingParser:CSVParser<CompanyListing>,
    private val intraDayInfoParser:CSVParser<IntraDayInfo>
):StockRepository {
    private val dao = db.dao
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {

        return flow {
            emit(Resource.Loading(true))
            val localListings =dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))

            val isDbEmpty =localListings.isEmpty() && query.isBlank()
            val shouldLoadFromCache=!isDbEmpty && !fetchFromRemote

            if(shouldLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try{
                val response=api.getListing()
                companyListingParser.parse(response.byteStream())
            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let {listings->
                dao.clearCompanyListings()
                dao.insertComapnyListings(
                    listings.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(Resource.Success(listings))
                emit(Resource.Loading(false))

            }

        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompnayInfo(symbol: String): Resource<CompanyInfo> {
       return  try {
            val result=api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
       }catch (e :IOException){
           e.printStackTrace()
           Resource.Error(
               message = "Couldn't load Company info"
           )
       }catch (e:HttpException){
           e.printStackTrace()
           Resource.Error(
               message = "Couldn't load Company info"
           )

       }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
         return try {
            val response = api.getIntraDayInfo(symbol)
            val results =intraDayInfoParser.parse(response.byteStream())
             Resource.Success(results)
         }catch (e :IOException){
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
         }catch (e:HttpException){
             e.printStackTrace()
             Resource.Error(
                 message = "Couldn't load intraday info"
             )

         }
    }

}