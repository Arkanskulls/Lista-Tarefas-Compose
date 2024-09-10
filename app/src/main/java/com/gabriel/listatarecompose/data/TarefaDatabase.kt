package com.gabriel.listatarecompose.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabriel.listatarecompose.model.Tarefa

@Database(entities = [Tarefa::class], version = 2, exportSchema = false)
abstract class TarefaDatabase : RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile
        private var INSTANCE: TarefaDatabase? = null

        fun getDatabase(context: Context): TarefaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarefaDatabase::class.java,
                    "tarefa_database"
                )
                    .fallbackToDestructiveMigration() // Para migrar sem precisar escrever um migrador
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}