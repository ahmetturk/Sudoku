package com.example.ahmet.sudoku.utility;

import com.example.ahmet.sudoku.model.Cell;

import java.util.Collections;

public final class CellUtil {

    public static int getValueFromDomain(Cell cell, boolean isRandom) {
        if (cell.domain.size() > 0) {
            if (isRandom) {
                Collections.shuffle(cell.domain);
            }
            return cell.domain.remove(0);
        }
        return -1;
    }
}
