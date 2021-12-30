package de.ritterweb.checkitv01.repository.repository

import android.app.Application
import androidx.lifecycle.LiveData
import de.ritterweb.checkitv01.repository.database.Ckl
import de.ritterweb.checkitv01.repository.database.CklGrp
import de.ritterweb.checkitv01.repository.database.CklDatabase
import de.ritterweb.checkitv01.repository.database.CklDbDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppRepository(application: Application) {
    private val cklDao: CklDbDao

    init {
        val db = CklDatabase.createInstance(application)
        cklDao = db.cklDao
    }

    // Implement all methods


    suspend fun insertCkl(ckl: Ckl) {
        withContext(Dispatchers.IO)
        {
            cklDao.insertCkl(ckl)
        }

    }

    suspend fun updateCkl(ckl: Ckl) {
        withContext(Dispatchers.IO)
        {
            cklDao.updateCkl(ckl)

        }

    }

    suspend fun deleteCkl(ckl: Ckl) {
        withContext(Dispatchers.IO)
        {
            cklDao.deleteCkl(ckl)

        }

    }

    suspend fun getClkByID(cklID: Long): Ckl? {
        var ckl: Ckl? = null
        withContext(Dispatchers.IO)
        {
            ckl = cklDao.getCklById(cklID)
        }
        return ckl
    }

    suspend fun getAllCkls(): List<Ckl>? {
        var ckls: List<Ckl>? = null
        withContext(Dispatchers.IO)
        {
            ckls = cklDao.getAllCkls()
        }
        return ckls
    }


    fun getLiveDataCkls(): LiveData<List<Ckl>> {
        return cklDao.getLiveDataCklList()
    }


    //////////////////////////////////////////
    // Hier für die CklGrps

    suspend fun insertCklGrp(cklGrp: CklGrp) {
        withContext(Dispatchers.IO)
        {
            cklDao.insertCklGrp(cklGrp)
        }

    }

    suspend fun updateCklGrp(cklGrp: CklGrp) {
        withContext(Dispatchers.IO)
        {
            cklDao.updateCklGrp(cklGrp)

        }

    }

    suspend fun deleteCklGrp(cklGrp: CklGrp) {
        withContext(Dispatchers.IO)
        {
            cklDao.deleteCklGrp(cklGrp)

        }

    }

    suspend fun getClkGrpByID(cklGrpID: Long): CklGrp? {
        var cklGrp: CklGrp? = null
        withContext(Dispatchers.IO)
        {
            cklGrp = cklDao.getCklGrpById(cklGrpID)
        }
        return cklGrp
    }

    suspend fun getAllCklGrps(): List<CklGrp>? {
        var cklGrps: List<CklGrp>? = null
        withContext(Dispatchers.IO)
        {
            cklGrps = cklDao.getAllCklGrpls()
        }
        return cklGrps
    }

    fun getLiveDataCklGrps(): LiveData<List<CklGrp>> {
        return cklDao.getLiveDataCklGrpList()
    }

}

