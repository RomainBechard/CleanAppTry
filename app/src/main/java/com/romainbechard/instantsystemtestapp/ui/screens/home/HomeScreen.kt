package com.romainbechard.instantsystemtestapp.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.romainbechard.instantsystemtestapp.R
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.ui.theme.LightBlue
import com.romainbechard.instantsystemtestapp.ui.theme.Purple200
import com.romainbechard.instantsystemtestapp.ui.theme.Teal200

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val articleList = homeViewModel.articlesList.collectAsState().value
    val errorState = homeViewModel.errorState.collectAsState().value
    val expandedCardIds = homeViewModel.expandedCardIdsList.collectAsState().value
    val subjectList = homeViewModel.subjectList.collectAsState().value
    val selectedIndex = homeViewModel.selectedSubject.collectAsState().value
    val inputText = homeViewModel.input.collectAsState()

    LaunchedEffect(subjectList) {
        if (articleList.isEmpty())
            homeViewModel.getDefaultList()
    }

    LaunchedEffect(selectedIndex) {
        if (selectedIndex == null) {
            homeViewModel.getDefaultList()
        } else {
            homeViewModel.getListFromSubject(subjectList[selectedIndex])
        }
    }

    if (errorState) {
        Text("There was an error occured while retrieving your data")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        SearchWidget(
            onSearch = { homeViewModel.getListFromSubject(null) },
            onValueChange = {
                homeViewModel.setInput(it)
            },
            text = inputText
        )
        LazyRow {
            itemsIndexed(subjectList) { index, item ->
                SubjectCell(
                    itemClicked = { homeViewModel.onSubjectPicked(index) },
                    subject = item,
                    index = index,
                    selectedIndex = selectedIndex
                )
            }
        }
        LazyColumn {
            itemsIndexed(articleList) { index, article ->
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchWidget(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    text: State<String>
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(32.dp)
    ) {
        TextField(
            value = text.value,
            onValueChange = { onValueChange(it) },
            singleLine = true,
            modifier = Modifier
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                onSearch()
            }),
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Image(
                    painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search icon"
                )
            },
            shape = RoundedCornerShape(32.dp)
        )
    }
}

@Composable
fun SubjectCell(
    itemClicked: (Int) -> Unit,
    subject: String,
    index: Int,
    selectedIndex: Int?
) {
    Surface(
        modifier = Modifier
            .clickable(onClick = { itemClicked(index) })
            .padding(8.dp)
            .defaultMinSize(minWidth = 100.dp)
            .animateContentSize(),
        shape = CircleShape,
        color = if (selectedIndex == index) Teal200 else Purple200,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedIndex == index) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_check_24),
                    contentDescription = "Check",
                    Modifier.padding(horizontal = 4.dp)
                )
            }
            Text(
                text = subject,
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}