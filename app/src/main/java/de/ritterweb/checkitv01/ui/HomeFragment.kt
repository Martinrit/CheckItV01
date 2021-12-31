package de.ritterweb.checkitv01.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import de.ritterweb.checkitv01.repository.database.Ckl
import de.ritterweb.checkitv01.ui.main.MainViewModel
import de.ritterweb.checkitv01.ui.main.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(R.layout.fragment_home){
    //  Class des Fragments:
    // Die Klasse wird aus Fragment abgeleitet, übergeben wird dabei das xml-Layout des Fragments

    // Anlegen der Variablen und Objekte die in der ganzen Klasse genutzt werden
    //
    // rootview:  die View der übergeordneten View
    private lateinit var rootView: View

    // Variablem fr die RecyclerView:
    //  1. Objekt für die Recyclerview
    //  2. der Adapter der Recyclerview
    //  alle Variablen sind als Lateinit definiert, dass bedeutet, dass sie später von Funktionen innerhalb der Klasse initialisiert werden
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CklAdapter3

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
    ):View {
        super.onCreate(savedInstanceState)

        // _binding wird dem FragementxxxxBinding des Fragements gleichgesetzt
        // damit ist dann auch binding gefüllt, dass ja in
        //  der Klasse ganz oben  per
        //              private val binding get() = _binding!!
        // dem _binding entspricht.
        // damit ist auch binding gefüllt und aktiv
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root



        binding.rvHome.adapter = CklAdapter3(ArrayList())

        /////////
        rootView = binding.root

        adapter = CklAdapter3(ArrayList())

        binding.rvHome.adapter = adapter


        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)



        ///////////////////////////////////////////
        // Observer einrichten
        // Es wird ein Observer auf die LiveCkls im MainViewModel gesetzt
        // immer wenn ein Eintrag geändert wurde wird für alle items das ausgeführt was in den geschweiften Klammern steht)
        mainViewModel.getLiveCkls().observe(viewLifecycleOwner, Observer { items ->
            adapter.updateContent(ArrayList(items))
        })
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView = view

        rv = rootView.findViewById(R.id.rv_home)

        adapter =  CklAdapter3(ArrayList())
        rv.adapter = adapter

        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)
        mainViewModel.getLiveCkls().observe(viewLifecycleOwner, Observer { items ->
            adapter.updateContent(ArrayList(items))
        })

//        // Implement the Interfaces:
//        adapter.setOnItemClickListener(object:CklAdapter2.OnItemClickListener {
//            override fun setOnItemClickListener(pos: Int) {
//
//                Log.v("asdf", "Click")
//            }
//        })
//
//            binding.buttonLogin.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
//            findNavController().navigate(action)
//        }


    }



    override fun onDestroyView() {
    // Besonderheit beim ViewBinding in Fragments: Da die Fragments auch nach dem Schließen im Hintergrund bestehen bleiben
    // muss die angelegte Instance der xxxBinding Klasse stets beim Schließen des Fragments gelöscht werden
        super.onDestroyView()
        _binding = null
    }

//    override fun setOnItemClickListener(position: Int) {
//        Log.v("asdf","Click")
//    }

}