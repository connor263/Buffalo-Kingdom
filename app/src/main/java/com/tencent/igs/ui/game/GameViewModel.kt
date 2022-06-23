package com.tencent.igs.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tencent.igs.utils.game.TETRIS_COLUMN_SIZE
import com.tencent.igs.utils.game.TETRIS_ROW_SIZE
import com.tencent.igs.utils.game.generateBlock
import com.tencent.igs.R
import com.tencent.igs.data.game.model.TetrisBlock

class GameViewModel : ViewModel() {
    var moveSpeed by mutableStateOf(1000)

    var listOfTetrisBlock = mutableStateListOf<TetrisBlock>()
        private set

    var currentFocusTetrisBlock =
        mutableStateOf(TetrisBlock(drawable = R.drawable.decal_8))
        private set

    var nextFocusTetrisBlock = mutableStateOf(TetrisBlock(drawable = R.drawable.decal_8))
        private set


    var score = mutableStateOf(0)
        private set


    var quickMoveDown = mutableStateOf(false)
        private set

    var focusBlockIsCreated = mutableStateOf(true)
        private set

    var gameOver = mutableStateOf(false)
        private set


    fun addBlock() {
        val block = currentFocusTetrisBlock.value
        when (block.type) {
            0 -> processBlocks(listOf(block.copy(y = block.y)), listOf())

            1 -> {
                val leadBlocks = mutableListOf(block, block.copy(x = block.x + 1))

                val restBlocks =
                    listOf(
                        block.copy(y = block.y + 1),
                        leadBlocks[1].copy(y = block.y + 1)
                    )

                processBlocks(leadBlocks, restBlocks)
            }
            2 -> {
                val leadBlocks = mutableListOf(
                    block,
                    block.copy(x = block.x + 1),
                    block.copy(x = block.x + 2, y = block.y + 1)
                )
                val restBlocks =
                    listOf(
                        block.copy(y = block.y + 1, x = block.x + 1),
                    )
                processBlocks(leadBlocks, restBlocks)
            }
            3 -> {
                val leadBlocks = mutableListOf(
                    block.copy(y = block.y + 1),
                    block.copy(x = block.x + 1),
                    block.copy(x = block.x + 2),

                    )
                val restBlocks =
                    listOf(
                        block.copy(y = block.y + 1, x = block.x + 1),
                    )
                processBlocks(leadBlocks, restBlocks)
            }
            4 -> {
                val leadBlocks = mutableListOf(block.copy(y = block.y - 1))
                val restBlocks =
                    listOf(
                        block.copy(y = block.y + 1),
                        block.copy(y = block.y),
                    )
                processBlocks(leadBlocks, restBlocks)
            }
        }


        focusBlockIsCreated.value = false
        disableQuickMoveDown()
        createFocusBlock()
    }

    private fun processBlocks(leadTetrisBlocks: List<TetrisBlock>, restTetrisBlocks: List<TetrisBlock>) {
        val listToDelete = mutableListOf<TetrisBlock>()
        val resultList = mutableListOf<TetrisBlock>()
        resultList.addAll(leadTetrisBlocks)
        resultList.addAll(restTetrisBlocks)
        if (resultList.any { it.y >= TETRIS_ROW_SIZE }) {
            gameOver.value = true
            return
        }

        resultList.forEach { currentBlock ->
            if (listOfTetrisBlock.any {
                    it.x == currentBlock.x &&
                            it.y == currentBlock.y &&
                            it.isEmpty
                }) {
                val block = listOfTetrisBlock.find {
                    it.x == currentBlock.x &&
                            it.y == currentBlock.y &&
                            it.isEmpty
                }

                val index = listOfTetrisBlock.indexOf(block)
                listOfTetrisBlock.remove(block)
                listOfTetrisBlock.add(index, currentBlock)
                listToDelete.add(currentBlock)
            }
        }
        resultList.removeAll(listToDelete)

        leadTetrisBlocks.forEach { lead ->
            val leadColumn =
                listOfTetrisBlock.filter { it.x == lead.x }.sortedByDescending { it.y }
            val peekRow = leadColumn.firstOrNull()?.y ?: 0

            if (lead.y > peekRow + 1) {
                repeat(lead.y - peekRow - 1) { index ->
                    val emptyBlock = lead.copy(
                        isEmpty = true,
                        y = peekRow + index + 1
                    )
                    if (!listOfTetrisBlock.any { it.x == emptyBlock.x && it.y == emptyBlock.y && it.isEmpty }) {
                        resultList.add(emptyBlock)
                    }
                }
            }
        }
        resultList.sortBy { it.y }
        listOfTetrisBlock.addAll(resultList)
    }

    fun checkWinRow(type: Int, row: Int) {
        val coef = when (type) {
            0 -> 0
            1 -> 1
            2 -> 1
            3 -> 1
            4 -> 3
            else -> 0
        }
        var indexValid = 0
        for (n in row + coef + 1 downTo 0) {
            val blockInRow = listOfTetrisBlock.filter { it.y == n && !it.isEmpty }
            if (blockInRow.count() == TETRIS_COLUMN_SIZE) {
                increaseScore()
                removeBlocks(blockInRow)
                for (i in n + 1..TETRIS_ROW_SIZE) {
                    listOfTetrisBlock.filter { it.y == i }.forEach {
                        it.y--
                    }
                }
                indexValid++
            }
        }
    }

    private fun removeBlocks(list: List<TetrisBlock>) {
        listOfTetrisBlock.removeAll(list)
    }

    fun createFocusBlock() {
        focusBlockIsCreated.value = true
        currentFocusTetrisBlock.value = nextFocusTetrisBlock.value
        currentFocusTetrisBlock.value.drawable = when (currentFocusTetrisBlock.value.type) {
            0 -> R.drawable.decal_1
            1 -> R.drawable.decal_2
            2 -> R.drawable.decal_3
            3 -> R.drawable.decal_4
            4 -> R.drawable.decal_5
            else -> R.drawable.decal_1
        }

        nextFocusTetrisBlock.value = generateBlock()
    }

    private fun increaseScore() {
        score.value += 50
    }

    fun moveLeftSide() {
        currentFocusTetrisBlock.value.moveY = -1
    }

    fun moveResetSide() {
        currentFocusTetrisBlock.value.moveY = 0
    }

    fun moveRightSide() {
        currentFocusTetrisBlock.value.moveY = 1
    }

    fun moveDown() = currentFocusTetrisBlock.value.y--


    fun nextMoveDown() = currentFocusTetrisBlock.value.y - 1

    fun getNewColumn(): Int {
        val focusMove = getFocusMove()
        moveResetSide()
        return currentFocusTetrisBlock.value.x + focusMove
    }


    fun getFocusMove() = currentFocusTetrisBlock.value.moveY


    fun enableQuickMoveDown() {
        quickMoveDown.value = true
    }

    private fun disableQuickMoveDown() {
        quickMoveDown.value = false
    }
}