package madmaze.hearc.ch.madmaze.fragments;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.p2p.WifiP2pInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;

import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.game.controller.GameController;
import madmaze.hearc.ch.madmaze.game.controller.WorldManager;
import madmaze.hearc.ch.madmaze.game.controller.IOTools;
import madmaze.hearc.ch.madmaze.game.model.Ball;
import madmaze.hearc.ch.madmaze.game.model.Goal;
import madmaze.hearc.ch.madmaze.game.model.Rectangle;
import madmaze.hearc.ch.madmaze.game.model.World;
import madmaze.hearc.ch.madmaze.game.view.GameSurfaceView;
import madmaze.hearc.ch.madmaze.game.wifi.Client;
import madmaze.hearc.ch.madmaze.game.wifi.Server;

public class GameFragment extends Fragment implements SensorEventListener {

    public static final String TAG = "Game";

    //View & controller
    GameSurfaceView view;
    GameController controller;

    //Sensor
    SensorManager sensorManager;
    Sensor gyroscope;

    int lastPosition = 0;

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
    }

    //Android widgets
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.wtf(TAG, "onCreateView: ");
        //super.onCreateView(inflater, container, savedInstanceState);
        //Connect server-client
        WorldManager wm = new WorldManager();
        int worldID;

        //Log.wtf(TAG, "Check last position: " + lastPosition);

        worldID = 1;//getArguments().getInt("worldID");


        controller = new GameController(wm.getWorld(worldID));

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

            int oldScore = IOTools.read(getActivity().getApplicationContext(), controller.getWorld().getName());
            int newScore = controller.getScore();
            if(newScore < oldScore || oldScore < 0) // if oldscore is < 0, then there is no score saved
                IOTools.write(getActivity().getApplicationContext(), controller.getWorld().getName(), newScore);

            //Init Fragment
            Fragment fragment = new ScoresFragment();

            //Create bundle to send to ScoreFragment
            //https://stackoverflow.com/questions/36041545/send-data-to-fragment-with-fragmenttransaction
            Bundle arguments = new Bundle();
            arguments.putString("LevelName", controller.getWorld().getName());
            fragment.setArguments(arguments);

            //Change view
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        SensorManager.getRotationMatrixFromVector(rotMat, vec);
        SensorManager.getOrientation(rotMat, orientation);

        //float yaw = (float) orientation[0]; //Yaw
        float pitch = (float) orientation[1]; //Pitch
        float roll = (float) orientation[2]; //Roll

        MainActivity activity = (MainActivity)getActivity();
        if(activity.isClient()) {
            controller.movePlayerX(-pitch);
        } else {
            controller.movePlayerY(-roll);
        }
    }

    public String getServerPos() {
        if(controller.getWorld() == null) {
            return "move;0";
        }
        return "move;"+controller.getWorld().getBallPlayer().getPosition().x;
    }

    public String getClientPos() {
        if(controller.getWorld() == null) {
            return "move;0";
        }
        return "move;"+controller.getWorld().getBallPlayer().getPosition().y;
    }

    public void movePlayerY(float value) {
        if(controller == null) {
            return;
        }
        controller.movePlayerY(value);
    }

    public void movePlayerX(float value) {
        if(controller == null) {
            return;
        }
        controller.movePlayerX(value);
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
