package triple.solution.chaters.activities.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import triple.solution.chaters.R
import triple.solution.chaters.helpers.goToActivity
import triple.solution.chaters.helpers.isValidEmail
import triple.solution.chaters.helpers.validate

class ForgotPasswordActivity : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        buttonBack.setOnClickListener {
            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        editTextEmail.validate {
            editTextEmail.error = if (isValidEmail(it)) null else "Debe ingresar un email correcto"
        }

        buttonForgotPassword.setOnClickListener {

            val email = editTextEmail.text.toString()

            if (isValidEmail(email)){
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this) {
                        Toast.makeText(this, "Un email ha sido enviado para resetear tu clave", Toast.LENGTH_LONG)
                            .show()

                        goToActivity<LoginActivity>{
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }

                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                    }
            } else {
                Toast.makeText(this, "Se produjo un error inesperado, favor volver a intentar", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
