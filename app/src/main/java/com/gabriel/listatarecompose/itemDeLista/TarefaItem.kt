package com.gabriel.listatarecompose.itemDeLista

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.gabriel.listatarecompose.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabriel.listatarecompose.model.Tarefa
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_GREEN_SELECTED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_RED_SELECTED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_YELLOW_SELECTED

@Composable
fun TarefaItem(
    position: Int,
    listaTarefas: MutableList<Tarefa>
) {
    val tituloTarefa = listaTarefas[position].tarefa
    val descricaoTarefa = listaTarefas[position].descricao
    val prioridade = listaTarefas[position].prioridade

    val nivelDePrioridade: String = when(prioridade){
        0 -> "SEM PRIORIDADE"
        1 -> "PRIORIDADE BAIXA"
        2 -> "PRIORIDADE MÉDIA"
        else -> "PRIORIDADE ALTA"
    }

    val color = when(prioridade){
        0 -> Color.Gray
        1 -> RADIO_BUTTON_GREEN_SELECTED
        2 -> RADIO_BUTTON_YELLOW_SELECTED
        else -> RADIO_BUTTON_RED_SELECTED
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Cor de fundo do Card
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = tituloTarefa.toString(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = descricaoTarefa.toString(),
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(color, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = nivelDePrioridade,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { /* Ação ao clicar no botão */ },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Deletar",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun TarefaItemPreview() {
    TarefaItem()
}*/