package com.ysl.daojishi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;

import java.util.Timer;
import java.util.TimerTask;

public class DaoJiShiActivity extends AppCompatActivity {

    // 倒计时 天 时分秒
    private TextView mDays_Tv, mHours_Tv, mMinutes_Tv, mSeconds_Tv;
    private TextView tvDjsTime;
    private Timer mTimer;
    private long mDay = 0;// 天
    private long mHour = 0;//小时,
    private long mMin = 1;//分钟,
    private long mSecond = 32;//秒
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daojishi);

        mTimer = new Timer();
        tvDjsTime = findViewById(R.id.tv_djs_time);
        mDays_Tv = (TextView) findViewById(R.id.days_tv);
        mHours_Tv = (TextView) findViewById(R.id.hours_tv);
        mMinutes_Tv = (TextView) findViewById(R.id.minutes_tv);
        mSeconds_Tv = (TextView) findViewById(R.id.seconds_tv);
        startRun();

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(v -> countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onFinish() {
                if (button != null) {
                    button.setText("重新获取");
                    button.setTextColor(Color.parseColor("#E94715"));
                    button.setClickable(true);
                    button.setEnabled(true);
                }
                cancel();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                if (button != null) {
                    button.setClickable(false);
                    button.setEnabled(false);
                    button.setText(millisUntilFinished / 1000 + "s");
                    button.setTextColor(Color.parseColor("#999999"));
                }
            }
        }.start());
        findViewById(R.id.btn2).setOnClickListener(v -> {
            if(countDownTimer != null){
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

    }

    /**
     * 开启倒计时
     *  //time为Date类型：在指定时间执行一次。
     *        timer.schedule(task, time);
     *  //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     *       timer.schedule(task, firstTime,period);
     *  //delay 为long类型：从现在起过delay毫秒执行一次。
     *       timer.schedule(task, delay);
     *  //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     *       timer.schedule(task, delay,period);
     */
    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                tvDjsTime.setText(mDay + "" + "天  " + getTv(mHour) + ":" + getTv(mMin) + ":" + getTv(mSecond));
                mDays_Tv.setText(mDay+"");//天数不用补位
                mHours_Tv.setText(getTv(mHour));
                mMinutes_Tv.setText(getTv(mMin));
                mSeconds_Tv.setText(getTv(mSecond));
                if (mSecond == 0 &&  mDay == 0 && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if(mDay < 0){
                        // 倒计时结束
                        mDay = 0;
                        mHour= 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }
}
