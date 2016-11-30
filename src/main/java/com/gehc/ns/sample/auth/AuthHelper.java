package com.gehc.ns.sample.auth;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;

public class AuthHelper {	
	private static final String authorityBaseURL = "https://login.microsoftonline.com";
	private String authorityURL;
	
	public AuthHelper(String tenant) {
		authorityURL = authorityBaseURL + "/" + tenant + "/";
	}
	
	public String getAccessToken(String appIdURI, String clientId, String clientSecret) throws AuthenticationException {
		AuthenticationResult authResult = null;
		try {
			authResult = getAccessTokenFromClientCredential(appIdURI, clientId, clientSecret);
		}
		catch (Throwable e) {
			throw new AuthenticationException(e.getMessage());
		}
		return authResult.getAccessToken();
	}
	
	private AuthenticationResult getAccessTokenFromClientCredential(String appIdURI, String clientId, String clientSecret) throws Throwable {
		AuthenticationContext context = null;
		AuthenticationResult result = null;
		ExecutorService executorService = null;
		ClientCredential clientCredential = new ClientCredential(clientId, clientSecret);
		try {
			executorService = Executors.newFixedThreadPool(1);
			context = new AuthenticationContext(authorityURL, true, executorService);


//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy-src.research.ge.com", 8080));
//			// if need to auth for proxy
//			Authenticator authenticator = new Authenticator() {
//				public PasswordAuthentication getPasswordAuthentication() {
//					return (new PasswordAuthentication("212579350",
//						"Password".toCharArray()));
//				}
//			};
//
//			Authenticator.setDefault(authenticator);
//			context.setProxy(proxy);

			Future<AuthenticationResult> future = context.acquireToken(
					appIdURI,
					clientCredential,
					null);
			result = future.get();
		}
		catch (ExecutionException e) {
			throw e.getCause();
		}
		
		if (result == null) {
            throw new Exception(
                    "authentication result was null");
        }
		
        return result;
	}
}