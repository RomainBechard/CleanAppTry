package com.romainbechard.instantsystemtestapp.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.romainbechard.instantsystemtestapp.R
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.ui.theme.LightBlue
import com.romainbechard.instantsystemtestapp.ui.theme.Purple200

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {

    val list = homeViewModel.articlesList.collectAsState().value
    val errorState = homeViewModel.errorState.collectAsState().value
    val expandedCardIds = homeViewModel.expandedCardIdsList.collectAsState().value

    LaunchedEffect(list) {
        if (list.isEmpty())
            homeViewModel.getArticlesList()
    }
    if (errorState) {
        Text("There was an error occured while retrieving your data")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LazyColumn {
            itemsIndexed(list) { index, article ->
                ExpandableArticleCell(
                    article = article,
                    onClick = { homeViewModel.onItemClicked(index) },
                    isExpanded = expandedCardIds.contains(index),
                    index = index
                )
            }
        }
    }

}

@Composable
fun ExpandableArticleCell(
    article: Article,
    index: Int,
    onClick: (Int) -> Unit = {},
    isExpanded: Boolean
) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp)),
        elevation = 10.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = LightBlue)
                .animateContentSize()
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = "Article related photo",
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onClick(index) },
                alignment = Alignment.Center,
                error = painterResource(id = R.drawable.noimageplaceholder)
            )

            Text(
                text = article.title,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onClick(index) }
            )
            if (isExpanded) {
                Text(
                    text = article.description,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
                Surface(
                    modifier = Modifier
                        .clickable { uriHandler.openUri(article.articleUrl) }
                        .padding(8.dp),
                    shape = CircleShape,
                    color = Purple200
                ) {
                    Text(
                        text = "Voir plus",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}