package madmaze.hearc.ch.madmaze.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import madmaze.hearc.ch.madmaze.game.controller.GameController;

/**
 * Created by KVull on 27.10.2017.
 */

public class GameSurfaceViewBackup extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder holder;
    private MyThread thread;

    private GameController gameController;
    private Paint paint;

    private int width;
    private int height;

    public GameSurfaceViewBackup(Context context, GameController gameController, int width, int height){
        super(context);
        holder = getHolder();

        holder.addCallback(this);

        this.gameController = gameController;
        this.width = width;
        this.height = height;
        paint = new Paint();
        //initialize paint object parameters

        setWillNotDraw(false); //this line is very important!
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
    }

    @Override
    // This is always called at least once, after surfaceCreated
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if (thread == null)
        {
            thread = new MyThread(holder, gameController);
            thread.setRunning(true);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        thread.setRunning(false);
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                Log.d(getClass().getSimpleName(), "Interrupted Exception", e);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //gameController.onTouchEvent(event); //handle user interaction
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        gameController.draw(canvas, paint);
    }

    /*public Thread getThread()
    {
        return thread;
    }*/

    public class MyThread extends Thread
    {
        private SurfaceHolder holder;
        private boolean running = false;

        private GameController gameController;

        public MyThread(SurfaceHolder holder, GameController gameController)
        {
            this.holder = holder;
            this.gameController = gameController;
        }

        @Override
        public void run()
        {
            Canvas canvas = null;
            while (running)
            {
                gameController.update(0); //TODO: update the time between last update() call and now => delta
                try
                {
                    canvas = holder.lockCanvas(null);
                    synchronized (holder)
                    {
                        postInvalidate();
                    }
                }
                finally
                {
                    if (canvas != null)
                    {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }

        }

        public void setRunning(boolean b)
        {
            running = b;
        }
    }
}
