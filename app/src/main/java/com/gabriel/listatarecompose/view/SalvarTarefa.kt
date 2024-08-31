package com.gabriel.listatarecompose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabriel.listatarecompose.componentes.Botao
import com.gabriel.listatarecompose.componentes.CaixaDeTexto
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_GREEN_DISABLED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_GREEN_SELECTED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_RED_DISABLED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_RED_SELECTED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_YELLOW_DISABLED
import com.gabriel.listatarecompose.ui.theme.RADIO_BUTTON_YELLOW_SELECTED
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.gabriel.listatarecompose.data.TarefaDao
import com.gabriel.listatarecompose.model.Tarefa
import com.gabriel.listatarecompose.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SalvarTarefa(
    navController: NavController,
    tarefaDao: TarefaDao,
    tarefaId: Int? = null // Parâmetro opcional para o ID da tarefa
) {
    // Estados para armazenar os dados da tarefa
    var tituloTarefa by remember { mutableStateOf("") }
    var descricaoTarefa by remember { mutableStateOf("") }
    var prioridadeSelecionada by remember { mutableStateOf(Prioridade.MEDIA) } // Valor padrão como Média

    // LaunchedEffect para carregar os dados da tarefa existente, se houver
    LaunchedEffect(tarefaId) {
        tarefaId?.let { id ->
            val tarefaExistente = tarefaDao.getTarefaById(id)
            tarefaExistente?.let { tarefa ->
                tituloTarefa = tarefa.tarefa ?: ""
                descricaoTarefa = tarefa.descricao ?: ""
                prioridadeSelecionada = when (tarefa.prioridade) {
                    1 -> Prioridade.BAIXA
                    2 -> Prioridade.MEDIA
                    3 -> Prioridade.ALTA
                    else -> Prioridade.MEDIA
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (tarefaId != null) "Editar Tarefa" else "Nova Tarefa",
                        style = TextStyle(fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Magenta
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(52.dp))
            CaixaDeTexto(
                value = tituloTarefa,
                onValueChange = { tituloTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Título Tarefa",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )
            CaixaDeTexto(
                value = descricaoTarefa,
                onValueChange = { descricaoTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 22.dp, 20.dp, 0.dp),
                label = "Descrição",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = prioridadeSelecionada == Prioridade.BAIXA,
                    onClick = { prioridadeSelecionada = Prioridade.BAIXA },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RADIO_BUTTON_GREEN_DISABLED,
                        selectedColor = RADIO_BUTTON_GREEN_SELECTED
                    )
                )
                Text(text = "Baixa")

                RadioButton(
                    selected = prioridadeSelecionada == Prioridade.MEDIA,
                    onClick = { prioridadeSelecionada = Prioridade.MEDIA },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RADIO_BUTTON_YELLOW_DISABLED,
                        selectedColor = RADIO_BUTTON_YELLOW_SELECTED
                    )
                )
                Text(text = "Média")

                RadioButton(
                    selected = prioridadeSelecionada == Prioridade.ALTA,
                    onClick = { prioridadeSelecionada = Prioridade.ALTA },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RADIO_BUTTON_RED_DISABLED,
                        selectedColor = RADIO_BUTTON_RED_SELECTED
                    )
                )
                Text(text = "Alta")
            }
            Botao(onClick = {
                // Verifique se os campos estão preenchidos e se a prioridade está selecionada
                if (tituloTarefa.isNotBlank() && descricaoTarefa.isNotBlank() && prioridadeSelecionada != null) {
                    val novaTarefa = Tarefa(
                        id = tarefaId ?: 0, // Manter o ID da tarefa se existir
                        tarefa = tituloTarefa,
                        descricao = descricaoTarefa,
                        prioridade = when (prioridadeSelecionada) {
                            Prioridade.BAIXA -> 1
                            Prioridade.MEDIA -> 2
                            Prioridade.ALTA -> 3
                            else -> 2 // Prioridade média como padrão
                        }
                    )

                    // Usar corrotina para realizar operações de banco de dados
                    CoroutineScope(Dispatchers.IO).launch {
                        if (tarefaId == null) {
                            tarefaDao.insert(novaTarefa) // Inserir nova tarefa
                        } else {
                            tarefaDao.update(novaTarefa) // Atualizar tarefa existente
                        }
                    }
                    // Navegar de volta para a lista de tarefas após salvar
                    navController.popBackStack()
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(20.dp),
                texto = "Salvar"
            )
        }
    }
}
enum class Prioridade {
    BAIXA, MEDIA, ALTA
}