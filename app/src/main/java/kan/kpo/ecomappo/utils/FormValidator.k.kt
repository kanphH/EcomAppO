package kan.kpo.ecomappo.utils

import android.util.Patterns

object ValidationUtils {

    fun validateEmail(email: String): String {
        return when {
            email.isEmpty() -> ""
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "invalid email"

            else -> ""
        }
    }

    fun validatePassword(password: String): String {
        return when {
            password.isEmpty() -> ""
            password.length < 6 -> "password need  > 6"
            else -> ""
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String {
        return when {
            confirmPassword.isEmpty() -> ""
            password != confirmPassword -> "pass not match"
            else -> ""
        }
    }



}