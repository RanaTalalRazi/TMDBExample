package  com.ranatalal.tmdbexample.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ranatalal.tmdbexample.views.models.ErrorModel
import retrofit2.Response

internal object ErrorUtils {
    inline fun <reified T> fromJson(jsonString: String?): T {
        return Gson().fromJson(jsonString, object : TypeToken<T>() {}.type)
    }

    fun <T> getErrorModelFromJson(response: Response<T>): ErrorModel {
        var model: ErrorModel
        var message: String? = null
        try {
            message = response.errorBody()?.string()
            model = fromJson(message)
        } catch (ex: Exception) {
            model = ErrorModel()

            if (message != null && message.count() < 1000) {
                model.message = message
            } else {
                model.message = "Error ${response.code()}: Something went wrong"
            }
        }
        return model
    }
}