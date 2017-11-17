package madmaze.hearc.ch.madmaze;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class CustomDialogFragmentWithTwoDestinations extends DialogFragment {

    public static CustomDialogFragmentWithTwoDestinations newInstance(int destinationFragmentA, int destinationFragmentB, int message){

        CustomDialogFragmentWithTwoDestinations dialog = new CustomDialogFragmentWithTwoDestinations();
        Bundle args = new Bundle();

        //region ARGUMENTS
        args.putInt("destinationA", destinationFragmentA);
        args.putInt("destinationB", destinationFragmentB);
        args.putInt("message", message);
        //endregion ARGUMENTS

        dialog.setArguments(args);

        return dialog;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final int destinationA = getArguments().getInt("destinationA");
        final int destinationB = getArguments().getInt("destinationB");

        final int message = getArguments().getInt("message");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(destinationA+ " or "+ destinationB)
                    .setMessage(message)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //change fragment
                                    dismiss();
                                    ((MainActivity)getActivity()).setViewPager(destinationA);
                                }
                            }
                    )
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //change fragment
                                    dismiss();
                                    ((MainActivity)getActivity()).setViewPager(destinationB);
                                }
                            }
                    )
                    .show();
    }
}
