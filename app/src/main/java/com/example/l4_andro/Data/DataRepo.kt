package com.example.l4_andro.Data

import android.content.Context

class DataRepo(context: Context) {
    private val LIST_SIZE = 2
    private var dataList: MutableList<DataItem>? = null
    private var myDao: MyDao
    private var db: MyDB

    companion object {
        private var INSTANCE: DataRepo? = null

        fun getInstance(context: Context): DataRepo {
            if (INSTANCE == null) {
                INSTANCE = DataRepo(context)
            }
            return INSTANCE as DataRepo
        }
    }

    init {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!
        dataList = MutableList(LIST_SIZE) { i -> DataItem(i) }
    }

    fun getData() : MutableList<DataItem>? {
        dataList = myDao.getAllData()
        return dataList
    }

    fun addItem(item: DataItem): Boolean {
        return myDao.insert(item) >= 0
    }
    fun deleteItem(item: DataItem): Boolean {
        return myDao.delete(item) > 0
    }

    fun isItemExists(dataItem: DataItem): Boolean {
        val itemId = dataItem?.id ?: return false
        val existingItem = myDao.getItemById(itemId)
        return existingItem != null
    }

    fun getItemById(itemId: Int): DataItem? {
        return myDao.getItemById(itemId)
    }

    fun updateItem(item: DataItem) {
        myDao.update(item)
    }


}