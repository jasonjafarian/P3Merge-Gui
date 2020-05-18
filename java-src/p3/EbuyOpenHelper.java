package p3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class EbuyOpenHelper {

	private static final Log logger = LogFactory.getLog(EbuyOpenHelper.class);

	/*
	 * * Authentication service entry point
	 */
	private static String lhost;
	private static String utilid;
	private static String utilpwd;
	private static String auth;
	private static String dashboardLocation;
	//private static String ombmaxlink;
	private static String ombmaxredirectlink;
	private static String restservername;
	//private static String restservername2;
	private static String fileuploadserviceurl;
	private static String fileuploadauthurl;
	private static String zipfileuploadlocation;
	//private static String primarySOLRServer;
	//private static String secondarySOLRServer;
	private static String solrCredentials;
	   private static boolean isBSPEnvironment;
	    private static String queryPipelinePath;
	    private static String casServerUrl;
	    private static String casServerProdUrl;

	public static final String eBuyOpenCookie = "ebuy_open_agreed";
	public static final String readyForDashboard = "readyfordashboard";
	public static final String loginDone = "loginDone";

	
	public static String getCasServerProdUrl() {
	        return EbuyOpenHelper.casServerProdUrl;
	}
	    
	public static void setCasServerProdUrl(final String casServerProdUrl) {
	        EbuyOpenHelper.casServerProdUrl = casServerProdUrl;
	}
	    
	public static String getZipfileuploadlocation() {
		return zipfileuploadlocation;
	}

	public static void setZipfileuploadlocation(String zipfileuploadlocation) {
		EbuyOpenHelper.zipfileuploadlocation = zipfileuploadlocation;
	}

	public static String getFileuploadserviceurl() {
		return fileuploadserviceurl;
	}

	public static void setFileuploadserviceurl(String fileuploadserviceurl) {
		EbuyOpenHelper.fileuploadserviceurl = fileuploadserviceurl;
	}

	public static String getFileuploadauthurl() {
		return fileuploadauthurl;
	}

	public static void setFileuploadauthurl(String fileuploadauthurl) {
		EbuyOpenHelper.fileuploadauthurl = fileuploadauthurl;
	}

	public static String getRestservername() {
		return restservername;
	}

	public static void setRestservername(String restservername) {
		EbuyOpenHelper.restservername = restservername;
	}

	/*public static String getRestservername2() {
                return restservername2;
        }

        public static void setRestservername2(String restservername2) {
                EbuyOpenHelper.restservername2 = restservername2;
        }*/


	public static String getOmbmaxredirectlink() {
		return ombmaxredirectlink;
	}



	public static void setOmbmaxredirectlink(String ombmaxredirectlink) {
		EbuyOpenHelper.ombmaxredirectlink = ombmaxredirectlink;
	}

	/*public static String getOmbmaxlink() {
		return ombmaxlink;
	}

	public static void setOmbmaxlink(String ombmaxlink) {
		EbuyOpenHelper.ombmaxlink = ombmaxlink;
	}*/

	public static String getLhost() {
		return lhost;
	}

	public static void setLhost(String lhost) {
		EbuyOpenHelper.lhost = lhost;
	}

	public static String getUtilid() {
		return utilid;
	}

	public static void setUtilid(String utilid) {
		EbuyOpenHelper.utilid = utilid;
	}

	public static String getUtilpwd() {
		return utilpwd;
	}

	public static void setUtilpwd(String utilpwd) {
		EbuyOpenHelper.utilpwd = utilpwd;
	}

	public static String getAuth() {
		return auth;
	}

	public static void setAuth(String auth) {
		EbuyOpenHelper.auth = auth;
	}

	public static String getDashboardLocation() {
		return dashboardLocation;
	}

	public static void setDashboardLocation(String dashboardLocation) {
		EbuyOpenHelper.dashboardLocation = dashboardLocation;
	}

	/*public static String getPrimarySOLRServer() {
		return primarySOLRServer;
	}

	public static void setPrimarySOLRServer(String primarySOLRServer) {
		EbuyOpenHelper.primarySOLRServer = primarySOLRServer;
	}*/

	/*public static String getSecondarySOLRServer() {
		return secondarySOLRServer;
	}

	public static void setSecondarySOLRServer(String secondarySOLRServer) {
		EbuyOpenHelper.secondarySOLRServer = secondarySOLRServer;
	}*/

	public static String getSolrCredentials() {
		return solrCredentials;
	}

	public static void setSolrCredentials(String solrCredentials) {
		EbuyOpenHelper.solrCredentials = solrCredentials;
	}
	
	 public static void setBSPEnvironment(final boolean isBSPEnvironment) {
	        EbuyOpenHelper.isBSPEnvironment = isBSPEnvironment;
	    }
	    
	    public static boolean isBSPEnvironment() {
	        return EbuyOpenHelper.isBSPEnvironment;
	    }
	    
	    public static void setQueryPipelinePath(final String queryPipelinePath) {
	        EbuyOpenHelper.queryPipelinePath = queryPipelinePath;
	    }
	    
	    public static String getQueryPipelinePath() {
	        return EbuyOpenHelper.queryPipelinePath;
	    }
	    
	    public static void setCasServerUrl(final String casServerUrl) {
	        EbuyOpenHelper.casServerUrl = casServerUrl;
	    }
	    
	    public static String getCasServerUrl() {
	        return EbuyOpenHelper.casServerUrl;
	    }
	    
	    public static String getOMBMaxLoginLink(final HttpServletRequest request) {
	        String casServerUrlTemp = getCasServerUrl();
	        if (isProdHost(request)) {
	            casServerUrlTemp = getCasServerProdUrl();
	        }
	        return casServerUrlTemp + "login?securityLevel=securePlus2&service=https://" + request.getHeader("x-forwarded-host") + request.getContextPath() + getOmbmaxredirectlink();
	    }
	    
	    public static boolean isProdHost(final HttpServletRequest request) {
	        return true;
	    }
	    
	    public static String getRedirectHost(final HttpServletRequest request) {
	        final String hostBsp = request.getHeader("x-forwarded-host");
	        if (hostBsp.contains("p3-dev.cap.gsa.gov")) {
	            return "https://p3-dev.cap.gsa.gov";
	        }
	        if (hostBsp.contains("p3-test.cap.gsa.gov")) {
	            return "https://p3-test.cap.gsa.gov";
	        }
	        if (hostBsp.contains("p3-staging.cap.gsa.gov")) {
	            return "https://p3-staging.cap.gsa.gov";
	        }
	        if (hostBsp.contains("p3.cap.gsa.gov")) {
	            return "https://p3.cap.gsa.gov";
	        }
	        return "";
	    }
	    
	    public static boolean isUserAuthenticated(final HttpServletRequest request) {
	        final HttpSession session = request.getSession();
	        return session.getAttribute("P3Checked") != null;
	    }
	    
	    public static void setLastRequestURI(final HttpServletRequest request, final String requestURI) {
	        final HttpSession session = request.getSession();
	        if (!isUserAuthenticated(request)) {
	            session.setAttribute("LAST_REQUESTED_URI", (Object)requestURI);
	        }
	    }
	    
	    public static String getLastRequestURI(final HttpServletRequest request) {
	        final HttpSession session = request.getSession();
	        final String lastURI = (String)session.getAttribute("LAST_REQUESTED_URI");
	        return lastURI;
	    }
	    
	    public static void removeLastRequestURI(final HttpServletRequest request) {
	        final HttpSession session = request.getSession();
	        session.removeAttribute("LAST_REQUESTED_URI");
	    }
	    
	    public static String getOMBMaxLoginLinkWithExternalDNS(final HttpServletRequest request) {
	        String casServerUrlTemp = getCasServerUrl();
	        if (isProdHost(request)) {
	            casServerUrlTemp = getCasServerProdUrl();
	        }
	        return casServerUrlTemp + "login?securityLevel=securePlus2&service=" + getRedirectHost(request) + request.getContextPath() + getOmbmaxredirectlink();
	    }

}
