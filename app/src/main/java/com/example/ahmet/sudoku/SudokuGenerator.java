package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.SudokuUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    private List<Sudoku> sudokuList;
    private int score;
    private Random rand;

    public SudokuGenerator() {
        sudokuList = new ArrayList<>();
        score = 0;
        rand = new Random();
    }

    public Sudoku createSudoku(Sudoku grid, int difficulty) {

        int size = -1;

        sudokuList.add(grid);

        // while reaching desired difficulty
        while (score < difficulty) {

            // remove a random cell from Sudoku
            removeRandomCell();

            // test if it is still valid Sudoku
            SudokuSolver sudokuSolver = new SudokuSolver(getSudoku());
            sudokuSolver.solve();

            if (sudokuSolver.isValid()) {
                // get difficulty score
                score = sudokuSolver.getDifficulty();
                getSudoku().difficulty = score;

            } else {  // if not a valid Sudoku
                // go back
                if (size == sudokuList.size()) {
                    sudokuList.clear();
                    sudokuList.add(grid);
                } else {
                    size = sudokuList.size();
                    sudokuList.remove(sudokuList.size() - 1);
                }
            }
        }

        return getSudoku();
    }

    private void removeRandomCell() {
        Sudoku sudoku = getSudoku();

        int position;
        do {
            position = rand.nextInt(81);

        } while (sudoku.cells[position].value == 0);

        sudokuList.add(SudokuUtil.removeNumber(sudoku, position));
    }

    public Sudoku getSudoku() {
        return sudokuList.get(sudokuList.size() - 1);
    }
}
