package com.example.dbpersonregistapp2026jangan;

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

import com.example.dbpersonregistapp2026jangan.databinding.ActivityRegistBinding;

public class PersonRegist extends AppCompatActivity {

    DBManager dbManager;
    SQLiteDatabase sqLiteDB;

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegistBinding binding;

    public final static int REQUEST_PHOTO_CODE=1;

    String[] majorlist={"영상미디어컨텐츠", "컴퓨터공학","사회복지","항공운항","게임컨텐츠"};
    String[] arealist={"서울", "수원","화성","인천","그외 지역"};
    Spinner major,area;

    EditText name,stdno,myPhone;
    RadioGroup garde;
    RadioButton one,two,three;
    CheckBox hobby01,hobby02,hobby03;
    Button findImage,registBtn;
    ImageView viewImage;

    String str_name,str_stdno,str_myPhone,str_grade,str_one,str_two,str_three;
    String str_hobby,str_hobby01,str_hobby02,str_hobby03,str_uri;  // uri = SD 카드에 있는 이미지를 문자열로 보내는 것
    String str_major,str_area;
    String str_photoUri;

    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        binding = ActivityRegistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        major=(Spinner) findViewById(R.id.major);   // 전공 스피너 요소 불러오기
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,majorlist);  // 스피너에 들어갈 데이터를 연결하는 어뎁터
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);   // 드롭다운 리스트 디자인
        major.setAdapter(adapter);   // 스피너에 어뎁터 연결

        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {  //   사용자가 선택한 값을 i로 가져온다.
                str_major=majorlist[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  // 아무 것도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(),"전공을 선택하세요!",Toast.LENGTH_SHORT).show();  //Toast.LENGTH_SHORT(보여지는 시간 설정. 지금은 짧다.)

            }
        });//전공선택


        area=(Spinner) findViewById(R.id.area);   // 거주지 스피너 요소 불러오기
        ArrayAdapter arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arealist);  // 스피너에 들어갈 데이터를 연결하는 어뎁터
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);   // 드롭다운 리스트 디자인
        area.setAdapter(arrayAdapter);   // 스피너에 어뎁터 연결

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  //   바로 위의 major와 같은 개념
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_area=arealist[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"거주지를 선택하세요!",Toast.LENGTH_SHORT).show();  //Toast.LENGTH_SHORT(보여지는 시간 설정. 지금은 짧다.)
            }
        });//거주지선택

        name=(EditText) findViewById(R.id.name);  // 위에서 선언한 것 연결
        stdno=(EditText) findViewById(R.id.stdno);  // 위에서 선언한 것 연결
        garde=(RadioGroup) findViewById(R.id.grade);  // 위에서 선언한 것 연결
        myPhone=(EditText) findViewById(R.id.myPhone);  // 위에서 선언한 것 연결
        one=(RadioButton) findViewById(R.id.one);  // 위에서 선언한 것 연결
        two=(RadioButton) findViewById(R.id.two);  // 위에서 선언한 것 연결
        three=(RadioButton) findViewById(R.id.three);  // 위에서 선언한 것 연결
        hobby01=(CheckBox) findViewById(R.id.hobby01);  // 위에서 선언한 것 연결
        hobby02=(CheckBox) findViewById(R.id.hobby02);  // 위에서 선언한 것 연결
        hobby03=(CheckBox) findViewById(R.id.hobby03);  // 위에서 선언한 것 연결
        findImage=(Button) findViewById(R.id.findImage);  // 위에서 선언한 것 연결
        registBtn=(Button) findViewById(R.id.registBtn);  // 위에서 선언한 것 연결
        viewImage=(ImageView) findViewById(R.id.viewImage);  // 위에서 선언한 것 연결

        findImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iit=new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                iit.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(iit,REQUEST_PHOTO_CODE);{ // 상수를 가지고 나와야 함(상수 이름 REQUEST_PHOTO_CODE)
                }



            }
        });//사진 선택




        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_name=name.getText().toString();  // 사용자가 작성한 이름 저장
                str_stdno=stdno.getText().toString();  // 사용자가 작성한 학번 저장
                str_myPhone=myPhone.getText().toString(); // 사용자가 작성한 전화번호 저장

                if(one.isChecked())  // one이 체크 되었는지 확인하는게 'isChecked()'
                    str_grade=one.getText().toString();
                else if(two.isChecked())
                    str_grade=two.getText().toString();
                else
                    str_grade=three.getText().toString();

                str_hobby="";  // 취미
                if(hobby01.isChecked()) str_hobby=str_hobby+hobby01.getText().toString()+" ";
                if(hobby02.isChecked()) str_hobby=str_hobby+hobby02.getText().toString()+" ";
                if(hobby03.isChecked()) str_hobby=str_hobby+hobby03.getText().toString()+" ";

                str_photoUri=photoUri.toString();

                try {  // 지켜본다
                    dbManager= new DBManager(PersonRegist.this);
                    sqLiteDB=dbManager.getWritableDatabase();

                    ContentValues valuse=new ContentValues();
                    valuse.put("name",str_name);
                    valuse.put("major",str_major);
                    valuse.put("stdno",str_stdno);
                    valuse.put("gread",str_grade);
                    valuse.put("phone",str_myPhone);
                    valuse.put("area",str_area);
                    valuse.put("hobby",str_hobby);

                    long newrow=sqLiteDB.insert("member",null,valuse);
                    sqLiteDB.close();
                    dbManager.close();
                    Toast.makeText(getApplicationContext(),"회원추가성공", Toast.LENGTH_SHORT).show();


                }catch(SQLException e){  // 오류가 생기면 catch가 잡는다.
                    Toast.makeText(getApplicationContext(),"회원추가실패", Toast.LENGTH_SHORT).show();
                }

                Intent it=new Intent(PersonRegist.this,PersonInfo.class);  // 정보를 보여주는 화면으로 이동

                it.putExtra("it_name",str_name);
                it.putExtra("it_stdno",str_stdno);
                it.putExtra("it_myPhone",str_myPhone);
                it.putExtra("it_grade",str_grade);
                it.putExtra("it_hobby",str_hobby);
                it.putExtra("it_major",str_major);
                it.putExtra("it_area",str_area);
                it.putExtra("it_photoUri",str_photoUri);

                startActivity(it);  // 이동

            }
        });//등록버튼

        //str_grade,str_one,str_two,str_three;
        //str_hobby01,str_hobby02,str_hobby03,str_uri;


    }//onCreate


    public void onActivityResult(int requestcode, int resultcode, Intent it){  //이거 시험에 나올수도?
        super.onActivityResult(requestcode,resultcode,it);  // 생성자
        if(requestcode==REQUEST_PHOTO_CODE){
            photoUri=it.getData();
            viewImage.setImageURI(photoUri);

        }
        else{
            Toast.makeText(getApplicationContext(),"사진 선택 오류",Toast.LENGTH_SHORT).show();
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

}//class