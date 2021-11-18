// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import kotlin.random.Random

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "路線名から駅一覧を出すやつ"
    ) {
        MyApp()
    }
}


@OptIn(ExperimentalGraphicsApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun MyApp() {

    MaterialTheme {
        var inputLineName by remember { mutableStateOf("") }
        var inputStartTag by remember { mutableStateOf("") }
        var inputEndTag by remember { mutableStateOf("") }

        var targetLineName = ""

        Column {
//            路線名検索
            Row(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = inputLineName,
                    onValueChange = { inputLineName = it },
                    label = { Text("路線名") },
                    modifier = Modifier.padding(8.dp),
                    maxLines = 1
                )
            }

//            タグ指定
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                OutlinedTextField(
                    value = inputStartTag,
                    onValueChange = { inputStartTag = it },
                    label = { Text("開始タグ") },
                    modifier = Modifier.padding(8.dp),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = inputEndTag,
                    onValueChange = { inputEndTag = it },
                    label = { Text("終了タグ") },
                    modifier = Modifier.padding(8.dp),
                    maxLines = 1
                )
            }

//            Copyするテキストの例文表示
            Row {
                Text(
                    text = "例： $inputStartTag 調布 $inputEndTag",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }

            val displayFlag = mutableStateOf(true)
            var targetLineCd: String = ""
            var alertFlag by remember { mutableStateOf(false) }

            LazyColumn {
                val pair = Line().getLinesListsFromLineName(inputLineName)

                items(pair.second.size) {
                    if (pair.second[it] != "line_name") {
                        Button(
                            onClick = {
                                displayFlag.value = !displayFlag.value
                                targetLineCd = pair.first[it]
                                targetLineName = pair.second[it]
                                alertFlag = true

//                                    路線名ボタンをクリックしたらクリップボードにコピーする
                                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                                val data = Station().getStationListFromLineCd(targetLineCd)
                                val copyText: MutableList<String> = mutableListOf()
                                data.forEach {
                                    copyText.add(inputStartTag + it + inputEndTag)
                                }
                                clipboard.setContents(StringSelection(copyText.joinToString("\n")), null)
                            },

                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                .fillMaxWidth()
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.hsl(Random.nextFloat() * 360, 0.4F, 0.5F)
                            )
                        ) {
                            Text(
                                text = pair.second[it],
                                color = Color(0xFFeeeeee),
                                style = MaterialTheme.typography.h6,
                            )

                            if (alertFlag) {
                                AlertDialog(
                                    onDismissRequest = { alertFlag = false },
                                    confirmButton = {
                                        Button(onClick = {alertFlag = false}) {
                                            Text("OK")
                                        }
                                    },
                                    text = {
                                        Text(
                                            "${targetLineName}をコピーしました！"
                                        )
                                    },
                                    title = { Text("Dialog")},
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}