package com.example.hera12.loginactivities.apputils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationAlertBox {

    FirebaseAuth userAuth;
    Context context;
    public EmailVerificationAlertBox(Context context,FirebaseAuth userAuth) {
        this.userAuth = userAuth;
        this.context = context;
    }

    public void EmailVerification(FirebaseAuth userAuth){
        FirebaseUser user = userAuth.getCurrentUser();
        user.sendEmailVerification();
        userAuth.signOut();
        showAlertDialogBox();

    }

    private void showAlertDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Email not verified")
                .setMessage("Please verify email and login again. Click Continue to verify")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int number) {
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_APP_EMAIL);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isVerified(FirebaseAuth userAuth){
        if(!userAuth.getCurrentUser().isEmailVerified()){
            return false;
        }
        return true;
    }
}
