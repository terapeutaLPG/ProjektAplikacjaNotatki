//
//package com.example.firebaseauth.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.Query
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class NotesViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//    private val auth = FirebaseAuth.getInstance()
//
//    private val _notes = MutableStateFlow<List<Note>>(emptyList())
//    val notes: StateFlow<List<Note>> = _notes
//
//    private var selectedNote: Note? = null
//    fun selectNote(note: Note) { selectedNote = note }
//    fun getSelectedNote(): Note? = selectedNote
//
//    fun loadNotes() {
//        val uid = auth.currentUser?.uid ?: return
//        db.collection("notes")
//            .document(uid)
//            .collection("user_notes")
//            .orderBy("timestamp", Query.Direction.DESCENDING)
//            .addSnapshotListener { snapshot, _ ->
//                val noteList = snapshot?.documents?.mapNotNull { doc ->
//                    val content = doc.getString("content") ?: return@mapNotNull null
//                    val timestamp = doc.getLong("timestamp") ?: 0L
//                    Note(id = doc.id, content = content, timestamp = timestamp)
//                } ?: emptyList()
//
//                _notes.value = noteList
//            }
//    }
//
//
//    fun deleteNote(id: String) {
//        db.collection("notes").document(id).delete()
//    }
//
//    fun updateNote(note: Note) {
//        db.collection("notes").document(note.id).set(note)
//    }
//    fun addNote(content: String) {
//        val uid = auth.currentUser?.uid ?: return
//        val newNote = hashMapOf(
//            "content" to content,
//            "timestamp" to System.currentTimeMillis()
//        )
//
//        db.collection("notes")
//            .document(uid)
//            .collection("user_notes")
//            .add(newNote)
//            .addOnSuccessListener {
//                loadNotes()
//            }
//            .addOnFailureListener { e ->
//
//                e.printStackTrace()
//            }
//    }
//}
//package com.example.firebaseauth.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.Query
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//data class Note(
//    val id: String = "",
//    val title: String? = null,
//    val content: String? = null,
//    val timestamp: Long? = null
//)
//
//class NotesViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//    private val auth = FirebaseAuth.getInstance()
//
//    private val _notes = MutableStateFlow<List<Note>>(emptyList())
//    val notes: StateFlow<List<Note>> = _notes
//
//    private var selectedNote: Note? = null
//    fun selectNote(note: Note) { selectedNote = note }
//    fun getSelectedNote(): Note? = selectedNote
//
//    fun loadNotes() {
//        val uid = auth.currentUser?.uid ?: return
//        db.collection("notes")
//            .document(uid)
//            .collection("user_notes")
//            .orderBy("timestamp", Query.Direction.DESCENDING)
//            .addSnapshotListener { snapshot, _ ->
//                val noteList = snapshot?.documents?.mapNotNull { doc ->
//                    val title = doc.getString("title")
//                    val content = doc.getString("content")
//                    val timestamp = doc.getLong("timestamp")
//                    if (content != null) {
//                        Note(
//                            id = doc.id,
//                            title = title,
//                            content = content,
//                            timestamp = timestamp
//                        )
//                    } else null
//                } ?: emptyList()
//
//                _notes.value = noteList
//            }
//    }
//
//    fun deleteNote(noteId: String) {
//        val uid = auth.currentUser?.uid ?: return
//        db.collection("notes")
//            .document(uid)
//            .collection("user_notes")
//            .document(noteId)
//            .delete()
//    }
//
//    fun updateNote(note: Note) {
//        val uid = auth.currentUser?.uid ?: return
//        val updatedData = hashMapOf(
//            "title" to note.title,
//            "content" to note.content,
//            "timestamp" to note.timestamp
//        )
//        db.collection("notes")
//            .document(uid)
//            .collection("user_notes")
//            .document(note.id)
//            .set(updatedData)
//    }
//
//    fun addNote(title: String?, content: String) {
//        val uid = auth.currentUser?.uid ?: return
//        val newNote = hashMapOf(
//            "title" to title,
//            "content" to content,
//            "timestamp" to System.currentTimeMillis()
//        )
//        db.collection("notes")
//            .document(uid)
//            .collection("user_notes")
//            .add(newNote)
//            .addOnSuccessListener {
//                loadNotes()
//            }
//            .addOnFailureListener { e ->
//                e.printStackTrace()
//            }
//    }
//}
package com.example.firebaseauth.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Note(
    val id: String = "",
    val title: String? = null,
    val content: String? = null,
    val timestamp: Long = 0L
)

class NotesViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private var selectedNote: Note? = null
    fun selectNote(note: Note) { selectedNote = note }
    fun getSelectedNote(): Note? = selectedNote

    fun loadNotes() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("notes")
            .document(uid)
            .collection("user_notes")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val noteList = snapshot?.documents?.mapNotNull { doc ->
                    val title = doc.getString("title")
                    val content = doc.getString("content")
                    val timestamp = doc.getLong("timestamp") ?: 0L
                    Note(id = doc.id, title = title, content = content, timestamp = timestamp)
                } ?: emptyList()

                _notes.value = noteList
            }
    }

    fun deleteNote(noteId: String) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("notes")
            .document(uid)
            .collection("user_notes")
            .document(noteId)
            .delete()
    }

    fun updateNote(note: Note) {
        val uid = auth.currentUser?.uid ?: return
        val updatedData = hashMapOf(
            "title" to note.title,
            "content" to note.content,
            "timestamp" to note.timestamp
        )
        db.collection("notes")
            .document(uid)
            .collection("user_notes")
            .document(note.id)
            .set(updatedData)
    }

    fun addNote(title: String?, content: String) {
        val uid = auth.currentUser?.uid ?: return
        val newNote = hashMapOf(
            "title" to title,
            "content" to content,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("notes")
            .document(uid)
            .collection("user_notes")
            .add(newNote)
            .addOnSuccessListener {
                loadNotes()
            }
            .addOnFailureListener { it.printStackTrace() }
    }
}
