package br.com.hype.domain.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val photoUri: Uri?
) : Serializable {
    fun toUi() = this
}

fun FirebaseUser.toModel() = User(
    id = this.uid,
    name = this.displayName.orEmpty(),
    email = this.email.orEmpty(),
    phone = this.phoneNumber.orEmpty(),
    photoUri = this.photoUrl
)
