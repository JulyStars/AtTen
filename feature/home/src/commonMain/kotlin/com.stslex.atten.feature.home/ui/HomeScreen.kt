package com.stslex.atten.feature.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stslex.atten.core.paging.model.PagingConfig
import com.stslex.atten.core.paging.model.PagingUiState
import com.stslex.atten.core.paging.ui.PagingColumn
import com.stslex.atten.core.ui.components.CardWithAnimatedBorder
import com.stslex.atten.core.ui.theme.AppDimension
import com.stslex.atten.core.ui.theme.AppTheme
import com.stslex.atten.feature.home.ui.components.HomeScreenItem
import com.stslex.atten.feature.home.ui.model.TodoUiModel
import com.stslex.atten.feature.home.ui.store.HomeStoreComponent.State
import com.stslex.atten.feature.home.ui.store.ScreenState
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    state: State,
    onItemLongCLick: (id: String) -> Unit,
    onItemClicked: (id: String) -> Unit,
    onLoadNext: () -> Unit,
    onCreateItemClick: () -> Unit,
    onDeleteItemsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        PagingColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = AppDimension.Padding.medium,
                ),
            pagingState = state.paging,
            onLoadNext = onLoadNext
        ) {
            items(
                count = state.paging.items.size,
                key = state.paging.key
            ) { index ->
                state.paging.items.getOrNull(index)?.let { item ->
                    HomeScreenItem(
                        modifier = Modifier.animateItemPlacement(),
                        item = item,
                        onItemClick = onItemClicked,
                        onItemLongClick = onItemLongCLick
                    )
                }
                Spacer(modifier = Modifier.height(AppDimension.Padding.medium))
            }
        }

        val buttonShapeRadius by animateDpAsState(
            targetValue = if (state.selectedItems.isNotEmpty()) {
                AppDimension.Radius.largest
            } else {
                AppDimension.Radius.medium
            }
        )

        CardWithAnimatedBorder(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppDimension.Padding.big)
                .wrapContentSize()
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max),
            onClick = {
                if (state.selectedItems.isNotEmpty()) {
                    onDeleteItemsClick()
                } else {
                    onCreateItemClick()
                }
            },
            isAnimated = state.selectedItems.isNotEmpty(),
            cornerRadius = buttonShapeRadius,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            disableBorderColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            AnimatedContent(
                modifier = Modifier.padding(AppDimension.Padding.big),
                targetState = state.selectedItems.isNotEmpty(),
                transitionSpec = {
                    fadeIn().plus(scaleIn()) togetherWith
                            fadeOut().plus(scaleOut())
                }
            ) { isDeleting ->
                Icon(
                    imageVector = if (isDeleting) {
                        Icons.Filled.Delete
                    } else {
                        Icons.Filled.Create
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
@Preview
internal fun HomeScreenPreview() {
    AppTheme {
        AppTheme {
            HomeScreen(
                state = State(
                    query = "",
                    paging = PagingUiState(
                        items = Array(10) {
                            TodoUiModel(
                                uuid = "UniqueKey $it",
                                title = "Title $it",
                                description = "Description $it",
                                isSelected = false
                            )
                        }.toList().toImmutableList(),
                        hasMore = true,
                        total = 100,
                        config = PagingConfig.DEFAULT
                    ),
                    screen = ScreenState.Content.Data,
                    selectedItems = persistentSetOf()
                ),
                onLoadNext = {},
                onItemClicked = {},
                onCreateItemClick = {},
                onItemLongCLick = {},
                onDeleteItemsClick = {}
            )
        }
    }
}