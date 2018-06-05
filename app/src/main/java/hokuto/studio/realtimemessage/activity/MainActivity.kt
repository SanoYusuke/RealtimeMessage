package hokuto.studio.realtimemessage.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import hokuto.studio.realtimemessage.R
import java.util.*

class MainActivity : AppCompatActivity() {

	private var myRef : DatabaseReference? = null

	private var mRef : CollectionReference? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

//		val database = FirebaseDatabase.getInstance()
//		myRef = database.getReference("message")

		val db = FirebaseFirestore.getInstance()
		mRef = db.collection("message")

		val editText = findViewById<EditText>(R.id.send_edit)

		val sendButton = findViewById<Button>(R.id.send_button)
		sendButton.setOnClickListener {
//			myRef?.setValue(editText.text.toString())
			val data = HashMap<String, Any>()
			data.put("title", editText.text.toString())

			mRef?.document("message_data")?.set(data)
		}

//		myRef?.addValueEventListener(object : ValueEventListener {
//			override fun onDataChange(dataSnapshot: DataSnapshot) {
//				// This method is called once with the initial value and again
//				// whenever data at this location is updated.
//				val value = dataSnapshot.getValue(String::class.java) ?: "データなし"
//				Log.d("MainActivity", "Value is: " + value)
//				Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show()
//			}
//
//			override fun onCancelled(error: DatabaseError) {
//				// Failed to read value
//				Log.w("MainActivity", "Failed to read value.", error.toException())
//			}
//		})

		mRef?.get()?.addOnCompleteListener({ task ->
					if (task.isSuccessful) {
						for (document in task.result) {
							Log.d("MainActivity", document.id + " => " + document.data)
						}
					} else {
						Log.w("MainActivity", "Error getting documents.", task.exception)
					}
				})
	}
}
