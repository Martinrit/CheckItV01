package de.ritterweb.checkitv01.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import de.ritterweb.checkitv01.R
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.ritterweb.checkitv01.databinding.DialogfragmentInputCklBinding

import de.ritterweb.checkitv01.main.MainViewModel
import de.ritterweb.checkitv01.main.MainViewModelFactory
import de.ritterweb.checkitv01.repository.database.Ckl
import java.text.SimpleDateFormat
import java.util.*

class InputCklFragmentDialog : DialogFragment(R.layout.dialogfragment_input_ckl) {


    //private val args: DialogTestArgs by navArgs()  //private var args: LoginFragment ist eine autogenerierte Classe.
    // Bei erstemaliger Anwendung vor dem Weiterschreiben Rebuild Projekt drücken
    // wenn navArgs() nicht erkannt wird, die ganze Zeile nochmal neu schreiben nach dem Rebuild


    //private var args: LoginFragment ist eine autogenerierte Classe.
    // Bei erstemaliger Anwendung vor dem Weiterschreiben Rebuild Projekt drücken

    private var _binding: DialogfragmentInputCklBinding? = null

    private val args: InputCklFragmentDialogArgs by navArgs()
    private lateinit var mCkl: Ckl

    private lateinit var mainViewModel: MainViewModel

    // addCkl  Boolean Variable, die in onCreate auf True gesetzt wird, wenn die in args.ckl übergebene Ckl null ist,
    private  var addCkl:Boolean =false
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infl = inflater
        _binding = DialogfragmentInputCklBinding.inflate(inflater, container, false)
        val view = binding.root

        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)

        // addCkl wird definiert abhänig davon ob eine Ckl übergeben wurde oder nicht
        addCkl = (args.ckl == null)


        binding.btnAbort.setOnClickListener() {

            val action = InputCklFragmentDialogDirections.actionDialogTestToHomeFragment()
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



        if (addCkl) {
            mCkl = Ckl(0, "", "", "heute", 1,0)
            binding.tvDialogTitle.setText("Neue Checkliste anlegen")
        }else{
            mCkl = args.ckl!!
            binding.tvDialogTitle.setText("Checkliste ändern")
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
                    binding.etBeschreibung.editText?.text.toString(),
                    Date().toStringFormat(),0,0)

                Toast.makeText(requireContext(), "Voc inserted in Database", Toast.LENGTH_SHORT)
                    .show()
            }

            val action = InputCklFragmentDialogDirections.actionDialogTestToHomeFragment()
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
    ////////////////////////////////////////////////////////////
    // Utils
    private fun Date.toStringFormat(pattern: String = "dd.MM.yyyy"): String {
        return SimpleDateFormat(pattern).format(this)
    }


}