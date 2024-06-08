package com.example.snackattack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class GameOverActivity extends AppCompatActivity {
    private AppCompatImageView gameOver_IMG_background;
    private MaterialButton gameOver_BTN_yes;
    private MaterialButton gameOver_BTN_no;
    private MaterialTextView gameOver_LBL_title;
    private MaterialTextView gameOver_LBL_playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViews();
        initViews();
    }

    private void findViews() {
        gameOver_LBL_title = findViewById(R.id.gameover_LBL_title);
        gameOver_IMG_background = findViewById(R.id.gameOver_IMG_background);
        gameOver_LBL_playAgain = findViewById(R.id.gameover_LBL_again);
        gameOver_BTN_yes = findViewById(R.id.gameover_BTN_yes);
        gameOver_BTN_no = findViewById(R.id.gameover_BTN_no);

    }

    private void initViews() {
        gameOver_BTN_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });
        gameOver_BTN_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


    }

}