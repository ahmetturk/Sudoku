package com.example.ahmet.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ahmet.sudoku.GridGenerator;
import com.example.ahmet.sudoku.R;
import com.example.ahmet.sudoku.SudokuGenerator;
import com.example.ahmet.sudoku.model.Sudoku;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playSudoku(View view) {
        Sudoku grid = GridGenerator.createGrid();
        SudokuGenerator sudokuGenerator = new SudokuGenerator();

        int difficulty = 0;

        switch (view.getId()) {
            case R.id.easy_button:
                difficulty = 40;
                break;
            case R.id.medium_button:
                difficulty = 100;
                break;
            case R.id.hard_button:
                difficulty = 500;
                break;
        }

        Sudoku sudoku = sudokuGenerator.createSudoku(grid, difficulty);

        Intent intent = new Intent(MainActivity.this, SudokuActivity.class);
        intent.putExtra(SudokuActivity.SUDOKU_INTENT, sudoku);
        startActivity(intent);
    }
}
