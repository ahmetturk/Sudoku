package com.example.ahmet.sudoku.utility;

import com.example.ahmet.sudoku.model.Sudoku;

public class SudokuUtil {

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
        Utils.narrowDomain(sudoku, position, value);
    }

}
