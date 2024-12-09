package com.utad.practica_2_v2.languages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utad.practica_2_v2.R

class LanguagesAdapter(
    private var list: List<Languages>,
    private  val onClickListener:(Languages) -> Unit): RecyclerView.Adapter<LanguagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LanguagesViewHolder(layoutInflater.inflate(R.layout.item_language, parent, false))
    }

    override fun onBindViewHolder(holder: LanguagesViewHolder, position: Int) {
        val item = list[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
   }


    fun submitList(languagesList: MutableList<Languages>) {
        this.list = languagesList
    }

    fun setList(lista:MutableList<Languages>){
        notifyItemRangeRemoved(0,list.size)
        this.list=lista
        notifyItemRangeInserted(0,lista.size)
    }

}