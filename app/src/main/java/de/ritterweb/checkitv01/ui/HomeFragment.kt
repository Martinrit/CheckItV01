package de.ritterweb.checkitv01.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import de.ritterweb.checkitv01.repository.database.Ckl
import de.ritterweb.checkitv01.ui.main.MainViewModel
import de.ritterweb.checkitv01.ui.main.MainViewModelFactory


class HomeFragment:Fragment(R.layout.fragment_home) {

    private lateinit var rootView:View
    // RecyclerView:
    private lateinit var rv: RecyclerView
    private lateinit var adapter:CklAdapter

    // MainViewModel:
    private lateinit var mainViewModel: MainViewModel


    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView = view

        initRecyclerView()


        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)

        var ckla:Ckl = Ckl(0L,"test", "beschreibtest","datum",0)
        mainViewModel.updateCkl(ckla)
        var ckltest :List<Ckl>? = mainViewModel.getAllCkls()

//        mainViewModel = ViewModelProvider(
//            requireActivity(),
//            MainViewModelFactory(requireActivity().application)
//        ).get(MainViewModel::class.java)

        //       get(MainViewModel::class.java)
        mainViewModel.getLiveCkls().observe(viewLifecycleOwner, Observer { items ->
            adapter.updateContent(ArrayList(items))
        })
        binding.buttonLogin.setOnClickListener{
            val action =HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView()
    {
        rv = rootView.findViewById(R.id.rv_home)
        adapter = CklAdapter(ArrayList())
        rv.adapter = adapter

        // Implement the Interfaces:
        adapter.setOnItemClickListener(object:CklAdapter.OnItemClickListener{
            override fun setOnItemClickListener(pos: Int) {
//                val dialog = DialogInput(adapter.content[pos])
//                dialog.show(parentFragmentManager,"update Voc")
            }

        })

        adapter.setOnItemLongClickListener(object:CklAdapter.OnItemLongClickListener{
            override fun setOnItemLongClickListener(pos: Int) {
//                startAlarmDialog(adapter.content[pos])
            }
        })
    }


}