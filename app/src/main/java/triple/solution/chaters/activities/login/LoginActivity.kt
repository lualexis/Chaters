package triple.solution.chaters.activities.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import triple.solution.chaters.R
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import triple.solution.chaters.helpers.*


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private val fireBaseAuth: FirebaseAuth  by lazy { FirebaseAuth.getInstance() }
    private val apiGoogleClient: GoogleApiClient by lazy { getGoogleApiClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ButtonGoToSignUp.setOnClickListener {
            goToActivity<SignUpActivity>()

            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonLogIn.setOnClickListener {

            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if (isValidEmail(email) && isValidPassword(password)){
                signInWithEmailAndPassword(email, password)
            } else {
                toast("Por favor verifique que todos los datos se encuentren correctos")
            }
        }

        textForgotPassword.setOnClickListener {
            goToActivity<ForgotPasswordActivity>()

            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        txtEmail.validate {
            txtEmail.error = if (isValidEmail(it)) null else "The email is not valid"
        }

        txtPassword.validate {
            txtPassword.error = if (isValidPassword(it)) null else "The Password is not valid"
        }
    }

    private fun getGoogleApiClient() : GoogleApiClient {
        val googleOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleOption)
            .build()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Conection Failed")
    }

    private fun signInWithEmailAndPassword(email: String, password: String){

        fireBaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val currentUser = fireBaseAuth.currentUser!!

                    if (currentUser.isEmailVerified){
                        Toast.makeText(this, "Usuario Logeado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Debe confirmar su correo primero", Toast.LENGTH_LONG).show()
                    }

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        this, "Usuario No existe.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }
}
