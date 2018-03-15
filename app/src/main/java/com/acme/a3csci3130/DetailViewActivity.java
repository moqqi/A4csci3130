package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Class used for Viewing, updating, and erasing existing Business.
 */
public class DetailViewActivity extends Activity {

    private EditText nameField, primBusinessField, addressField, provinceField, numberField;
    Business receivedPersonInfo;

    private MyApplicationData appData;

    /**
     * Creates a detailed view of Business information.  User is able
     * to update information based on the initialized data in the text views.
     * @param savedInstanceState saved instance of application used to call super
     *                           onCreate.
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = (MyApplicationData)getApplication();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedPersonInfo = (Business)getIntent().getSerializableExtra("Business");

        numberField = (EditText) findViewById(R.id.number);
        nameField = (EditText) findViewById(R.id.name);
        primBusinessField = (EditText) findViewById(R.id.primBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

        if(receivedPersonInfo != null){
            numberField.setText(receivedPersonInfo.number);
            nameField.setText(receivedPersonInfo.name);
            primBusinessField.setText(receivedPersonInfo.primBusiness);
            if(receivedPersonInfo.address != null){
                addressField.setText(receivedPersonInfo.address);
            }
            if(receivedPersonInfo.province != null){
                provinceField.setText(receivedPersonInfo.province);
            }
        }
    }

    /**
     * Using values entered by the user on the text fields, method checks if
     * data is correct format.  Then updates the business on Firebase.
     * @param v View of the current Activity.
     */
    public void updateBusiness(View v){
        appData = (MyApplicationData)getApplication();
        String number = numberField.getText().toString();
        String name = nameField.getText().toString(); // required 2-48 characters
        String primBusiness = primBusinessField.getText().toString(); // required {Fisher, Distributor, Processor, Fish Monger}
        String address = addressField.getText().toString(); //required < 50 characters
        String province = provinceField.getText().toString(); // required { {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT,““}
        Business business = new Business(receivedPersonInfo.businessID, number, name, primBusiness, address, province);

        if(checkInformation(business)){
            appData.firebaseReference.child(receivedPersonInfo.businessID).setValue(business);
            finish();
        } else {
            //let user know information is incorrect.
            Context context = getApplicationContext();
            CharSequence text = "Invalid input when updating Business!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    /**
     * Using the reference of the business passed in, removes the value
     * from Firebase.
     * @param v View of the current activity.
     */
    public void eraseBusiness(View v)
    {
        //Get the app wide shared variables
        appData = (MyApplicationData)getApplication();
        if(receivedPersonInfo != null){
            appData.firebaseReference.child(receivedPersonInfo.businessID).removeValue();
        }
        finish();
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
