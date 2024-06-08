package com.example.snackattack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final int DELAY = 400;
    private AppCompatImageView game_IMG_background;
    private AppCompatImageView[] game_IMG_hearts;
    private AppCompatImageView[][] game_IMG_broccoli;
    private AppCompatImageView[] game_IMG_monster;
    private MaterialButton game_BTN_right;
    private MaterialButton game_BTN_left;
    private int lives = 3;
    private final int ROWS = 5, COLS = 3;

    private enum direction {right, left}

    private boolean pause = false;
    private Random rn = new Random();
    private boolean spawn = true;
    private boolean isEndlessMode = false; // Flag for endless mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the mode from the intent
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if ("endless".equals(mode)) {
            isEndlessMode = true;
        }
        findViews();
        initViews();
        updateUI();
    }

    private void updateUI() {
        updateLives();
        moveDownUI();
        vegFromSky();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        pause = true;
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> moveLeft());
        game_BTN_right.setOnClickListener(v -> moveRight());
    }

    private void findViews() {
        game_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };
        game_IMG_broccoli = new AppCompatImageView[][]{
                {findViewById(R.id.game_IMG_brocco1), findViewById(R.id.game_IMG_brocco2), findViewById(R.id.game_IMG_brocco3)},
                {findViewById(R.id.game_IMG_brocco4), findViewById(R.id.game_IMG_brocco5), findViewById(R.id.game_IMG_brocco6)},
                {findViewById(R.id.game_IMG_brocco7), findViewById(R.id.game_IMG_brocco8), findViewById(R.id.game_IMG_brocco9)},
                {findViewById(R.id.game_IMG_brocco10), findViewById(R.id.game_IMG_brocco11), findViewById(R.id.game_IMG_brocco12)},
                {findViewById(R.id.game_IMG_brocco13), findViewById(R.id.game_IMG_brocco14), findViewById(R.id.game_IMG_brocco15)},
                {findViewById(R.id.game_IMG_brocco16), findViewById(R.id.game_IMG_brocco17), findViewById(R.id.game_IMG_brocco18)}
        };
        game_IMG_monster = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_monster1),
                findViewById(R.id.game_IMG_monster2),
                findViewById(R.id.game_IMG_monster3)
        };
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_IMG_background = findViewById(R.id.game_IMG_background);

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_broccoli[j][i].setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_monster[0].setVisibility(View.INVISIBLE);
        game_IMG_monster[1].setVisibility(View.VISIBLE);
        game_IMG_monster[2].setVisibility(View.INVISIBLE);
    }

    private void moveRight() {
        if (game_IMG_monster[2].getVisibility() != View.VISIBLE) {
            if (game_IMG_monster[0].getVisibility() == View.VISIBLE) {
                updateLocationUI(1, direction.right);
            } else {
                updateLocationUI(2, direction.right);
            }
        }
    }

    private void moveLeft() {
        if (game_IMG_monster[0].getVisibility() != View.VISIBLE) {
            if (game_IMG_monster[1].getVisibility() == View.VISIBLE) {
                updateLocationUI(0, direction.left);
            } else {
                updateLocationUI(1, direction.left);
            }
        }
    }

    private void reduceLives() {
        if (!isEndlessMode) { // Only reduce lives in regular mode
            lives--;
        }
    }

    private void updateLives() {
        for (int i = 0; i < lives; i++) {
            game_IMG_hearts[i].setVisibility(View.VISIBLE);
        }

        for (int i = lives; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
        if (lives == 0 && !isEndlessMode) gameOver(); // Only end game in regular mode
    }

    private void gameOver() {
        stopTimer();
        pause = true;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_broccoli[j][i].setVisibility(View.INVISIBLE);
            }
        }
        Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
        startActivity(intent);
    }

    private final Handler handler = new Handler();
    private final Runnable runnable1 = new Runnable() {
        public void run() {
            handler.postDelayed(runnable1, DELAY);
            updateUI();
        }
    };

    private void updateLocationUI(int location, direction direction) {
        if (direction == direction.right) {
            game_IMG_monster[location - 1].setVisibility(View.INVISIBLE);
            game_IMG_monster[location].setVisibility(View.VISIBLE);
        } else {
            game_IMG_monster[location + 1].setVisibility(View.INVISIBLE);
            game_IMG_monster[location].setVisibility(View.VISIBLE);
        }
        isCrash();
    }

    private void isCrash() {
        for (int i = 0; i < COLS; i++) {
            if (game_IMG_broccoli[ROWS][i].getVisibility() == View.VISIBLE && game_IMG_monster[i].getVisibility() == View.VISIBLE) {
                reduceLives();
                vibrate();
                Toast.makeText(this, "BAHHH", Toast.LENGTH_SHORT).show();
                updateLives();
            }
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void vegFromSky() {
        if (spawn) {
            int i = rn.nextInt(3);
            game_IMG_broccoli[0][i].setVisibility(View.VISIBLE);
        }
        spawn = !spawn;
    }

    private void moveDownUI() {
        if (!pause) {
            for (int j = 0; j < COLS; j++) {
                game_IMG_broccoli[ROWS][j].setVisibility(View.INVISIBLE);
            }
            for (int j = 0; j < COLS; j++) {
                for (int i = ROWS; i > 0; i--) {
                    if (game_IMG_broccoli[i - 1][j].getVisibility() == View.VISIBLE) {
                        game_IMG_broccoli[i - 1][j].setVisibility(View.INVISIBLE);
                        game_IMG_broccoli[i][j].setVisibility(View.VISIBLE);
                    }
                }
            }
            isCrash();
        }
    }

    private void startTimer() {
        handler.postDelayed(runnable1, DELAY);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable1);
    }
}
