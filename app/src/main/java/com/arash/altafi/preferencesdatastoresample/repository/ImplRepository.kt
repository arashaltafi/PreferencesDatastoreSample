package com.arash.altafi.preferencesdatastoresample.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arash.altafi.preferencesdatastoresample.Phonebook
import kotlinx.coroutines.flow.*

class ImplRepository(private val context: Context) : Abstract {

    private val Context.datastore: DataStore<Preferences> by
    preferencesDataStore(name = DataStore_NAME)

    companion object {
        const val DataStore_NAME = "PHONEBOOK"
        val NAME = stringPreferencesKey("NAME")
        val PHONE_NUMBER = stringPreferencesKey("PHONE")
        val address = stringPreferencesKey("ADDRESS")
    }

    override suspend fun savePhoneBook(phonebook: Phonebook) {
        context.datastore.edit { phonebooks ->
            phonebooks[NAME] = phonebook.name ?: ""
            phonebooks[PHONE_NUMBER] = phonebook.phone ?: ""
            phonebooks[address] = phonebook.address ?: ""
        }
    }

    override suspend fun getPhoneBook(): Flow<Phonebook> {
        return context.datastore.data.map { phonebook ->
            Phonebook(
                name = phonebook[NAME],
                address = phonebook[address],
                phone = phonebook[PHONE_NUMBER]
            )
        }
    }

    override suspend fun dropPhoneBook(): Flow<Boolean> {
        context.datastore.edit {
            it.clear()
        }
        return flow {
             context.datastore.data.count() == 0
        }
    }
}