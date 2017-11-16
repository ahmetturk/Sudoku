package com.example.ahmet.sudoku;

import java.util.ArrayList;

public class Sudoku {
    public Cell[] cells;

    public Sudoku(int [] numbers) {
        cells = new Cell[81];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
        }

        int value;
        for (int i = 0; i < 81; i++)
        {
            value = numbers[i];
            if (value != 0)
            {
                cells[i].value = value;
                cells[i].domain.clear();
                narrowDomain(i, value);
            }
        }
    }

    public Sudoku(Sudoku parent, int position, int value) {
        cells = new Cell[81];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
        }

        for (int i = 0; i < 81; i++) // copy all variables from parent
        {
            cells[i].value = parent.cells[i].value;
            cells[i].domain = new ArrayList<>(parent.cells[i].domain);
        }
        // then assign variableNumber to value and clear the domain
        cells[position].value = value;
        cells[position].domain.clear();
        // now other variables that share domain with this variable need to narrow domain
        narrowDomain(position, value);
    }

    // TODO: MOVE THIS
    private void narrowDomain(int position, int value) {
        // row
        int rowNumber = position % 9;
        int rowStart = position - rowNumber;
        for (int i = rowStart; i < rowStart + 9; i++)
        {
            if (i == position) {
                continue;
            }
            if (cells[i].domain.contains(value))
            {
                cells[i].domain.remove(cells[i].domain.indexOf(value));
            }
        }

        // column
        int columnStart = position % 9;
        for (int i = columnStart; i < columnStart + 81; i += 9)
        {
            if (i == position) {
                continue;
            }
            if (cells[i].domain.contains(value))
            {
                cells[i].domain.remove(cells[i].domain.indexOf(value));
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
                if (cells[j].domain.contains(value))
                {
                    cells[j].domain.remove(cells[j].domain.indexOf(value));
                }
            }
        }
    }
}
