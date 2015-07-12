package com.bolasepakmalaysia.bm;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class bmAuthenticatorActivity extends AccountAuthenticatorActivity {

    // todo: entah cleanup
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";

    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
        this.setContentView(R.layout.activity_bm_authenticator);
    }

    public void onCancelClick(View v) {
        this.finish();
    }

    public void onSaveClick(View v) {

        TextView tvUsername;
        TextView tvPassword;
        String username;
        String password;
        boolean hasErrors = false;

        tvUsername = (TextView) this.findViewById(R.id.uc_txt_username);
        tvPassword = (TextView) this.findViewById(R.id.uc_txt_password);

        tvUsername.setBackgroundColor(Color.WHITE);
        tvPassword.setBackgroundColor(Color.WHITE);

        username = tvUsername.getText().toString();
        password = tvPassword.getText().toString();

        /*
        if (username.length() < 3) {
            hasErrors = true;
            tvUsername.setBackgroundColor(Color.MAGENTA);
        }
        if (password.length() < 3) {
            hasErrors = true;
            tvPassword.setBackgroundColor(Color.MAGENTA);
        }
        if (apiKey.length() < 3) {
            hasErrors = true;
            tvApiKey.setBackgroundColor(Color.MAGENTA);
        }

        if (hasErrors) {
            return;
        }
        */

        // Now that we have done some simple "client side" validation it
        // is time to check with the server

        //new GetAuthTokenMiawTask(this).execute(username, password);

        // ... perform some network activity here
        try{
            getauthtokenviewmodel vm = new GetAuthTokenMiawTask(this).execute(username, password).get();
            hasErrors = !(vm.issuccess);
        } catch (Exception e) {
            hasErrors = true;
        }

        // finished
        String accountType = bmAuthenticator.ACCOUNT_TYPE; //this.getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);
        if (accountType == null) {
            accountType = bmAuthenticator.ACCOUNT_TYPE;
        }

        AccountManager accMgr = AccountManager.get(getApplicationContext());

        if (hasErrors) {

            // handel errors
            //tvPassword.setBackgroundColor(Color.BLUE);
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);

        } else {

            // This is the magic that adds the account to the Android Account Manager
            final Account account = new Account(username, accountType);
            Boolean rr = accMgr.addAccountExplicitly(account, password, null);

            // Now we tell our caller, could be the Android Account Manager or even our own application
            // that the process was successful

            final Intent intent = new Intent();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
            this.setAccountAuthenticatorResult(intent.getExtras());
            if (rr) {
                this.setResult(RESULT_OK, intent);
            } else {
                this.setResult(RESULT_CANCELED, intent);
            }
            this.finish();
        }
    }

    private void createOrDontCreateAccount(getauthtokenviewmodel vm){

        Context context = getApplicationContext();

        // finished
        String accountType = bmAuthenticator.ACCOUNT_TYPE; //this.getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);
        AccountManager accMgr = AccountManager.get(getApplicationContext());

//        if (vm.issuccess){
//            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
//        }
        if (!vm.issuccess) {

            // handel errors
            //tvPassword.setBackgroundColor(Color.BLUE);

        } else {

            // This is the magic that adds the account to the Android Account Manager
            final Account account = new Account(vm.username, accountType);

                Boolean rr = accMgr.addAccountExplicitly(account, vm.password, null);

                // Now we tell our caller, could be the Andreoid Account Manager or even our own application
                // that the process was successful

                final Intent intent = new Intent();
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, vm.username);
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);

                this.setAccountAuthenticatorResult(intent.getExtras());
                if (vm.issuccess) {
                    this.setResult(RESULT_OK, intent);
                } else {
                    this.setResult(RESULT_CANCELED, intent);
                }
            this.finish();

        }

    }

    private class GetAuthTokenMiawTask extends AsyncTask<String, Integer, getauthtokenviewmodel> {

        private ProgressDialog dialog;
        private bmAuthenticatorActivity activity;
        private Context context;

        public GetAuthTokenMiawTask(bmAuthenticatorActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(getApplicationContext());
        }

        @Override
        protected void onPreExecute() {
            //this.dialog.setMessage("Progress start");
            //this.dialog.show();
        }

        @Override
        protected void onPostExecute(getauthtokenviewmodel vm) {
            //if (dialog.isShowing()) {
            //    dialog.hide();
            //}
            //activity.createOrDontCreateAccount(vm);
        }

        @Override
        protected getauthtokenviewmodel doInBackground(String... strings) {

            String username = strings[0];
            String password = strings[1];

            getauthtokenviewmodel vm = new getauthtokenviewmodel();
            vm.username = username;
            vm.password = password;

            try {
                URL url = new URL("http://www.bolasepakmalaysia.com/account/getauthtoken?username=" + username + "&password=" + password);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                if (con.getResponseCode() != 200) {
                    throw new RuntimeException("HTTP error code : " + con.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                Gson gson = new Gson();
                vm = gson.fromJson(br, getauthtokenviewmodel.class);
                con.disconnect();

            } catch (Exception e) {
                vm.issuccess = false;
            }

            return vm;
        }
    }
}