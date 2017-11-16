package com.example.ahmet.sudoku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SudokuAdapter extends BaseAdapter{

    private Sudoku mSudoku;

    public SudokuAdapter(Sudoku sudoku) {
        this.mSudoku = sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.mSudoku = sudoku;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSudoku.cells.length;
    }

    @Override
    public Integer getItem(int position) {
        return mSudoku.cells[position].value;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, null);
        }
        TextView cellTextView = (TextView) convertView.findViewById(R.id.cell_text_view);
        if (getItem(position) != 0) {
            cellTextView.setText(getItem(position).toString());
        }
        else {
            cellTextView.setText(" ");
        }

        return convertView;
    }
}
