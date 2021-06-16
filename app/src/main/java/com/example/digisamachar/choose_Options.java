package com.example.digisamachar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class choose_Options extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    String concat;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_options);
        this.setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        radioGroup= (RadioGroup) findViewById(R.id.group);
    }

    public void radioButton(View view) {
        int id=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(id);
        concat=radioButton.getText().toString();
        Toast.makeText(this,concat+" Clicked",Toast.LENGTH_SHORT).show();
    }

    public void search(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("category",concat);
        startActivity(intent);
        finish();
    }
}