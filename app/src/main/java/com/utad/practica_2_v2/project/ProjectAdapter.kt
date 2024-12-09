package com.utad.practica_2_v2.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utad.practica_2_v2.R
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.languages.LanguagesViewHolder

class ProjectAdapter(private var list: List<Project>,
                     private  val onClickListener:(Project) -> Unit): RecyclerView.Adapter<ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProjectViewHolder(layoutInflater.inflate(R.layout.item_project, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = list[position]
        holder.render(item, onClickListener)
     }

    fun submitList(projectList: MutableList<Project>) {
        this.list = projectList
    }
}