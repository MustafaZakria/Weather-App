package com.vodafone.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.vodafone.core.R

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_sm))
    ) {

        IconButton(
            modifier = Modifier.clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_md))),
            onClick = { onBackClick() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
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
}