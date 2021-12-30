package de.ritterweb.checkitv01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import de.ritterweb.checkitv01.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val args: LoginFragmentArgs by navArgs()  //private var args: LoginFragment ist eine autogenerierte Classe.
                                                      // Bei erstemaliger Anwendung vor dem Weiterschreiben Rebuild Projekt drücken
                                                        // wenn navArgs() nicht erkannt wird, die ganze Zeile nochmal neu schreiben nach dem Rebuild


    //private var args: LoginFragment ist eine autogenerierte Classe.
    // Bei erstemaliger Anwendung vor dem Weiterschreiben Rebuild Projekt drücken

    private var _binding: FragmentLoginBinding? = null



    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(username,password)
            findNavController().navigate(action)
        }
    }
}