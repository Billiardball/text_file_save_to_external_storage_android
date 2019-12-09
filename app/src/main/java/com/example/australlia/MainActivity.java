package com.example.australlia;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
     EditText first;
     EditText last;
     EditText email;
     EditText post;
     Button save;
     String filePath;
     String mString = "";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first = (EditText) findViewById(R.id.firstNameInput);
        last = (EditText) findViewById(R.id.lastNameInput);
        email = (EditText) findViewById(R.id.emailAddressInput);
        post = (EditText) findViewById(R.id.postcodeInput);
        filePath = Environment.getExternalStorageDirectory().getPath()+"/Downloads";
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please input your First Name.", Toast.LENGTH_SHORT).show();
                }else if (last.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please input your Last Name.", Toast.LENGTH_SHORT).show();
                }else if (email.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please input your Email Address.", Toast.LENGTH_SHORT).show();
                }else if (post.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please input your Post Code.", Toast.LENGTH_SHORT).show();
                }else {

                    if(CheckingPermissionIsEnabledOrNot())
                    {
                        mString = "First Name: " + first.getText().toString() +
                                "\nLast Name: " + last.getText().toString() +
                                "\nEmail Address: " + email.getText().toString() +
                                "\nPost Code: " + post.getText().toString();
                        writeFileOnInternalStorage("myTextFile.txt", mString);
                        Toast.makeText(MainActivity.this, "Successfully saved !", Toast.LENGTH_LONG).show();
                    }

                    // If, If permission is not enabled then else condition will execute.
                    else {

                        //Calling method to enable permission.
                        RequestMultiplePermission();

                    }


                }
            }
        });
    }
    public void writeFileOnInternalStorage(String sFileName, String sBody){

        try{
            File gpxfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }


    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                }, 999);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 999:

                if (grantResults.length > 0) {

                    boolean ReadStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (ReadStorage && WriteStorage) {


                    }
                    else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }
}


