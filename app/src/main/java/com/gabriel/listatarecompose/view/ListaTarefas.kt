package com.gabriel.listatarecompose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import com.gabriel.listatarecompose.R
import com.gabriel.listatarecompose.itemDeLista.TarefaItem
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gabriel.listatarecompose.data.TarefaDao
import com.gabriel.listatarecompose.model.Tarefa
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTarefas(
    navController: NavController,
    tarefaDao: TarefaDao // Passando o TarefaDao como parâmetro
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Tarefas",
                        style = TextStyle(fontSize = 18.sp, color = Color.White)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Magenta // Cor do fundo do top bar
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("SalvarTarefa")
                },
                containerColor = Color.Magenta // Cor do botão
            ) {
                // Ícone de adicionar tarefa
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_add), contentDescription = "Salvar tarefa")
            }
        },
        containerColor = Color.Black // Cor do fundo da aplicação
    ) { paddingValues -> // Recebe os valores de padding do Scaffold
        val coroutineScope = rememberCoroutineScope() // Cria um escopo de corrotina para operações assíncronas

        // Observar o Flow do banco de dados
        val listaTarefas by tarefaDao.getAllTarefas().collectAsState(initial = emptyList()) // Coleta de Flow reativo

        // LazyColumn com padding para evitar sobreposição com a TopAppBar
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(), // Adiciona o padding correto no topo
                bottom = 16.dp // Espaço adicional no final para o FAB não sobrepor as tarefas
            )
        ) {
            itemsIndexed(listaTarefas) { position, tarefa ->
                TarefaItem(
                    position = position,
                    tarefa = tarefa,
                    onDelete = { tarefaToDelete ->
                        coroutineScope.launch {
                            // Apagar a tarefa do banco de dados
                            tarefaDao.delete(tarefaToDelete)
                        }
                    },
                    onClick = {
                        // Navegar para a tela de edição, passando a tarefa selecionada
                        navController.navigate("SalvarTarefa/${tarefa.id}")
                    }
                )
            }
        }
    }
}
