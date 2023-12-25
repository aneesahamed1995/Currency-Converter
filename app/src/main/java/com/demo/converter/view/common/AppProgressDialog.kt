package com.demo.converter.view.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.demo.converter.common.TestTag

@Composable
fun AppProgressDialog(title:String, modifier:Modifier =  Modifier.fillMaxWidth()){
    Dialog(onDismissRequest = { }, DialogProperties(false, false)) {
        Surface(color = MaterialTheme.colors.surface, modifier = modifier.testTag(TestTag.APP_PROGRESS_DIALOG)) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    Modifier
                        .padding(start = 20.dp, end = 16.dp, top = 20.dp, bottom = 20.dp)
                        .size(40.dp),MaterialTheme.colors.primary,4.dp)
                Text(text = title, style = MaterialTheme.typography.subtitle2, color = MaterialTheme.colors.onSurface)
            }
        }
    }
}