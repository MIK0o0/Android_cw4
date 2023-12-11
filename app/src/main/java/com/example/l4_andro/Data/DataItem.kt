package com.example.l4_andro.Data;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random
import kotlin.random.nextInt

val humanoids = arrayOf("Bird", "Fish", "Mammal")
@Entity(tableName = "item_table")
class DataItem {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "name")
    var text_name : String = "Default aniamal name"
    @ColumnInfo(name = "spec")
    var text_spec : String = "Default specification"
    @ColumnInfo(name = "strength")
    var item_strength : Int = Random.nextInt(0, 100)
    @ColumnInfo(name = "item_type")
    var item_type : String = humanoids[Random.nextInt(0, 3)]
    @ColumnInfo(name = "is_dangerous")
    var dangerous : Boolean = Random.nextBoolean()

    constructor()
    constructor(num: Int) : this() {
        text_name = "Default animal name "+num
    }
    constructor(name: String, spec:String, strength:Int, type:String, danger:Boolean) : this() {
        text_name = name
        text_spec = spec
        item_strength = strength
        item_type = type
        dangerous = danger

    }

    fun setName(name: String) {
        this.text_name = name
    }
    fun setSpec(spec: String) {
        this.text_spec = spec
    }
    fun setStrength(strength: Int) {
        this.item_strength = strength
    }
    fun setType(type: String) {
        this.item_type = type
    }
    fun setDanger(danger: Boolean) {
        this.dangerous = danger
    }

    override fun toString(): String {
        return "DataItem(id=$id, text_name='$text_name', text_spec='$text_spec', item_strength=$item_strength, item_type='$item_type', dangerous=$dangerous)"
    }
}




