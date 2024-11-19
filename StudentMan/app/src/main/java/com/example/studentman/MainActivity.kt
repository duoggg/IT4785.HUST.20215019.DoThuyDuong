package com.example.studentman

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(
        StudentModel("Nguyễn Thị Kim", "SV101"),
        StudentModel("Trần Văn Phúc", "SV102"),
        StudentModel("Lê Thị Thanh", "SV103"),
        StudentModel("Phạm Minh Tuấn", "SV104"),
        StudentModel("Đỗ Thị Mai", "SV105"),
        StudentModel("Vũ Văn Kiên", "SV106"),
        StudentModel("Hoàng Thị Hồng", "SV107"),
        StudentModel("Bùi Văn Đạt", "SV108"),
        StudentModel("Đinh Thị Hương", "SV109"),
        StudentModel("Nguyễn Văn Quang", "SV110"),
        StudentModel("Phạm Thị Hạnh", "SV111"),
        StudentModel("Trần Văn Lộc", "SV112"),
        StudentModel("Lê Thị Bích", "SV113"),
        StudentModel("Vũ Văn Tâm", "SV114"),
        StudentModel("Hoàng Văn Khải", "SV115"),
        StudentModel("Đỗ Minh Châu", "SV116"),
        StudentModel("Nguyễn Thị Nga", "SV117"),
        StudentModel("Trần Văn Sơn", "SV118"),
        StudentModel("Phạm Thị Như", "SV119"),
        StudentModel("Lê Văn Phát", "SV120")
    )

    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students, ::editStudent, ::removeStudent)
        findViewById<RecyclerView>(R.id.recycler_view_students).apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Add new student
        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            val dialogView = LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.layout_alert_dialog, null)

            val editHoten = dialogView.findViewById<EditText>(R.id.edit_hoten)
            val editMssv = dialogView.findViewById<EditText>(R.id.edit_mssv)

            AlertDialog.Builder(this)
                .setTitle("Nhập thông tin sinh viên")
                .setView(dialogView)
                .setPositiveButton("Thêm") { _, _ ->
                    val hoten = editHoten.text.toString()
                    val mssv = editMssv.text.toString()
                    if (hoten.isNotEmpty() && mssv.isNotEmpty()) {
                        addStudent(StudentModel(hoten, mssv))
                        Log.v("TAG", "$hoten: $mssv")
                    }
                }
                .setNegativeButton("Thoát", null)
                .create()
                .show()
        }
    }

    //Edit
    private fun editStudent(position: Int) {
        val currentStudent = students[position]

        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.layout_dialog)

        val editHoten = dialog.findViewById<EditText>(R.id.edit_hoten)
        val editMssv = dialog.findViewById<EditText>(R.id.edit_mssv)
        editHoten.setText(currentStudent.studentName)
        editMssv.setText(currentStudent.studentId)

        dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val hoten = editHoten.text.toString()
            val mssv = editMssv.text.toString()

            if (hoten.isNotEmpty() && mssv.isNotEmpty()) {
                students[position] = StudentModel(hoten, mssv)
                studentAdapter.notifyItemChanged(position)
            }
            Log.v("TAG", "$hoten: $mssv")
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    //Remove
    private fun removeStudent(position: Int) {
        val currentStudent = students[position]

        AlertDialog.Builder(this)
            .setTitle("Bạn có thực sự muốn xóa sinh viên ${currentStudent.studentName}?")
            .setPositiveButton("Có") { _, _ ->
                students.removeAt(position)
                studentAdapter.notifyItemRemoved(position)

                val view = findViewById<View>(R.id.recycler_view_students)
                Snackbar.make(view, "Đã xóa sinh viên ${currentStudent.studentName}", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(position, currentStudent)
                        studentAdapter.notifyItemInserted(position)
                    }
                    .show()
            }
            .setNegativeButton("Không", null)
            .create()
            .show()
    }

    private fun addStudent(student: StudentModel) {
        students.add(student)
        studentAdapter.notifyItemInserted(students.size - 1)
    }
}