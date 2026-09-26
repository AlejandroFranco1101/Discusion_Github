package com.example.discusion_githubpublic

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discusion_githubpublic.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repoAdapter: RepoAdapter
    private val repoList: MutableList<RepoResponse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        binding.searchRepos.setOnQueryTextListener(this)

        // 🌟 PLUS EXTRA: Búsqueda inicial predeterminada (mojombo - ejemplo oficial de la guía)
        searchUserRepos("mojombo")
    }

    private fun initRecyclerView() {
        // 🌟 PLUS EXTRA: Al pulsar un repositorio se abre directamente en el navegador web
        repoAdapter = RepoAdapter(repoList) { repo ->
            val repoUrl = repo.htmlUrl
            if (!repoUrl.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Enlace no disponible", Toast.LENGTH_SHORT).show()
            }
        }

        binding.listRepos.layoutManager = LinearLayoutManager(this)
        binding.listRepos.adapter = repoAdapter
    }

    /**
     * Consulta los repositorios públicos del usuario mediante Retrofit.
     */
    private fun searchUserRepos(username: String) {
        val cleanUser = username.trim()
        if (cleanUser.isEmpty()) return

        // 🌟 PLUS: Feedback visual de carga
        binding.progressBar.visibility = View.VISIBLE
        binding.layoutEmpty.visibility = View.GONE

        val call: Call<List<RepoResponse>> = RetrofitClient.instance.getUserRepos(cleanUser)
        call.enqueue(object : Callback<List<RepoResponse>> {
            override fun onResponse(
                call: Call<List<RepoResponse>>,
                response: Response<List<RepoResponse>>
            ) {
                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    val repos = response.body()!!
                    repoList.clear()

                    if (repos.isNotEmpty()) {
                        repoList.addAll(repos)
                        repoAdapter.notifyDataSetChanged()
                        binding.listRepos.visibility = View.VISIBLE
                        binding.layoutEmpty.visibility = View.GONE
                    } else {
                        showEmptyState("El usuario \"$cleanUser\" no tiene repositorios públicos.")
                    }
                } else if (response.code() == 404) {
                    // Usuario inexistente en GitHub
                    showEmptyState("No se encontró al usuario \"$cleanUser\" en GitHub.")
                } else {
                    showEmptyState("Error del servidor de GitHub (Código: ${response.code()}).")
                }
            }

            override fun onFailure(call: Call<List<RepoResponse>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                showEmptyState("Sin conexión a Internet. Verifica tu red e inténtalo de nuevo.")
                Toast.makeText(this@MainActivity, "Error de conexión de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showEmptyState(message: String) {
        repoList.clear()
        repoAdapter.notifyDataSetChanged()
        binding.listRepos.visibility = View.GONE
        binding.layoutEmpty.visibility = View.VISIBLE
        binding.tvEmptyMessage.text = message
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
        binding.searchRepos.clearFocus()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            searchUserRepos(query)
            hideKeyboard()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) {
            repoList.clear()
            repoAdapter.notifyDataSetChanged()
            binding.layoutEmpty.visibility = View.GONE
        }
        return true
    }
}