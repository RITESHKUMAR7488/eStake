package com.example.estake.authModule.repositories

import com.example.estake.common.utilities.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await // ⚡ Allows Firebase to work with Coroutines
import javax.inject.Inject

interface AuthRepository {
    // ⚡ 'suspend' functions can run in the background without blocking the UI
    suspend fun loginUser(email: String, pass: String): UiState<String>
    suspend fun registerUser(email: String, pass: String, name: String): UiState<String>
}

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun loginUser(email: String, pass: String): UiState<String> {
        return try {
            // ⚡ 1. Wait for Firebase Auth (No callback needed!)
            auth.signInWithEmailAndPassword(email, pass).await()

            // ⚡ 2. Check if we have a User ID
            val userId = auth.currentUser?.uid ?: throw Exception("User ID is null")

            // ⚡ 3. Success
            UiState.Success("Login Successful")

        } catch (e: Exception) {
            // ⚡ 4. Catch any error automatically
            UiState.Failure(e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun registerUser(email: String, pass: String, name: String): UiState<String> {
        return try {
            // 1. Create User in Auth
            auth.createUserWithEmailAndPassword(email, pass).await()
            val userId = auth.currentUser?.uid ?: throw Exception("User creation failed")

            // 2. Create User Profile Map
            val userMap = hashMapOf(
                "uid" to userId,
                "name" to name,
                "email" to email,
                "role" to "user" // Default role
            )

            // 3. Save to Firestore (Wait for it to finish)
            firestore.collection("users").document(userId).set(userMap).await()

            UiState.Success("Registration Successful")
        } catch (e: Exception) {
            UiState.Failure(e.localizedMessage ?: "Registration Failed")
        }
    }
}