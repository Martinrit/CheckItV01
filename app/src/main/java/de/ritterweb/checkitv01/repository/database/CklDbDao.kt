package de.ritterweb.checkitv01.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CklDbDao {


    /////////////////////////////////////
    //  alles fpr CKL

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCkl(ckl: Ckl)

    @Delete
    suspend fun deleteCkl(ckl: Ckl)

    @Update
    suspend fun updateCkl(ckl: Ckl)

    @Query("Select * FROM Ckl WHERE id =:cklId")
    suspend fun getCklById(cklId: Long): Ckl

    @Query("Select * FROM Ckl ")
    suspend fun getAllCkls(): List<Ckl>

    @Query("Select * FROM Ckl")
    fun getLiveDataCklList(): LiveData<List<Ckl>>

    /////////////////////////////////////
    //  alles fpr CklGrp

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCklGrp(cklGrp: CklGrp)

    @Delete
    suspend fun deleteCklGrp(cklGrp: CklGrp)

    @Update
    suspend fun updateCklGrp(cklGrp: CklGrp)

    @Query("Select * FROM CklGrp WHERE id =:cklId")
    suspend fun getCklGrpById(cklId: Long): CklGrp

    @Query("Select * FROM CklGrp ")
    suspend fun getAllCklGrpls(): List<CklGrp>

    @Query("Select * FROM CklGrp ORDER BY name ASC ")
    fun getLiveDataCklGrpList(): LiveData<List<CklGrp>>





}