package de.ritterweb.checkitv01.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CklGrp(
    @PrimaryKey(autoGenerate = true) var id : Long,
    var name: String,
    var beschreibung: String
)
