package com.sdivc.novajia.photobrowser.Listener;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.CollapsibleActionView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Maya on 2018/4/23.
 */

public class MultiPointTounchListener  implements View.OnTouchListener{
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                matrix.set(view.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(event.getX(),event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f){
                    savedMatrix.set(matrix);
                    minPoint(mid,event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG){
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x,event.getY()-start.y);
                }else if (mode == ZOOM){
                    float newDist = spacing(event);
                    if (newDist > 10f){
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale,scale,mid.x,mid.y);
                    }
                }
                break;
        }
        view.setImageMatrix(matrix);
        return true;
    }

    private void minPoint(PointF mid, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        mid.set(x / 2,y / 2);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


}
