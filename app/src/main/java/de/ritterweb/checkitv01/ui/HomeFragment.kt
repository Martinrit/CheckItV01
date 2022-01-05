package de.ritterweb.checkitv01.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import de.ritterweb.checkitv01.main.MainViewModel
import de.ritterweb.checkitv01.main.MainViewModelFactory
import de.ritterweb.checkitv01.ui.CklAdapter
import kotlin.collections.ArrayList


class HomeFragment : Fragment(R.layout.fragment_home), CklAdapter.OnItemClickListener,
    CklAdapter.OnItemLongClickListener {
    //  Class des Fragments:
    // Die Klasse wird aus Fragment abgeleitet, übergeben wird dabei das xml-Layout des Fragments
    // ebenso wird die KLasse aus dem CklAdapter.OnItemClickListener abgeleitet, der dort nur als Interface angelegt ist und wiederum hier im Homefragement
    // bei der Vewendung implementiert werden muss.

    // Anlegen der Variablen und Objekte die in der ganzen Klasse genutzt werden
    //
    // rootview:  die View der übergeordneten View
    private lateinit var rootView: View

    // Variablem fr die RecyclerView:
    //  der Adapter der Recyclerview
    //  alle Variablen sind als Lateinit definiert, dass bedeutet, dass sie später von Funktionen innerhalb der Klasse initialisiert werden
    //    private lateinit var rv: RecyclerView   wird hier nicht verwendet, weil über ViewBinding erledigt wird.
    private lateinit var adapter: CklAdapter

    // MainViewModel:  Hier die Verbindung zum MainViewModell der Anwendung, inder in drei Stufen
    //     1. Stufe : das MainViewModel  Zugriff auf AppRepository und Verabeitungslogik für die Variablen ( nicht in UI! definieren, damit UI unabhängig von Datenlogik und Berechnungen bleibt)
    //     2. Stufe : das App Repository - Wird vom Main View Model aufgerufen und greift auf die unterschiedlichen Datenquellen zu, u.a. Datenbank
    //     3. Stufe : die DAO der Datenbank
    private lateinit var mainViewModel: MainViewModel

    //////////////////////////////////////////////////////////////////
    // Vorbereitung ViewBinding im Fragement
    // _binding:  Das ist notwendig und entspricht dem Standard beim ViewBinding in einem Fragment. Die Variable bleibt bestehen solang das Fragment besteht.
    // erklärung unter  https://developer.android.com/topic/libraries/view-binding
    // die FragmentHomeBinding für das Segment wird automatisch generiert wenn dies im gradle File so gesetzt ist.
    // Besonderheit beim ViewBinding in Fragments: Da die Fragments auch nach dem Schließen im Hintergrund bestehen bleiben
    // muss die angelegte Instance der xxxBinding Klasse stets beim Schließen des Fragments gelöscht werden
    //
    //Erklärung: zum !! Operator Hiner Binding:
    //    Das Problem ist, dass _binding vom Typ FragementHomeBinding definiert und auf null gesetzt wurde
    //    Da direkt die Binding Variable binding advon abgeleitet wird. Damit das nicht zu einer Fehlermeldung führt wird hier  der !! Operator eingesetzt
    // !! operator  Erklärung in https://kotlinlang.org/docs/null-safety.html#the-operator
    //
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

// Companion Object - Wurde in einem der Tutorials gemacht, Ich lasse das erstmal weg
    // Die Logik, ist, dass zunächst eine Klasse angelegt wird und dann innerhalb der Instand der Klasse gearbeitet wird
    // Ich versuche es mal ohne.
    // Wenn man das wieder einschaltet, werden alle Klassen innerhalb des Companion Objects definiert
    //
//    companion object {
//        /////////////////////////////////////////////////////////
//        // Anlegen eines Companion Objects:
//        // Was das ist, erklärt https://kotlinlang.org/docs/object-declarations.html#object-declarations-overview
//        // Habe ich nocht nicht ganz verstanden.
//
//        fun newInstance() = HomeFragment()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)


        // _binding wird dem FragementxxxxBinding des Fragements gleichgesetzt
        // damit ist dann auch binding gefüllt, dass ja in
        //  der Klasse ganz oben  per
        //              private val binding get() = _binding!!
        // dem _binding entspricht.
        // damit ist auch binding gefüllt und aktiv
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //die Klassenvariable Adapter wird gesetzt
        // Hier wird nur der Typ übergeben, aber nicht der Inhalt
        // ebenso wird dem Adapter der listener übergeben der hier im Fragment definiert ist, dies geschieht mit this
        adapter = CklAdapter(ArrayList(), this, this)

        // der Adapter der Recyclerview wird gesetzt
        // es wird der in der vorherigen Zeile definiete adapter verwendet
        binding.rvHome.adapter = adapter
//        binding.rvHome.adapter = CklAdapter(ArrayList())

        // es wird als LayoutManager für die RecyclingView ein LinearLayoutManagergesetzt. Es gäbe auch andere siehe Doku
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())

        // set has fixedSize= true kann gesetzt werden, wenn alle RV gleich groß sind. dann wird Rechenzeit gespart. Das kann ggf.auch zu einem Problem führen
        // Daher hier auskommentiert. Kann ggf später testweise eingeschaltet werden
        //    binding.rvHome.setHasFixedSize(true)


        rootView = binding.root          //Variable der Klasse wir initialisiert ( lateinit)

        // Das mainViewModel wirt instanziiert
        // Es wird aus der ViewModelProvider-Klasse, die als eigenes Kotlin Class File angelegt wurde generiert
        // Aufruf des ViewModelproviders mit den erforderlichen Paramentern und der Funktion .get(), die das MainViewModel lädt
        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)


        ///////////////////////////////////////////
        // Observer einrichten  und die LiveDatas dem Adapter als Content hinzufügen
        // Es wird ein Observer auf die LiveCkls im MainViewModel gesetzt
        // immer wenn ein Eintrag geändert wurde wird für alle items das ausgeführt was in den geschweiften Klammern steht)
        // noch nicht ganz verstanden: aber wie sollte es anders sein:
        // Es werden die LiveCkls aus dem MainViewModel geladen und ein Observer gesetzt.
        // dabei wird der Content des ArrayLists für jedem item dazugefügt bzw. ergänzt
        //  in der geschweiften Klammer wird gesagt, was geschiet wenn der Observer eine Änderung sieht:
        // dabei werden jedesmal der Inhalt aller items der LiveCkl in der ArrayList geupdatet
        //  die Update-Methode wurde hierzu in der apapterKlasse definiert.
        mainViewModel.getLiveCkls().observe(viewLifecycleOwner, Observer { items ->
            adapter.updateContent(ArrayList(items))
        })

        binding.btnAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDialogTest(null)
            findNavController().navigate(action)
        }


        // Rückgabe der View des HomeFragements - Rückgabe muss bei Fragment gemacht werden.
        // Wenn Aufruf aus einer Activity dann geht das etwas anders, siehe  https://developer.android.com/topic/libraries/view-binding

        return binding.root

    }

    /////////  hier wird die onItemClick Funktion die im Interface des Adapters angelegt ist implementiert.
