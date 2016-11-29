package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

public class RenderThread extends Thread {
    private static final String TAG = "[RenderThread]";

    private boolean isRunning = false;
    private boolean isPaused = false;
    private boolean isFocused = false;

    private boolean refresh = false;

    private int fps = 0;
    private int framePeriod = 0;
    private long nextFrame = 0;
    private int frames = 0;
    private long prevFrame = 0;

    private SurfaceHolder surfaceHolder = null;

    private Component component = null;

    public RenderThread(SurfaceHolder surfaceHolder, Component component, int fps) {
        this.surfaceHolder = surfaceHolder;
        this.component = component;
        setFps(fps);
    }

    public void onStart() {
//        Log.d(TAG, "onStart");
        synchronized (surfaceHolder) {
            isRunning = true;
            start();
        }
    }

    public void onPause() {
//        Log.d(TAG, "onPause");
        synchronized (surfaceHolder) {
            isPaused = true;
        }
    }

    public void onPause(boolean refresh) {
        onPause();
        if (refresh) {
            refresh();
        }
    }

    public void refresh() {
//        Log.d(TAG, "refresh");
        synchronized (surfaceHolder) {
            refresh = true;
            surfaceHolder.notify();
        }
    }

    public void onResume() {
//        Log.d(TAG, "onResume");
        synchronized (surfaceHolder) {
            isPaused = false;
            surfaceHolder.notify();
        }
    }

    public void onStop() {
//        Log.d(TAG, "onStop");
        synchronized (surfaceHolder) {
            isRunning = false;
            surfaceHolder.notify();
        }

        boolean retry = true;
        while (retry) {
            try {
                join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
//        Log.d(TAG, "onWindowFocusChanged");
        synchronized (surfaceHolder) {
            this.isFocused = hasFocus;
            if (hasFocus) {
                surfaceHolder.notify();
            }
        }
    }

    private boolean holdOn() {
        return (isRunning && (isPaused || (!isFocused)));
    }

    @Override
    public void run() {
        while (isRunning) {
            if (holdOn()) {
                while (holdOn() && !refresh) {
                    synchronized (surfaceHolder) {
                        try {
                            surfaceHolder.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!isRunning) {
                    break;
                }

                if (refresh) {
                    nextFrame -= framePeriod;
                    refresh = false;
                }
            }

            if (surfaceHolder.getSurface().isValid()) {
                long now = System.currentTimeMillis();
                if (now < nextFrame) {
                    continue;
                } else {
                    nextFrame = now + framePeriod;
                }

                long elapsed = now - prevFrame;
                if (elapsed > 1000) {
//                    Log.d(TAG, "FPS - " + (frames * 1000) / elapsed);
                    prevFrame = System.currentTimeMillis();
                    frames = 0;
                }
                frames++;

                Rect dirty = null;
                Canvas canvas = null;
                Paint paint = new Paint();
                try {
                    canvas = surfaceHolder.lockCanvas(dirty);
                    synchronized (surfaceHolder) {
                        if (canvas != null) {
                            component.update();
                            component.render(canvas, paint);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public void screenshot(Bitmap bitmap) {
        onPause();

        Canvas canvas = new Canvas(bitmap);
        if (surfaceHolder.getSurface().isValid()) {
            Paint paint = new Paint();
            try {
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        component.update();
                        component.render(canvas, paint);
                    }
                }
            } finally {
            }
        }
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
        framePeriod = 1000 / fps;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isFocused() {
        return isFocused;
    }
}