package kan.kpo.ecomappo.utils

import android.util.Patterns

object ValidationUtils {

    fun validateEmail(email: String): String {
        return when {
            email.isEmpty() -> ""
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "อีเมลไม่ถูกต้อง"

            else -> ""
        }
    }

    fun validatePassword(password: String): String {
        return when {
            password.isEmpty() -> ""
            password.length < 6 -> "รหัสผ่านต้องมีอย่างน้อย 6 ตัวอักษร"
            else -> ""
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String {
        return when {
            confirmPassword.isEmpty() -> ""
            password != confirmPassword -> "รหัสผ่านไม่ตรงกัน"
            else -> ""
        }
    }



    fun validatePhoneNumber(phone: String): String {
        return when {
            phone.isEmpty() -> ""
            phone.length < 10 -> "เบอร์โทรต้องมี 10 หลัก"
            !phone.all { it.isDigit() } -> "เบอร์โทรต้องเป็นตัวเลขเท่านั้น"
            else -> ""
        }
    }
}