package com.gabriel.listatarecompose.view

import android.annotation.SuppressLint

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import com.gabriel.listatarecompose.R
import com.gabriel.listatarecompose.itemDeLista.TarefaItem
import com.gabriel.listatarecompose.model.Tarefa


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTarefas(
    navController: NavController
){ Scaffold(
    topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Lista de Tarefas",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = androidx.compose.ui.graphics.Color.Magenta // Cor do fundo do top bar
            )
        )
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("SalvarTarefa")
            },
            containerColor = androidx.compose.ui.graphics.Color.Magenta, // Cor do botão

        ) {
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_add), contentDescription = "Salvar tarefa")


        }
    },
    containerColor = androidx.compose.ui.graphics.Color.Black // Cor do fundo da aplicação

) {
    val listaTarefas: MutableList<Tarefa> = mutableListOf(
            Tarefa(
                tarefa = "Jogar bola",
                descricao = "dsadsadasddfas",
                prioridade = 0
            ),
            Tarefa(
                tarefa = "cidade",
                descricao = "dsadsadasddfas",
                prioridade = 1
            ),
            Tarefa(
                tarefa = "cabare",
                descricao = "dsadsadasddfas",
                prioridade = 2
            ),
            Tarefa(
                tarefa = "Bar do chicote",
                descricao = "dsadsadasddfas",
                prioridade = 3
            )
        )
        LazyColumn{
            itemsIndexed(listaTarefas){position, _ ->
                TarefaItem(position = position, listaTarefas = listaTarefas )

            }
        }
    }
}