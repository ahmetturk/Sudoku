package com.example.ahmet.sudoku;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Cell implements Parcelable {
    public static final Parcelable.Creator<Cell> CREATOR
            = new Parcelable.Creator<Cell>() {
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };


    public int value;
    public List<Integer> domain;
    private int domainIterator;

    public Cell() {
        this.value = 0;
        this.domain = new ArrayList<>(9);
        for(int i = 1; i < 10; i++) {
            domain.add(i);
        }
        domainIterator = 0;
    }

    private Cell(Parcel in) {
        value = in.readInt();
        domain = new ArrayList<>();
        in.readList(domain, Integer.class.getClassLoader());
        domainIterator = in.readInt();
    }

    public int getValueFromDomain() {
        if (domainIterator < domain.size()) {
            return domain.get(domainIterator++);
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(value);
        parcel.writeList(domain);
        parcel.writeInt(domainIterator);
    }
}
