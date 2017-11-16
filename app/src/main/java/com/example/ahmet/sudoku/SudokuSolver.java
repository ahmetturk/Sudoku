package com.example.ahmet.sudoku;

import android.app.AlertDialog;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    private List<Sudoku> sudokuList;

    public SudokuSolver(Sudoku sudoku) {
        sudokuList = new ArrayList<Sudoku>();
        sudokuList.add(sudoku);
    }

    public Sudoku getSudoku() {
        return sudokuList.get(sudokuList.size() - 1);
    }

    public boolean solve() {
        Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);

        // select an unassigned variable, return -1 if there is none
        int unassignedCell = selectUnassingnedCell();
        if (unassignedCell == -1) {
            return true;
        }
        else {
            boolean result = false;
            for (int i = 0; i < sudoku.cells[unassignedCell].domain.size(); i++) {
                int value = getValueFromDomain(unassignedCell, i);
                boolean isConsistent = testConsistency(unassignedCell, value); // forward checking
                if (isConsistent) {
                    sudokuList.add(new Sudoku(sudoku, unassignedCell, value));
                    result = solve();
                    if (result) {
                        return true;
                    }
                }
            }
            if (!result) { // means there is not any value consistent so go back!!!
                sudokuList.remove(sudokuList.size() - 1);
                return false;
            }
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
        return cellNumber; // return an unassinged cell from sudoku
    }

    private int getValueFromDomain(int position, int i) {
        // return a value from domain of cell at @position
        Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);
        return sudoku.cells[position].domain.get(i);
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