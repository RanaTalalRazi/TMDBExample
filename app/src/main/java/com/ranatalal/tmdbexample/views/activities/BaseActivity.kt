package com.ranatalal.tmdbexample.views.activities


import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.helper.UIHelper
import com.ranatalal.tmdbexample.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import java.util.*

open class BaseActivity : AppCompatActivity() {
    val uiHelper: UIHelper by inject()

    val movieViewModel: MovieViewModel by stateViewModel()

    var loading: Dialog? = null
    fun showLoading() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_dialog)
        loading = builder.create()
        if (!loading!!.isShowing)
            loading?.show()
    }


    fun hideLoading() {
        if (loading != null) {
            if (loading!!.isShowing) {
                loading!!.cancel()
                loading!!.dismiss()
            }
        }
    }


}