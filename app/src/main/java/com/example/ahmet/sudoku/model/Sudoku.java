package com.example.ahmet.sudoku.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ahmet.sudoku.utility.SudokuUtil;

import java.util.ArrayList;

public class Sudoku implements Parcelable {

    public Cell[] cells;
    public int difficulty;
    public int cellIterator;

    public Sudoku() {
        cells = new Cell[81];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
        }
        difficulty = 0;
        cellIterator = 0;
    }

    // TODO Ilerde silinecek
    public Sudoku(int[] numbers) {
        cells = new Cell[81];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
        }

        for (int position = 0; position < 81; position++) {
            int value = numbers[position];
            if (value != 0) {
                cells[position].value = value;
                cells[position].isConstant = true;
                cells[position].domain.clear();
                SudokuUtil.narrowDomain(this, position, value);
            }
        }
    }

    // CODE BELOW RELATED ANDROID PARCELABLE, NOT FOR SUDOKU
    private Sudoku(Parcel in) {
        cells = new Cell[81];
        in.readTypedArray(cells, Cell.CREATOR);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sudoku> CREATOR
            = new Parcelable.Creator<Sudoku>() {
        public Sudoku createFromParcel(Parcel in) {
            return new Sudoku(in);
        }

        public Sudoku[] newArray(int size) {
            return new Sudoku[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(cells, 0);
    }
}
