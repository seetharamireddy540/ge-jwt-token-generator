package com.gehc.ns.sample.auth;

import java.io.InputStream;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Throwable
    {    	
    	String filename = "config.properties";
    	Properties prop = getAppProperties(filename);
    	
    	String SvcUrl = prop.getProperty("SvcUrl");
    	
    	// Azure AD config values
    	String GENorthstarAppIdURI = prop.getProperty("GENorthstarAppIdURI");
    	String PartnerClientId = prop.getProperty("PartnerClientId");
    	
    	// Client secrets are labeled as "keys" in the AAD portal
    	String PartnerClientSecret = prop.getProperty("PartnerClientSecret");
    	
    	// Tenant ID can be provided in one of two forms:
    	// 1) AAD GUID
    	// 2) onmicrosoft domain name for tenant AAD, e.g. mytenant.onmicrosoft.com
    	// The first variant can be found from manage.windowsazure.com portal when the AAD is selected
    	// The URL in the browser will be in this format:
    	// https://manage.windowsazure.com/<AAD_name>#Workspaces/ActiveDirectoryExtension/Directory/<tenant_id>
    	// The second variant can be found by creating a user in the tenant AAD and examining its domain
    	// e.g. You create a "testuser" account in the mytenant.onmicrosoft.com domain,
    	// then the user will appear as testuser@mytenant.onmicrosoft.com in the list of AAD users
    	// in the old Azure portal (manage.windowsazure.com), so your tenant ID is mytenant.onmicrosoft.com
    	String PartnerAzureADTenantId = prop.getProperty("PartnerAzureADTenantId");
    	
    	// 1) Acquire the access token for the "GE Northstar" app
    	//    using the client ID and client secret of the Partner app
    	//    registered in the partner Azure AD
    	AuthHelper authHelper = new AuthHelper(PartnerAzureADTenantId);    	
        String token = authHelper.getAccessToken(GENorthstarAppIdURI, PartnerClientId, PartnerClientSecret);
        
        System.out.println("Token successfully acquired: " + token);
        
        // 2) Call the GE Northstar service with the acquired access token
//        String svcValue = RestClient.get(SvcUrl, token);
//
//        System.out.println(svcValue);
		System.exit(0);
    }
    
    private static Properties getAppProperties(String filename) throws Throwable {
    	InputStream input = App.class.getClassLoader().getResourceAsStream(filename);
    	if (input == null) {
    		throw new Exception("Unable to load properties file");
    	}
    	
    	Properties prop = new Properties();
    	prop.load(input);    	
    	return prop;
    }
}
