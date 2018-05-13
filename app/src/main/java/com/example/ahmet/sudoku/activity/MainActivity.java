package com.example.ahmet.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ahmet.sudoku.GridGenerator;
import com.example.ahmet.sudoku.R;
import com.example.ahmet.sudoku.model.Sudoku;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playSudoku(View view) {
        int[] sudokuNumbers;

        switch (view.getId()) {
            case R.id.easy_button:
                sudokuNumbers = new int[]{
                        3, 0, 0, 5, 7, 0, 0, 0, 9,
                        0, 0, 1, 0, 0, 9, 3, 8, 0,
                        5, 0, 2, 4, 0, 0, 7, 0, 0,
                        7, 0, 5, 0, 8, 0, 1, 0, 0,
                        0, 4, 0, 2, 0, 0, 0, 6, 0,
                        0, 0, 9, 7, 0, 3, 5, 0, 8,
                        0, 0, 8, 0, 0, 6, 2, 0, 3,
                        0, 2, 4, 3, 0, 0, 8, 0, 0,
                        1, 0, 0, 0, 9, 2, 0, 0, 4};
                break;
            case R.id.medium_button:
                sudokuNumbers = new int[]{
                        0, 0, 8, 5, 0, 0, 9, 3, 0,
                        1, 0, 0, 0, 0, 8, 0, 0, 7,
                        0, 0, 5, 4, 0, 0, 0, 0, 6,
                        0, 6, 0, 0, 0, 9, 0, 7, 0,
                        0, 0, 4, 0, 5, 3, 6, 0, 0,
                        0, 1, 0, 0, 0, 7, 0, 2, 0,
                        9, 0, 0, 0, 0, 6, 3, 0, 0,
                        4, 0, 0, 8, 0, 0, 0, 0, 2,
                        0, 2, 7, 0, 0, 4, 1, 0, 0};
                break;
            case R.id.hard_button:
                sudokuNumbers = new int[]{
                        7, 0, 0, 0, 5, 0, 0, 0, 2,
                        0, 0, 0, 0, 0, 7, 0, 1, 0,
                        0, 5, 0, 0, 2, 0, 0, 0, 3,
                        6, 0, 0, 0, 3, 0, 0, 5, 0,
                        0, 1, 0, 0, 8, 0, 0, 4, 0,
                        0, 8, 0, 0, 7, 9, 0, 0, 6,
                        1, 0, 0, 0, 6, 0, 0, 9, 0,
                        0, 4, 0, 5, 0, 0, 0, 0, 0,
                        2, 0, 0, 0, 1, 0, 0, 0, 7};
                break;
            default:
                sudokuNumbers = new int[81];
        }

        Sudoku sudoku = new Sudoku(sudokuNumbers);

        //Sudoku sudoku = GridGenerator.createGrid();

        Intent intent = new Intent(MainActivity.this, SudokuActivity.class);
        intent.putExtra(SudokuActivity.SUDOKU_INTENT, sudoku);
        startActivity(intent);
    }
}
