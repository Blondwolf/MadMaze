package madmaze.hearc.ch.madmaze.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import madmaze.hearc.ch.madmaze.game.controller.GameController;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;    // Le holder
    DrawingThread thread;           // Le thread dans lequel le dessin se fera

    Paint paint;

    GameController controller;

    //For xml inflation
    public GameSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        thread = new DrawingThread();

        paint = new Paint();
        paint.setColor(Color.RED);
    }

    //Programmaticaly
    public GameSurfaceView(Context context, GameController controller) {
        this(context);

        this.controller = controller;
    }

    //Update from surfaceview
    @Override
    protected void onDraw(Canvas canvas) {
        controller.update(0);       //Calculate delta
        postInvalidate();
        controller.draw(canvas, paint);
    }

    // Que faire quand le surface change ? (L'utilisateur tourne son téléphone par exemple)
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        controller.updateSurfaceInfos(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //setWillNotDraw(false);      //Allow to redraw, called postinvalidate instead. Maybe approach is wrong
        thread.keepDrawing = true;
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.keepDrawing = false;

        boolean joined = false;
        while (!joined) {
            try {
                thread.join();
                joined = true;
            } catch (InterruptedException e) {}
        }
    }

    private class DrawingThread extends Thread {
        // Utilisé pour arrêter le dessin quand il le faut
        boolean keepDrawing = true;

        @Override
        public void run() {

            while (keepDrawing) {
                Canvas canvas = null;

                try {
                    // On récupère le canvas pour dessiner dessus
                    canvas = surfaceHolder.lockCanvas();
                    // On s'assure qu'aucun autre thread n'accède au holder
                    synchronized (surfaceHolder) {
                        // Et on dessine
                        onDraw(canvas);
                    }
                }
                finally {
                    // Notre dessin fini, on relâche le Canvas pour que le dessin s'affiche
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
            }
        }
    }
}