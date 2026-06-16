package com.example.dbpersonregistapp2026;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.dbpersonregistapp2026.databinding.ActivityInfoBinding;

public class PersonInfo extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityInfoBinding binding;

    TextView name, stdno, myPhone, grade, hobby, area, major;
    ImageView viewImage;
    Uri photoUri;

    String str_name="", str_stdno="", str_myPhone="", str_grade="";
    String str_hobby="", str_major="", str_area="", str_photoUri="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle("등록된 회원 정보");

        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        name = (TextView) findViewById(R.id.name);
        stdno = (TextView) findViewById(R.id.stdno);
        myPhone = (TextView) findViewById(R.id.myPhone);
        grade = (TextView) findViewById(R.id.grade);
        hobby = (TextView) findViewById(R.id.hobby);
        major = (TextView) findViewById(R.id.major);
        area = (TextView) findViewById(R.id.area);
        viewImage = (ImageView) findViewById(R.id.viewImage);

        Intent it = getIntent();
        str_name = it.getStringExtra("it_name");
        str_stdno = it.getStringExtra("it_stdno");
        str_myPhone = it.getStringExtra("it_myPhone");
        str_grade = it.getStringExtra("it_grade");
        str_hobby = it.getStringExtra("it_hobby");
        str_major = it.getStringExtra("it_major");
        str_area = it.getStringExtra("it_area");
        str_photoUri = it.getStringExtra("it_photoUri");

        name.setText(str_name);
        stdno.setText(str_stdno);
        myPhone.setText(str_myPhone);
        grade.setText(str_grade);
        hobby.setText(str_hobby);
        major.setText(str_major);
        area.setText(str_area);

        photoUri = Uri.parse(str_photoUri);
        viewImage.setImageURI(photoUri);

    } //onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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

        else if (id == R.id.action_settings2) {
            Intent it = new Intent(this, PersonRegist.class);
            startActivity(it);

            return true;
        }

        else if (id == R.id.action_settings4) {
            Intent it = new Intent(this, PersonList.class);
            startActivity(it);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

} //class