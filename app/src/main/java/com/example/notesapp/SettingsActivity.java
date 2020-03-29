package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    //UI
    private Toolbar mSettingsToolbar;
    private ImageView mBackArrowButton;

    //Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettingsToolbar=findViewById(R.id.settings_toolbar);
        mBackArrowButton=findViewById(R.id.settings_toolbar_back_arrow_button);

        setSupportActionBar(mSettingsToolbar);

        mBackArrowButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.settings_toolbar_back_arrow_button:{

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            }

        }

    }
}
