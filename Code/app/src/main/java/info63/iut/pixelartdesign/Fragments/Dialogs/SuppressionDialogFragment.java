package info63.iut.pixelartdesign.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import info63.iut.pixelartdesign.Accessors.FileAccessor;
import info63.iut.pixelartdesign.Accessors.FileModifier;
import info63.iut.pixelartdesign.Accessors.IMediaFiles;
import info63.iut.pixelartdesign.Accessors.IModifier;
import info63.iut.pixelartdesign.R;

public class SuppressionDialogFragment extends DialogFragment {
    IMediaFiles fileAccessor = new FileAccessor();
    IModifier fileModifier = new FileModifier();
    public final static int REQUEST_CODE_DIALOG = 0;

    public SuppressionDialogFragment(){
        //Required empty public constructor
    }

    public static SuppressionDialogFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        SuppressionDialogFragment fragment = new SuppressionDialogFragment();
        bundle.putInt("pos", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.sentence_suppresion).setPositiveButton(R.string.delete_confirmation, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fileModifier.deleteFile(getArguments().getInt("pos"));
                getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE_DIALOG, null);
                dismiss();
            }
        }).setNegativeButton(R.string.cancel_confirmation, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
