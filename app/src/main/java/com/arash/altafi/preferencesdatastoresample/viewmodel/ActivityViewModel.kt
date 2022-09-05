package com.arash.altafi.preferencesdatastoresample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arash.altafi.preferencesdatastoresample.Phonebook
import com.arash.altafi.preferencesdatastoresample.repository.ImplRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val implRepository: ImplRepository) :
    ViewModel() {

    var phone: String = ""
    var address: String = ""
    var name: String = ""

    var phonebook: MutableLiveData<Phonebook> = MutableLiveData()
    var isDropPhonebook: MutableLiveData<Boolean> = MutableLiveData()

    fun saveData() {
        viewModelScope.launch(Dispatchers.IO) {
            implRepository.savePhoneBook(
                Phonebook(
                    phone = phone,
                    address = address,
                    name = name
                )
            )
        }
    }

    fun dropData() {
        viewModelScope.launch(Dispatchers.IO) {
            implRepository.dropPhoneBook().collect {
                isDropPhonebook.postValue(it)
            }
        }
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            implRepository.getPhoneBook().collect {
                phonebook.postValue(it)
            }
        }
    }
}