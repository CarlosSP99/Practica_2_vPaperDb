package com.utad.practica_2_v2.project

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utad.practica_2_v2.databinding.ItemLanguageBinding
import com.utad.practica_2_v2.databinding.ItemProjectBinding
import com.utad.practica_2_v2.languages.Languages

class ProjectViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemProjectBinding.bind(view)

    fun render(project: Project, onClickListener:(Project) -> Unit, onDeleteClickListener:(Project) -> Unit){
        binding.tvTitle.text = project.name
        binding.tvDate.text=project.date.toString()
        binding.tvTime.text=project.timeNeeded.toString()
        binding.tvShortDescription.text=project.shortDescription
        binding.tvPriority.text=project.priority.displayName
        itemView.setOnClickListener{
            onClickListener(project)
        }
        binding.btnDelete.setOnClickListener{
            onDeleteClickListener(project)
        }
    }

}