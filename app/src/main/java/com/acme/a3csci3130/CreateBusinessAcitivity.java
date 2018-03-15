package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Class used for creating the Business based
 * on user input.
 */
public class CreateBusinessAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, primBusinessField, addressField, provinceField, numberField;
    private MyApplicationData appState;

    /**
     * Creates a detailed view of Business information required for input.  User is able
     * to input information to be used by submitInfoButton().
     * @param savedInstanceState Used to call the super onCreate method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());
        submitButton = (Button) findViewById(R.id.submitButton);
        numberField = (EditText) findViewById(R.id.number);
        nameField = (EditText) findViewById(R.id.name);
        primBusinessField = (EditText) findViewById(R.id.primBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

    }

    /**
     * Used to take entered information, verify it using checkInformation.
     * Sending correct information off to Firebase for adding to Database.
     * @param v Current view of activity.
     */
    public void submitInfoButton(View v) {
        String bID = appState.firebaseReference.push().getKey();//each entry needs a unique Id
        String number = numberField.getText().toString(); //9-digit number
        String name = nameField.getText().toString(); // required 2-48 characters
        String primBusiness = primBusinessField.getText().toString(); // required {Fisher, Distributor, Processor, Fish Monger}
        String address = addressField.getText().toString(); //required < 50 characters
        String province = provinceField.getText().toString(); // required {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT,““}
        Business business = new Business(bID, number, name, primBusiness, address, province);

        if(checkInformation(business)) {
            appState.firebaseReference.child(bID).setValue(business);
            finish();
        } else {
            //Let the user know they have to check information
            //let user know information is incorrect.
            Context context = getApplicationContext();
            CharSequence text = "Invalid input when creating Business!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    /**
     * Checks to see if each individual value on the business is correct.  Used as a safeguard
     * past the Firebase validation rules.
     * @param business business ready for submission to Firebase.
     * @return a boolean detailing if the values of the business match against preset rules.
     */
    private boolean checkInformation(Business business){
        boolean check;
        check = true;

        List primaries = Arrays.asList("Fisher", "Distributor", "Processor", "Fish Monger");
        List provinces = Arrays.asList("AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT","");

        if(business.number.length() != 9){
            check = false;
        }
        if(2 > business.name.length() && business.name.length() > 48){
            check = false;
        }
        if(!primaries.contains(business.primBusiness)){
            check = false;
        }
        if(business.address.length() > 50){
            check = false;
        }
        if(!provinces.contains(business.province)){
            check = false;
        }

        return check;
    }
}
