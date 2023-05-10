package com.jroslar.listafacturasjetpackcomposev21.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jroslar.listafacturasjetpackcomposev21.data.database.model.FacturaEntity

@Dao
interface FacturasDao {
    @Insert
    fun insertFacturas(facturas: List<FacturaEntity>)
    @Query("DELETE from factura_entity")
    fun clearDataBase()
    @Query("SELECT * from factura_entity")
    fun getFacturas(): List<FacturaEntity>
}