package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.ahmet.sudoku.model.Cell.UNASSIGNED;

public class SudokuSolver {
    private static final int ROW_CELL = 9;
    private static final int TOTAL_CELL = ROW_CELL * ROW_CELL;
    private static final int MAX_DOMAIN = 10;
    private static final int NONE = -1;

    private List<Sudoku> sudokuList;
    private boolean isRandom;

    public SudokuSolver(Sudoku sudoku) {
        new SudokuSolver(sudoku, false);
    }

    public SudokuSolver(Sudoku sudoku, boolean isRandom) {
        this.sudokuList = new ArrayList<>();
        this.sudokuList.add(sudoku);
        this.isRandom = isRandom;
    }

    public Sudoku getSudoku() {
        return sudokuList.get(sudokuList.size() - 1);
    }

    public void solve() {
        boolean result;
        do {
            result = step();
        } while (result);
    }

    private boolean step() {
        Sudoku sudoku = getSudoku();

        int unassignedCell = selectUnassignedCell();
        if (unassignedCell == NONE) {
            // this means sudoku is solved
            return false;
        }

        int value = Utils.getValueFromDomain(sudoku.cells[unassignedCell], isRandom);

        if (value == NONE) {
            // wrong guess go back
            sudokuList.remove(sudokuList.size() - 1);
            return true;
        }

        boolean isConsistent = testConsistency(unassignedCell, value);
        if (isConsistent) {
            // write a number to Sudoku
            sudokuList.add(new Sudoku(sudoku, unassignedCell, value));
        }
        return true;
    }

    private int selectUnassignedCell() {
        Sudoku sudoku = getSudoku();

        int cellNumber = NONE;
        int domainSize = MAX_DOMAIN;
        for (int i = 0; i < TOTAL_CELL; i++) {
            if (sudoku.cells[i].value == UNASSIGNED) // zero means it is unassigned
            {
                if (domainSize > sudoku.cells[i].domain.size()) {
                    cellNumber = i;
                    domainSize = sudoku.cells[i].domain.size();
                }
            }
        }
        return cellNumber; // return an unassinged item_cell from sudoku
    }

    private boolean testConsistency(int position, int value) {
        Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);

        // row
        int rowNumber = position % ROW_CELL;
        int rowStart = position - rowNumber;
        for (int i = rowStart; i < rowStart + ROW_CELL; i++) {
            if (i == position) {
                continue;
            }
            if (sudoku.cells[i].domain.size() == 1 && sudoku.cells[i].domain.get(0) == value) {
                return false;
            }
        }

        // column
        int columnStart = position % ROW_CELL;
        for (int i = columnStart; i < columnStart + TOTAL_CELL; i += ROW_CELL) {
            if (i == position) {
                continue;
            }
            if (sudoku.cells[i].domain.size() == 1 && sudoku.cells[i].domain.get(0) == value) {
                return false;
            }
        }

        // area
        int columnPlace = position % 3;
        int rowPlace = (position / ROW_CELL) % 3;
        int areaStart = position - (ROW_CELL * rowPlace) - columnPlace;

        for (int i = areaStart; i < areaStart + 3 * ROW_CELL; i += ROW_CELL) {
            for (int j = i; j < i + 3; j++) {
                if (j == position) {
                    continue;
                }
                if (sudoku.cells[j].domain.size() == 1 && sudoku.cells[j].domain.get(0) == value) {
                    return false;
                }
            }
        }

        return true;
    }
}
