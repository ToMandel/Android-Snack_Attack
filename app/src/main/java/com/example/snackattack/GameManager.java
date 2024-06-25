package com.example.snackattack;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class GameManager {
    private final int DELAY = 700;
    private int lives = 3;
    private final int ROWS = 5, COLS = 3;
    private boolean pause = false;
    private boolean spawn = true;
    private boolean isEndlessMode = false;

    private AppCompatImageView[] game_IMG_hearts;
    private AppCompatImageView[][] game_IMG_broccoli;
    private AppCompatImageView[] game_IMG_monster;

    private final Handler handler = new Handler();
    private final Runnable runnable1 = new Runnable() {
        public void run() {
            handler.postDelayed(runnable1, DELAY);
            updateUI();
        }
    };

    private Context context;
    private Random rn = new Random();

    public GameManager(Context context, boolean isEndlessMode) {
        this.context = context;
        this.isEndlessMode = isEndlessMode;
    }

    public void setViews(AppCompatImageView[] game_IMG_hearts, AppCompatImageView[][] game_IMG_broccoli, AppCompatImageView[] game_IMG_monster) {
        this.game_IMG_hearts = game_IMG_hearts;
        this.game_IMG_broccoli = game_IMG_broccoli;
        this.game_IMG_monster = game_IMG_monster;
    }

    public void startGame() {
        pause = false;
        handler.postDelayed(runnable1, DELAY);
    }

    public void pauseGame() {
        stopTimer();
        pause = true;
    }

    private void updateUI() {
        updateLives();
        moveDownUI();
        vegFromSky();
    }

    private void updateLives() {
        for (int i = 0; i < lives; i++) {
            game_IMG_hearts[i].setVisibility(View.VISIBLE);
        }

        for (int i = lives; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
        if (lives == 0 && !isEndlessMode) gameOver();
    }

    private void reduceLives() {
        if (!isEndlessMode) {
            lives--;
        }
    }

    private void gameOver() {
        stopTimer();
        pause = true;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_broccoli[j][i].setVisibility(View.INVISIBLE);
            }
        }
        Intent intent = new Intent(context, GameOverActivity.class);
        context.startActivity(intent);
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

    private void isCrash() {
        for (int i = 0; i < COLS; i++) {
            if (game_IMG_broccoli[ROWS][i].getVisibility() == View.VISIBLE && game_IMG_monster[i].getVisibility() == View.VISIBLE) {
                reduceLives();
                vibrate();
                Toast.makeText(context, "BAHHH", Toast.LENGTH_SHORT).show();
                updateLives();
            }
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable1);
    }

    public void moveRight() {
        if (game_IMG_monster[2].getVisibility() != View.VISIBLE) {
            if (game_IMG_monster[0].getVisibility() == View.VISIBLE) {
                updateLocationUI(1, direction.right);
            } else {
                updateLocationUI(2, direction.right);
            }
        }
    }

    public void moveLeft() {
        if (game_IMG_monster[0].getVisibility() != View.VISIBLE) {
            if (game_IMG_monster[1].getVisibility() == View.VISIBLE) {
                updateLocationUI(0, direction.left);
            } else {
                updateLocationUI(1, direction.left);
            }
        }
    }

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

    private enum direction {right, left}
}
