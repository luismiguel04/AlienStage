package com.example.alienstage

data class Pago(
    val idPago:Int,//ok
    val tipo:String,// back
    val referencia:String,//ok back
    val ubicacion:String,//ok from

    val fecha:String,//ok form
    val hora:String,//OK form
    val duracion:Int,//ok form
    val estatus:String,// ok back

    val cantidad:Int,//ok
    val idRuta:Int,
    val nombreru:String,
    val idServicio:Int,//ok back
    val nombreser:String,//ok back
    val idPaquete:Int,//ok
    val nombrep:String,//ok

    val idWeb:Int,//ok
    val nombre:String,//ok
    val apellido:String,//ok
    val correo:String,//ok

)
