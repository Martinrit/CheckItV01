package de.ritterweb.checkitv01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment:Fragment() {

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

      binding.buttonLogin.setOnClickListener{
          val action =HomeFragmentDirections.actionHomeFragmentToLoginFragment()
          findNavController().navigate(action)
      }


    }
}