// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File


@Composable
@Preview
fun App()
{
    val dir: String = System.getProperty("user.dir")
    val sdkPatg = "$dir/sdk_path.txt"
    val ipPatg = "$dir/ip.txt"
    var sdkFile = File(sdkPatg)
    var ipFile = File(ipPatg)

    if (!ipFile.isFile) ipFile.createNewFile()
    if (!sdkFile.isFile) sdkFile.createNewFile()


    val defoltColor = Color(51,222,132, 255)
    val defoltDarkColor = Color(22,139,78, 255)

    var sdkText by remember { mutableStateOf(sdkFile.readText()) }
    var ipText by remember { mutableStateOf(ipFile.readText()) }
    var portText by remember { mutableStateOf("") }

    MaterialTheme {
        Column {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                value = sdkText,
                onValueChange = {
                    sdkText = it
                    sdkFile.writeText(it)
                                },
                label = { Text("Путь к SDK") },
                colors = TextFieldDefaults.textFieldColors(focusedLabelColor =  defoltDarkColor, focusedIndicatorColor = defoltColor, cursorColor = defoltDarkColor)

            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                value = ipText,
                onValueChange = {
                    ipText = it
                    ipFile.writeText(it)
                                },
                label = { Text("IP") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(focusedLabelColor =  defoltDarkColor, focusedIndicatorColor = defoltColor, cursorColor = defoltDarkColor)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                value = portText,
                onValueChange = { portText = it },
                label = { Text("Port") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(focusedLabelColor =  defoltDarkColor, focusedIndicatorColor = defoltColor, cursorColor = defoltDarkColor)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                onClick = {
                    Runtime
                        .getRuntime()
                        .exec("$sdkText\\adb connect $ipText:$portText")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = defoltColor)
            )
            {
                Text("подключить")
            }

    }
    }
}

fun main() = application {
    Window(
        icon = painterResource("android_icon.png"),
        onCloseRequest = ::exitApplication,
        title = "WiFi android ADB",
        state = rememberWindowState(width = 500.dp, height = 340.dp)
    ) {
        App()
    }
}