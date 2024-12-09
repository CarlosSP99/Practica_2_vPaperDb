package com.utad.practica_2_v2.languages

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utad.practica_2_v2.databinding.ItemLanguageBinding

class LanguagesViewHolder(view: View):
    RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener {


    val binding = ItemLanguageBinding.bind(view)
    private lateinit var languages: Languages

    fun render(item: Languages, onClickListener:(Languages) -> Unit){
       languages = item
        binding.tvName.text = item.name
        itemView.setOnClickListener {
            onClickListener(item)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.setHeaderTitle(languages.name)
        // Añade opciones al menú contextual
        menu.add(0, 0, 0, "Eliminar") // ID 0
        menu.add(0, 1, 0, "Editar")   // ID 1
    }

}
