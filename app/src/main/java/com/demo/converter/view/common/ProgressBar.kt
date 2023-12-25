package com.demo.converter.view.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.demo.converter.common.TestTag

@Composable
fun ProgressBar(modifier: Modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = modifier.testTag(TestTag.PROGRESS_LOADER),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}