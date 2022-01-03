package de.ritterweb.checkitv01.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import de.ritterweb.checkitv01.NavGraphDirections
import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    ///////////////
    //// View Binding in Activity, einfacher als in einem Segment
    private lateinit var binding: ActivityMainBinding
    //////////////////



    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ///////////////
        //// View Binding in Activity, einfacher als in einem Segment
        binding = ActivityMainBinding.inflate(layoutInflater)
        var mDrawerLayout : DrawerLayout = drawer_layout

        //////////////////////////
        // Anfang Toolbar einrichten
 //       val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.findNavController()
        var id: Int = binding.drawerLayout.id
        appBarConfiguration = AppBarConfiguration(         //  Die Suche wird als Top-Fragment definiert, damit nach der Suche aus dem HomeScreen beim Rücksprung über den Pfeil oben nicht im Homescreen ein Pfeil zurück zu Homescreen angezeigt wird
            setOf(R.id.homeFragment, R.id.searchFragment),   // appBarConfiguration kann in der FOlge zusätlich mit in die Funktionen übergeben werden umd das gewünschte Verhalten zu erreichen
            mDrawerLayout                  // hier wird noch das Menu für das Hamburger Menu übergeben, dass dann auch in der AppBar angezeigt wird
                                                    // Problem: der übergebene Wert drawer_layout ist noch mit Android Extensions gebaut. Übergabe eines binding Elements funktioniert bei mir noch nicht
        )


        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)  // hier wird zusätzlich die AppBar Configuration übergeben, damit sowohl Hoime als auch Search als Top angesehene werden
        // Ende Toolbar einrichten
        //////////////////////////

        //////////////////////////
        // Anfang Botton Menu  einrichten
        binding.bottomNav.setupWithNavController(navController)
     //   binding.bottomNav.setupWithNavController(navController)


        //////////////////////////
        /// Das HamburgerMenu einrichten

        nav_view.setupWithNavController(navController)

        //nav_view.setupWithNavController(navController)
        //binding.navView.setupWithNavController(navController)

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
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()    // wenn Navigate upfunktionier , also navController.navigateUp() true ist, wird True zurückgegeben, Andernfalls das Ergbnis des Super.OnNavigate....
                                                                            // siehe Stelle 6:45min in https://www.youtube.com/watch?v=yLOsaR_nDrU&list=PLrnPJCHvNZuCamMFswP597mUF-whXoAA6&index=5
                                                                            // Damit das Hamburger Menu funktioniert muss navigateUP mit der appBarConfiguration aufgerufen werden. ( Achtung muss importiert werden)
    }
}