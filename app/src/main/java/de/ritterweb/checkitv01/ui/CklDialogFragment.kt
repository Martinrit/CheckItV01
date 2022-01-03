package de.ritterweb.checkitv01.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import de.ritterweb.checkitv01.R

class CklDialogFragment : DialogFragment(R.layout.dialog_clk_input){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Soll das gemacht werden?")
            .setPositiveButton("OK") { _,_ -> }
            .create()

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}



