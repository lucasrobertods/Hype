package br.com.hype.presenter.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.hype.databinding.ActivityLoginBinding
import br.com.hype.domain.model.User
import br.com.hype.domain.model.toModel
import br.com.hype.presenter.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        supportActionBar?.hide()
        setContentView(binding.root)

        setupView()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if (SessionManager.user == null) {
                setupSessionUser()
                navigateToHome()
            } else navigateToHome()
        }
    }

    private fun setupView() {
        setupSessionUser()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnWithoutLogin.setOnClickListener {
            navigateToHome()
        }

        binding.btnGoogle.setOnClickListener {
            showGoogleLogin()
        }
    }

    private fun showGoogleLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val responseUser = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            setupSessionUser()
            navigateToHome()
            Toast.makeText(this, "Login realizado com sucesso!!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Falha no login!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupSessionUser() {
        auth = Firebase.auth
        auth.currentUser?.let { fbUser ->
            if (!fbUser.displayName.isNullOrEmpty())
                SessionManager.user = fbUser.toModel()
        }
    }


}