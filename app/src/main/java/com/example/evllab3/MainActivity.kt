package com.example.evllab3

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.evllab3.database.models.Movie
import com.example.evllab3.fragments.MainContentFragment
import com.example.evllab3.fragments.MainListFragment
import com.example.evllab3.networkutils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , MainListFragment.ClickedMovieListener {

    private lateinit var mainFragment:MainListFragment
    private lateinit var mainContentFragment: MainContentFragment
    private var resource = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarmain)

        initFragments()
    }

    fun initFragments(){
        mainFragment = MainListFragment()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            resource = R.id.portrait_main_place_holder
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            showContent(R.id.land_main_movieviewer_ph, Movie())
            resource =R.id.land_main_place_holder
        }

        val intent = Intent(this, NewActivity::class.java)
        main_btn_add.setOnClickListener { startActivityForResult(intent , AppConstants.ADD_TASK_REQUEST) }

        changeFragment(resource, mainFragment)
    }
    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }

    private fun showContent(id_placeholder: Int, movie: Movie) {
        mainContentFragment = MainContentFragment.newInstance(movie)
        changeFragment(id_placeholder, mainContentFragment)
    }

    override fun managePortraitItemClick(movie: Movie) = showContent(R.id.portrait_main_place_holder, movie)

    override fun managedLandscapeItemClick(movie: Movie) = showContent(R.id.land_main_movieviewer_ph, movie)

}
