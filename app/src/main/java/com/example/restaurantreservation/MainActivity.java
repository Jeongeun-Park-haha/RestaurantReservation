package com.example.restaurantreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.example.restaurantreservation.R;

public class MainActivity extends AppCompatActivity {
    String reserved_date;
    String reserved_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView cal = (CalendarView)findViewById(R.id.calendarView);
        //날짜 변경될 때 이벤트 받기 위한 리스너
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                EditText date = (EditText)findViewById(R.id.textDate);
                date.setText(year+"/"+(month+1)+"/"+dayOfMonth); //년, 월, 일
                reserved_date = Integer.toString(year) + "년" + Integer.toString(month+1)+"월"+ Integer.toString(dayOfMonth)+"일";
            }
        });

        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        //시간 변경될 때 이벤트 받기 위한 리스너
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                EditText time = (EditText)findViewById(R.id.textTime);
                time.setText(hourOfDay+":"+minute);
                reserved_time = Integer.toString(hourOfDay)+"시"+ Integer.toString(minute)+"분";
            }
        });

        Button button = (Button) findViewById(R.id.button);
        //예약버튼 눌렸을 때 이벤트 받기 위한 리스너
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(); //다이얼로그 함수 호출
            }
        });
    }


   //옵션메뉴
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reservmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.clear) {
            //현재 시간 담고있는 Calendar 객체
            final Calendar c = Calendar.getInstance();
            CalendarView cal = (CalendarView)findViewById(R.id.calendarView);
            TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
            EditText today = (EditText)findViewById(R.id.textDate);
            //시스템에서 현재 시간 가져오기
            long now = System.currentTimeMillis();
            //Date 객체에 현재 시간 저장
            Date date = new Date(now);
            //포맷을 문자열로 새로 만듬
            SimpleDateFormat simple_now = new SimpleDateFormat("yyyy/M/dd");
            String string_now = simple_now.format(date);
            today.setText(string_now);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            timePicker.setHour(hour);
            timePicker.setMinute(min);
        }
        return true;
    }

    //예약 다이얼로그 메세지 띄우기
    public void showMessage(){
        CalendarView cal = (CalendarView)findViewById(R.id.calendarView);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);

        //다이얼로그 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //속성 세팅
        builder.setMessage(reserved_date + reserved_time + "에 예약하시겠습니까?");
        //예약 눌렀을 때s
        builder.setPositiveButton("예약", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메세지 출력
                Toast.makeText(getApplicationContext(),reserved_date + reserved_time + "에 예약되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
        //취소 눌렀을 때
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메세지 출력
                Toast.makeText(getApplicationContext(), "예약이 취소되었습니다", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
