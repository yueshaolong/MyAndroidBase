package com.ysl.myandroidbase;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.ysl.myandroidbase.MyFragment.FragmentListener;
import com.ysl.myandroidbase.bean.Cat;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    public static final String TAG = "MainActivity";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate is invoke  ");



        /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", "ysl");
        fragment.setArguments(bundle);
        transaction.add(R.id.fragment, fragment);
        transaction.show(fragment);
        transaction.commit();*/

        findViewById(R.id.tv1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                /*intent.putExtra("name", "ysl");
                intent.putExtra("age", 22);*/

                Bundle bundle = new Bundle();
                /*bundle.putString("name1", "Mjj");
                bundle.putInt("age1", 18);*/
                Cat cat = new Cat();
                cat.name = "tom";
                cat.age = 1;
                bundle.putParcelable("cat", cat);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent is invoke");
    }

    public String getTitles(){
        return "getTitle";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState is invoke");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState is invoke");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart is invoke  ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart is invoke  ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume is invoke  ");

        /*if (onActivityDataChangedListener != null) {
            onActivityDataChangedListener.onActivityDataChanged("哈哈哈，activity的数据变了。lalala");
        } else {
            throw new IllegalArgumentException("fragment must invoke setOnActivityDataChangedListener()");
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause is invoke");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop is invoke");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke");
    }

    @Override
    public void process(String str) {
        Log.d(TAG, str);
    }

    private OnActivityDataChangedListener onActivityDataChangedListener;

    public interface OnActivityDataChangedListener {
        void onActivityDataChanged(String string);
    }

    public void setOnActivityDataChangedListener(OnActivityDataChangedListener onActivityDataChangedListener) {
        this.onActivityDataChangedListener = onActivityDataChangedListener;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected is invoke");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected is invoke");
        }
    };

}
