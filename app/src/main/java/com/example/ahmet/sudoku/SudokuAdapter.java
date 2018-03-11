package com.example.ahmet.sudoku;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class SudokuAdapter extends RecyclerView.Adapter<SudokuAdapter.SudokuAdapterViewHolder> {

    private Sudoku mSudoku;

    public SudokuAdapter(Sudoku sudoku) {
        this.mSudoku = sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.mSudoku = sudoku;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SudokuAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new SudokuAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SudokuAdapterViewHolder holder, int position) {
        int value = mSudoku.cells[position].value;

        if (value != 0) {
            holder.mCellText.setText(String.format(Locale.US, "%d", value));
        } else {
            holder.mCellText.setText(" ");
        }
    }

    @Override
    public int getItemCount() {
        return mSudoku.cells.length;
    }

    public static class SudokuAdapterViewHolder extends RecyclerView.ViewHolder {

        public CellTextView mCellText;

        public SudokuAdapterViewHolder(View itemView) {
            super(itemView);
            mCellText = (CellTextView) itemView;
        }
    }
}
