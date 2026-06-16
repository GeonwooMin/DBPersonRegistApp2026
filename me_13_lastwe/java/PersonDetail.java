package com.example.dbpersonregistapp2026;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.dbpersonregistapp2026.databinding.ActivityDetailBinding;

public class PersonDetail extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDetailBinding binding;

    DBManager dbManager;
    SQLiteDatabase sqlite;

    TextView name,stdno,myPhone,grade,hobby,area,major;
    ImageView viewImage;
    Uri photoUri;

    Button deleteBtn;

    String str_name="",str_stdno="",str_phone="",str_grade="",str_hobby="",str_major="",str_area="";
    String str_photoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        name = (TextView)findViewById(R.id.name);
        stdno = (TextView)findViewById(R.id.stdno);
        myPhone = (TextView)findViewById(R.id.myPhone);
        grade = (TextView) findViewById(R.id.grade);
        hobby = (TextView) findViewById(R.id.hobby);
        major = (TextView) findViewById(R.id.major);
        area = (TextView) findViewById(R.id.area);
        //viewImage = (ImageView) findViewById(R.id.viewImage);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        try {
            dbManager = new DBManager(this);
            sqlite = dbManager.getReadableDatabase();
            Intent it = getIntent();
            str_name = it.getStringExtra("it_name");
            str_stdno = it.getStringExtra("it_stdno");

            Cursor cursor = sqlite.rawQuery("select * from member where name = ?",
            new String[]{str_name}, null);
            if (cursor!=null && cursor.moveToNext()){
                int id_name=cursor.getColumnIndex("name");
                str_name=cursor.getString(id_name);

                int id_stdno=cursor.getColumnIndex("stdno");
                str_stdno=cursor.getString(id_stdno);

                int id_major=cursor.getColumnIndex("major");
                str_major=cursor.getString(id_major);

                int id_grade=cursor.getColumnIndex("grade");
                str_grade=cursor.getString(id_grade);

                int id_phone=cursor.getColumnIndex("phone");
                str_phone=cursor.getString(id_phone);

                int id_area=cursor.getColumnIndex("area");
                str_area=cursor.getString(id_area);

                int id_hobby =cursor.getColumnIndex("hobby");
                str_hobby=cursor.getString(id_hobby);

                name.setText(str_name);
                stdno.setText(str_stdno);
                myPhone.setText(str_phone);
                grade.setText(str_grade);
                hobby.setText(str_hobby);
                major.setText(str_major);
                area.setText(str_area);
                //photoUri = Uri.parse(str_photoUri);
                //viewImage.setImageURI(photoUri);

            }
            else {
                Toast.makeText(getApplicationContext(),
                        "해당 회원 정보가 없습니다.",
                        Toast.LENGTH_SHORT).show();
            } //출력

            //회원 삭제
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbManager=new DBManager(PersonDetail.this);
                    sqlite=dbManager.getReadableDatabase();
                    sqlite.delete("member","name=?",new String[]{str_name});

                    Toast.makeText(getApplicationContext(),
                            "해당 회원 삭제",
                            Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(PersonDetail.this,PersonList.class);
                    startActivity(it);
                }
            });

        }catch (SQLException e){



        } //try-catch


    } //onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        setTitle("회원 상세 정보");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
            return true;

        } else if (id == R.id.action_settings2) {
            Intent it = new Intent(this,PersonRegist.class);
            startActivity(it);
            return true;

        } else if (id == R.id.action_settings4) {
            Intent it = new Intent(this,PersonList.class);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}//class