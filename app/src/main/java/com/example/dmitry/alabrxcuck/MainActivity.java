package com.example.dmitry.alabrxcuck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TaskInterface {

    TextView textView;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_joke);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        button = (Button) findViewById(R.id.btn_getJoke);
        RxFragment fragment = getFragment();
        if(fragment == null) {
            fragment = new RxFragment();
        }
        fragment.onAttach(this);
        RxFragment finalFragment = fragment;
        button.setOnClickListener(view -> {
            textView.setText("");
            progressBar.setVisibility(View.VISIBLE);
            finalFragment.getJoke();
        });

        if(savedInstanceState!=null) {
            textView.setText(savedInstanceState.getString("joke"));
            if (savedInstanceState.getInt("progress") == View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE); } else {
                progressBar.setVisibility(View.GONE);
            }
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private RxFragment getFragment(){
        RxFragment fragment = (RxFragment) getFragmentManager().findFragmentByTag(RxFragment.class.getName());
        if(fragment == null){
            fragment = new RxFragment();
        }
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("progress", progressBar.getVisibility());
        outState.putString("joke", textView.getText().toString());
    }

    @Override
    public void onTaskStart() {

    }

    @Override
    public void onFinish(String joke) {
        progressBar.setVisibility(View.GONE);
        textView.setText(joke);
    }
}
