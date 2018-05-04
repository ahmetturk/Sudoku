package com.example.ahmet.sudoku.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Cell implements Parcelable {

    public static final int UNASSIGNED = 0;

    public Integer value;
    public List<Integer> domain;
    public boolean isConstant;

    public Cell() {
        value = UNASSIGNED;
        domain = new ArrayList<>(9);
        for (int i = 1; i < 10; i++) {
            domain.add(i);
        }
        isConstant = false;
    }

    private Cell(Parcel in) {
        value = in.readInt();
        if (in.readByte() == 0x01) {
            domain = new ArrayList<>();
            in.readList(domain, Integer.class.getClassLoader());
        } else {
            domain = null;
        }
        isConstant = in.readByte() != 0x00;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        if (domain == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(domain);
        }
        dest.writeByte((byte) (isConstant ? 0x01 : 0x00));
    }
}
