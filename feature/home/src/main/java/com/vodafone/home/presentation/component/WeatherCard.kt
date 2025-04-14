package com.vodafone.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vodafone.core.R.dimen.corner_radius_md
import com.vodafone.core.R.dimen.padding_md
import com.vodafone.core.R.dimen.padding_sm
import com.vodafone.core.R.dimen.padding_xs
import com.vodafone.core.R.dimen.text_sm
import com.vodafone.core.R.dimen.text_xs
import com.vodafone.core.domain.model.Weather

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weather: Weather,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(corner_radius_md)),
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(padding_md),
                    vertical = dimensionResource(padding_sm)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(padding_xs))
                ) {
                    Text(
                        text = weather.city,
                        fontSize = dimensionResource(text_sm).value.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = weather.date,
                        fontSize = dimensionResource(text_xs).value.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .alpha(0.7f),
                    )
                }
                Text(
                    text = weather.condition,
                    fontSize = dimensionResource(text_sm).value.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .alpha(0.7f),
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                weather.icon?.let {
                    AsyncImage(
                        model = weather.icon,
                        contentScale = ContentScale.Crop,
                        contentDescription = "weather image",
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color.Transparent)
                    )
                }
                Text(
                    text = weather.temperature,
                    fontSize = dimensionResource(text_sm).value.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .alpha(0.7f),
                )
            }
        }
    }
}