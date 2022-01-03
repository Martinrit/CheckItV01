package de.ritterweb.checkitv01.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import de.ritterweb.checkitv01.R
import kotlinx.android.synthetic.*
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import de.ritterweb.checkitv01.databinding.FragmentLoginBinding
import de.ritterweb.checkitv01.databinding.TestDialogBinding
import de.ritterweb.checkitv01.repository.database.Ckl
import de.ritterweb.checkitv01.ui.main.MainViewModel
import de.ritterweb.checkitv01.ui.main.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class DialogTest : DialogFragment(R.layout.dialog_clk_input) {


    //private val args: DialogTestArgs by navArgs()  //private var args: LoginFragment ist eine autogenerierte Classe.
    // Bei erstemaliger Anwendung vor dem Weiterschreiben Rebuild Projekt drücken
    // wenn navArgs() nicht erkannt wird, die ganze Zeile nochmal neu schreiben nach dem Rebuild


    //private var args: LoginFragment ist eine autogenerierte Classe.
    // Bei erstemaliger Anwendung vor dem Weiterschreiben Rebuild Projekt drücken

    private var _binding: TestDialogBinding? = null

    private val args: DialogTestArgs by navArgs()
    private lateinit var mCkl: Ckl

    private lateinit var mainViewModel: MainViewModel

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): DialogTest {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = DialogTest()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infl = inflater
        _binding = TestDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)


        if (args.ckl != null) {
            mCkl = args.ckl!!
        }

        binding.btnAbort.setOnClickListener() {

            val action = DialogTestDirections.actionDialogTestToHomeFragment()
            findNavController().navigate(action)
        }

        binding.btnSave.setOnClickListener() {

            saveData()

        }

        return view

        //return inflater.inflate(R.layout.dialog_fragement_ckl_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (mCkl != null) {
            binding.etinputName.setText(mCkl.name)
            binding.etInputBeschreibung.setText(mCkl.beschreibung)
        }

    }


    private fun saveData() {

        if (!TextUtils.isEmpty(binding.etinputName.editableText)&& !TextUtils.isEmpty(binding.etInputBeschreibung.editableText))
        {
            if (args.ckl != null) {
                mCkl?.name = binding.etName.editText?.text.toString()
                mCkl?.beschreibung = binding.etBeschreibung.editText?.text.toString()
                mainViewModel.updateCkl(mCkl!!)
                Toast.makeText(requireContext(), "Ckl updated in Database", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mainViewModel.insertCkl(
                    binding.etName.editText?.text.toString(),
                    binding.etBeschreibung.editText?.text.toString()
                )

                Toast.makeText(requireContext(), "Voc inserted in Database", Toast.LENGTH_SHORT)
                    .show()
            }

            val action = DialogTestDirections.actionDialogTestToHomeFragment()
            findNavController().navigate(action)
        }
        else
        {
            Toast.makeText(
                requireContext(),
                "Please insert data in both Fields!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}