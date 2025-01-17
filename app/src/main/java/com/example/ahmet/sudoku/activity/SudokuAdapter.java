package com.example.ahmet.sudoku.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmet.sudoku.R;
import com.example.ahmet.sudoku.model.Cell;
import com.example.ahmet.sudoku.model.Sudoku;
import com.example.ahmet.sudoku.utility.SudokuUtil;

import java.util.Locale;

public class SudokuAdapter extends RecyclerView.Adapter<SudokuAdapter.SudokuAdapterViewHolder> {

    private Context mContext;
    private Sudoku mSudoku;
    private int mSelected;

    public SudokuAdapter(Context context) {
        this.mContext = context;
    }

    public void setSudoku(Sudoku sudoku) {
        this.mSudoku = sudoku;
        this.mSelected = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SudokuAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new SudokuAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SudokuAdapterViewHolder holder, int position) {
        int value = mSudoku.cells[position].value;
        boolean isConstant = mSudoku.cells[position].isConstant;

        if (value != 0) {
            holder.mCellText.setText(String.format(Locale.US, "%d", value));
        } else {
            holder.mCellText.setText(" ");
        }

        if (isConstant) {
            holder.mCellText.setTypeface(Typeface.DEFAULT);
            holder.mCellText.setTextColor(Color.BLACK);
        } else {
            Typeface typeface = ResourcesCompat.getFont(mContext, R.font.bradley);
            holder.mCellText.setTypeface(typeface);
            holder.mCellText.setTextColor(Color.BLUE);
        }

        int color;
        if (mSelected == position) {
            color = ContextCompat.getColor(mContext, R.color.selectedCell);
        } else {
            color = ContextCompat.getColor(mContext, R.color.backgroundCell);
        }
        holder.mCellText.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        if (mSudoku != null) {
            return mSudoku.cells.length;
        }
        return 0;
    }

    public void writeNumber(Integer action) {
        // silinmek isteniyor
        if (action == 0) {
            // Sayiyi sil ve tum sodukoyu bastan olustur
            mSudoku = SudokuUtil.removeNumber(mSudoku, mSelected, true);
        }
        // selected cell su an bos
        else if (mSudoku.cells[mSelected].value == 0) {
            // Sayiyi yazmak uygun mu bak yani domainde var mi
            if (mSudoku.cells[mSelected].domain.contains(action)) {
                mSudoku.cells[mSelected].value = action;
                mSudoku.cells[mSelected].domain.clear();
                // Varsa sayiyi yaz ve narrow domaini cagir
                SudokuUtil.narrowDomain(mSudoku, mSelected, action);
            } else {
                return;
            }
        }
        // su an bos degil ve yazili olandan baska sayi yazilmak isteniyor
        else if (mSudoku.cells[mSelected].value != action) {
            Integer previous = mSudoku.cells[mSelected].value;

            mSudoku = SudokuUtil.removeNumber(mSudoku, mSelected, true);

            // istenen sayi yazilabilir domainde var
            if (mSudoku.cells[mSelected].domain.contains(action)) {
                mSudoku.cells[mSelected].value = action;
                mSudoku.cells[mSelected].domain.clear();
                // Varsa sayiyi yaz ve narrow domaini cagir
                SudokuUtil.narrowDomain(mSudoku, mSelected, action);
            }
            // domainde yokmus bari eskisini tekrar yazalim
            else if (mSudoku.cells[mSelected].domain.contains(previous)) {
                mSudoku.cells[mSelected].value = previous;
                mSudoku.cells[mSelected].domain.clear();
                // Varsa sayiyi yaz ve narrow domaini cagir
                SudokuUtil.narrowDomain(mSudoku, mSelected, previous);
                return;
            }
            // onu da yazamadik madem bos dursun o zaman :)
        }

        // cell degisti goruntuyu guncelle
        notifyItemChanged(mSelected);

        // sudoku acaba bitti mi
        boolean isFinished = true;
        for (Cell cell : mSudoku.cells) {
            if (cell.value == 0) {
                isFinished = false;
            }
        }

        // helal bitmis
        if (isFinished) {
            new AlertDialog.Builder(mContext)
                    .setTitle("Congratulations")
                    .setMessage("Sudoku is completed")
                    .show();
        }
    }

    public int getSelected() {
        return mSelected;
    }

    private void setSelected(int position) {
        if (mSelected == position) {
            mSelected = -1;
            notifyItemChanged(position);
            return;
        }

        if (!mSudoku.cells[position].isConstant) {
            int previous = mSelected;
            mSelected = position;

            if (previous != -1) {
                notifyItemChanged(previous);
            }

            notifyItemChanged(position);
        }
    }

    public class SudokuAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView mCellText;

        public SudokuAdapterViewHolder(View itemView) {
            super(itemView);
            mCellText = itemView.findViewById(R.id.cell_text_view);

            mCellText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelected(getAdapterPosition());
                }
            });
        }
    }
}
