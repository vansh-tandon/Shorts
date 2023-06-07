package io.project.shorts.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import io.project.shorts.databinding.ActivityMain2Binding
import io.project.shorts.models.current.Video
import io.project.shorts.repository.Repository
import io.project.shorts.viewmodels.MainViewModel
import io.project.shorts.viewmodels.ViewModelFactoryMain

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private lateinit var adapter: VideoAdapter
    private lateinit var viewModel: MainViewModel

    private val exoPlayerItems = ArrayList<ExoPlayerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val aints = intent
       val position = aints.getIntExtra("ANAME",0)


        val repository = Repository()
        val viewModelFactory = ViewModelFactoryMain(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]




        viewModel.videosResponse?.observe(this) { info ->
           // Toast.makeText(this,info.toString() , Toast.LENGTH_SHORT).show()

//            adapter = VideoAdapter(this,info!!.data!!.posts, object : VideoAdapter.OnVideoPreparedListener {
//                override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem) {
//                    exoPlayerItems.add(exoPlayerItem)
//                }
//            })
//            binding.viewPager2.adapter = adapter
//        }
        adapter = VideoAdapter(this,info!!.data!!.posts, object : VideoAdapter.OnVideoPreparedListener {
            override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem) {
                exoPlayerItems.add(exoPlayerItem)
            }
        })
        binding.viewPager2.adapter = adapter
           binding.viewPager2.setCurrentItem(position)
    }




    binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val previousIndex = exoPlayerItems.indexOfFirst { it.exoPlayer.isPlaying }
                if (previousIndex != -1) {
                    val player = exoPlayerItems[previousIndex].exoPlayer
                    player.pause()
                    player.playWhenReady = false
                }
                val newIndex = exoPlayerItems.indexOfFirst { it.position == position }
                if (newIndex != -1) {
                    val player = exoPlayerItems[newIndex].exoPlayer
                    player.playWhenReady = true
                    player.play()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()

        val index = exoPlayerItems.indexOfFirst { it.position == binding.viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.pause()
            player.playWhenReady = false
        }
    }

    override fun onResume() {
        super.onResume()

        val index = exoPlayerItems.indexOfFirst { it.position == binding.viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.playWhenReady = true
            player.play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (exoPlayerItems.isNotEmpty()) {
            for (item in exoPlayerItems) {
                val player = item.exoPlayer
                player.stop()
                player.clearMediaItems()
            }
        }
    }

}