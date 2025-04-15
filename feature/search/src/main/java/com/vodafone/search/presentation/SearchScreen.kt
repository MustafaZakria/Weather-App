package com.vodafone.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.R
import com.vodafone.core.domain.model.City
import com.vodafone.core.presentation.components.LoadingIndicator
import com.vodafone.search.presentation.components.CityItem
import com.vodafone.search.presentation.components.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onCityItemClick: (city: City) -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val cityList by viewModel.cityList.collectAsState()
    val cityListLoading by viewModel.cityListLoading.collectAsState()
    val searchValue by viewModel.searchValue.collectAsState()

    SearchScreenContent(
        onCityItemClick = { city ->
//            onCityItemClick(city)
            viewModel.onSelectCity(city)
        },
        onNavigateToHome = onNavigateToHome,
        cityList = cityList,
        cityListLoading = cityListLoading,
        searchValue = searchValue,
        onSearchValueChange = { value ->
            viewModel.onSearchValueChange(value)
        }
    )
}

@Composable
fun SearchScreenContent(
    onCityItemClick: (city: City) -> Unit,
    onNavigateToHome: () -> Unit,
    cityList: List<City>,
    cityListLoading: Boolean,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = dimensionResource(R.dimen.screen_top_padding),
                start = dimensionResource(R.dimen.padding_md),
                end = dimensionResource(R.dimen.padding_md),
                bottom = dimensionResource(R.dimen.padding_md)
            )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_sm))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_sm))
            ) {

                IconButton(
                    modifier = Modifier.clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_md))),
                    onClick = { onNavigateToHome() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = com.vodafone.search.R.drawable.ic_arrow_left),
                        contentDescription = stringResource(com.vodafone.search.R.string.back_button),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                    )
                }

                SearchBar(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_md)))
                        .background(Color.White),
                    hint = stringResource(id = com.vodafone.search.R.string.search_city),
                    searchStateValue = searchValue,
                    onSearchValueChange = onSearchValueChange
                )
            }


            if (cityListLoading) {
                LoadingIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.padding_md))
                        .align(Alignment.CenterHorizontally)
                )
            } else {

                if (cityList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xs)),
                        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_sm))
                    ) {
                        items(cityList) { city ->
                            CityItem(
                                city = city,
                                onItemClick = onCityItemClick,
                                showDivider = city != cityList.last()
                            )
                        }
                    }
                }

                if (cityList.isEmpty()) {
                    Text(
                        text = stringResource(id = com.vodafone.search.R.string.no_result),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = dimensionResource(R.dimen.text_sm).value.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(R.dimen.padding_lg))
                    )
                }
            }
        }
    }
}
