package com.example.ahmet.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SudokuSolver sudokuSolver;
    private SudokuAdapter sudokuAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.sudoku_grid);

        Button solveButton = (Button) findViewById(R.id.solve_button);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudokuSolver.solve();
                sudokuAdapter.setSudoku(sudokuSolver.getSudoku());
            }
        });

        Button easyButton = (Button) findViewById(R.id.easy_button);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] sudokuNumbers = {3, 0, 0, 5, 7, 0, 0, 0, 9,
                        0, 0, 1, 0, 0, 9, 3, 8, 0,
                        5, 0, 2, 4, 0, 0, 7, 0, 0,
                        7, 0, 5, 0, 8, 0, 1, 0, 0,
                        0, 4, 0, 2, 0, 0, 0, 6, 0,
                        0, 0, 9, 7, 0, 3, 5, 0, 8,
                        0, 0, 8, 0, 0, 6, 2, 0, 3,
                        0, 2, 4, 3, 0, 0, 8, 0, 0,
                        1, 0, 0, 0, 9, 2, 0, 0, 4};
                Sudoku sudoku = new Sudoku(sudokuNumbers);
                sudokuSolver = new SudokuSolver(sudoku);
                sudokuAdapter = new SudokuAdapter(sudokuSolver.getSudoku());
                gridView.setAdapter(sudokuAdapter);
            }
        });

        Button mediumButton = (Button) findViewById(R.id.medium_button);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] sudokuNumbers = {0, 0, 8, 5, 0, 0, 9, 3, 0,
                        1, 0, 0, 0, 0, 8, 0, 0, 7,
                        0, 0, 5, 4, 0, 0, 0, 0, 6,
                        0, 6, 0, 0, 0, 9, 0, 7, 0,
                        0, 0, 4, 0, 5, 3, 6, 0, 0,
                        0, 1, 0, 0, 0, 7, 0, 2, 0,
                        9, 0, 0, 0, 0, 6, 3, 0, 0,
                        4, 0, 0, 8, 0, 0, 0, 0, 2,
                        0, 2, 7, 0, 0, 4, 1, 0, 0};
                Sudoku sudoku = new Sudoku(sudokuNumbers);
                sudokuSolver = new SudokuSolver(sudoku);
                sudokuAdapter = new SudokuAdapter(sudokuSolver.getSudoku());
                gridView.setAdapter(sudokuAdapter);
            }
        });

        Button hardButton = (Button) findViewById(R.id.hard_button);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] sudokuNumbers = {7, 0, 0, 0, 5, 0, 0, 0, 2,
                        0, 0, 0, 0, 0, 7, 0, 1, 0,
                        0, 5, 0, 0, 2, 0, 0, 0, 3,
                        6, 0, 0, 0, 3, 0, 0, 5, 0,
                        0, 1, 0, 0, 8, 0, 0, 4, 0,
                        0, 8, 0, 0, 7, 9, 0, 0, 6,
                        1, 0, 0, 0, 6, 0, 0, 9, 0,
                        0, 4, 0, 5, 0, 0, 0, 0, 0,
                        2, 0, 0, 0, 1, 0, 0, 0, 7};
                Sudoku sudoku = new Sudoku(sudokuNumbers);
                sudokuSolver = new SudokuSolver(sudoku);
                sudokuAdapter = new SudokuAdapter(sudokuSolver.getSudoku());
                gridView.setAdapter(sudokuAdapter);
            }
        });
    }
}
