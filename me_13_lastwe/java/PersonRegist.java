package com.example.dbpersonregistapp2026;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.dbpersonregistapp2026.databinding.ActivityRegistBinding;

public class PersonRegist extends AppCompatActivity {

    DBManager dbManeger;
    SQLiteDatabase sqLiteDB;

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegistBinding binding;

    public final static int REQUEST_PHOTO_CODE = 1;

    String [] majorlist = {"영상미디어콘텐츠", "컴퓨터공학", "사회복지", "항공운항", "게임콘텐츠"};
    String [] arealist = {"서울특별시", "인천광역시", "수원시", "화성시", "그 외 지역"};
    Spinner major,area;

    EditText name, stdno, myPhone;
    RadioGroup grade;
    RadioButton one, two, three;
    CheckBox hobby01, hobby02, hobby03;
    Button findImage, registBtn;
    ImageView viewImage;

    String str_name, str_stdno, str_myPhone, str_grade, str_one, str_two, str_three;
    String str_hobby, str_hobby01, str_hobby02, str_hobby03, str_uri, str_major, str_area, str_photoUri;

    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setTitle("회원등록");

        binding = ActivityRegistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        major = (Spinner) findViewById(R.id.major);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, majorlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        major.setAdapter(adapter);

        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_major = majorlist[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"전공을 선택하세요!", Toast.LENGTH_SHORT).show();
            }
        }); //전공선택

        area = (Spinner) findViewById(R.id.area);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arealist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        area.setAdapter(adapter1);

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_area = arealist[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"거주지를 선택하세요!", Toast.LENGTH_SHORT).show();
            }
        }); //거주지선택

        name = (EditText) findViewById(R.id.name);
        stdno = (EditText) findViewById(R.id.stdno);
        myPhone = (EditText) findViewById(R.id.myPhone);
        grade = (RadioGroup) findViewById(R.id.grade);
        one = (RadioButton) findViewById(R.id.one);
        two = (RadioButton) findViewById(R.id.two);
        three = (RadioButton) findViewById(R.id.three);
        hobby01 = (CheckBox) findViewById(R.id.hobby01);
        hobby02 = (CheckBox) findViewById(R.id.hobby02);
        hobby03 = (CheckBox) findViewById(R.id.hobby03);
        findImage= (Button) findViewById(R.id.findImage);
        registBtn = (Button) findViewById(R.id.registBtn);
        viewImage = (ImageView) findViewById(R.id.viewImage);

        findImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iit = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                iit.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(iit, REQUEST_PHOTO_CODE);

            }
        }); //사진 선택

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_name = name.getText().toString();
                str_stdno = stdno.getText().toString();
                str_myPhone = myPhone.getText().toString();

                if(one.isChecked())
                    str_grade = one.getText().toString();
                else if (two.isChecked())
                    str_grade = two.getText().toString();
                else
                    str_grade = three.getText().toString();

                str_hobby = "";
                if (hobby01.isChecked()) str_hobby = str_hobby + hobby01.getText().toString();
                if (hobby02.isChecked()) str_hobby = str_hobby + hobby02.getText().toString() + " ";
                if (hobby03.isChecked()) str_hobby = str_hobby + hobby03.getText().toString() + " ";

                str_photoUri = photoUri.toString();

                try {
                    dbManeger = new DBManager(PersonRegist.this);
                    sqLiteDB = dbManeger.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("name",str_name);
                    values.put("major",str_major);
                    values.put("stdno",str_stdno);
                    values.put("grade",str_grade);
                    values.put("phone",str_myPhone);
                    values.put("area",str_area);
                    values.put("hobby",str_hobby);

                    long newrow = sqLiteDB.insert("member",null,values);
                    sqLiteDB.close();
                    dbManeger.close();
                    Toast.makeText(getApplicationContext(),"회원추가성공", Toast.LENGTH_SHORT).show();

                }catch(SQLException e){
                    Toast.makeText(getApplicationContext(),"회원추가실패", Toast.LENGTH_SHORT).show();
                }

                Intent it = new Intent(PersonRegist.this,PersonInfo.class);
                it.putExtra("it_name",str_name);
                it.putExtra("it_stdno",str_stdno);
                it.putExtra("it_myPhone",str_myPhone);
                it.putExtra("it_grade",str_grade);
                it.putExtra("it_hobby",str_hobby);
                it.putExtra("it_major",str_major);
                it.putExtra("it_area",str_area);
                it.putExtra("it_photoUri",str_photoUri);

                startActivity(it);

            }
        }); //등록버튼

    } //onCreate

    public void onActivityResult(int requestcode, int resultcode, Intent it){
        super .onActivityResult(requestcode, resultcode, it);
        if (requestcode == REQUEST_PHOTO_CODE){
            photoUri = it.getData();
            viewImage.setImageURI(photoUri);
        }

        else{
            Toast.makeText(getApplicationContext(), "사진 선택 오류", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_regist, menu);
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
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

} //class