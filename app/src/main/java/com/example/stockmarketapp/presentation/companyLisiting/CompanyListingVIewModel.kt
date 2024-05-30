package com.example.stockmarketapp.presentation.companyLisiting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CompanyListingVIewModel @Inject constructor(
    private val repository: StockRepository
) :ViewModel() {

    var state by mutableStateOf(CompanyListingState())
    var searchJob: Job?=null

    init {
        getCompanyListing()
    }
    fun onEvent(event :CompanyListingEvent){
        when(event){
            is CompanyListingEvent.Refresh ->{
             getCompanyListing(fetchFromRemote = true)
            }

            is CompanyListingEvent.OnSearchQueryChange->{
               state= state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob=viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }

            }
        }
    }

    private fun getCompanyListing(fetchFromRemote :Boolean=false,
                                 query:String=state.searchQuery.lowercase(Locale.ROOT)){
        viewModelScope.launch {
            repository.
            getCompanyListings(fetchFromRemote,query)
                .collect{result->
                    when(result){
                        is Resource.Success->{
                            result.data?.let {
                                state=state.copy(companies = it)
                            }
                        }
                        is Resource.Error->{

                        }
                        is Resource.Loading->{

                        }
                    }
                }

        }
    }
}