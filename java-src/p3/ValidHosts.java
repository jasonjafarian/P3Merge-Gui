// 
// Decompiled by Procyon v0.5.30
// 

package p3;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class ValidHosts implements Serializable
{
    public static String DEV_HOST;
    public static String TEST_HOST;
    public static String STAGING_HOST;
    public static String PROD_HOST;
    
    public static String getWhiteListHost(final String domain) {
        final Map<String, String> validDomains = new HashMap<String, String>();
        validDomains.put(ValidHosts.DEV_HOST, ValidHosts.DEV_HOST);
        validDomains.put(ValidHosts.TEST_HOST, ValidHosts.TEST_HOST);
        validDomains.put(ValidHosts.STAGING_HOST, ValidHosts.STAGING_HOST);
        validDomains.put(ValidHosts.PROD_HOST, ValidHosts.PROD_HOST);
        final String validDomain = validDomains.get(domain);
        if (validDomain == null) {
            return "";
        }
        return validDomain;
    }
    
    static {
        DEV_HOST = "https://p3-dev.cap.gsa.gov";
        TEST_HOST = "https://p3-test.cap.gsa.gov";
        STAGING_HOST = "https://p3-staging.cap.gsa.gov";
        PROD_HOST = "https://p3.cap.gsa.gov";
    }
}
