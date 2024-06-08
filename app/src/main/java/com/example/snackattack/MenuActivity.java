package com.example.snackattack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {
    private AppCompatImageView menu_IMG_background;
    private MaterialButton menu_BTN_reg;
    private MaterialButton menu_BTN_endless;
    private MaterialButton menu_BTN_exit;
    private MaterialTextView menu_LBL_title;
    private MaterialTextView menu_LBL_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();
    }

    private void findViews() {
        menu_LBL_title = findViewById(R.id.menu_LBL_title);
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_LBL_mode = findViewById(R.id.menu_LBL_mode);
        menu_BTN_reg = findViewById(R.id.menu_BTN_regular);
        menu_BTN_endless = findViewById(R.id.menu_BTN_endless);
        menu_BTN_exit = findViewById(R.id.menu_BTN_exit);

    }

    private void initViews() {
        menu_BTN_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("mode", "regular");
                startActivity(intent);
            }
        });
        menu_BTN_endless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("mode", "endless");
                startActivity(intent);
            }
        });

        menu_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });


    }

}