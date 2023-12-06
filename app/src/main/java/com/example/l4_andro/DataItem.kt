package com.example.l4_andro;

import kotlin.random.Random
import kotlin.random.nextInt

class DataItem {
    val humanoids = arrayOf("Bird", "Fish", "Mammal")

    var text_name : String = "Default aniamal name"
    var text_spec : String = "Default specification"
    var item_strength : Int = Random.nextInt(0, 100)
    var item_type : String = humanoids[Random.nextInt(0, 3)]
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
}




