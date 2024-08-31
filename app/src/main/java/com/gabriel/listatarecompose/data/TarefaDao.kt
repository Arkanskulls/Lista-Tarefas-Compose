package com.gabriel.listatarecompose.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gabriel.listatarecompose.model.Tarefa

@Dao
interface TarefaDao {
    @Insert
    suspend fun insert(tarefa: Tarefa)

    @Update
    suspend fun update(tarefa: Tarefa) // Método para atualizar uma tarefa

    @Delete
    suspend fun delete(tarefa: Tarefa) // Método para deletar uma tarefa

    @Query("SELECT * FROM tarefa_table ORDER BY prioridade DESC")
    fun getAllTarefas(): LiveData<List<Tarefa>>

    @Query("SELECT * FROM tarefa_table WHERE id = :id LIMIT 1")
    suspend fun getTarefaById(id: Int): Tarefa? // Buscar uma tarefa por ID


}