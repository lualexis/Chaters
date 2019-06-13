package triple.solution.chaters.activities.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import triple.solution.chaters.R
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import triple.solution.chaters.helpers.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private val fireBaseAuth: FirebaseAuth  by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonBack.setOnClickListener {

            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        buttonSignUp.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (isValidEmail(email) && isValidPassword(password) &&
                isValidConfirmPassword(password, confirmPassword)){
                signUpByEmail(email, password)
            } else {
                Toast.makeText(
                    this, "Por favor verifique todos los campos son obligatorios",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        editTextEmail.validate {
            editTextEmail.error = if (isValidEmail(it)) null else "The email is not valid"
        }

        editTextPassword.validate {
            editTextPassword.error = if (isValidPassword(it)) null else "The password is not valid"
        }

        editTextConfirmPassword.validate {
            editTextConfirmPassword.error = if (isValidConfirmPassword(editTextPassword.text.toString(), it)) null else "The confirm password is not equals than password"
        }
    }

    private fun signUpByEmail(email: String, password: String){
        fireBaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    fireBaseAuth.currentUser!!.sendEmailVerification()
                        .addOnCompleteListener(this){
                            Toast.makeText(
                                this, "Un email ha sido enviado, por favor confirmar.",
                                Toast.LENGTH_LONG
                            ).show()

                            goToActivity<LoginActivity>{
                                //Este flag es para que no vuelva apretando el botón back
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }

                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this, "Ocurrio un error inesperado, favor volver a intentar",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
