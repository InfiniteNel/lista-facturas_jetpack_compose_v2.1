package com.jroslar.listafacturasjetpackcomposev21.data.model


import android.os.Parcel
import android.os.Parcelable
import com.jroslar.listafacturasjetpackcomposev21.data.database.model.FacturaEntity
import kotlinx.serialization.Serializable

@Serializable
data class FacturaModel(
    val descEstado: String,
    val importeOrdenacion: Float,
    val fecha: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(descEstado)
        parcel.writeFloat(importeOrdenacion)
        parcel.writeString(fecha)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FacturaModel> {
        override fun createFromParcel(parcel: Parcel): FacturaModel {
            return FacturaModel(parcel)
        }

        override fun newArray(size: Int): Array<FacturaModel?> {
            return arrayOfNulls(size)
        }
    }
}

fun FacturaModel.toFacturaEntity(): FacturaEntity = FacturaEntity(null, descEstado, importeOrdenacion, fecha)
