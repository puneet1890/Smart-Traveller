package com.example.puneet.smarttravellerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity
{
    EditText et_Registration_Name,et_Registration_Email,et_Registration_Password,et_Registration_Confirm_Password;
    Button btn_Registration_Register,btn_Registration_Login;

    DBDistanceHelper dbDistanceHelper;

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Log.d("smart traveller","in onCreate of RegistrationActivity");

        db = new DBHelper(this);
        dbDistanceHelper = new DBDistanceHelper(this);

        et_Registration_Name = findViewById(R.id.et_Registration_Name);
        et_Registration_Email = findViewById(R.id.et_Registration_Email);
        et_Registration_Password = findViewById(R.id.et_Registration_Password);
        et_Registration_Confirm_Password = findViewById(R.id.et_Registration_Confirm_Password);

        btn_Registration_Register = findViewById(R.id.btn_Registration_Register);
        btn_Registration_Login = findViewById(R.id.btn_Registration_Login);
    }

    public void reg_register(View view)
    {
        Log.d("smart traveller", "in register() of Registration Activity - Adding user to the Table");

        String name = et_Registration_Name.getText().toString();
        String email = et_Registration_Email.getText().toString();
        String pass = et_Registration_Password.getText().toString();
        String confirmPass = et_Registration_Confirm_Password.getText().toString();

        Log.d("smart traveller", "User data before adding to the table " +name+" "+ email + " " + pass + " " + confirmPass);

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty())
        {
            displayToast("Name/Username/Password/Confirm Password field empty");
        }
        else if(pass.hashCode()!= confirmPass.hashCode())
        {
            displayToast("Passwords don't match");
        }
        else
            {
                db.addUser(name,email, pass, confirmPass);

                Log.d("smart traveller","Adding places to visit in 1 Hr");

                dbDistanceHelper.addPlaces("Wonderla","12.8346","77.4010","Bangalore","1");
                dbDistanceHelper.addPlaces("Bannerghatta National Park","12.8003","77.5770","Bangalore","1");
                dbDistanceHelper.addPlaces("Lalbagh Botanical Garden","12.9507","77.5848","Bangalore","1");
                dbDistanceHelper.addPlaces("Snow City","13.0058","77.5921","Bangalore","1");
                dbDistanceHelper.addPlaces("Bangalore Palace","12.9988","77.5921","Bangalore","1");
                dbDistanceHelper.addPlaces("ISKCON Temple","28.5570","77.2520","Bangalore","1");
                dbDistanceHelper.addPlaces("Dodda Basavana Temple","12.9429","77.5682","Bangalore","1");
                dbDistanceHelper.addPlaces("Lumbini Gardens","13.0434","77.6096","Bangalore","1");
                dbDistanceHelper.addPlaces("Jawaharlal Nehru Planetarium","12.9849","77.5896","Bangalore","1");
                dbDistanceHelper.addPlaces("Savanadurga","12.9206","77.2944","Bangalore","1");


                Log.d("smart traveller","Adding places to visit in 2 Hrs");

                dbDistanceHelper.addPlaces("Nandhi Hills","13.3702","77.6835","Bangalore","2");
                dbDistanceHelper.addPlaces("Ramanagara","12.6003","77.4702","Bangalore","2");
                dbDistanceHelper.addPlaces("Anthargange","13.8320","75.7455","Bangalore","2");
                dbDistanceHelper.addPlaces("Manchanabele Dam","12.8749","77.3361","Bangalore","2");
                dbDistanceHelper.addPlaces("Hogenakkal falls","12.1160","77.7774","Bangalore","2");
                dbDistanceHelper.addPlaces("Skandagiri Hills","13.4181","77.6830","Bangalore","2");
                dbDistanceHelper.addPlaces("Bheemeshwari","12.3122","77.2741","Bangalore","2");
                dbDistanceHelper.addPlaces("Shivanasamudra Falls","12.2997","77.1773","Bangalore","2");
                dbDistanceHelper.addPlaces("Chunci Falls","12.5462","77.4199","Bangalore","2");
                dbDistanceHelper.addPlaces("Devarayanadurga","13.3737","77.2075","Bangalore","2");


                Log.d("smart traveller","Adding places to visit in 3 Hrs");

                dbDistanceHelper.addPlaces("Mysore","12.2958","76.6394","Bangalore","3");
                dbDistanceHelper.addPlaces("Lepakshi","13.8041","77.6049","Bangalore","3");
                dbDistanceHelper.addPlaces("Srirangapatna","12.4216","76.6931","Bangalore","3");
                dbDistanceHelper.addPlaces("Shravanabelagola","12.8563","76.4799","Bangalore","3");
                dbDistanceHelper.addPlaces("Talakadu","12.1947","77.0305","Bangalore","3");
                dbDistanceHelper.addPlaces("Ranganathittu Bird Sanctuary","12.4244","76.6563","Bangalore","3");
                dbDistanceHelper.addPlaces("BR Hills","11.9956","77.1428","Bangalore","3");
                dbDistanceHelper.addPlaces("Kokkare Bellur Bird Sanctuary","12.5133","77.0877","Bangalore","3");
                dbDistanceHelper.addPlaces("Kotilingeshwara","12.9952","78.2957","Bangalore","3");
                dbDistanceHelper.addPlaces("Coorg","12.3375","75.8069","Coorg","5");

                displayToast("User registered successfully");
                Log.d("smart traveller", "User data has been added to the table " +name+" "+ email + " " + pass + " " + confirmPass);

                Log.d("smart traveller","Successfully finished adding data");
                finish();
            }
    }

    public void reg_login(View view)
    {
        Log.d("smart traveller","You are navigated back to login page");
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    private void displayToast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

