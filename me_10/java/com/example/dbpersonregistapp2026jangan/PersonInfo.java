package com.example.dbpersonregistapp2026jangan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.dbpersonregistapp2026jangan.databinding.ActivityInfoBinding;

public class PersonInfo extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityInfoBinding binding;

    TextView name,stdno,myPhone,garde,hobby,area,major;
    ImageView viewImage;
    Uri photoUri;  // uri = SD 카드에 있는 이미지를 문자열로 보내는 것


    String str_name="",str_stdno="",str_myPhone="",str_grade="";
    String str_hobby="";
    String str_major="",str_area="";
    String str_photoUri="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);



        name=(TextView) findViewById(R.id.name);  // 위에서 선언한 것 연결
        stdno=(TextView) findViewById(R.id.stdno);  // 위에서 선언한 것 연결
        garde=(TextView) findViewById(R.id.grade);  // 위에서 선언한 것 연결
        myPhone=(TextView) findViewById(R.id.myPhone);  // 위에서 선언한 것 연결
        hobby=(TextView) findViewById(R.id.hobby);  // 위에서 선언한 것 연결
        viewImage=(ImageView) findViewById(R.id.viewImage);  // 위에서 선언한 것 연결
        major=(TextView) findViewById(R.id.major);
        area=(TextView) findViewById(R.id.area);

        Intent it =getIntent();
        str_name= it.getStringExtra("it_name");
        str_stdno= it.getStringExtra("it_stdno");
        str_myPhone= it.getStringExtra("it_myPhone");
        str_grade= it.getStringExtra("it_grade");
        str_hobby= it.getStringExtra("it_hobby");
        str_major= it.getStringExtra("it_major");
        str_area= it.getStringExtra("it_area");
        str_photoUri= it.getStringExtra("it_photoUri");

        name.setText(str_name);
        stdno.setText(str_stdno);
        myPhone.setText(str_myPhone);
        garde.setText(str_grade);
        hobby.setText(str_hobby);
        major.setText(str_major);
        area.setText(str_area);

        photoUri=Uri.parse(str_photoUri);  // 문자열을 Uri로 바꿔줌
        viewImage.setImageURI(photoUri);


    }//onCreate

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
        else if(id==R.id.action_settings4){
            Intent it = new Intent(this, PersonRegist.class);
            startActivity(it);
            return true;

        }



        return super.onOptionsItemSelected(item);
    }

}//class