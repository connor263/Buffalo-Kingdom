package com.tencent.igs.data.game.model

import androidx.annotation.DrawableRes
import com.tencent.igs.utils.game.TETRIS_COLUMN_SIZE
import com.tencent.igs.utils.game.TETRIS_ROW_SIZE

data class TetrisBlock(
    var x: Int = TETRIS_COLUMN_SIZE / 2,
    var y: Int = TETRIS_ROW_SIZE,
    var moveY: Int = 0,
    var type: Int = 0,
    @DrawableRes var drawable: Int,
    var isEmpty: Boolean = false,
)
