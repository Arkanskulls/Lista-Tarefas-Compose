package com.gabriel.listatarecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gabriel.listatarecompose.data.TarefaDao
import com.gabriel.listatarecompose.view.ListaTarefas
import com.gabriel.listatarecompose.view.SalvarTarefa

import com.gabriel.listatarecompose.data.TarefaDatabase // Importe sua classe de banco de dados
import com.gabriel.listatarecompose.ui.theme.ListaTareComposeTheme


class MainActivity : ComponentActivity() {
    private lateinit var tarefaDao: TarefaDao // Declare a variável para o Dao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar o TarefaDao usando o método getDatabase
        tarefaDao = TarefaDatabase.getDatabase(applicationContext).tarefaDao() // Criação da instância do TarefaDao

        setContent {
            ListaTareComposeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "listaTarefas") {
                    // Rota para a tela de lista de tarefas
                    composable(
                        route = "listaTarefas"
                    ) {
                        ListaTarefas(navController, tarefaDao) // Passando o TarefaDao aqui
                    }

                    // Rota para a tela de salvar tarefa, que aceita um parâmetro ID de tarefa
                    composable(
                        route = "SalvarTarefa/{tarefaId}", // Aceita um parâmetro ID de tarefa
                        arguments = listOf(navArgument("tarefaId") { type = NavType.IntType }) // Define o tipo do argumento como Int
                    ) { backStackEntry ->
                        val tarefaId = backStackEntry.arguments?.getInt("tarefaId") // Obtém o ID da tarefa dos argumentos
                        SalvarTarefa(navController, tarefaDao, tarefaId) // Passando o ID da tarefa para SalvarTarefa
                    }

                    // Rota para adicionar uma nova tarefa (sem ID)
                    composable(
                        route = "SalvarTarefa"
                    ) {
                        SalvarTarefa(navController, tarefaDao, null) // Passando null para tarefaId, indicando uma nova tarefa
                    }
                }
            }
        }
    }
}