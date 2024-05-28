package com.example.stockmarketapp.data.repository

import android.net.http.HttpException
import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.data.mapper.toCompanyListing
import com.example.stockmarketapp.data.mapper.toCompanyListingEntity
import com.example.stockmarketapp.data.remote.dto.StockApi
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api :StockApi,
    val db :StockDatabase,
    val companyListingParser:CSVParser<CompanyListing>
):StockRepository {
    private val dao =db.dao
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

}