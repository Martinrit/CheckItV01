package de.ritterweb.checkitv01.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.databinding.FragmentHomeBinding
import de.ritterweb.checkitv01.repository.database.Ckl
import de.ritterweb.checkitv01.ui.main.MainViewModel
import de.ritterweb.checkitv01.ui.main.MainViewModelFactory
import kotlinx.android.synthetic.main.dialog_clk_input.view.*

class DialogFragementCklInput(var ckl: Ckl? = null):DialogFragment(R.layout.dialog_clk_input)
{


    private lateinit var rootView:View

    // Buttons:
    private lateinit var btnSave:Button
    private lateinit var btnAbort:Button

    // TextInputLayout
    private lateinit var etName:TextInputLayout
    private lateinit var etBeschreibung:TextInputLayout

    // ViewModel:
    private lateinit var mainViewModel: MainViewModel

    // ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        rootView = binding.root

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity(),
            MainViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)
        initButtons()
        initEditTexts()

        // Check if voc is not null:
        if(ckl != null)
        {
            // setText from the passed voc object
            etName.editText?.setText(ckl?.name)
            etBeschreibung.editText?.setText(ckl?.beschreibung)
        }
    }

    private fun initButtons()
    {
//        btnSave = rootView.findViewById(R.id.dialog_btn_save)
//        btnAbort = rootView.findViewById(R.id.dialog_btn_abort)
//
//        btnSave.setOnClickListener { saveData() }
//        btnAbort.setOnClickListener { dismiss() }
    }

    private fun initEditTexts()
    {
        etName = binding.root.etName
        etBeschreibung = binding.root.etBeschreibung
    }

    private fun saveData()
    {
        if(!TextUtils.isEmpty(etName.editText?.text.toString()) && !TextUtils.isEmpty(etBeschreibung.editText?.text.toString()))
        {
            if(ckl != null)
            {
                ckl?.name = etName.editText?.text.toString()
                ckl?.beschreibung = etBeschreibung.editText?.text.toString()
                mainViewModel.updateCkl(ckl!!)
                Toast.makeText(requireContext(),"Voc updated in Database",Toast.LENGTH_SHORT).show()
            }
            else
            {
                mainViewModel.insertCkl(etName.editText?.text.toString(),etBeschreibung.editText?.text.toString())
                Toast.makeText(requireContext(),"Ckl inserted in Database",Toast.LENGTH_SHORT).show()
            }

            dismiss()
        }
        else
        {
            Toast.makeText(requireContext(),"Please insert data in both Fields!",Toast.LENGTH_SHORT).show()
        }
    }
}