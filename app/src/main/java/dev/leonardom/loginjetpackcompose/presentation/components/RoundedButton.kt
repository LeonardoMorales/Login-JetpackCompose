package dev.leonardom.loginjetpackcompose.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    displayProgressBar: Boolean = false,
    onClick: () -> Unit
) {
    if(!displayProgressBar) {
        Button(
            modifier = modifier.width(280.dp).height(50.dp),
            onClick = onClick,
            shape = RoundedCornerShape(50),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White
                )
            )
        }
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colors.primary,
            strokeWidth = 6.dp
        )
    }
}