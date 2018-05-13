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
    public static void addValueToSudoku(Sudoku sudoku, int position, int value) {
        // Assign value to cell by position and clear the domain
        sudoku.cells[position].value = value;
        sudoku.cells[position].domain.clear();
        // Narrow domains of cells that share domain with this cell
        narrowDomain(sudoku, position, value);
    }

    public static Sudoku addNumber(Sudoku sudoku, int position, int value) {
        Sudoku newSudoku = new Sudoku();

        for (int i = 0; i < newSudoku.cells.length; i++) {
            // copy all variables from sudoku
            newSudoku.cells[i].value = sudoku.cells[i].value;
            newSudoku.cells[i].domain = new ArrayList<>(sudoku.cells[i].domain);
        }

        // then assign variableNumber to value and clear the domain
        newSudoku.cells[position].value = value;
        newSudoku.cells[position].domain.clear();
        // now other variables that share domain with this variable need to narrow domain
        narrowDomain(newSudoku, position, value);

        return newSudoku;
    }

    public static Sudoku removeNumber(Sudoku sudoku, int position) {
        // TODO Gelistirilebilir
        sudoku.cells[position].value = 0;
        int[] numbers = new int[81];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = sudoku.cells[i].value;
        }
        Sudoku newSudoku = new Sudoku(numbers);

        for (int i = 0; i < newSudoku.cells.length; i++) {
            newSudoku.cells[i].isConstant = sudoku.cells[i].isConstant;
        }

        return newSudoku;
    }

    public static void narrowDomain(Sudoku sudoku, int position, int value) {

        // row
        int rowNumber = position % 9;
        int rowStart = position - rowNumber;
        for (int i = rowStart; i < rowStart + 9; i++) {
            if (i == position) {
                continue;
            }
            if (sudoku.cells[i].domain.contains(value)) {
                sudoku.cells[i].domain.remove(sudoku.cells[i].domain.indexOf(value));
            }
        }

        // column
        int columnStart = position % 9;
        for (int i = columnStart; i < columnStart + 81; i += 9) {
            if (i == position) {
                continue;
            }
            if (sudoku.cells[i].domain.contains(value)) {
                sudoku.cells[i].domain.remove(sudoku.cells[i].domain.indexOf(value));
            }
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
                if (sudoku.cells[j].domain.contains(value)) {
                    sudoku.cells[j].domain.remove(sudoku.cells[j].domain.indexOf(value));
                }
            }
        }
    }
}
