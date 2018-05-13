package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.SudokuUtil;

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

            Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);

            // remove a random cell from Sudoku
            removeRandomCell(sudoku);

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

    private void removeRandomCell(Sudoku sudoku) {
        int position;

        do {
            position = rand.nextInt(81);

        } while (sudoku.cells[position].value != 0);

        sudokuList.add(SudokuUtil.removeNumber(sudoku, position));
    }

    public Sudoku getSudoku() {
        return sudokuList.get(sudokuList.size() - 1);
    }
}
