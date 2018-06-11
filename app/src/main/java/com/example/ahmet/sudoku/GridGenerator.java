package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.SudokuUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridGenerator {

    private static final int ORDER = 3;
    private static final int BOX_SIZE = ORDER * ORDER;

    private static Sudoku sudoku;

    public GridGenerator() {

    }

    /**
     * Create a fully completed Sudoku grid
     */
    public static Sudoku createGrid() {
        sudoku = new Sudoku();

        // First, add numbers to top-left box of Sudoku
        fillTopLeftBox();

        // Second, add numbers to top-middle box of Sudoku
        fillTopMiddleBox();

        // Third, add numbers to top-right box of Sudoku
        fillTopRightBox();

        // Fourth, add number to first column of Sudoku
        fillFirstColumn();

        // Fifth, add number to remaining cells of Sudoku with backtracking
        fillRemainingCells();

        return sudoku;
    }

    private static void fillTopLeftBox() {
        List<Integer> firstBoxList = getRandomPermutation();
        for (int i = 0; i < ORDER; i++) {
            for (int j = 0; j < ORDER; j++) {
                SudokuUtil.addValueToSudoku(sudoku,
                        i * BOX_SIZE + j,
                        firstBoxList.get(i * ORDER + j));
            }
        }
    }

    private static void fillTopMiddleBox() {
        List<Integer> topRowList = getRandomPermutation();
        List<Integer> middleRowList = getRandomPermutation();
        List<Integer> bottomRowList = getRandomPermutation();

        for (int i = 0; i < ORDER; i++) {
            topRowList.remove(sudoku.cells[i].value);
            middleRowList.remove(sudoku.cells[BOX_SIZE + i].value);
            bottomRowList.remove(sudoku.cells[2 * BOX_SIZE + i].value);
        }

        // top row
        for (int i = 0; i < ORDER; i++) {
            middleRowList.remove(topRowList.get(i));
            bottomRowList.remove(topRowList.get(i));
            SudokuUtil.addValueToSudoku(sudoku, ORDER + i, topRowList.get(i));
        }

        // middle row
        int j = 0;
        while (bottomRowList.size() > ORDER && j < ORDER) {
            bottomRowList.remove(middleRowList.get(j));
            SudokuUtil.addValueToSudoku(sudoku, BOX_SIZE + ORDER + j, middleRowList.get(j));
            j++;
        }

        middleRowList.removeAll(bottomRowList);

        for (; j < ORDER; j++) {
            SudokuUtil.addValueToSudoku(sudoku, BOX_SIZE + ORDER + j, middleRowList.get(j));
        }

        // bottom row
        for (int i = 0; i < ORDER; i++) {
            SudokuUtil.addValueToSudoku(sudoku, 2 * BOX_SIZE + ORDER + i, bottomRowList.get(i));
        }
    }

    private static void fillTopRightBox() {
        for (int i = 0; i < ORDER; i++) {
            List<Integer> rowList = getRandomPermutation();
            for (int j = 0; j < 2 * ORDER; j++) {
                rowList.remove(sudoku.cells[i * BOX_SIZE + j].value);
            }

            for (int j = 0; j < ORDER; j++) {
                SudokuUtil.addValueToSudoku(sudoku,
                        i * BOX_SIZE + 2 * ORDER + j, rowList.get(j));
            }
        }
    }

    private static void fillFirstColumn() {
        List<Integer> columnList = getRandomPermutation();

        for (int i = 0; i < ORDER; i++) {
            columnList.remove(sudoku.cells[i * BOX_SIZE].value);
        }

        for (int i = 0; i < 2 * ORDER; i++) {
            SudokuUtil.addValueToSudoku(sudoku,
                    (i + 3) * BOX_SIZE, columnList.get(i));
        }
    }

    private static List<Integer> getRandomPermutation() {
        List<Integer> list = new ArrayList<>(BOX_SIZE);
        for (int i = 1; i <= BOX_SIZE; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    private static void fillRemainingCells() {
        SudokuSolver sudokuSolver = new SudokuSolver(sudoku, true);
        sudokuSolver.solve();
        sudoku = sudokuSolver.getSolution();
    }
}
