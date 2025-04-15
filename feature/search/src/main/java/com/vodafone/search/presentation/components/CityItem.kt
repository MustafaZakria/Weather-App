package com.vodafone.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodafone.core.domain.model.City

@Composable
fun CityItem(
    city: City,
    onItemClick: (City) -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "${city.name}, ${city.country}",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = dimensionResource(com.vodafone.core.R.dimen.text_sm).value.sp,
            modifier = Modifier
                .padding(vertical = dimensionResource(com.vodafone.core.R.dimen.padding_xs))
                .fillMaxWidth()
                .clickable { onItemClick(city) }
        )
        if (showDivider) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = dimensionResource(com.vodafone.core.R.dimen.padding_sm))
            )
        }
    }
}