package com.example.stockmarketapp.presentation.companyLisiting

import com.example.stockmarketapp.domain.model.CompanyListing

data class CompanyListingState (
    val companies : List<CompanyListing> = emptyList(),
    val isLoading :Boolean=false,
    val isRefreshing : Boolean=false,
    val searchQuery:String=""
)