package  com.ranatalal.tmdbexample.views.models

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.ranatalal.tmdbexample.R
import java.io.Serializable

class ErrorModel(
    @field:SerializedName("http_response")
    val httpResponse: Int? = 0,

    @field:SerializedName("code")
    var code: Int? = null,

    @field:SerializedName("success")
    val success: Boolean = false,

    @field:SerializedName("cmd")
    val cmd: String? = "",

    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("title")
    var title: String? = ""
) : Serializable {
    fun getErrorMessage(context: Context): String? {
        return message
            ?: if (isNoInternetError) context.resources.getString(R.string.no_internet) else context.getString(
                R.string.unexpected_error
            )
    }


    var isNoInternetError = false

}