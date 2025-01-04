package com.example.ygo_calc.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ygo_calc.R
import com.example.ygo_calc.ui.theme.YGO_CALCTheme
import com.example.ygo_calc.viewmodel.CalcViewModel

@Composable
fun C_CalcPage(
    modifier: Modifier = Modifier,
    viewModel: CalcViewModel = viewModel()
) {
    val context = LocalContext.current

    // 勝敗判定
    if (viewModel.showModal.value) {
        val text = if (viewModel.player1LifePoints.value <= 0) {
            "Player 2 wins!"
        } else {
            "Player 1 wins!"
        }
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Duel End") },
            text = { Text(text) },
            confirmButton = {
                Button(onClick = {
                    viewModel.saveGameResult(context)
                    viewModel.reset()
                }) {
                    Text("OK")
                }
            }
        )
    }

    Card (
        modifier = modifier
            .fillMaxSize(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim)
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            C_PlayerInfo(player = 1, lifePoints = viewModel.player1LifePoints.value, viewModel = viewModel)
            // 空白
            Spacer(modifier = Modifier.weight(1f))
            C_PlayerInfo(player = 2, lifePoints = viewModel.player2LifePoints.value, viewModel = viewModel)
        }
        C_CalcButtons(viewModel = viewModel)
    }
}

// プレイヤー情報
@Composable
fun C_PlayerInfo (
    player: Int,
    lifePoints: Int,
    viewModel: CalcViewModel
){
    Card(
        modifier = Modifier.padding(
            horizontal = 32.dp,
            vertical = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RectangleShape
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // プレイヤー名
            Text(
                text = "Player $player",
                modifier = Modifier
                    .padding(16.dp)
            )
            // ライフポイント
            Text(
                text = lifePoints.toString(),
                fontSize = 32.sp,
            )

            val buttonModifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
                .height(40.dp)
                .width(80.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
                    shape = RoundedCornerShape(16.dp)
                )
            val shape = RoundedCornerShape(16.dp)

            // イコール
            Button(
                onClick = { viewModel.updateLifePoints(player, viewModel.inputNumber.value.toInt()) },
                modifier = buttonModifier,
                shape = shape,
            )
            {
                Text(
                    text = stringResource(R.string.btn_eq)
                )
            }

            // 加算
            Button(
                onClick = { viewModel.updateLifePoints(player, viewModel.inputNumber.value.toInt(), Int::plus) },
                modifier = buttonModifier,
                shape = shape,
            )
            {
                Text(
                    text = stringResource(R.string.btn_add)
                )
            }
            // 減算
            Button(
                onClick = { viewModel.updateLifePoints(player, viewModel.inputNumber.value.toInt(), Int::minus) },
                modifier = buttonModifier,
                shape = shape
            )
            {
                Text(
                    text = stringResource(R.string.btn_sub)
                )
            }
            // 乗算
            Button(
                onClick = { viewModel.updateLifePoints(player, viewModel.inputNumber.value.toInt(), Int::times) },
                modifier = buttonModifier,
                shape = shape
            ) {
                Text(
                    text = stringResource(R.string.btn_mlt)
                )
            }
            // 徐算ボタン
            Button(
                onClick = { viewModel.updateLifePoints(player, viewModel.inputNumber.value.toInt(), Int::div) },
                modifier = buttonModifier,
                shape = shape
            )
            {
                Text(
                    text = stringResource(R.string.btn_div)
                )
            }
        }
    }


}

// 電卓
@Composable
fun C_CalcButtons (viewModel: CalcViewModel){
    val buttons = listOf(
        "7", "8", "9",
        "4", "5", "6",
        "1", "2", "3",
        "0", "back", "clear",
    )

    // 電卓の入力値
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RectangleShape
    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = viewModel.inputNumber.value,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    // 電卓ボタン
    LazyVerticalGrid (
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(16.dp)
    ) {
        items(buttons) { button ->
            Button(
                onClick = {
                    when (button) {
                        "back" -> viewModel.inputNumber.value = viewModel.inputNumber.value.dropLast(1)
                        "clear" -> viewModel.inputNumber.value = "0"
                        else -> {
                            val newInput = viewModel.inputNumber.value + button
                            var newValue = newInput.toLongOrNull()
                            viewModel.addInputNumber(newValue)
                        }
                    }
                },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = button,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    YGO_CALCTheme {
        C_CalcPage()
    }
}