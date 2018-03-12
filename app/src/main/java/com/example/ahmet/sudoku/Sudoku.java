package com.example.ahmet.sudoku;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Sudoku implements Parcelable {

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
    public Cell[] cells;

    public Sudoku(int [] numbers) {
        cells = new Cell[81];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
        }

        int value;
        for (int position = 0; position < 81; position++)
        {
            value = numbers[position];
            if (value != 0)
            {
                cells[position].value = value;
                cells[position].isConstant = true;
                cells[position].domain.clear();
                Utils.narrowDomain(this, position, value);
            }
        }
    }

    public Sudoku(Sudoku parent, int position, int value) {
        cells = new Cell[81];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
            // copy all variables from parent
            cells[i].value = parent.cells[i].value;
            cells[i].domain = new ArrayList<>(parent.cells[i].domain);
        }

        // then assign variableNumber to value and clear the domain
        cells[position].value = value;
        cells[position].domain.clear();
        // now other variables that share domain with this variable need to narrow domain
        Utils.narrowDomain(this, position, value);
    }

    private Sudoku(Parcel in) {
        cells = new Cell[81];
        in.readTypedArray(cells, Cell.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(cells, 0);
    }
}
