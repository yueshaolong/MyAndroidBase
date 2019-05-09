package com.ysl.myandroidbase.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysl.myandroidbase.activity.MainActivity.OnActivityDataChangedListener;

public class MyFragment extends Fragment {
    public static final String TAG = "MyFragment";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach is invoke");
        /*if (isAdded()){
            String id = getArguments().getString("id");
            Log.d(TAG, "onAttach is invoke "+id);
        }*/

        String titles = ((MainActivity) context).getTitles();//通过强转成宿主activity，就可以获取到传递过来的数据
        Log.d(TAG, "onAttach is invoke context "+titles);
        String titles1 = ((MainActivity)getActivity()).getTitles();
        Log.d(TAG, "onAttach is invoke getActivity "+titles1);

        if(context instanceof FragmentListener) {
            listener = (FragmentListener)context;
        } else{
            throw new IllegalArgumentException("activity must implements FragmentListener");
        }

        ((MainActivity)getActivity()).setOnActivityDataChangedListener(new OnActivityDataChangedListener() {
            @Override
            public void onActivityDataChanged(String string) {
                Log.d(TAG, "收到activity的数据："+string);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate is invoke");
        /*if (isAdded()){
            String id = getArguments().getString("id");
            Log.d(TAG, "onCreate is invoke "+id);
        }*/
        String titles = ((MainActivity)getActivity()).getTitles();
        Log.d(TAG, "onAttach is invoke "+titles);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView is invoke");
        /*if (isAdded()){
            String id = getArguments().getString("id");
            Log.d(TAG, "onCreateView is invoke "+id);
        }*/
        String titles = ((MainActivity)getActivity()).getTitles();
        Log.d(TAG, "onAttach is invoke "+titles);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated is invoke");
        /*if (isAdded()){
            String id = getArguments().getString("id");
            Log.d(TAG, "onActivityCreated is invoke "+id);
        }*/
        String titles = ((MainActivity)getActivity()).getTitles();
        Log.d(TAG, "onAttach is invoke "+titles);

        listener.process("我是接口，在传数据呢。");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart is invoke");
        /*if (isAdded()){
            String id = getArguments().getString("id");
            Log.d(TAG, "onStart is invoke "+id);
        }*/
        String titles = ((MainActivity)getActivity()).getTitles();
        Log.d(TAG, "onAttach is invoke "+titles);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume is invoke");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause is invoke");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop is invoke");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView is invoke");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke");
        listener = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach is invoke");
    }

    private FragmentListener listener;
    public interface FragmentListener{
        void process(String str);
    }

}

