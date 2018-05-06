package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.Utils;

import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    private List<Sudoku> sudokuList;
    private int difficulty;
    private int score;
    private Random rand;

    public SudokuGenerator() {
        difficulty = 100;
        score = 0;
        rand = new Random();
    }

    public Sudoku createSudoku(Sudoku grid) {

        sudokuList.add(grid);

        // while reaching desired difficulty
        while (score > difficulty) {

            // remove a random cell from Sudoku
            removeRandomCell();

            // test if it is still valid Sudoku
            SudokuSolver sudokuSolver = new SudokuSolver(getSudoku());
            sudokuSolver.solve();

            if (sudokuSolver.isValid()) {
                // get difficulty score
                score = sudokuSolver.getDifficulty();

            } else {  // if not a valid Sudoku
                // go back
                sudokuList.remove(sudokuList.size() - 1);
            }
        }

        return getSudoku();
    }

    private void removeRandomCell() {
        int position;
        Sudoku sudoku = getSudoku();

        do {
            position = rand.nextInt(81);

        } while (sudoku.cells[position].value != 0);

        sudokuList.add(Utils.removeNumber(sudoku, position));
    }

    public Sudoku getSudoku() {
        return sudokuList.get(sudokuList.size() - 1);
    }
}
