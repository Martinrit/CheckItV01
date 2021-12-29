package de.ritterweb.checkitv01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)      // Hier wird das Menu in der Toolbar angelegt aus dem xml Layoutfile
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // in dem return if werden zwei Fälle unterschieden:
        // wenn der MenuPunkt termsAndConditions aufgerufen wird ( übergeben im item als ID) wird über eine globalAction, die im Navgraph pber rechtsklick, Add, Global gesetzt wird
        // direkt von jedem Fragment in dieses Fragment navigiert
        ////
        // wenn andere Menupunkte aufgerufen wird über die im NavGraph definierte Navigation gewechselt.


        return if(item.itemId == R.id.termsAndConditions) {
                val action = NavGraphDirections.actionGlobalTermsFragment()
                navController.navigate(action)
            true

        }else  {

             // hier wird die FragmentNavigation ausgewählt, die sich daraus ergibt, was im LayoutFile des Menus als Label gesetzt ist
        // und gleichzeitig im NavGraph als ID für das Fragement gesetzt ist ( Beide Werte müssen identisch sein!
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
                // Diese Syntax führt dazu, dass das Ergebnis von item.onNavDestinationSelected(navController) übergeben wird, wenn es true ist
                // wenn das Ergebnis fals ist, wird das ergebnis von super.onOptionsItemSelected(item) zurückgegeben.
                //in der super.xxx Funktion wird dann ggf. die Fehlerbehandlung im Standard ausgelöst

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()    // wenn Navigate upfunktionier , also navController.navigateUp() true ist, wird True zurückgegeben, Andernfalls das Ergbnis des Super.OnNavigate....
                                                                            // siehe Stelle 6:45min in https://www.youtube.com/watch?v=yLOsaR_nDrU&list=PLrnPJCHvNZuCamMFswP597mUF-whXoAA6&index=5
    }
}