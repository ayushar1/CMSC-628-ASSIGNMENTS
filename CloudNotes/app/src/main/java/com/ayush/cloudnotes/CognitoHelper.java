package com.ayush.cloudnotes;

import android.content.Context;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.*;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.*;
import com.amazonaws.regions.Regions;

public class CognitoHelper {

    private CognitoUserPool userPool;
    private static String loggedInUserId = "";

    public CognitoHelper(Context context) {
        userPool = new CognitoUserPool(
                context,
                Constants.COGNITO_POOL_ID,
                Constants.COGNITO_CLIENT_ID,
                null,
                Regions.fromName(Constants.COGNITO_REGION)
        );
    }

    public static String getLoggedInUserId() { return loggedInUserId; }

    public void signUp(String username, String password, String email,
                       SignUpHandler handler) {
        CognitoUserAttributes attrs = new CognitoUserAttributes();
        attrs.addAttribute("email", email);
        userPool.signUpInBackground(username, password, attrs, null, handler);
    }

    public void confirmSignUp(String username, String code,
                              GenericHandler handler) {
        CognitoUser user = userPool.getUser(username);
        user.confirmSignUpInBackground(code, false, handler);
    }

    public void signIn(String username, String password,
                       AuthenticationHandler handler) {
        loggedInUserId = username;
        CognitoUser user = userPool.getUser(username);
        user.getSessionInBackground(handler);
    }

    public void signOut() {
        CognitoUser user = userPool.getCurrentUser();
        if (user != null) user.signOut();
        loggedInUserId = "";
    }

    public boolean isUserLoggedIn() {
        return userPool.getCurrentUser() != null
                && !userPool.getCurrentUser().getUserId().isEmpty();
    }
}