package com.example.ahmet.sudoku;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CellTextView extends AppCompatTextView {

    public CellTextView(Context context) {
        super(context);
    }

    public CellTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CellTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
