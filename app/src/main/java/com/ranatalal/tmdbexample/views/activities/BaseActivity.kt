package com.ranatalal.tmdbexample.views.activities


import androidx.appcompat.app.AppCompatActivity
import com.ranatalal.tmdbexample.helper.UIHelper
import com.ranatalal.tmdbexample.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import java.util.*

open class BaseActivity : AppCompatActivity() {
    val uiHelper: UIHelper by inject()

    val movieViewModel: MovieViewModel by stateViewModel()


}