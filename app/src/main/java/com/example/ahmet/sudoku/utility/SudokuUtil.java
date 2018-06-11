package com.example.ahmet.sudoku.utility;

import com.example.ahmet.sudoku.model.Sudoku;

import java.util.ArrayList;

public final class SudokuUtil {

    /**
     * Add value to Sudoku by cell position
     *
     * @param sudoku   Sudoku where the value be added
     * @param position Position of the cell
     * @param value    Value be added to the cell
     */
    public static void addValueToSudoku(Sudoku sudoku, int position, Integer value) {
        // Assign value to cell by position and clear the domain
        sudoku.cells[position].value = value;
        sudoku.cells[position].domain.clear();
        // Narrow domains of cells that share domain with this cell
        narrowDomain(sudoku, position, value);
    }

    public static Sudoku addNumber(Sudoku sudoku, int position, Integer value) {
        Sudoku newSudoku = new Sudoku();

        for (int i = 0; i < newSudoku.cells.length; i++) {
            // copy all variables from sudoku
            newSudoku.cells[i].value = sudoku.cells[i].value;
            newSudoku.cells[i].domain = new ArrayList<>(sudoku.cells[i].domain);
            newSudoku.cells[i].isConstant = sudoku.cells[i].isConstant;
        }

        newSudoku.difficulty = sudoku.difficulty + 1;
        if (sudoku.cells[position].domain.size() > 1) {
            newSudoku.difficulty += 100;
        }

        // then assign variableNumber to value and clear the domain
        newSudoku.cells[position].value = value;
        newSudoku.cells[position].domain.clear();
        // now other variables that share domain with this variable need to narrow domain
        narrowDomain(newSudoku, position, value);

        return newSudoku;
    }

    public static Sudoku removeNumber(Sudoku sudoku, int position, boolean isGame) {
        Integer[] numbers = new Integer[81];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = sudoku.cells[i].value;
        }
        numbers[position] = 0;

        Sudoku newSudoku = new Sudoku(numbers);
        if (isGame) {
            for (int i = 0; i < newSudoku.cells.length; i++) {
                newSudoku.cells[i].isConstant = sudoku.cells[i].isConstant;
            }
        }
        return newSudoku;
    }

    public static void narrowDomain(Sudoku sudoku, int position, Integer value) {

        // row
        int rowNumber = position % 9;
        int rowStart = position - rowNumber;
        for (int i = rowStart; i < rowStart + 9; i++) {
            if (i == position) {
                continue;
            }
            sudoku.cells[i].domain.remove(value);
        }

        // column
        int columnStart = position % 9;
        for (int i = columnStart; i < columnStart + 81; i += 9) {
            if (i == position) {
                continue;
            }
            sudoku.cells[i].domain.remove(value);
        }

        // area
        int columnPlace = position % 3;
        int rowPlace = (position / 9) % 3;
        int areaStart = position - (9 * rowPlace) - columnPlace;
        for (int i = areaStart; i < areaStart + 27; i += 9) {
            for (int j = i; j < i + 3; j++) {
                if (j == position) {
                    continue;
                }
                sudoku.cells[j].domain.remove(value);
            }
        }
    }
}
