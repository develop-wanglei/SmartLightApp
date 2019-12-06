package com.atplatform.yuyenchia.ledcontrol;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;

public class MainActivity extends AppCompatActivity {
    public AVObject Led_Data1;
    public AVObject Led_Data2;
    public AVObject Led_Data3;//数据库结构体
    SeekBar Led_seekbar;
    private int duty_cycle_pre = 0;
    private int duty_cycle_now = 0;
    private int duty_cycle_temp = 0;//PWM占空比状态
    private long TimeTicket = 0;
    private Handler LoopHandler = new Handler();
    private Switch Led_bus_1, Led_bus_2, Led_bus_3;
    TextView View_duty_cycle;
    ImageView bus1_on,bus1_off;
    ImageView bus2_on,bus2_off;
    ImageView bus3_on,bus3_off;//灯状态图片
    private boolean toaststate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Led_bus_1=findViewById(R.id.switch1);
        Led_bus_2=findViewById(R.id.switch2);
        Led_bus_3=findViewById(R.id.switch3);
        Led_seekbar=findViewById(R.id.seekBar);
        View_duty_cycle=findViewById(R.id.textView2);
        bus1_off=findViewById(R.id.imageView);
        bus1_on=findViewById(R.id.imageView2);
        bus2_off=findViewById(R.id.imageView3);
        bus2_on=findViewById(R.id.imageView4);
        bus3_off=findViewById(R.id.imageView5);
        bus3_on=findViewById(R.id.imageView6);

        bus1_on.setVisibility(View.INVISIBLE);
        bus1_off.setVisibility(View.VISIBLE);
        bus2_on.setVisibility(View.INVISIBLE);
        bus2_off.setVisibility(View.VISIBLE);
        bus3_on.setVisibility(View.INVISIBLE);
        bus3_off.setVisibility(View.VISIBLE);

        Led_Data1 = AVObject.createWithoutData("LEDS", "5de75866dd3c13007f5e129a");
        Led_Data2 = AVObject.createWithoutData("LEDS", "5de7686921b47e007fb43925");
        Led_Data3 = AVObject.createWithoutData("LEDS", "5de7686f21b47e007fb43928");
        LoopHandler.post(looper);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Led_bus_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Led_Data1.put("LED_STATE", false);
                    Led_Data1.saveInBackground();
                    bus1_on.setVisibility(View.VISIBLE);
                    bus1_off.setVisibility(View.INVISIBLE);
                } else {
                    Led_Data1.put("LED_STATE", true);
                    Led_Data1.saveInBackground();
                    bus1_on.setVisibility(View.INVISIBLE);
                    bus1_off.setVisibility(View.VISIBLE);
                }

            }
        });
        Led_bus_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Led_Data2.put("LED_STATE", false);
                    Led_Data2.saveInBackground();
                    bus2_on.setVisibility(View.VISIBLE);
                    bus2_off.setVisibility(View.INVISIBLE);
                } else {
                    Led_Data2.put("LED_STATE", true);
                    Led_Data2.saveInBackground();
                    bus2_on.setVisibility(View.INVISIBLE);
                    bus2_off.setVisibility(View.VISIBLE);
                }

            }
        });
        Led_bus_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Led_Data3.put("LED_STATE", false);
                    Led_Data3.saveInBackground();
                    bus3_on.setVisibility(View.VISIBLE);
                    bus3_off.setVisibility(View.INVISIBLE);
                } else {
                    Led_Data3.put("LED_STATE", true);
                    Led_Data3.saveInBackground();
                    bus3_on.setVisibility(View.INVISIBLE);
                    bus3_off.setVisibility(View.VISIBLE);
                }

            }
        });
        Led_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                duty_cycle_temp = progress;
                //SMART_LED_SCAN.Upload_duty_cycle(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private Runnable looper = new Runnable() {
        @Override
        public void run() {
            TimeTicket++;
            if (TimeTicket > 9999) {
                TimeTicket = 0;
            }
            if (TimeTicket % 300 == 0) {
                duty_cycle_now = duty_cycle_temp;
                if (duty_cycle_pre != duty_cycle_now) {
                    Led_Data1.put("DUTY_CYCLE", duty_cycle_temp);
                    Led_Data1.saveInBackground();
                }
                duty_cycle_pre = duty_cycle_now;
            }
            View_duty_cycle.setText(String.valueOf(duty_cycle_temp));
            LoopHandler.postDelayed(looper, 1);
        }
    };
}











