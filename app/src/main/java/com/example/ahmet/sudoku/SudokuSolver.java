package com.example.ahmet.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    private List<Sudoku> sudokuList;

    public SudokuSolver(Sudoku sudoku) {
        sudokuList = new ArrayList<>();
        sudokuList.add(sudoku);
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
        Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);

        int unassignedCell = selectUnassingnedCell();
        if (unassignedCell == -1) {
            // this means sudoku is solved
            return false;
        }

        int value = Utils.getValueFromDomain(sudoku.cells[unassignedCell]);
        if (value == -1) {
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

    private int selectUnassingnedCell() {
        Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);
        int cellNumber = -1;
        int domainSize = 10;
        for (int i = 0; i < 81; i++)
        {
            if (sudoku.cells[i].value == 0) // zero means it is unassigned
            {
                if (domainSize > sudoku.cells[i].domain.size())
                {
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
        int rowNumber = position % 9;
        int rowStart = position - rowNumber;
        for (int i = rowStart; i < rowStart + 9; i++)
        {
            if (i == position) {
                continue;
            }
            if (sudoku.cells[i].domain.size() == 1 && sudoku.cells[i].domain.get(0) == value) {
                return false;
            }
        }

        // column
        int columnStart = position % 9;
        for (int i = columnStart; i < columnStart + 81; i+=9)
        {
            if (i == position) { continue; }
            if (sudoku.cells[i].domain.size() == 1 && sudoku.cells[i].domain.get(0) == value) {
                return false;
            }
        }

        // area
        int columnPlace = position % 3;
        int rowPlace = (position / 9) % 3;
        int areaStart = position - (9 * rowPlace) - columnPlace;
        for (int i = areaStart; i < areaStart + 27; i += 9) {
            for (int j = i; j < i + 3; j++)
            {
                if (j == position) { continue; }
                if (sudoku.cells[j].domain.size() == 1 && sudoku.cells[j].domain.get(0) == value) {
                    return false;
                }
            }
        }

        return true;
    }
}
