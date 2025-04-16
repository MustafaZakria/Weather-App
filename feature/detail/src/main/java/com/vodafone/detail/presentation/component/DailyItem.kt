package com.vodafone.detail.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vodafone.core.domain.model.Daily

@Composable
fun DailyItem(daily: Daily, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(com.vodafone.core.R.dimen.padding_sm)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = daily.date,
            fontSize = dimensionResource(com.vodafone.core.R.dimen.text_sm).value.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = daily.icon,
                contentScale = ContentScale.FillBounds,
                contentDescription = "weather image",
                modifier = Modifier.size(50.dp),
            )

            Text(
                text = daily.status,
                fontSize = dimensionResource(com.vodafone.core.R.dimen.text_xs).value.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {

            Text(
                text = daily.maxTemperature,
                fontSize = dimensionResource(com.vodafone.core.R.dimen.text_sm).value.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 5.dp),
            )
            Text(
                text = daily.minTemperature,
                fontSize = dimensionResource(com.vodafone.core.R.dimen.text_sm).value.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}