package de.ritterweb.checkitv01.ui

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.repository.database.Ckl

class CklAdapter(var content: ArrayList<Ckl>) : RecyclerView.Adapter<CklAdapter.ViewHolder>() {

    private val statusDrawables = arrayOf(R.drawable.ic_checklist) //// Anderer ICON Ergänzen!!!!!!

    // Interface:
    private lateinit var mItemListener: OnItemClickListener
    private lateinit var mItemLongListener: OnItemLongClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main, parent, false)
        return ViewHolder(view, mItemListener, mItemLongListener)
    }

    override fun onBindViewHolder(holder: CklAdapter.ViewHolder, position: Int) {
        val ckl = content[position]
        holder.tvName.text = ckl.name
        holder.tvBeschreibung.text = ckl.beschreibung
        holder.icIcon.setImageResource(statusDrawables[ckl.status])
    }

    override fun getItemCount(): Int {
        return content.size
    }


    class ViewHolder(
        itemView: View,
        mItemListener: OnItemClickListener,
        mItemLongListener: OnItemLongClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvBeschreibung: TextView = itemView.findViewById(R.id.tvBeschreibung)
        val icIcon: ImageView = itemView.findViewById(R.id.ivIcon)

        init {
            // Implement simple OnClickListener for each Entry
            itemView.setOnClickListener() {
                mItemListener?.setOnItemClickListener(adapterPosition)
            }

            // Implement simple OnLongClickListener for each Entry:
            itemView.setOnClickListener() {
                mItemLongListener?.setOnItemLongClickListener(adapterPosition)

            }

        }

    }


    fun updateContent(content: ArrayList<Ckl>) {
        this.content = content
        notifyDataSetChanged()
    }


    // OnItemClickListener:
    interface OnItemClickListener
    {
        fun setOnItemClickListener(pos:Int)
    }

    fun setOnItemClickListener(mItemListener:OnItemClickListener)
    {
        this.mItemListener = mItemListener
    }

    // OnLongItemClickListener:
    interface OnItemLongClickListener
    {
        fun setOnItemLongClickListener(pos:Int)
    }
    fun setOnItemLongClickListener(mItemLongListener:OnItemLongClickListener)
    {
        this.mItemLongListener = mItemLongListener
    }

}
