package com.arash.altafi.preferencesdatastoresample.repository

import com.arash.altafi.preferencesdatastoresample.Phonebook
import kotlinx.coroutines.flow.Flow

interface Abstract {

    suspend fun savePhoneBook(phonebook: Phonebook)

    suspend fun getPhoneBook(): Flow<Phonebook>

    suspend fun dropPhoneBook(): Flow<Boolean>
}