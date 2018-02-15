package com.apalon.notes.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.apalon.notes.R;

public class AboutDialog extends DialogFragment implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(createView());
        builder.setTitle(R.string.title_about);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    private View createView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.item_about, null);

        LinearLayout developer = v.findViewById(R.id.linearLayout_developer);
        developer.setOnClickListener(this);
        LinearLayout designer = v.findViewById(R.id.linearLayout_designer);
        designer.setOnClickListener(this);

        return v;
    }

    static String urlDeveloper = "https://www.linkedin.com/in/egor-pavlenko-318a60112/";
    static String urlDesigner = "https://www.linkedin.com/in/denis-runets-9a0283145/";

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.linearLayout_developer:
                intent.setData(Uri.parse(urlDeveloper));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;
            case R.id.linearLayout_designer:
                intent.setData(Uri.parse(urlDesigner));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;
        }
    }
}
