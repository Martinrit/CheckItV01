package de.ritterweb.checkitv01.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CklDbDao {


    /////////////////////////////////////
    //  alles fpr CKL

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCkl(ckl:Ckl)

    @Delete
    suspend fun deleteCkl(ckl:Ckl)

    @Update
    suspend fun updateCkl(ckl:Ckl)

    @Query("Select * FROM Ckl WHERE id =:cklId")
    suspend fun getCklById(cklId: Long): Ckl

    @Query("Select * FROM Ckl ")
    suspend fun getCklList(): List<Ckl>

    @Query("Select * FROM Ckl ")
    fun getLiveDataCklList(): LiveData<List<Ckl>>

    /////////////////////////////////////
    //  alles fpr CklGrp

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCklGrp(ckl:CklGrp)

    @Delete
    suspend fun deleteCklGrp(ckl:CklGrp)

    @Update
    suspend fun updateCklGrp(ckl:CklGrp)

    @Query("Select * FROM CklGrp WHERE id =:cklId")
    suspend fun getCklGrpById(cklId: Long): CklGrp

    @Query("Select * FROM CklGrp ")
    suspend fun getCklGrplList(): List<CklGrp>

    @Query("Select * FROM CklGrp ")
    fun getLiveDataCklGrpList(): LiveData<List<CklGrp>>





}