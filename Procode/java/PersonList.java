package com.example.dbpersonregistapp2026;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.dbpersonregistapp2026.databinding.ActivityListBinding;

public class PersonList extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityListBinding binding;

    DBManager dbManager;
    SQLiteDatabase sqLite;

    String str_name="",str_stdno="",str_grade="",str_major="",str_phone="";
    LinearLayout membership;

    TextView[] tv = new TextView[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        membership = (LinearLayout) findViewById(R.id.membership);

        try{
            dbManager = new DBManager(this);
            sqLite = dbManager.getReadableDatabase();
            Cursor cursor = sqLite.rawQuery("select * from member",null,null);

            int i=0;

            while(cursor.moveToNext()){
                int id_name = cursor.getColumnIndex("name");
                str_name = cursor.getString(id_name);
                //str_name = cursor.getString(cursor.getColumnIndex("name"));

                int id_major = cursor.getColumnIndex("major");
                str_major = cursor.getString(id_major);

                int id_stdno = cursor.getColumnIndex("stdno");
                str_stdno = cursor.getString(id_stdno);

                int id_grade = cursor.getColumnIndex("grade");
                str_grade = cursor.getString(id_grade);

                int id_phone = cursor.getColumnIndex("phone");
                str_phone = cursor.getString(id_phone);

                LinearLayout layout = new LinearLayout(this);
                layout.setId(i);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(20,10,20,10);
                layout.setTag(str_name);

                //이름
                TextView tv_name = new TextView(this);
                tv_name.setTextSize(30);
                tv_name.setBackgroundColor(Color.argb(50,0,255,0));
                tv_name.setText(str_name);
                tv_name.setId(i);
                layout.addView(tv_name);
                tv[i]=tv_name;
                tv[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(PersonList.this,PersonDetail.class);
                        it.putExtra("it_name",tv_name.getText().toString());
                        startActivity(it);
                    }
                });//회원 상세정보 이동

                //전공
                TextView tv_major = new TextView(this);
                tv_major.setTextSize(20);
                tv_major.setBackgroundColor(Color.argb(10,0,0,0));
                tv_major.setText(str_major);
                tv_major.setId(i);
                layout.addView(tv_major);

                //학번
                TextView tv_stdno = new TextView(this);
                tv_stdno.setTextSize(20);
                tv_stdno.setBackgroundColor(Color.argb(10,0,0,0));
                tv_stdno.setText(str_stdno);
                tv_stdno.setId(i);
                layout.addView(tv_stdno);

                //학년
                TextView tv_grade = new TextView(this);
                tv_grade.setTextSize(20);
                tv_grade.setBackgroundColor(Color.argb(10,0,0,0));
                tv_grade.setText(str_grade);
                tv_grade.setId(i);
                layout.addView(tv_grade);

                //전화번호
                TextView tv_phone = new TextView(this);
                tv_phone.setTextSize(20);
                tv_phone.setBackgroundColor(Color.argb(10,0,0,0));
                tv_phone.setText(str_phone);
                tv_phone.setId(i);
                layout.addView(tv_phone);

                membership.addView(layout);
                i=i+1;
            } //while

            sqLite.close();
            dbManager.close();

        }catch(SQLException e){
            Toast.makeText(getApplicationContext(),"DB 접근 오류",Toast.LENGTH_SHORT).show();
        }
    } //onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setTitle("전체 회원 조회");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings2) {
            Intent it = new Intent(this,PersonRegist.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}//class