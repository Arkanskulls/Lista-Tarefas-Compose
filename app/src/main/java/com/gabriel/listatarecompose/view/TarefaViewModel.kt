package com.gabriel.listatarecompose.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gabriel.listatarecompose.data.TarefaDao
import com.gabriel.listatarecompose.model.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class TarefaViewModel(private val tarefaDao: TarefaDao) : ViewModel() {

    // Convertendo Flow para LiveData usando asLiveData
    val todasTarefas: Flow<List<Tarefa>> = tarefaDao.getAllTarefas()

    // Função para carregar tarefas (opcional, se você precisar de lógica adicional)
    fun carregarTarefas() {
        viewModelScope.launch {
            // Aqui você pode fazer operações de banco de dados, se necessário
        }
    }
}