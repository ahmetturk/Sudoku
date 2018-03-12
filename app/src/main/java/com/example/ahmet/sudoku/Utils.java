package com.example.ahmet.sudoku;

public class Utils {
    public static int getValueFromDomain(Cell cell) {
        if (cell.domain.size() > 0) {
            return cell.domain.remove(0);
        }
        return -1;
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

    public static Sudoku removeNumber(Sudoku sudoku, int position) {
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
}

