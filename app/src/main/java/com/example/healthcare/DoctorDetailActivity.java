package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailActivity extends AppCompatActivity {
    private String[][] doctor_details1 =
            {
                    {"Nama Dokter: Richard Lee", "Alamat Rumah Sakit: Depok", "Masa: 7thn", "No Telepon: 081317716656", "Biaya: 100000"},
                    {"Nama Dokter: Clara Johnson", "Alamat Rumah Sakit: Jakarta", "Masa: 10thn", "No Telepon: 082134567890", "Biaya: 120000"},
                    {"Nama Dokter: Ahmad Fauzi", "Alamat Rumah Sakit: Bandung", "Masa: 5thn", "No Telepon: 081223344556", "Biaya: 90000"},
                    {"Nama Dokter: Siti Nurhaliza", "Alamat Rumah Sakit: Surabaya", "Masa: 8thn", "No Telepon: 083356789012", "Biaya: 110000"},
                    {"Nama Dokter: William Tan", "Alamat Rumah Sakit: Yogyakarta", "Masa: 12thn", "No Telepon: 085674321098", "Biaya: 130000"}
            };

    private String[][] doctor_details2 =
            {
                    {"Nama Dokter: Lisa Putri", "Alamat Rumah Sakit: Bali", "Masa: 6thn", "No Telepon: 087123456789", "Biaya: 95000"},
                    {"Nama Dokter: Kevin Hartono", "Alamat Rumah Sakit: Medan", "Masa: 9thn", "No Telepon: 081345678901", "Biaya: 115000"},
                    {"Nama Dokter: Maria Lim", "Alamat Rumah Sakit: Semarang", "Masa: 11thn", "No Telepon: 082178945612", "Biaya: 125000"},
                    {"Nama Dokter: David Suryanto", "Alamat Rumah Sakit: Makassar", "Masa: 4thn", "No Telepon: 083456789012", "Biaya: 88000"},
                    {"Nama Dokter: Anita Wijaya", "Alamat Rumah Sakit: Pontianak", "Masa: 3thn", "No Telepon: 081234567890", "Biaya: 85000"}
            };

    private String[][] doctor_details3 =
            {
                    {"Nama Dokter: Hannah Chandra", "Alamat Rumah Sakit: Malang", "Masa: 5thn", "No Telepon: 085712345678", "Biaya: 90000"},
                    {"Nama Dokter: Yusuf Pratama", "Alamat Rumah Sakit: Palembang", "Masa: 7thn", "No Telepon: 081987654321", "Biaya: 105000"},
                    {"Nama Dokter: Grace Tanoto", "Alamat Rumah Sakit: Manado", "Masa: 8thn", "No Telepon: 083123498765", "Biaya: 110000"},
                    {"Nama Dokter: Arif Budiman", "Alamat Rumah Sakit: Balikpapan", "Masa: 6thn", "No Telepon: 085678901234", "Biaya: 100000"},
                    {"Nama Dokter: Nina Ardianti", "Alamat Rumah Sakit: Banjarmasin", "Masa: 10thn", "No Telepon: 081234598765", "Biaya: 120000"}
            };

    private String[][] doctor_details4 =
            {
                    {"Nama Dokter: Rina Mahardika", "Alamat Rumah Sakit: Maluku", "Masa: 5thn", "No Telepon: 082167892345", "Biaya: 90000"},
                    {"Nama Dokter: Bambang Setiawan", "Alamat Rumah Sakit: Aceh", "Masa: 8thn", "No Telepon: 081276543210", "Biaya: 110000"},
                    {"Nama Dokter: Diana Kusuma", "Alamat Rumah Sakit: Lampung", "Masa: 7thn", "No Telepon: 083456712345", "Biaya: 105000"},
                    {"Nama Dokter: Andi Syahputra", "Alamat Rumah Sakit: Pekanbaru", "Masa: 9thn", "No Telepon: 081234567654", "Biaya: 115000"},
                    {"Nama Dokter: Tania Wijayanti", "Alamat Rumah Sakit: Jambi", "Masa: 4thn", "No Telepon: 082178945678", "Biaya: 88000"}
            };

    private String[][] doctor_details5 =
            {
                    {"Nama Dokter: Fajar Santoso", "Alamat Rumah Sakit: Ternate", "Masa: 3thn", "No Telepon: 081356789012", "Biaya: 85000"},
                    {"Nama Dokter: Lina Purwanti", "Alamat Rumah Sakit: Kupang", "Masa: 6thn", "No Telepon: 083245678901", "Biaya: 95000"},
                    {"Nama Dokter: Rizky Aditya", "Alamat Rumah Sakit: Gorontalo", "Masa: 8thn", "No Telepon: 081234567890", "Biaya: 110000"},
                    {"Nama Dokter: Maya Sari", "Alamat Rumah Sakit: Bengkulu", "Masa: 10thn", "No Telepon: 085678912345", "Biaya: 120000"},
                    {"Nama Dokter: Doni Saputra", "Alamat Rumah Sakit: Papua", "Masa: 2thn", "No Telepon: 082167890123", "Biaya: 80000"}
            };

    TextView tv;
    Button btn;
    String[][] doctor_details ={};
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_detail);

        tv = findViewById(R.id.textViewODTitle);
        btn = findViewById(R.id.buttonLTBack);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if (title.compareTo("Family Physician")==0)
            doctor_details =doctor_details1;
        else
        if (title.compareTo("Dietician")==0)
            doctor_details =doctor_details2;
        else
        if (title.compareTo("Dentist")==0)
            doctor_details =doctor_details3;
        else
        if (title.compareTo("Surgeon")==0)
            doctor_details =doctor_details4;
        else
            doctor_details =doctor_details5;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList();
        for (int i=0; i<doctor_details.length; i++){
            item = new HashMap<String,String>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees: " +doctor_details[i][4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c,R.id.line_d, R.id.line_e}
        );

        ListView lst = findViewById(R.id.listViewOD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[i][0]);
                it.putExtra("text3", doctor_details[i][1]);
                it.putExtra("text4", doctor_details[i][3]);
                it.putExtra("text5", doctor_details[i][4]);
                startActivity(it);
            }
        });
    }
}