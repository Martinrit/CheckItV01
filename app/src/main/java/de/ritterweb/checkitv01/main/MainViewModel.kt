package de.ritterweb.checkitv01.main

import android.app.Application
import android.app.StatusBarManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import de.ritterweb.checkitv01.repository.repository.AppRepository
import de.ritterweb.checkitv01.repository.database.Ckl
import de.ritterweb.checkitv01.repository.database.CklGrp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    ////////////////////////////////////////////////////////////
    // Repository:
    private val repository = AppRepository(application)
    private var liveCklList = repository.getLiveDataCkls()
    private var liveCklGrpList = repository.getLiveDataCklGrps()

    ////////////////////////////////////////////////////////////
    // Methods to interact with the repository:
    fun insertCkl(name: String, beschreibung: String, datum:String, status:Int,orderNr:Int) {
        viewModelScope.launch {
            val ckl = Ckl(0L, name, beschreibung, datum, status,orderNr)
            repository.insertCkl(ckl)
        }
    }

    fun updateCkl(ckl: Ckl) {
        viewModelScope.launch {
            repository.updateCkl(ckl)
        }
    }

    fun deleteCkl(ckl: Ckl) {
        viewModelScope.launch {
            repository.deleteCkl(ckl)
        }
    }

    fun deleteAllCkl() {
        viewModelScope.launch {
            repository.deleteAllCkl()
        }
    }

    fun getCklById(cklId: Long): Ckl? {
        var ckl: Ckl? = null
        viewModelScope.launch {
            ckl = repository.getClkByID(cklId)
        }

        return ckl
    }

    fun getAllCkls(): List<Ckl>? {
        var ckls: List<Ckl>? = null
        viewModelScope.launch {
            ckls = repository.getAllCkls()
        }
        return ckls
    }

    /////////////////////////////////////////////////////////////
    // Getters for LiveData
    // Hier wird die oben in der Variable Liste liveCklList bei Aufruf der Getterfunktion GetLiveCkl überwiesen
    fun getLiveCkls(): LiveData<List<Ckl>> = liveCklList


    ////////////////////////////////////////////////////////////
    /// Funktionen für cklGrp


    fun insertCklGrp(name: String, beschreibung: String) {
        viewModelScope.launch {
            val cklGrp = CklGrp(0L, name, beschreibung)
            repository.insertCklGrp(cklGrp)
        }
    }

    fun updateCklGrp(cklGrp: CklGrp) {
        viewModelScope.launch {
            repository.updateCklGrp(cklGrp)
        }
    }

    fun deleteCklGrp(cklGrp: CklGrp) {
        viewModelScope.launch {
            repository.deleteCklGrp(cklGrp)
        }
    }

    fun getCklGrpById(cklId: Long): CklGrp? {
        var cklGrp: CklGrp? = null
        viewModelScope.launch {
            cklGrp = repository.getClkGrpByID(cklId)
        }

        return cklGrp
    }

    fun getAllCklGrps(): List<CklGrp>? {
        var cklGrps: List<CklGrp>? = null
        viewModelScope.launch {
            cklGrps = repository.getAllCklGrps()
        }
        return cklGrps
    }

    ////////////////////////////////////////////////////////////
    // Getters for LiveData
    // Hier wird die oben in der Variable Liste liveCklGrpList bei Aufruf der Getterfunktion GetLiveCklGrp überwiesen


    fun getLiveCklGrps(): LiveData<List<CklGrp>> = liveCklGrpList


    ////////////////////////////////////////////////////////////
    // Utils
    private fun Date.toStringFormat(pattern: String = "dd.MM.yyyy"): String {
        return SimpleDateFormat(pattern).format(this)
    }
}