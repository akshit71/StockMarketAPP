package com.example.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComapnyListings(
        companyListingEntity: List<CompanyListingEntity>
    )

    @Query("Delete FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
           SELECT *
            FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query)==symbol
        """
    )
    suspend fun searchCompanyListing(query:String): List<CompanyListingEntity>
}