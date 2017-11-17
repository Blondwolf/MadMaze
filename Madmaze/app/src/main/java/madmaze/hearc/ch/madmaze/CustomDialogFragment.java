package madmaze.hearc.ch.madmaze;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    public static CustomDialogFragment newInstance(int destinationFragment,  MessageType type, int message){

        CustomDialogFragment dialog = new CustomDialogFragment();
        Bundle args = new Bundle();

        //region ARGUMENTS
        args.putInt("destination", destinationFragment);
        args.putString("message_type", type.name());
        args.putInt("message", message);
        //endregion ARGUMENTS

        dialog.setArguments(args);

        return dialog;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final int destination = getArguments().getInt("destination");
        final String type = getArguments().getString("message_type");
        final int message = getArguments().getInt("message");


        switch (MessageType.valueOf(type)) {
            case REDIRECT_TO_NEW_FRAGMENT:
                //region A
                return new AlertDialog.Builder(getActivity())
                        .setTitle(destination)
                        .setMessage(message)
                        .setPositiveButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //change fragment
                                        dismiss();
                                        ((MainActivity)getActivity()).setViewPager(destination);
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
            case SIMPLE_MESSAGE:
                //region B
                return new AlertDialog.Builder(getActivity())
                        .setTitle(destination)
                        .setMessage(message)
                        .setNeutralButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dismiss();
                                    }
                                }
                        )
                        .show();
                //endregion B
            case QUIT:
                //region C
                return new AlertDialog.Builder(getActivity())
                        .setTitle(destination)
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
                //endregion C
        }
        return null;
    }
}
