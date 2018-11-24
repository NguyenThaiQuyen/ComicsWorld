package fivesecond.it.dut.comicsworld.utils;

import android.app.Dialog;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.interfaces.DialogCallback;
import fivesecond.it.dut.comicsworld.widget.CustomDialog;

public class GlobalUtils {
    public static int rating = 0;
    public static void  dialogRate(Context context, final DialogCallback dialogCallback){

        final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialogTheme);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_rate, null);

        dialog.setContentView(v);
        dialog.setTitle("Hello");
        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);

        SmileRating smileRating = (SmileRating) dialog.findViewById(R.id.smile_rating);
        smileRating.setSelectedSmile(BaseRating.OKAY);



        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {


            public String TAG;

            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley){
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        rating = 2;
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        rating = 4;
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        rating = 5;
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        rating = 3;
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        rating = 1;
                        break;
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallback != null)
                    dialogCallback.callback(rating);
                dialog.dismiss();
            }
        });

        dialog.show();


//        Dialog dialog =new Dialog(this);
//        dialog.setContentView(R.layout.dialog_rate);
//        dialog.setTitle("Đánh giá");
//        dialog.show();
    }
}
