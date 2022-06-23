package com.tencent.igs.ui.game.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tencent.igs.ui.game.GameViewModel
import com.tencent.igs.utils.game.TETRIS_COLUMN_SIZE
import com.tencent.igs.utils.game.TETRIS_ROW_SIZE

@Composable
fun FocusBlock(viewModel: GameViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val HEIGHT_STEP = remember { (screenHeight.value / TETRIS_ROW_SIZE).toInt() }
    val WIDTH_STEP = remember { (screenWidth.value / TETRIS_COLUMN_SIZE).toInt() }

    val focusBlockIsCreated by remember { viewModel.focusBlockIsCreated }

    var index by remember { mutableStateOf(0) }
    val focusBlockLastX = remember { Animatable(WIDTH_STEP.toFloat() * 3) }
    val focusBlockLastY = remember { Animatable(HEIGHT_STEP * -2F) }

    Box(
        modifier = Modifier
            .size(WIDTH_STEP.dp, HEIGHT_STEP.dp)
            .offset(focusBlockLastX.value.dp, focusBlockLastY.value.dp)

    ) {
        FocusBlockView(
            type = viewModel.currentFocusTetrisBlock.value.type,
            widthStep = WIDTH_STEP,
            heightStep = HEIGHT_STEP
        )
    }


    LaunchedEffect(focusBlockIsCreated) {
        if (focusBlockIsCreated) {
            index++
        } else {
            viewModel.createFocusBlock()
        }
    }

    LaunchedEffect(viewModel.getFocusMove()) {
        if (focusBlockIsCreated && !viewModel.quickMoveDown.value) {
            val blocks = viewModel.listOfTetrisBlock
            val stepWidth = when (viewModel.getFocusMove()) {
                -1 -> focusBlockLastX.value - WIDTH_STEP
                1 -> focusBlockLastX.value + WIDTH_STEP
                else -> return@LaunchedEffect
            }

            val focusBlock = viewModel.currentFocusTetrisBlock.value
            val newColumn = viewModel.getNewColumn()

            val bufferFocusBlockList =
                when (focusBlock.type) {
                    0 -> listOf(focusBlock.copy(x = newColumn))
                    1 -> {
                        listOf(
                            focusBlock.copy(
                                x = newColumn
                            ),
                            focusBlock.copy(
                                x = newColumn,
                                y = focusBlock.y + 1
                            ),
                            focusBlock.copy(
                                x = newColumn + 1,
                            ),
                            focusBlock.copy(
                                x = newColumn + 1,
                                y = focusBlock.y + 1
                            )
                        )
                    }
                    2 -> {
                        listOf(
                            focusBlock.copy(
                                x = newColumn,
                            ),
                            focusBlock.copy(
                                x = newColumn + 1,
                            ),

                            focusBlock.copy(
                                x = newColumn + 1,
                                y = focusBlock.y + 1
                            ),
                            focusBlock.copy(
                                x = newColumn + 2,
                                y = focusBlock.y + 1
                            )
                        )
                    }
                    3 -> {
                        listOf(
                            focusBlock.copy(
                                x = newColumn,
                                y = focusBlock.y + 1
                            ),
                            focusBlock.copy(
                                x = newColumn + 1,
                                y = focusBlock.y + 1
                            ),
                            focusBlock.copy(
                                x = newColumn + 1,
                            ),
                            focusBlock.copy(
                                x = newColumn + 2,
                            ),
                        )
                    }
                    4 -> {
                        listOf(
                            focusBlock.copy(x = newColumn, y = focusBlock.y - 1),
                            focusBlock.copy(x = newColumn, y = focusBlock.y),
                            focusBlock.copy(x = newColumn, y = focusBlock.y + 1)
                        )
                    }
                    else -> return@LaunchedEffect
                }

            var indexValid = 0
            bufferFocusBlockList.forEach { block ->

                if (blocks.any { it.x == block.x && it.y == block.y && !it.isEmpty } ||
                    block.x < 0 ||
                    block.x >= TETRIS_COLUMN_SIZE) {
                    return@LaunchedEffect
                } else {
                    indexValid++
                    if (indexValid != bufferFocusBlockList.count()) return@forEach
                }

                focusBlockLastX.snapTo(stepWidth)
                viewModel.currentFocusTetrisBlock.value.x = newColumn
                viewModel.moveResetSide()
            }
        }
    }

    LaunchedEffect(index) {
        if (focusBlockIsCreated) {
            val stepHeight = focusBlockLastY.value + HEIGHT_STEP
            val result = focusBlockLastY.animateTo(
                targetValue = stepHeight,
                animationSpec = tween(if (viewModel.quickMoveDown.value) 100 else viewModel.moveSpeed)
            )
            when (result.endReason) {
                AnimationEndReason.BoundReached -> {}
                AnimationEndReason.Finished -> {
                    val blocks = viewModel.listOfTetrisBlock
                    val focusBlock = viewModel.currentFocusTetrisBlock.value
                    val nextRow = viewModel.nextMoveDown()

                    val bufferFocusBlockList =
                        when (focusBlock.type) {
                            0 -> listOf(focusBlock.copy(y = nextRow))
                            1 -> listOf(
                                focusBlock.copy(y = nextRow),
                                focusBlock.copy(y = nextRow, x = focusBlock.x + 1)
                            )
                            2 -> listOf(
                                focusBlock.copy(y = nextRow),
                                focusBlock.copy(y = nextRow, x = focusBlock.x + 1),
                                focusBlock.copy(y = nextRow + 1, x = focusBlock.x + 2),
                            )
                            3 -> {
                                listOf(
                                    focusBlock.copy(y = nextRow + 1),
                                    focusBlock.copy(y = nextRow, x = focusBlock.x + 1),
                                    focusBlock.copy(y = nextRow, x = focusBlock.x + 2),
                                )
                            }
                            4 -> listOf(focusBlock.copy(y = nextRow - 1))

                            else -> return@LaunchedEffect
                        }

                    bufferFocusBlockList.forEach { block ->
                        val blocksForColumn =
                            blocks.filter { it.x == block.x }
                                .sortedByDescending { it.y }.filter { it.y <= block.y }

                        val nextBlock = blocksForColumn.firstOrNull()
                        val row = nextBlock?.y ?: 0
                        val isBlank = nextBlock?.isEmpty ?: false

                        if (
                            blocksForColumn.any { it.x == block.x && it.y == block.y && !it.isEmpty } ||
                            (block.y <= row && !isBlank)
                        ) {
                            viewModel.addBlock()

                            viewModel.checkWinRow(block.type, block.y)

                            index = 0
                            focusBlockLastX.snapTo(WIDTH_STEP.toFloat() * 3)
                            focusBlockLastY.snapTo(HEIGHT_STEP * -2F)
                            return@LaunchedEffect
                        }
                    }
                    viewModel.moveDown()
                    index++
                }
            }
        }
    }

    LaunchedEffect(viewModel.quickMoveDown.value) {
        if (viewModel.quickMoveDown.value) {
            index++
        }
    }
}