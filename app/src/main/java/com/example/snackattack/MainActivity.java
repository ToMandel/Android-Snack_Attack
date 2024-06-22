package com.example.snackattack;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private GameManager gameManager;
    private AppCompatImageView[] game_IMG_hearts;
    private AppCompatImageView[][] game_IMG_broccoli;
    private AppCompatImageView[] game_IMG_monster;
    private MaterialButton game_BTN_right;
    private MaterialButton game_BTN_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the mode from the intent
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        boolean isEndlessMode = "endless".equals(mode);

        gameManager = new GameManager(this, isEndlessMode);

        findViews();
        gameManager.setViews(game_IMG_hearts, game_IMG_broccoli, game_IMG_monster);
        initViews();
        gameManager.startGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameManager.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameManager.pauseGame();
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> gameManager.moveLeft());
        game_BTN_right.setOnClickListener(v -> gameManager.moveRight());
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                game_IMG_broccoli[j][i].setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_monster[0].setVisibility(View.INVISIBLE);
        game_IMG_monster[1].setVisibility(View.VISIBLE);
        game_IMG_monster[2].setVisibility(View.INVISIBLE);
    }
}
