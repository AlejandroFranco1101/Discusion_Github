package com.example.discusion_githubpublic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.discusion_githubpublic.databinding.ItemRepoBinding
import com.squareup.picasso.Picasso

/**
 * ViewHolder que enlaza cada objeto RepoResponse con la vista item_repo.xml usando View Binding.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemRepoBinding = ItemRepoBinding.bind(view)

    /**
     * Enlaza los datos del repositorio a las vistas de la tarjeta.
     */
    fun bind(repo: RepoResponse, onItemClick: (RepoResponse) -> Unit) {
        binding.tvRepoName.text = repo.name ?: "Repositorio sin nombre"
        binding.tvRepoDescription.text = repo.getSafeDescription()
        binding.tvRepoLanguage.text = repo.getSafeLanguage()
        binding.tvRepoStars.text = "⭐ ${repo.starsCount ?: 0}"
        binding.tvRepoForks.text = "🍴 ${repo.forksCount ?: 0}"

        // Carga del avatar del propietario usando Picasso
        val avatarUrl = repo.owner?.avatarUrl
        if (!avatarUrl.isNullOrBlank()) {
            Picasso.get()
                .load(avatarUrl)
                .placeholder(R.drawable.ic_person_placeholder)
                .error(R.drawable.ic_person_placeholder)
                .into(binding.ivOwnerAvatar)
        } else {
            binding.ivOwnerAvatar.setImageResource(R.drawable.ic_person_placeholder)
        }

        // 🌟 PLUS EXTRA: Callback al pulsar sobre el repositorio para abrirlo en el navegador
        itemView.setOnClickListener {
            onItemClick(repo)
        }
    }
}
