package com.example.mainproject.UserProfilePage;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mainproject.LoginAndRegistration.UserData;
import com.example.mainproject.PaymentsAndBalance.UserAccountBalance;
import com.example.mainproject.UtilityClasses.Message;
import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.SharePreferencesHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserProfileActivity extends AppCompatActivity implements GetCredentials.Communicator, ProvideNewEmail.ReProvideEmail {
    private EditText editFirstName,editLastName,editMobile;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ActionBar actionBar;
    private String TAG="UserActivity";
    private boolean editFlag=false;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initialize();
        setEditTextValues();
        actionBar.setTitle("Profile");
    }
    private void initialize() {
        editFirstName=findViewById(R.id.EditFirstNameInProfile);
        editLastName=findViewById(R.id.editLastNameInProfile);
        editMobile=findViewById(R.id.editMobileInProfile);


        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("users");
        actionBar=getSupportActionBar();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.dropdown_for_profile_activity,menu);
        Log.d(TAG, "onCreateOptionsMenu: called");
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: called times ");
        if(editFlag) {
            menu.findItem(R.id.edit).setVisible(false);
            menu.findItem(R.id.save).setVisible(true);
        }
        else{
            menu.findItem(R.id.save).setVisible(false);
            menu.findItem(R.id.edit).setVisible(true);
        }


        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.edit:
                editProfile();
                Log.d(TAG, "onOptionsItemSelected: Edit profile");
                editFlag=true;
                invalidateOptionsMenu();

                break;
            case R.id.save:
                Log.d(TAG, "onOptionsItemSelected: Save Data");
                saveDetails();
                invalidateOptionsMenu();

                break;
            case R.id.changeEmail:
                Log.d(TAG, "onOptionsItemSelected: change email");
               changeEmail();

                break;

        }
        return super.onOptionsItemSelected(item);
    }



    //Save the updated name and mobile No.
    public void saveDetails() {
        String first,last,phone;
        first=editFirstName.getText().toString();
        last=editLastName.getText().toString();
        phone=editMobile.getText().toString();

        if(setEmptyErrorMessages() && incorrectInputMessages())
        {
            return;
        }

        UserData userData=new UserData();
        userData.setFirstName(first);
        userData.setLastName(last);
        userData.setMobileNo(Long.parseLong(phone));
        userData.setEmail(user.getEmail());
        userData.setBalance(UserAccountBalance.USER_BALANCE);

        reference.child(user.getUid()).setValue(userData);
        Log.d(TAG, "saveDetails: Details saved");
        Message.message(this,"Details saved");
        editFlag=false;
        disableAllEditText();
    }
    private boolean incorrectInputMessages() {
        if(editMobile.getText().toString().length()<10)
        {
            editMobile.setError("Enter valid mobile Number");
            return true;
        }
        return  false;
    }
    private boolean setEmptyErrorMessages() {
        CharSequence error="Field can't be empty";
        if(editFirstName.getText().toString().isEmpty() || editLastName.getText().toString().isEmpty() || editMobile.getText().toString().isEmpty())
        {
            if(editFirstName.getText().toString().isEmpty())
                editFirstName.setError(error);
            if(editLastName.getText().toString().isEmpty())
                editLastName.setError(error);
            if(editMobile.getText().toString().isEmpty())
                editMobile.setError(error);
            return true;
        }
        return false;


    }


    //The editTexts will have the users Data
    /*Data will be loaded into SharedPreferences while logging in load from SharedPreferences whenever loading data*/
    private void setEditTextValues() {
        SharePreferencesHelper helper=new SharePreferencesHelper(this);
        editFirstName.setText(helper.loadPreferences("FirstName"));
        editLastName.setText(helper.loadPreferences("LastName"));
        editMobile.setText(helper.loadPreferences("MobileNumber"));
    }
    private void editProfile() {
        enableAllEdittexts();

    }
    private void enableAllEdittexts() {
        editFirstName.setEnabled(true);
        editLastName.setEnabled(true);
        editMobile.setEnabled(true);

        editFirstName.requestFocus();
    }
    private void disableAllEditText() {
        editFirstName.setEnabled(false);
        editLastName.setEnabled(false);
        editMobile.setEnabled(false);
    }


    private void changeEmail() {


        manager = getSupportFragmentManager();
        GetCredentials d=new GetCredentials();
        d.show(manager,"GetCredentials");
    }
    @Override
    public void submitCredentials(final String email, String pass) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass); // Current Login Credentials \\
        // Prompt the user to re-provide their sign-in credentials

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()) {
                           Log.d(TAG, "User re-authenticated.");
                           ProvideNewEmail provideNewEmail=new ProvideNewEmail();
                           provideNewEmail.show(manager,"ProvideNewEmail");


                       }
                       else
                           Log.d(TAG, "onComplete: User cannot be reAuthenticated");

                    }
                });

    }
    @Override
    public void updateEmail(final String email) {
        user = FirebaseAuth.getInstance().getCurrentUser();


        assert user != null;
        user.updateEmail(email)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User email address updated.");
                        Message.message(UserProfileActivity.this,"Your Email address has been changed");
                        reference.child("email").setValue(email);
                    }
                    else{
                        Log.d(TAG, "onComplete: Email not updated");
                        Message.message(UserProfileActivity.this,"Your Email address cannot be changed");
                    }
                }
            });
    }


    //If back is pressed disable the edit operation and go back to original screen
    @Override
    public void onBackPressed() {
        if(editFlag)
        {
            editFlag=false;
            invalidateOptionsMenu();
            setEditTextValues();
            disableAllEditText();
        }
        else
            super.onBackPressed();
    }
}
