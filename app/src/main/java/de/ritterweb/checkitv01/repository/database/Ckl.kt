package de.ritterweb.checkitv01.repository.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Ckl(
    @PrimaryKey(autoGenerate = true) var id:Long,
    var name:String,
    var beschreibung:String,
    var date: String,
    var status:Int
): Parcelable


