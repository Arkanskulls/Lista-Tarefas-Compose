package com.gabriel.listatarecompose.itemDeLista

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.gabriel.listatarecompose.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gabriel.listatarecompose.model.Tarefa
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_GREEN_SELECTED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_RED_SELECTED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_YELLOW_SELECTED

@Composable
fun TarefaItem(
    position: Int,
    tarefa: Tarefa,
    onDelete: (Tarefa) -> Unit, // Função de callback para deletar a tarefa
    onClick: () -> Unit // Função de callback para clicar na tarefa
) {
    val tituloTarefa = tarefa.tarefa ?: "Título não disponível"
    val descricaoTarefa = tarefa.descricao ?: "Descrição não disponível"
    val prioridade = tarefa.prioridade
    val urlImagem = tarefa.imagemUrl

    val nivelDePrioridade: String = when (prioridade) {
        1 -> "PRIORIDADE BAIXA"
        2 -> "PRIORIDADE MÉDIA"
        3 -> "PRIORIDADE ALTA"
        else -> "SEM PRIORIDADE"
    }

    val color = when (prioridade) {
        1 -> RADIO_BUTTON_GREEN_SELECTED
        2 -> RADIO_BUTTON_YELLOW_SELECTED
        3 -> RADIO_BUTTON_RED_SELECTED
        else -> Color.Gray
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() } // Adiciona a funcionalidade de clique
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = tituloTarefa,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = descricaoTarefa,
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
                    onClick = { onDelete(tarefa) }, // Chama a função onDelete ao clicar na lixeira
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Deletar",
                        tint = Color.Red
                    )
                }
            }

            // Adicionando a imagem abaixo dos textos
            if (!urlImagem.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = // Placeholder enquanto carrega
                    rememberAsyncImagePainter(ImageRequest.Builder // Imagem de erro caso a URL falhe
                        (LocalContext.current).data(
                        data = urlImagem // URL da imagem
                    ).apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.loading) // Placeholder enquanto carrega
                        error(R.drawable.error_24) // Imagem de erro caso a URL falhe
                    }).build()
                    ),
                    contentDescription = "Imagem da tarefa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Definindo a altura da imagem
                        .clip(RoundedCornerShape(10.dp)) // Arredondando as bordas da imagem
                )
            }
        }
    }
}
/*@Preview(showBackground = true)
@Composable
fun TarefaItemPreview() {
    TarefaItem()
}*/