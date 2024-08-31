package com.gabriel.listatarecompose.data
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extensão para criar um DataStore de preferências
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {

    companion object {
        // Chave para armazenar um valor inteiro
        val EXAMPLE_KEY = intPreferencesKey("example_key")
    }

    // Fluxo para observar o valor da chave EXAMPLE_KEY
    val exampleFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[EXAMPLE_KEY] ?: 0 // Retorna 0 se a chave não existir
        }

    // Função para atualizar o valor da chave EXAMPLE_KEY
    suspend fun updateExample(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[EXAMPLE_KEY] = value
        }
    }
}