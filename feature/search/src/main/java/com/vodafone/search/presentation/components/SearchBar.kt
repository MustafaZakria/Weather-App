package com.vodafone.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.vodafone.core.R
import com.vodafone.core.presentation.ui.theme.WeatherappTheme

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String,
    searchStateValue: String,
    onSearchValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        TextField(
            value = searchStateValue,
            onValueChange = { value ->
                onSearchValueChange.invoke(value)
            },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(id = com.vodafone.core.R.dimen.corner_radius_md)),
            placeholder = {
                Text(
                    text = hint,
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Composable
@Preview
private fun SearchBarPreview() {
    WeatherappTheme {
        SearchBar(
            modifier = Modifier
                .clip(RoundedCornerShape( dimensionResource(id = R.dimen.corner_radius_md)))
                .background(Color.White),
            hint = "Search for city",
            searchStateValue = "",
            onSearchValueChange = {}
        )
    }
}