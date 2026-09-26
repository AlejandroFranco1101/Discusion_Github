package com.example.discusion_githubpublic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador que gestiona la colección de repositorios públicos para el RecyclerView.
 */
class RepoAdapter(
    private val repos: List<RepoResponse>,
    private val onItemClick: (RepoResponse) -> Unit
) : RecyclerView.Adapter<RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.bind(repo, onItemClick)
    }

    override fun getItemCount(): Int = repos.size
}
