package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateBusinessAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, primBusinessField, addressField, provinceField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        primBusinessField = (EditText) findViewById(R.id.primBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

    }

    public void submitInfoButton(View v) {
        //TODO Should probably verify data is correct here....
        //each entry needs a unique ID
        String number = appState.firebaseReference.push().getKey(); //9-digit number
        String name = nameField.getText().toString(); // required 2-48 characters
        String primBusiness = primBusinessField.getText().toString(); // required {Fisher, Distributor, Processor, Fish Monger}
        String address = addressField.getText().toString(); //required < 50 characters
        String province = provinceField.getText().toString(); // required { {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT,““}
        Business person = new Business(number, name, primBusiness, address, province);

        appState.firebaseReference.child(number).setValue(person);

        finish();

    }
}
