package madmaze.hearc.ch.madmaze;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;
import madmaze.hearc.ch.madmaze.fragments.FragmentFactory;

public class CustomDialogFragment extends DialogFragment {

    private static final String TAG = "CustomDialogFragment";

    public static CustomDialogFragment newInstance(FragmentType destinationA, FragmentType destinationB, MessageType type, int message){

        CustomDialogFragment dialog = new CustomDialogFragment();
        Bundle args = new Bundle();

        //region ARGUMENTS
        args.putString("destinationA", destinationA.name());
        args.putString("destinationB", destinationB.name());
        args.putString("message_type", type.name());
        args.putInt("message", message);
        //endregion ARGUMENTS

        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final String destinationA = getArguments().getString("destinationA");
        final String destinationB = getArguments().getString("destinationB");

        final String type = getArguments().getString("message_type");
        final int message = getArguments().getInt("message");

        switch (MessageType.valueOf(type)) {
            case REDIRECT_TO_NEW_FRAGMENT:
                //region A
                return new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: OK - redirect to new fragment");

                                        //go to fragment A
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.frame_container, FragmentFactory.createFragment(FragmentType.valueOf(destinationA)));
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                }
                        )
                        .setNegativeButton(R.string.alert_dialog_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: CANCEL - redirect to new fragment");
                                        dialog.cancel();
                                    }
                                }
                        )
                        .show();

                //endregion A
            case REDIRECT_TO_TWO_FRAGMENTS:
                //region B
                return new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: OK - redirect to 2 fragments: A - " + destinationA);

                                        //go to fragment A
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.frame_container, FragmentFactory.createFragment(FragmentType.valueOf(destinationA)));
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                }
                        )
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: OK - redirect to 2 fragments: B - " + destinationB);
                                        //go to fragment B
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.frame_container, FragmentFactory.createFragment(FragmentType.valueOf(destinationB)));
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                }
                        )
                        .show();
                //endregion B
            case SIMPLE_MESSAGE:
                //region C
                return new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage(message)
                        .setNeutralButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: OK - simple message ");
                                        dismiss();
                                    }
                                }
                        )
                        .show();
                //endregion C
            case QUIT:
                //region D
                return new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: OK - quit");
                                        getActivity().finish();
                                    }
                                }
                        )
                        .setNegativeButton(R.string.alert_dialog_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.wtf(TAG, "onClick: CANCEL - quit");
                                        dialog.cancel();
                                    }
                                }
                        )
                        .show();
                //endregion D
        }
        return null;
    }
}
