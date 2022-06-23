package com.tencent.igs.ui.game.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tencent.igs.R

@Composable
fun FocusBlockView(type: Int, widthStep: Int, heightStep: Int) {
    when (type) {
        0 -> Image(
            modifier = Modifier.size(widthStep.dp, heightStep.dp),
            painter = painterResource(id = R.drawable.decal_1),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        1 -> {
            Column(
                modifier = Modifier
                    .size(widthStep.dp * 2, heightStep.dp * 2)
            ) {
                repeat(2) {
                    Row(modifier = Modifier.size(widthStep.dp * 2, heightStep.dp)) {
                        repeat(2) {
                            Image(
                                modifier = Modifier
                                    .size(widthStep.dp, heightStep.dp)
                                    .weight(0.5F),
                                painter = painterResource(id = R.drawable.decal_2),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
        2 -> {
            Column(
                modifier = Modifier
                    .size(widthStep.dp * 3, heightStep.dp * 2)
            ) {
                Row(
                    modifier = Modifier.size(widthStep.dp * 3, heightStep.dp)
                ) {
                    repeat(3) {
                        Image(
                            modifier = Modifier
                                .size(widthStep.dp, heightStep.dp)
                                .weight(0.50F),
                            painter = painterResource(id = if (it == 0) R.drawable.decal_8 else R.drawable.decal_3),
                            alpha = if (it == 0) 0F else 1F,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Row(
                    modifier = Modifier.size(widthStep.dp * 3, heightStep.dp)
                ) {
                    repeat(3) {
                        Image(
                            modifier = Modifier
                                .size(widthStep.dp, heightStep.dp)
                                .weight(0.50F),
                            painter = painterResource(id = if (it == 2) R.drawable.decal_8 else R.drawable.decal_3),
                            alpha = if (it == 2) 0F else 1F,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        3 -> {
            Column(
                modifier = Modifier
                    .size(widthStep.dp * 3, heightStep.dp * 2)
            ) {
                Row(
                    modifier = Modifier.size(widthStep.dp * 3, heightStep.dp)
                ) {
                    repeat(3) {
                        Image(
                            modifier = Modifier
                                .size(widthStep.dp, heightStep.dp)
                                .weight(0.50F),
                            painter = painterResource(id = if (it == 2) R.drawable.decal_8 else R.drawable.decal_4),
                            alpha = if (it == 2) 0F else 1F,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Row(
                    modifier = Modifier.size(widthStep.dp * 3, heightStep.dp)
                ) {
                    repeat(3) {
                        Image(
                            modifier = Modifier
                                .size(widthStep.dp, heightStep.dp)
                                .weight(0.50F),
                            painter = painterResource(id = if (it == 0) R.drawable.decal_8 else R.drawable.decal_4),
                            alpha = if (it == 0) 0F else 1F,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        4 -> {
            Column(
                modifier = Modifier
                    .size(widthStep.dp, heightStep.dp * 3),
                verticalArrangement = Arrangement.Top
            ) {
                repeat(3) {
                    Image(
                        modifier = Modifier
                            .size(widthStep.dp, heightStep.dp)
                            .weight(0.35F),
                        painter = painterResource(id = R.drawable.decal_5),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}