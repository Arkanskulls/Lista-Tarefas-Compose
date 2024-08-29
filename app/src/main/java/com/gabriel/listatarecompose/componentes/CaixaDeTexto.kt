package com.gabriel.listatarecompose.componentes


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaixaDeTexto(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    maxLines: Int,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth() // Aplicado o modificador para largura máxima
                    .clip(RoundedCornerShape(10.dp)), // Adicionado o shape de arredondamento,
        label = { Text(text = label) }, // Corrigido para um lambda
        maxLines = maxLines,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Blue
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )



    )
}

/*@Composable
@Preview
fun CaixaDeTextoPreview() {
    CaixaDeTexto(
        value = "Gabriel",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        label = "Descrição",
        maxLines = 1
    )
}*/