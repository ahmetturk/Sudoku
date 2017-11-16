package com.example.ahmet.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    public int value;
    public List<Integer> domain;

    public Cell() {
        this.value = 0;
        this.domain = new ArrayList<Integer>(9);
        for(int i = 1; i < 10; i++) {
            domain.add(i);
        }
    }
}