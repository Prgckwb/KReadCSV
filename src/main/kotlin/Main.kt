// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "路線名から駅一覧を出すやつ"
    ) {
        MyApp()
    }
}

@Composable
fun MyApp() {

    MaterialTheme {
        var inputLineName by remember { mutableStateOf("") }
        var inputTag by remember { mutableStateOf("") }

        Column {
            Row(modifier = Modifier.padding(8.dp)) {


                OutlinedTextField(
                    value = inputLineName,
                    onValueChange = { inputLineName = it },
                    label = { Text("路線名") },
                    modifier = Modifier.padding(8.dp)
                )

                OutlinedTextField(
                    value = inputTag,
                    onValueChange = { inputTag = it },
                    label = { Text("タグ") },
                    modifier = Modifier.padding(8.dp)
                )


            }
            val displayFlag = mutableStateOf(true)
            var targetLineCd: String = ""

            LazyColumn {
                val pair = Line().getLinesListsFromLineName(inputLineName)

                if (displayFlag.value) {
                    items(pair.second.size) {
                        Button(
                            onClick = {
                                displayFlag.value = !displayFlag.value
                                targetLineCd = pair.first[it]
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                .fillMaxWidth()
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00c853))
                        ) {
                            Text(text = pair.second[it])
                        }
                    }
                } else {
                    item {
                        Button(onClick = { displayFlag.value = !displayFlag.value }) {
                            Text("もどる", modifier = Modifier.padding(8.dp).clip(CircleShape))
                        }
                        SelectionContainer {
                            Text(
                                text = Station().getStationListFromLineCd(targetLineCd),
                                modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}