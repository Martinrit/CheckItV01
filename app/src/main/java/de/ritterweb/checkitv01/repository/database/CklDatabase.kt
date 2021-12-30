package de.ritterweb.checkitv01.repository.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ckl::class, CklGrp::class],version = 1, exportSchema = false)
abstract class CklDatabase():RoomDatabase()
{

    abstract val cklDao: CklDbDao

    companion object
    {

        @Volatile   // @Volatile erzwingt, dass alle Änderungen direkt im Hauptspeicher gespeichert werden und nicht gecacht.
        // Damit sind Änderungen immer sofort in allen Threads die die DB nutzen sichtbar
        private var INSTANCE: CklDatabase? = null

        fun createInstance(application: Application): CklDatabase
        {
            synchronized(this)  // synchronized stell sicher das immer nur ein Thread eine Instanz erstellen darf.
            // Es kann also immer nur eine Instanz der DB geben
            {
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                        application.applicationContext,
                        CklDatabase::class.java,
                        "ckl_database"
                    )
                        .fallbackToDestructiveMigration()   // Anweisung, wie er sich im Falle eines Versionsupdates der DB verhalten soll
                        .build()
                    INSTANCE = instance
                }
                return instance   // Rückgabe instance, unabhängig ob die DB neu geöffnet wurde oder die vorhandene Instance verwendet wird
            }

        }
    }




}