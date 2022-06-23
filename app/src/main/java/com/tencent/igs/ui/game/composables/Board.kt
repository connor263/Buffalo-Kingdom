package com.tencent.igs.ui.game.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tencent.igs.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.tencent.igs.ui.game.GameViewModel
import com.tencent.igs.utils.game.TETRIS_COLUMN_SIZE

@Composable
fun Board(viewModel: GameViewModel = hiltViewModel()) {
    val blocks = viewModel.listOfTetrisBlock

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            repeat(TETRIS_COLUMN_SIZE) { columnIndex ->
                LazyColumn(
                    reverseLayout = true,
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1 / TETRIS_COLUMN_SIZE.toFloat())
                ) {
                    val columnBlocks = blocks.filter { it.x == columnIndex }

                    items(columnBlocks) {
                        Image(
                            modifier = Modifier
                                .size(50.dp),
                            painter = if (it.isEmpty) {
                                painterResource(id = R.drawable.decal_8)
                            } else {
                                painterResource(id = it.drawable)
                            },
                            alpha = if (it.isEmpty) 0F else 1F,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    FocusBlock()
}