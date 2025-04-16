package com.vodafone.detail.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vodafone.core.R.dimen.corner_radius_md
import com.vodafone.core.domain.model.WeatherDetail

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    weatherDetail: WeatherDetail
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(corner_radius_md)),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = weatherDetail.icon,
                contentScale = ContentScale.FillBounds,
                contentDescription = "weather image",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Transparent)
                    .layoutId("weatherIcon"),
            )

            Column {
                Text(
                    text = weatherDetail.temperature,
                    fontSize = dimensionResource(com.vodafone.core.R.dimen.text_lg).value.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = weatherDetail.status,
                    fontSize = dimensionResource(com.vodafone.core.R.dimen.text_md).value.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                )


                Text(
                    text = weatherDetail.date,
                    fontSize = dimensionResource(com.vodafone.core.R.dimen.text_sm).value.sp,
                    color = Color.White,
                    modifier = Modifier
                        .alpha(0.5f)
                )
            }
        }
    }
}