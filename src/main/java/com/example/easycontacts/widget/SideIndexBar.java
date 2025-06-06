package com.example.easycontacts.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SideIndexBar extends View {
    private static final String[] LETTERS = {
            "A","B","C","D","E","F","G","H","I","J","K",
            "L","M","N","O","P","Q","R","S","T","U","V",
            "W","X","Y","Z"
    };

    private int selectedIndex = -1;
    private Paint textPaint;
    private Paint highlightPaint;
    private OnLetterChangeListener listener;

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
        void onLetterTouchEnd();
    }

    public SideIndexBar(Context context) {
        this(context, null);
    }

    public SideIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(dpToPx(14));
        textPaint.setTextAlign(Paint.Align.CENTER);

        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setColor(Color.parseColor("#6200EE")); // 主色调紫色
        highlightPaint.setTextSize(dpToPx(14));
        highlightPaint.setFakeBoldText(true);
        highlightPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int singleHeight = height / LETTERS.length;

        for (int i = 0; i < LETTERS.length; i++) {
            float x = width / 2f;
            float y = singleHeight * i + singleHeight * 0.75f; // 调整基线位置

            if (i == selectedIndex) {
                canvas.drawText(LETTERS[i], x, y, highlightPaint);
            } else {
                canvas.drawText(LETTERS[i], x, y, textPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int oldSelected = selectedIndex;
        int c = (int) (y / getHeight() * LETTERS.length);
        if (c < 0) c = 0;
        if (c >= LETTERS.length) c = LETTERS.length - 1;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                selectedIndex = c;
                if (listener != null && oldSelected != selectedIndex) {
                    listener.onLetterChange(LETTERS[selectedIndex]);
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                selectedIndex = -1;
                if (listener != null) {
                    listener.onLetterTouchEnd();
                }
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 外部调用设置高亮字母
     * @param letter 大写字母 A-Z
     */
    public void setSelectedLetter(String letter) {
        if (letter == null || letter.length() == 0) {
            selectedIndex = -1;
            invalidate();
            return;
        }

        int index = -1;
        for (int i = 0; i < LETTERS.length; i++) {
            if (LETTERS[i].equals(letter)) {
                index = i;
                break;
            }
        }

        if (index != selectedIndex) {
            selectedIndex = index;
            invalidate();
        }
    }

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.listener = listener;
    }

    // dp转px
    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}