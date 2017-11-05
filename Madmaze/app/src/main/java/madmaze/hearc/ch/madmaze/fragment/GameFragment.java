package madmaze.hearc.ch.madmaze.fragment;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import madmaze.hearc.ch.madmaze.game.controller.GameController;
import madmaze.hearc.ch.madmaze.game.model.Ball;
import madmaze.hearc.ch.madmaze.game.model.Wall;
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
        super.onCreate(savedInstanceState);

        //Sensors
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        //Create or load world
        World world = new World();
        world.setBallPlayer(new Ball(new PointF(100, 100), 40));
        world.addElement(new Wall(new PointF(100, 100), new PointF(50, 50)));

        controller = new GameController(world);
    }

    //Android widgets
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new GameSurfaceView(getActivity().getApplicationContext(), controller);
        view.setBackgroundColor(controller.getWorld().getBackground());       //Need to remove when show the real background
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    //**        Sensors     **//
    //https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-gyro
    @Override
    public void onSensorChanged(SensorEvent event) {
        float axisX = event.values[0];
        float axisY = event.values[1];
        float axisZ = event.values[2];

        //Normalize
        float angularSpeed = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
        //if()

        controller.movePlayer(axisY, axisX);
        //System.out.println(axisX +" / "+ axisY +" / "+ axisZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Nothing normally
    }
}
