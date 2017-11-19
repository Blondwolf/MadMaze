package madmaze.hearc.ch.madmaze;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;


import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;
import madmaze.hearc.ch.madmaze.fragments.FragmentFactory;

public class CustomDialogFragment extends DialogFragment {

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
                        .setTitle(destinationA)
                        .setMessage(message)
                        .setPositiveButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //change fragment
                                        dismiss();
                                        //
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
                                        dialog.cancel();
                                    }
                                }
                        )
                        .show();
                //endregion A
            case REDIRECT_TO_TWO_FRAGMENTS:
                //region B
                return new AlertDialog.Builder(getActivity())
                        .setTitle(destinationA+ " or "+ destinationB)
                        .setMessage(message)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //change fragment
                                        dismiss();
                                        //
                                    }
                                }
                        )
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //change fragment
                                        dismiss();
                                        //
                                    }
                                }
                        )
                        .show();
                //endregion B
            case SIMPLE_MESSAGE:
                //region C
                return new AlertDialog.Builder(getActivity())
                        .setTitle(destinationA)
                        .setMessage(message)
                        .setNeutralButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dismiss();
                                    }
                                }
                        )
                        .show();
                //endregion C
            case QUIT:
                //region D
                return new AlertDialog.Builder(getActivity())
                        .setTitle(destinationA)
                        .setMessage(message)
                        .setPositiveButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        getActivity().finish();
                                    }
                                }
                        )
                        .setNegativeButton(R.string.alert_dialog_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
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
