package com.tencent.igs.utils.game

import android.content.Context
import android.media.MediaPlayer
import com.tencent.igs.R
import com.tencent.igs.data.game.model.TetrisBlock
import com.tencent.igs.utils.game.enums.GameSound
import kotlinx.coroutines.NonCancellable.start
import kotlin.random.Random


fun generateBlock(): TetrisBlock {
    val rand = Random(System.nanoTime())
    return TetrisBlock(
        drawable = 0, type = when ((0..15).random(rand)) {
            in 0..3 -> 0
            in 3..6 -> 1
            in 7..10 -> 2
            in 11..12 -> 3
            in 12..14 -> 4
            else -> 0
        }
    )
}