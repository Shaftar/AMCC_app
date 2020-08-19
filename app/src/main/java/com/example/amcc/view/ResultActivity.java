package com.example.amcc.view;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.annotation.Nullable;


import com.example.amcc.R;
import com.example.amcc.model.ApiDataModel;


public class ResultActivity extends BaseActivity {

    private static final String TAG = "ResultActivity";
    private TextView resultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultView = findViewById(R.id.resultTextViewID);
        ApiDataModel oject = getIntent().getParcelableExtra("ApiResponse");
        if (oject != null) {
            Log.d(TAG, "onCreate: " + oject.toString());
            resultView.setText(oject.toString());
        } else {
            resultView.setText(R.string.something_wrong);
            resultView.setTextColor(Color.RED);
        }


    }

}
