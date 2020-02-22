package com.alphateamone.theophilus.tfis.DialogBox;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alphateamone.theophilus.tfis.R;

/**
 * Created by Theophilus on 2/20/2018.
 */

public class MyFragDialogBox extends DialogFragment implements View.OnClickListener {

    private Button yes, no;
    Communicator communicator;
    private EditText editTextString;
    private LinearLayout linearLayout, waitingLoad;
    private Typeface mTypeface, mTypefaceBlack, mTypefaceBold, mTypefaceRegular;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator    = (Communicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.topup_layout_fragment, null);

        yes         = view.findViewById(R.id.acceptBtn);
        no          = view.findViewById(R.id.cancelBtn);
        editTextString  = view.findViewById(R.id.passwordTyped);
        waitingLoad     = view.findViewById(R.id.waitingLoad);
        linearLayout    = view.findViewById(R.id.layMain);

        mTypefaceBlack  = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/hurme-geometric-bold.ttf");

        yes.setTypeface(mTypefaceBlack);
        no.setTypeface(mTypefaceBlack);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.acceptBtn){
            communicator.onDialogMsgTask(editTextString.getText().toString().trim());
            linearLayout.setVisibility(View.GONE);
            waitingLoad.setVisibility(View.VISIBLE);
        }
        else{
            communicator.onDialogMsgTask("");
            dismiss();
        }
    }

    public interface Communicator{
        void onDialogMsgTask(String textString);
    }
}
