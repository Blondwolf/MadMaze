package madmaze.hearc.ch.madmaze.fragments;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;

import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.game.controller.GameController;
import madmaze.hearc.ch.madmaze.game.model.Ball;
import madmaze.hearc.ch.madmaze.game.model.Goal;
import madmaze.hearc.ch.madmaze.game.model.Rectangle;
import madmaze.hearc.ch.madmaze.game.model.World;
import madmaze.hearc.ch.madmaze.game.view.GameSurfaceView;

public class GameFragment extends Fragment implements SensorEventListener {

    public static final String TAG = "Game";

    //View & controller
    GameSurfaceView view;
    GameController controller;

    //Sensor
    SensorManager sensorManager;
    Sensor gyroscope;

    //**        Fragment        **//

    public GameFragment() {
        // Required empty public constructor
    }

    //Singleton
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    //Logic
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.wtf(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        //Sensors
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        //Create or load world

        //TODO
        //Maybe, change init order to :
        //1. new Gamecontroller
        //2. world = new World
        //3. controller.setWorld(world)
        //This way we can send to the world the screen size from the surfaceview
        //and update it in the surfaceview too

        World world = new World();
        //should retrieve position dynamically from canvas -> PointF(world.posx - a, world.posy - b)
        world.setBallPlayer(new Ball(new PointF(100, 100), 40));
        world.setGoal(new Goal(new PointF(1700, 975), 50));

        world.addElement(new Rectangle(new PointF(300, 0), new PointF(200, 500)));
        world.addElement(new Rectangle(new PointF(500, 700), new PointF(200, 500)));

        controller = new GameController(world);
    }

    //Android widgets
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.wtf(TAG, "onCreateView: ");
        super.onCreateView(inflater, container, savedInstanceState);
        view = new GameSurfaceView(getActivity().getApplicationContext(), controller);
        view.setBackgroundColor(controller.getWorld().getBackground());       //It's not the real background but a default color screen
        return view;
    }

    @Override
    public void onResume() {
        Log.wtf(TAG, "onResume: ");
        super.onResume();
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
        controller.start();
    }

    @Override
    public void onPause() {
        Log.wtf(TAG, "onPause: ");
        super.onPause();
        sensorManager.unregisterListener(this);
        controller.pause();
    }

    //**        Sensors     **//
    //https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-gyro
    @Override
    public void onSensorChanged(SensorEvent event) {
        //https://stackoverflow.com/questions/16763331/using-the-rotation-vector-sensor
        float vec[] = event.values;
        float[] orientation = new float[3];
        float[] rotMat = new float[9];

        //test game end
        if(controller.isGameEnd()){
            Log.wtf(TAG, "onSensorChanged: GAME END");
            //change view
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new ScoresFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        SensorManager.getRotationMatrixFromVector(rotMat, vec);
        SensorManager.getOrientation(rotMat, orientation);

        //float yaw = (float) orientation[0]; //Yaw
        float pitch = (float) orientation[1]; //Pitch
        float roll = (float) orientation[2]; //Roll

        controller.movePlayer(-pitch, -roll);   //Y is reversed
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Nothing normally
    }

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
