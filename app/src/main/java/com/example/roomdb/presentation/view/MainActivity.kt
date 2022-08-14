package com.example.roomdb.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roomdb.core.database.AppDatabase
import com.example.roomdb.core.database.Student
import com.example.roomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appDb : AppDatabase
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDb = AppDatabase.getDatabase(this)
        binding.btnWriteData.setOnClickListener {
            writeData()
        }

        binding.btnDeleteAll.setOnClickListener {

            GlobalScope.launch {

                appDb.studentDao().deleteAll()


            }

        }
    }

    private fun writeData(){

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etPhoneNo.text.toString()

        if(firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()     ) {
            val student = Student(
                null, firstName, lastName, rollNo.toInt()
            )
            GlobalScope.launch(Dispatchers.IO) {

                appDb.studentDao().insert(student)
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etPhoneNo.text.clear()

            Toast.makeText(this@MainActivity,"Successfully written",Toast.LENGTH_SHORT).show()
        }else Toast.makeText(this@MainActivity,"PLease Enter Data",Toast.LENGTH_SHORT).show()

    }

    private suspend fun displayData(student: Student){

        withContext(Dispatchers.Main){

            binding.tvFirstName.text = student.firstName
            binding.tvLastName.text = student.lastName
            binding.tvRollNo.text = student.phoneNo.toString()

        }

    }

}