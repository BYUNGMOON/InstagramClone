package com.example.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity {

    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;
    private Button btnGetAllData;
    private Button btnTransition;

    private String allKickBoxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnNextActivity);

        txtGetData = findViewById(R.id.txtGetData);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("FpPjiHd2wS", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object != null && e == null) {

                            txtGetData.setText(object.get("Name") + "\n"
                                    + "Punch Power : " + object.get("punch_power") + "\n"
                                    + "punch_Speec : " + object.get("punch_speed") + "\n"
                                    + "kick_Power : " + object.get("kick_power") + "\n"
                                    + "kick_Speed : " + object.get("kick_speed"));
                        }
                        else {
                            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxer = "";

                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                queryAll.whereGreaterThan("punch_power", 1000);
                queryAll.whereGreaterThanOrEqualTo("punch_speed", 300);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            if (objects.size() > 0) {

                                for (ParseObject kickBoxer : objects) {

                                    allKickBoxer = allKickBoxer + kickBoxer.get("punch_power") + "\n";
                                }

                                FancyToast.makeText(SignUp.this,
                                        allKickBoxer,
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,
                                        true).show();
                            }
                            else {
                                FancyToast.makeText(SignUp.this,
                                        "Fail",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,
                                        true).show();
                            }
                        }

                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);

            }
        });




    }

    //kickboxer punch speed, punch power, kick speed, kick power, name of kickboxer, callback.
    public void btnUploadClicked(View v) {

        try {

            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("Name", edtName.getText().toString());
            kickBoxer.put("punch_speed", edtPunchSpeed.getText().toString());
            kickBoxer.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {
                        FancyToast.makeText(SignUp.this,
                                kickBoxer.get("Name") + " is saved successfully",
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS,
                                true).show();

                        edtName.setText("");
                        edtPunchSpeed.setText("");
                        edtPunchPower.setText("");
                        edtKickSpeed.setText("");
                        edtKickPower.setText("");
                    } else {
//                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });


        } catch (Exception e) {

// Test Update hahahahaha
            FancyToast.makeText(SignUp.this,
                    e.getMessage(),
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,
                    true).show();
        }
    }
}