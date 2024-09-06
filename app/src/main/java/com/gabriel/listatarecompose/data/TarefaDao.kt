package com.gabriel.listatarecompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gabriel.listatarecompose.model.Tarefa
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {
    @Insert
    suspend fun insert(tarefa: Tarefa) // Método para inserir uma nova tarefa

    @Update
    suspend fun update(tarefa: Tarefa) // Método para atualizar uma tarefa

    @Delete
    suspend fun delete(tarefa: Tarefa) // Método para deletar uma tarefa

    @Query("SELECT * FROM tarefa_table ORDER BY prioridade DESC")
    fun getAllTarefas(): Flow<List<Tarefa>> // Usar Flow para retornar uma lista reativa de tarefas

    @Query("SELECT * FROM tarefa_table WHERE id = :id LIMIT 1")
    suspend fun getTarefaById(id: Int): Tarefa? // Buscar uma tarefa por ID
}