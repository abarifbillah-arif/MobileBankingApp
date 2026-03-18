package com.example.mobilebanking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebanking.R;
import com.example.mobilebanking.models.User;
import com.example.mobilebanking.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private LinearLayout loginLayout, registerLayout;
    
    // Login views
    private EditText etLoginMobile, etLoginPin;
    private Button btnLogin;
    private TextView tvForgotPin;
    
    // Register views
    private EditText etRegName, etRegMobile, etRegEmail, etRegPin, etRegConfirmPin;
    private Button btnRegister;
    
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initViews();
        setupTabLayout();
        setupClickListeners();
        
        db = FirebaseFirestore.getInstance();
        
        // Check if user is already logged in
        checkLoginStatus();
    }
    
    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        loginLayout = findViewById(R.id.loginLayout);
        registerLayout = findViewById(R.id.registerLayout);
        
        // Login views
        etLoginMobile = findViewById(R.id.etLoginMobile);
        etLoginPin = findViewById(R.id.etLoginPin);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPin = findViewById(R.id.tvForgotPin);
        
        // Register views
        etRegName = findViewById(R.id.etRegName);
        etRegMobile = findViewById(R.id.etRegMobile);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPin = findViewById(R.id.etRegPin);
        etRegConfirmPin = findViewById(R.id.etRegConfirmPin);
        btnRegister = findViewById(R.id.btnRegister);
    }
    
    private void setupTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loginLayout.setVisibility(View.VISIBLE);
                    registerLayout.setVisibility(View.GONE);
                } else {
                    loginLayout.setVisibility(View.GONE);
                    registerLayout.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    
    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> loginUser());
        btnRegister.setOnClickListener(v -> registerUser());
        tvForgotPin.setOnClickListener(v -> handleForgotPin());
    }
    
    private void loginUser() {
        String mobile = etLoginMobile.getText().toString().trim();
        String pin = etLoginPin.getText().toString().trim();
        
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(pin)) {
            Toast.makeText(this, "সব ফিল্ড পূরণ করুন", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (mobile.length() != 11) {
            Toast.makeText(this, "সঠিক মোবাইল নম্বর দিন", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (pin.length() != 4) {
            Toast.makeText(this, "পিন ৪ ডিজিটের হতে হবে", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Check in Firestore
        db.collection(Constants.COLLECTION_USERS)
            .whereEqualTo("mobile", mobile)
            .whereEqualTo("pin", pin)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null && !snapshot.isEmpty()) {
                        User user = snapshot.getDocuments().get(0).toObject(User.class);
                        user.setId(snapshot.getDocuments().get(0).getId());
                        
                        // Save user session
                        getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
                            .edit()
                            .putString(Constants.KEY_USER_ID, user.getId())
                            .putString(Constants.KEY_USER_NAME, user.getName())
                            .putString(Constants.KEY_USER_MOBILE, user.getMobile())
                            .putBoolean(Constants.KEY_IS_LOGGED_IN, true)
                            .apply();
                        
                        Toast.makeText(this, "লগইন সফল হয়েছে", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "ভুল মোবাইল নম্বর বা পিন", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "লগইন ব্যর্থ হয়েছে", Toast.LENGTH_SHORT).show();
                }
            });
    }
    
    private void registerUser() {
        String name = etRegName.getText().toString().trim();
        String mobile = etRegMobile.getText().toString().trim();
        String email = etRegEmail.getText().toString().trim();
        String pin = etRegPin.getText().toString().trim();
        String confirmPin = etRegConfirmPin.getText().toString().trim();
        
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || 
            TextUtils.isEmpty(pin) || TextUtils.isEmpty(confirmPin)) {
            Toast.makeText(this, "সব ফিল্ড পূরণ করুন", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (mobile.length() != 11) {
            Toast.makeText(this, "সঠিক মোবাইল নম্বর দিন", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (pin.length() != 4) {
            Toast.makeText(this, "পিন ৪ ডিজিটের হতে হবে", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!pin.equals(confirmPin)) {
            Toast.makeText(this, "পিন মিলছে না", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Check if mobile already exists
        db.collection(Constants.COLLECTION_USERS)
            .whereEqualTo("mobile", mobile)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && !task.getResult().isEmpty()) {
                        Toast.makeText(this, "এই নম্বরে ইতিমধ্যে অ্যাকাউন্ট আছে", Toast.LENGTH_SHORT).show();
                    } else {
                        // Create new user
                        User user = new User(name, mobile, email, pin);
                        
                        db.collection(Constants.COLLECTION_USERS)
                            .add(user)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(this, "নিবন্ধন সফল হয়েছে! লগইন করুন", Toast.LENGTH_SHORT).show();
                                
                                // Switch to login tab
                                tabLayout.selectTab(tabLayout.getTabAt(0));
                                
                                // Clear fields
                                etRegName.setText("");
                                etRegMobile.setText("");
                                etRegEmail.setText("");
                                etRegPin.setText("");
                                etRegConfirmPin.setText("");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "নিবন্ধন ব্যর্থ হয়েছে", Toast.LENGTH_SHORT).show();
                            });
                    }
                }
            });
    }
    
    private void handleForgotPin() {
        Toast.makeText(this, "সাপোর্টের সাথে যোগাযোগ করুন: ১৬২৭৮", Toast.LENGTH_LONG).show();
    }
    
    private void checkLoginStatus() {
        boolean isLoggedIn = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
            .getBoolean(Constants.KEY_IS_LOGGED_IN, false);
            
        if (isLoggedIn) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }
}
