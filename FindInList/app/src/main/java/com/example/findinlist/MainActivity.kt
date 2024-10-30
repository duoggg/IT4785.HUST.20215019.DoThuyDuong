package com.example.findinlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findinlist.adapter.StudentAdapter
import com.example.findinlist.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Tạo danh sách sinh viên mẫu
        studentList = listOf(
            Student("Tran Van Bao", "20220001"),
            Student("Nguyen Thi Cam", "20220002"),
            Student("Pham Thanh Duy", "20220003"),
            Student("Le Thi Anh Dao", "20220004"),
            Student("Nguyen Van Minh", "20220005"),
            Student("Tran Hoang Anh", "20220006"),
            Student("Phan Quoc Bao", "20220007"),
            Student("Do Ngoc Phuoc", "20220008"),
            Student("Vu Thi Ha", "20220009"),
            Student("Nguyen Minh Tri", "20220010"),
            Student("Pham Van Phuc", "20220011"),
            Student("Ly Thi Thu", "20220012"),
            Student("Tran Van Tam", "20220013"),
            Student("Le Thi Tuyet", "20220014"),
            Student("Phan Bao Khanh", "20220015"),
            Student("Do Van Khai", "20220016"),
            Student("Ngo Thi Hien", "20220017"),
            Student("Vu Minh Tan", "20220018")
        )


        // Thiết lập RecyclerView với danh sách sinh viên
        studentAdapter = StudentAdapter(studentList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        // Thiết lập tìm kiếm khi người dùng nhập từ khóa
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val keyword = s.toString().trim()
                if (keyword.length > 2) {
                    filterList(keyword)
                } else {
                    studentAdapter.updateList(studentList)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    // Hàm lọc danh sách sinh viên dựa trên từ khóa
    private fun filterList(keyword: String) {
        val filteredList = studentList.filter {
            it.name.contains(keyword, ignoreCase = true) || it.mssv.contains(keyword)
        }
        studentAdapter.updateList(filteredList)
    }
}
