package io.project.shorts.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import io.project.shorts.databinding.ActivityScrollingBinding
import io.project.shorts.repository.Repository
import io.project.shorts.viewmodels.MainViewModel
import io.project.shorts.viewmodels.ViewModelFactoryMain

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: ThumbnailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()


        val repository = Repository()
        val viewModelFactory = ViewModelFactoryMain(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]


        (viewModel as MainViewModel).videosResponse?.observe(this) { info ->
            adapter = ThumbnailAdapter(this,info!!.data!!.posts)
            binding.rvThumbnails.adapter = adapter
            setupRecyclerView()
        }
    }
    private fun setupRecyclerView() =binding.rvThumbnails.apply {
        layoutManager = GridLayoutManager(context,2)
    }
}