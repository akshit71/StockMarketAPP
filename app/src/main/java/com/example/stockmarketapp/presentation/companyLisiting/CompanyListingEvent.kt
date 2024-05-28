package com.example.stockmarketapp.presentation.companyLisiting





sealed interface CompanyListingEvent {
    data object Refresh:CompanyListingEvent
    data class OnSearchQueryChange(val  query: String):CompanyListingEvent

}