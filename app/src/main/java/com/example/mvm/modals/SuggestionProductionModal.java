package com.example.mvm.modals;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mvm.R;
import com.example.mvm.model.Category;

public class SuggestionProductionModal extends DialogFragment {
    private Category category;

    public SuggestionProductionModal(Category category){
        this.category = category;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View mView = getLayoutInflater().inflate(R.layout.result_production_sggestion, null);
        ImageView im = mView.findViewById(R.id.image);
        TextView tv = mView.findViewById(R.id.name);

        im.setImageBitmap(category.getImage());
        tv.setText(category.getName());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //image = getView().findViewById(R.id.image);
        //image.setImageBitmap(category.getImage());
        return builder.create();
    }
}
