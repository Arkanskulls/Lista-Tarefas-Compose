package com.gabriel.listatarecompose.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel.listatarecompose.data.TarefaDao
import com.gabriel.listatarecompose.model.Tarefa
import kotlinx.coroutines.launch


class TarefaViewModel(private val tarefaDao: TarefaDao) : ViewModel() {

    // LiveData para observar as tarefas
    val todasTarefas: LiveData<List<Tarefa>> = tarefaDao.getAllTarefas()

    // Função para carregar tarefas (opcional, se você precisar de lógica adicional)
    fun carregarTarefas() {
        viewModelScope.launch {
            // Aqui você pode fazer operações de banco de dados, se necessário
        }
    }
}