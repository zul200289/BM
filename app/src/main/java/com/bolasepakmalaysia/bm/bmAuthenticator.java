package com.bolasepakmalaysia.bm;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by zul on 28-Nov-14.
 */
public class bmAuthenticator extends AbstractAccountAuthenticator {

    private final Context mContext;
    public final static String ACCOUNT_TYPE = General.ACCOUNT_TYPE;

    public bmAuthenticator(Context context){
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] strings, Bundle bundle) throws NetworkErrorException {

        final Bundle result;
        final Intent intent;

        intent = new Intent(this.mContext, bmAuthenticatorActivity.class);
        //intent.putExtra(Constants.AUTHTOKEN_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        result = new Bundle();
        result.putParcelable(AccountManager.KEY_INTENT, intent);

        return result;

    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {

        return null;

    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        // Get the authToken

        // get account manager
        final AccountManager am = AccountManager.get(mContext);

        // get authtoken from account manager first
        String authToken = am.peekAuthToken(account, authTokenType);

        // if none is returned from account manager, get from server
        if ( TextUtils.isEmpty(authToken)){

            authToken = "";
            final String username = account.name;
            final String password = am.getPassword(account);

            // get authToken from server

            // url
            String url = "https://www.bolasepakmalaysia.com/account/getauthtoken?username=" + username + "&password=" + password;

            // httpclient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            try {
                HttpResponse response = httpClient.execute(httpPost);
                String responseString = EntityUtils.toString(response.getEntity());
                //User createdUser = new Gson().fromJson(responseString, User.class);
                if ( TextUtils.equals(responseString, "failed")) {
                    authToken = null;
                } else {
                    authToken = responseString;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // if we get an auth token, then return it.

        if (!TextUtils.isEmpty(authToken)){

            final Bundle result = new Bundle();

            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;

        }


        // if we don't get an auth token, the show the login activity
        final Intent intent = new Intent(mContext, bmAuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        intent.putExtra(bmAuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(bmAuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(bmAuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;

    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
