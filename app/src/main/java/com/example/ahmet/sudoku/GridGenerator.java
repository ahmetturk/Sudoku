package com.example.ahmet.sudoku;

import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.SudokuUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridGenerator {

    private static final int ORDER = 3;
    private static final int BOX_SIZE = ORDER * ORDER;

    private Sudoku sudoku;

    public GridGenerator() {

    }

    /**
     * Create a fully completed Sudoku grid
     */
    public Sudoku createGrid() {
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

    private void fillTopLeftBox() {
        List<Integer> firstBoxList = getRandomPermutation();
        for (int i = 0; i < ORDER; i++) {
            for (int j = 0; j < ORDER; j++) {
                SudokuUtil.addValueToSudoku(sudoku,
                        i * BOX_SIZE + j,
                        firstBoxList.get(i * ORDER + j));
            }
        }
    }

    private void fillTopMiddleBox() {
        Set<Integer> setNotTop = new HashSet<>();
        Set<Integer> setNotMiddle = new HashSet<>();
        Set<Integer> setNotBottom = new HashSet<>();

        // top row
        List<Integer> topRowList = getRandomPermutation();
        for (int i = 0; i < ORDER; i++) {
            setNotTop.add(sudoku.cells[i].value);
        }
        topRowList.removeAll(setNotTop);

        for (int i = 0; i < ORDER; i++) {
            setNotMiddle.add(topRowList.get(i));
            setNotBottom.add(topRowList.get(i));
            SudokuUtil.addValueToSudoku(sudoku, ORDER + i, topRowList.get(i));
        }

        // middle row
        List<Integer> middleRowList = getRandomPermutation();
        List<Integer> bottomRowList = getRandomPermutation();

        for (int i = 0; i < ORDER; i++) {
            setNotMiddle.add(sudoku.cells[BOX_SIZE + i].value);
        }
        middleRowList.removeAll(setNotMiddle);

        for (int i = 0; i < ORDER; i++) {
            setNotBottom.add(sudoku.cells[2 * BOX_SIZE + i].value);
        }
        bottomRowList.removeAll(setNotBottom);

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

    private void fillTopRightBox() {
        List<Integer> rowList;
        Set<Integer> setNot = new HashSet<>();

        for (int i = 0; i < ORDER; i++) {
            rowList = getRandomPermutation();
            for (int j = 0; j < 2 * ORDER; j++) {
                setNot.add(sudoku.cells[i * BOX_SIZE + j].value);
            }
            rowList.removeAll(setNot);
            setNot.clear();

            for (int j = 0; j < ORDER; j++) {
                SudokuUtil.addValueToSudoku(sudoku,
                        i * BOX_SIZE + 2 * ORDER + j, rowList.get(j));
            }
        }
    }

    private void fillFirstColumn() {
        List<Integer> rowList = getRandomPermutation();;
        Set<Integer> setNot = new HashSet<>();

        for (int i = 0; i < ORDER; i++) {
            setNot.add(sudoku.cells[i * BOX_SIZE].value);
        }
        rowList.removeAll(setNot);

        for (int i = 0; i < 2 * ORDER; i++) {
            SudokuUtil.addValueToSudoku(sudoku,
                    (i + 3) * BOX_SIZE, rowList.get(i));
        }
    }

    private List<Integer> getRandomPermutation() {
        List<Integer> list = new ArrayList<>(BOX_SIZE);
        for (int i = 1; i <= BOX_SIZE; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    private void fillRemainingCells() {
         SudokuSolver sudokuSolver = new SudokuSolver(sudoku, true);
         sudokuSolver.solve();
         sudoku = sudokuSolver.getSudoku();
    }
}
