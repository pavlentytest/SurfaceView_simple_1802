package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SymbolTable;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    private SurfaceHolder holder;
    private boolean flag;

    MyThread(SurfaceHolder holder) {
        this.holder = holder;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public long getTime() {
        return System.nanoTime()/1000; // мксек.
    }

    private long redrawTime = 0;
    @Override
    public void run() {
        Canvas canvas;
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 1000000) {
                continue;
            }
            // блокировка Canvas для отрисовки
            canvas = holder.lockCanvas();
            drawCircle(canvas);
            // разблокируем и показываем
            holder.unlockCanvasAndPost(canvas);
            redrawTime = getTime();
        }
    }

    private void drawCircle(Canvas canvas) {
        int x = canvas.getWidth()/2;
        int y = canvas.getHeight()/2;
        canvas.drawColor(Color.BLACK);

        float radius = (float)(300*Math.random());
        canvas.drawCircle(x, y, radius, paint);
    }
}
