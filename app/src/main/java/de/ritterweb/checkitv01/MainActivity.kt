package de.ritterweb.checkitv01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import de.ritterweb.checkitv01.databinding.ActivityMainBinding
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    ///////////////
    //// View Binding in Activity, einfacher als in einem Segment
    private lateinit var binding: ActivityMainBinding
    //////////////////

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ///////////////
        //// View Binding in Activity, einfacher als in einem Segment
        binding = ActivityMainBinding.inflate(layoutInflater)

        //////////////////////////
        // Anfang Toolbar einrichten
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(toolbar)   //// Merkwürdig. Hie muss ich toolbar als Variable verwenden, die aus dem alten BindingVerfahren kommt. eine Verewendung von binding.toolbar führt dazu, dass in der Toolbar nichts angezeigt wird
        setupActionBarWithNavController(navController)
        // Ende Toolbar einrichten
        //////////////////////////

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()    // wenn Navigate upfunktionier , also navController.navigateUp() true ist, wird True zurückgegeben, Andernfalls das Ergbnis des Super.OnNavigate....
                                                                            // siehe Stelle 6:45min in https://www.youtube.com/watch?v=yLOsaR_nDrU&list=PLrnPJCHvNZuCamMFswP597mUF-whXoAA6&index=5
    }
}