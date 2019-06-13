package triple.solution.chaters.helpers

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, message, duration).show()

inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

fun EditText.validate(validation: (String) -> Unit) {

    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })

}

fun Activity.isValidEmail(email: String) : Boolean {
    val patern = Patterns.EMAIL_ADDRESS

    return patern.matcher(email).matches()
}

fun Activity.isValidPassword(password: String) : Boolean {
    val patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$"
    val pattern = Pattern.compile(patternPassword)

    return pattern.matcher(password).matches()
}

fun Activity.isValidConfirmPassword(password: String, confirmPassword: String) : Boolean {
    return password == confirmPassword
}