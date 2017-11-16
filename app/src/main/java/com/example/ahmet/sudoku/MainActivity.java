package com.example.ahmet.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SudokuSolver sudokuSolver;
    private SudokuAdapter sudokuAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.sudoku_grid);
        gridView.setVisibility(View.INVISIBLE);

        Button solveButton = (Button) findViewById(R.id.solve_button);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sudokuSolver != null) {
                    sudokuSolver.solve();
                    sudokuAdapter.setSudoku(sudokuSolver.getSudoku());
                } else {
                    Toast.makeText(MainActivity.this, "Select a Sudoku", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button stepButton = (Button) findViewById(R.id.step_button);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sudokuSolver != null) {
                    sudokuSolver.step();
                    sudokuAdapter.setSudoku(sudokuSolver.getSudoku());
                } else {
                    Toast.makeText(MainActivity.this, "Select a Sudoku", Toast.LENGTH_SHORT).show();
                }
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
                showSudoku(new Sudoku(sudokuNumbers));
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
                showSudoku(new Sudoku(sudokuNumbers));
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
                showSudoku(new Sudoku(sudokuNumbers));
            }
        });
    }

    private void showSudoku(Sudoku sudoku) {
        sudokuSolver = new SudokuSolver(sudoku);
        sudokuAdapter = new SudokuAdapter(sudokuSolver.getSudoku());
        gridView.setVisibility(View.VISIBLE);
        gridView.setAdapter(sudokuAdapter);
    }
}
