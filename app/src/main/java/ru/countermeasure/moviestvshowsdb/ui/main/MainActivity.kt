package ru.countermeasure.moviestvshowsdb.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.ui.main.master.MoviesListFragment

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            //Toast.makeText(this, "Item selected: ${it.title}", Toast.LENGTH_SHORT).show()
            when (it.itemId) {
                R.id.mi_popular -> {

                    true
                }
                R.id.mi_new -> {

                    true
                }
                R.id.mi_soon -> {

                    true
                }
                else -> false
            }
        }
        bottom_navigation.setOnNavigationItemReselectedListener {}

        val tr = supportFragmentManager.beginTransaction()
        tr.add(R.id.fl_placeholder, MoviesListFragment(), MOVIES_LIST_FRAGMENT_TAG)
        tr.commit()
    }

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MOVIES_LIST_FRAGMENT_TAG"
    }
}