//  Der Rumpf der Funktion kann durch Drücken von CTRL+'I'  automatisch angelegt werden
//  Denn das Fragment erbt, siehe Klasendefinition ganz oben, nicht nur von Fragement, sondern auch von OnItemClickListener
//  und durch das Vererben wird die Implemtierung von onItemClick zwingend durch das Interface im Adapter vorgeschrieben
    override fun onItemClick(postion: Int) {
        Toast.makeText(requireContext(), "Position $postion wurde geklickt", Toast.LENGTH_LONG)
            .show()
        var selectedCkl = adapter.cklLists[postion]
        val action = HomeFragmentDirections.actionHomeFragmentToDialogTest(selectedCkl)

        findNavController().navigate(action)
    }
    /////////  hier wird die onItemLongClick Funktion die im Interface des Adapters angelegt ist implementiert.
//  Der Rumpf der Funktion kann durch Drücken von CTRL+'I'  automatisch angelegt werden
//  Denn das Fragment erbt, siehe Klasendefinition ganz oben, nicht nur von Fragement, sondern auch von OnItemClickListener
//  und durch das Vererben wird die Implemtierung von onItemClick zwingend durch das Interface im Adapter vorgeschrieben
    override fun onItemLongClick(postion: Int) {
        Toast.makeText(requireContext(), "Position $postion wurde LOOOONG geklickt", Toast.LENGTH_LONG)
            .show()
//        var selectedCkl = adapter.cklLists[postion]
//        val action = HomeFragmentDirections.actionHomeFragmentToDialogTest(selectedCkl)

        //findNavController().navigate(action)
    }

    override fun onDestroyView() {
        // Besonderheit beim ViewBinding in Fragments: Da die Fragments auch nach dem Schließen im Hintergrund bestehen bleiben
        // muss die angelegte Instance der xxxBinding Klasse stets beim Schließen des Fragments gelöscht werden
        super.onDestroyView()
        _binding = null
    }


}