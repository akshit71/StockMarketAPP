package com.example.stockmarketapp.presentation.companyLisiting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyListingScreen(
    viewModel :CompanyListingVIewModel= hiltViewModel()
){
    val state =viewModel.state

    val pullRefreshState = rememberPullRefreshState(refreshing = state.isRefreshing,
        onRefresh = { viewModel.onEvent(CompanyListingEvent.Refresh) })


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
           OutlinedTextField(
               value =state.searchQuery,
               onValueChange ={
                   viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(it))
               },
               modifier = Modifier
                   .padding(16.dp)
                   .fillMaxWidth(),
               placeholder = {
                   Text(text = "Search...")
               },
               maxLines = 1,
               singleLine = true)

      PullRefreshIndicator(refreshing = state.isRefreshing, state = pullRefreshState)
      LazyColumn(modifier = Modifier.fillMaxSize()){
          items(state.companies.size){
              index ->
              val company =state.companies[index]
              CompanyItem(company =company,
                  modifier = Modifier
                      .fillMaxWidth()
                      .clickable {
                          TODO()
                      }
                      .padding(16.dp))
                if(index <state.companies.size){
                    Divider(modifier = Modifier
                        .padding(horizontal = 16.dp))
                }
          }
      }
    }



}
