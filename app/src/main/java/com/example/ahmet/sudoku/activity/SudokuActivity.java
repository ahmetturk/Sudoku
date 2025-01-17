package com.example.ahmet.sudoku.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ahmet.sudoku.R;
import com.example.ahmet.sudoku.SudokuSolver;
import com.example.ahmet.sudoku.model.Sudoku;

public class SudokuActivity extends AppCompatActivity {

    public static final String SUDOKU_INTENT = "sudoku";

    private SudokuAdapter sudokuAdapter;
    private Sudoku sudoku;
    private Sudoku solution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        // recycler view shows sudoku
        RecyclerView recyclerView = findViewById(R.id.sudoku_grid);

        // grid layout manager makes 9 columns in recycler view
        GridLayoutManager layoutManager = new GridLayoutManager(this, 9);
        recyclerView.setLayoutManager(layoutManager);

        // receive sudoku model from main activity
        sudoku = getIntent().getParcelableExtra(SUDOKU_INTENT);

        SudokuSolver sudokuSolver = new SudokuSolver(sudoku);
        sudokuSolver.solve();
        solution = sudokuSolver.getSolution();

        // adapter populates views according to input sudoku
        sudokuAdapter = new SudokuAdapter(this);
        sudokuAdapter.setSudoku(sudoku);
        recyclerView.setAdapter(sudokuAdapter);
    }

    // writes pressed number to selected item_cell
    public void writeNumberToSudoku(View view) {

        int selected = sudokuAdapter.getSelected();
        Integer action;

        if (selected == -1) {
            return;
        }

        switch (view.getId()) {
            case R.id.button_one:
                action = 1;
                break;
            case R.id.button_two:
                action = 2;
                break;
            case R.id.button_three:
                action = 3;
                break;
            case R.id.button_four:
                action = 4;
                break;
            case R.id.button_five:
                action = 5;
                break;
            case R.id.button_six:
                action = 6;
                break;
            case R.id.button_seven:
                action = 7;
                break;
            case R.id.button_eight:
                action = 8;
                break;
            case R.id.button_nine:
                action = 9;
                break;
            case R.id.button_remove:
                action = 0;
                break;
            default:
                return;
        }

        sudokuAdapter.writeNumber(action);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sudoku_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_solve:
                sudokuAdapter.setSudoku(solution);
        }

        return super.onOptionsItemSelected(item);
    }
}
