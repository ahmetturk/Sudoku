package com.example.ahmet.sudoku.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    }

    public void writeNumberToSudoku(View view) {
        String text;

        switch (view.getId()) {
            case R.id.button_one:
                text = getString(R.string.one);
                break;
            case R.id.button_two:
                text = getString(R.string.two);
                break;
            case R.id.button_three:
                text = getString(R.string.three);
                break;
            case R.id.button_four:
                text = getString(R.string.four);
                break;
            case R.id.button_five:
                text = getString(R.string.five);
                break;
            case R.id.button_six:
                text = getString(R.string.six);
                break;
            case R.id.button_seven:
                text = getString(R.string.seven);
                break;
            case R.id.button_eight:
                text = getString(R.string.eight);
                break;
            case R.id.button_nine:
                text = getString(R.string.nine);
                break;
            case R.id.button_remove:
                text = getString(R.string.remove);
                break;
            default:
                text = "default";
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
                sudokuSolver.solve();
                sudokuAdapter.setSudoku(sudokuSolver.getSudoku());
        }

        return super.onOptionsItemSelected(item);
    }
}
