package com.bashirli.mymovie.data.repository

import android.net.Uri
import android.util.Log
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.user.UserDTO
import com.bashirli.mymovie.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import kotlin.Exception

class FirebaseRepositoryImpl @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore : FirebaseFirestore,
    private val storage : FirebaseStorage
) : FirebaseRepository {

    private val userDB =  firestore.collection("UserData")


    override suspend fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.loading(null))
            val response = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.success(response))
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage?:"Error",null))
        }
    }

    override suspend fun createUser(userDTO: UserDTO): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.loading(null))
       try {
           val filePath = "images/${UUID.randomUUID()}.jpg"
           val storageRef = storage.reference.child(filePath)
           var imageURL:String = "null"

           userDTO.image?.let {
               storageRef.putFile(it)
                   .addOnSuccessListener {
                       storageRef.downloadUrl.addOnSuccessListener {
                           imageURL = it.toString()
                       }.addOnFailureListener {
                           trySend(Resource.error(it.localizedMessage, null))
                       }
                   }.addOnFailureListener {
                       trySend(Resource.error(it.localizedMessage, null))
                   }.await()
           }




           //UserData
           val user = hashMapOf<String,Any>(
               "email" to userDTO.email,
               "firstName" to userDTO.firstName,
               "lastName" to userDTO.lastName,
               "dateOfBirth" to userDTO.dateOfBirth,
               "image" to imageURL,
               "backgroundImage" to "null"
           )

           userDB.document(userDTO.token)
               .collection("Data")
               .add(user)
               .addOnSuccessListener {
                   trySend(Resource.success("Success"))
               }.addOnFailureListener {
                   trySend(Resource.error(it.localizedMessage,null))
               }.await()
       }catch (e:Exception){
           trySend(Resource.error(e.localizedMessage,null))
       }
            awaitClose { trySend(Resource.success("Success")) }



        }

    override suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {

       try {

           emit(Resource.loading(null))
           val response = auth.signInWithEmailAndPassword(email, password).await()

           emit(Resource.success(response))
       }catch (e:Exception){
           emit(Resource.error(e.localizedMessage?:"Error",null))
       }
    }

    override suspend fun getToken(): Flow<Resource<String>> = flow {
        try {
            val token = auth.currentUser?.uid
            if(!token.isNullOrEmpty()){
                emit(Resource.success(token))
            }else{
                emit(Resource.error("Error",null))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage,null))
        }
    }
}
