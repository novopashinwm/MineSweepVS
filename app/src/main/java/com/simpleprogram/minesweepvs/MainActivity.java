package com.simpleprogram.minesweepvs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private final int COLS = 16;
    private final int ROWS = 16;
    private final int BOMBS = 40;
    private final int IMAGE_SIZE = 32;
   GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.grid);


        ImageView oImageView = new ImageView(this);
        oImageView.setImageResource(R.drawable.bomb_exploded);
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
        param.width = GridLayout.LayoutParams.WRAP_CONTENT;
        param.rightMargin = 5;
        param.topMargin = 5;
        param.setGravity(Gravity.CENTER);
        param.columnSpec = GridLayout.spec(0);
        param.rowSpec = GridLayout.spec(0);
        oImageView.setLayoutParams (param);
        gridLayout.addView(oImageView);

    }
}
