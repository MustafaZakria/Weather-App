package com.vodafone.home.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodafone.home.R

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(
                text = stringResource(id = R.string.hello),
                fontSize = dimensionResource(com.vodafone.core.R.dimen.text_lg).value.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
                maxLines = 1,
            )
            Text(
                text = stringResource(id = R.string.discover_the_weather),
                fontSize = dimensionResource(com.vodafone.core.R.dimen.text_md).value.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                modifier = Modifier,
                maxLines = 1,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable { onSearchClick() },
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "main search",
                modifier = Modifier
                    .padding(all = dimensionResource(com.vodafone.core.R.dimen.padding_xs))
                    .size(dimensionResource(com.vodafone.core.R.dimen.icon_size_standard)),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }


    }

}