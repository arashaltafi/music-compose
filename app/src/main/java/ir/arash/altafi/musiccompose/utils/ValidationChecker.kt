package ir.arash.altafi.musiccompose.utils

object ValidationChecker {

    fun isValidPhone(phone: String): Boolean {
        return phone.length == 11 && phone.startsWith("09")
    }

    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length in 6..32
    }

    fun isValidMobile(mobile: String): Boolean {
        return mobile.length == 11 && mobile.startsWith("09")
    }

    fun isValidName(name: String): Boolean {
        return name.length in 2..20
    }

    fun isValidFamily(family: String): Boolean {
        return family.length in 4..30
    }

}