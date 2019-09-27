package com.simpleprogram.minesweepvs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.simpleprogram.minesweepvs.View.MineView;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private final int COLS = 16;
    private final int ROWS = 16;
    private final int BOMBS = 40;
    public static final int IMAGE_SIZE = 16;
    private MineView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        grid = findViewById(R.id.grid);
        grid.setGame(game);
    }
}
