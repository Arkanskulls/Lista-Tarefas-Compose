package com.gabriel.listatarecompose.model
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tarefa_table")
data class Tarefa (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tarefa: String? = null,
    val descricao: String? = null,
    val prioridade: Int? = null,
    val imagemUrl: String? // Adicionando um campo para a URL da imagem

)