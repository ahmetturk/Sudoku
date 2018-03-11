package com.example.ahmet.sudoku.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ahmet.sudoku.R;
import com.example.ahmet.sudoku.Sudoku;
import com.example.ahmet.sudoku.SudokuAdapter;
import com.example.ahmet.sudoku.SudokuSolver;

public class SudokuActivity extends AppCompatActivity {

    public static final String SUDOKU_INTENT = "sudoku";

    private RecyclerView recyclerView;
    private SudokuSolver sudokuSolver;
    private SudokuAdapter sudokuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        recyclerView = findViewById(R.id.sudoku_grid);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 9);
        recyclerView.setLayoutManager(layoutManager);

        Sudoku sudoku = getIntent().getParcelableExtra(SUDOKU_INTENT);

        sudokuSolver = new SudokuSolver(sudoku);
        sudokuAdapter = new SudokuAdapter(sudokuSolver.getSudoku());
        recyclerView.setAdapter(sudokuAdapter);

        Button solveButton = findViewById(R.id.solve_button);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudokuSolver.solve();
                sudokuAdapter.setSudoku(sudokuSolver.getSudoku());
            }
        });

        Button stepButton = findViewById(R.id.step_button);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudokuSolver.step();
                sudokuAdapter.setSudoku(sudokuSolver.getSudoku());
            }
        });
    }
}
