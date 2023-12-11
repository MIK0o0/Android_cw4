package com.example.l4_andro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.l4_andro.Data.DataItem
import com.example.l4_andro.Data.DataRepo

class MyViewModel : ViewModel() {
    private val _sharedItem = MutableLiveData<DataItem>()
    private val dataRepo = DataRepo.getInstance()
    val sharedItem: LiveData<DataItem> get() = _sharedItem

    fun updateItem(newItem: DataItem) {
        _sharedItem.value = newItem
    }

    fun saveItem(newItem: DataItem) {
        _sharedItem.value = newItem
        dataRepo.updateItem(newItem)
    }
}