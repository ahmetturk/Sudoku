package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.SudokuUtil;

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
    private Sudoku solution;
    private boolean isRandom;
    private boolean isValid;

    public SudokuSolver(Sudoku sudoku) {
        this(sudoku, false);
    }

    public SudokuSolver(Sudoku sudoku, boolean random) {
        sudokuList = new ArrayList<>();
        sudokuList.add(sudoku);
        isRandom = random;
        isValid = false;
    }

    public Sudoku getSolution() {
        return solution;
    }

    public void solve() {
        while (true) {
            Sudoku sudoku = sudokuList.get(sudokuList.size() - 1);

            int unassignedCell = selectUnassignedCell(sudoku);

            // this means sudoku is solved
            if (unassignedCell == NONE) {
                if (!isValid) {
                    isValid = true;
                    solution = sudoku;

                    // remove the solution and continue to search another one
                    sudokuList.remove(sudokuList.size() - 1);

                } else {
                    // sudoku has second solution so it is not valid
                    isValid = false;
                    break;
                }

            } else {
                int value = getValueFromDomain(sudoku, unassignedCell);

                if (value != NONE) {
                    boolean isConsistent = testConsistency(sudoku, unassignedCell, value);
                    if (isConsistent) {
                        // write value to unassigned cell of Sudoku
                        sudokuList.add(SudokuUtil.addNumber(sudoku, unassignedCell, value));
                    }

                } else {
                    // wrong guess go back
                    sudokuList.remove(sudokuList.size() - 1);

                    // all sudoku branches traversed
                    if (sudokuList.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * return true if solved Sudoku has unique solution
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * return difficulty score of Sudoku
     * <p>
     * Difficulty = Branches * 100 + Empty Cells
     */
    public int getDifficulty() {
        return solution.difficulty;
    }

    private int selectUnassignedCell(Sudoku sudoku) {
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
        return cellNumber; // return an unassigned item_cell from sudoku
    }

    private boolean testConsistency(Sudoku sudoku, int position, int value) {
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

    private int getValueFromDomain(Sudoku sudoku, int unassignedCell) {
        int value;

        List<Integer> domain = sudoku.cells[unassignedCell].domain;

        // shuffle only for first time
        if (isRandom && sudoku.cellIterator == 0) {
            Collections.shuffle(domain);
        }

        // is there any element for cell iterator in domain
        if (sudoku.cellIterator <= domain.size() - 1) {
            value = domain.get(sudoku.cellIterator);
            sudoku.cellIterator++;
        } else {
            value = NONE;
        }

        return value;
    }
}
