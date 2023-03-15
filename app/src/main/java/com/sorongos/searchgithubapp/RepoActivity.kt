package com.sorongos.searchgithubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sorongos.searchgithubapp.databinding.ActivityRepoBinding

/**조회한 user을 클릭하면 실행되는 액티비티*/
class RepoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}