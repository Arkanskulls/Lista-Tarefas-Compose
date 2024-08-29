package com.gabriel.listatarecompose.componentes

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Botao(
    onClick: () -> Unit,
    modifier: Modifier,
    texto: String

){
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,  // Usando Color.Blue para o fundo
            contentColor = Color.White    // Cor do texto
        )
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }

}