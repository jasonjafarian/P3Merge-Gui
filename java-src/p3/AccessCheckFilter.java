package p3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet. * ;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AccessCheckFilter implements Filter {

	private static final Log logger = LogFactory.getLog(AccessCheckFilter.class);

	public static final String CHECK_TOKEN = "P3Checked";
	public static final String ACCESS_DENIED = "P3AccessDenied";

	final List < String > excludedRequestList = Collections.unmodifiableList(Arrays.asList("/bootstrap/js/", "/p3/js/", "/p3/art/", "/p3/css/", "/banana/css/", "/banana/vendor/", "/p3/fonts/", "/banana/help.js", "/p3/app/components/", "/banana/app/app.js", "/banana/app/directives/", "/banana/app/controllers/", "/banana/app/services/", "/banana/config.js", "/banana/app/dashboards/", "/banana/app/partials/", "/banana/app/components/", "/banana/app/panels/", "/solr/p3coll", "/banana/img/", "/banana/logstash_logs", "/p3/classic/"));

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestUri = httpRequest.getRequestURI();
	    String contextPath = httpRequest.getContextPath();
	        
	         
	    if (requestUri != null && !this.verifyRequestURI(request, requestUri)) {

			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
			String queryString = ((HttpServletRequest) request).getQueryString();
			HttpSession session = httpRequest.getSession();
             String dashboardLocation = EbuyOpenHelper.getDashboardLocation();
			String referer = httpRequest.getHeader("referer");
			logger.debug("AccessCheck: referer  == " + referer);
			System.out.println("AccessCheck: referer  == " + referer);
			//System.out.println("AccessCheck: referer  == " + referer);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			logger.debug("AccessCheck: Date and Time  == " + dateFormat.format(date)); // 2014/08/06 15:59:48
			System.out.println("AccessCheck: Date and Time  == " + dateFormat.format(date)); // 2014/08/06 15:59:48
			logger.debug("AccessCheck: requestUri  == " + requestUri);
			System.out.println("AccessCheck: requestUri  == " + requestUri);
            logger.debug("AccessCheck: Session   == " + session);
            System.out.println("AccessCheck: Session   == " + session);
            logger.debug("AccessCheck: session.getAttribute(CHECK_TOKEN) == " + session.getAttribute("P3Checked"));
            System.out.println("AccessCheck: session.getAttribute(CHECK_TOKEN) == " + session.getAttribute("P3Checked"));
            logger.debug("httpRequest.getContextPath() == " + httpRequest.getContextPath());
            System.out.println("httpRequest.getContextPath() == " + httpRequest.getContextPath());
		/*	System.out.println("AccessCheck: requestUri  == " + requestUri);
			System.out.println("AccessCheck: Session   == " + session);
			System.out.println("AccessCheck: session.getAttribute(CHECK_TOKEN) == " + session.getAttribute(CHECK_TOKEN));
			System.out.println("httpRequest.getContextPath() == " + httpRequest.getContextPath());*/
			String dataSource = (String) session.getAttribute("datasource");
			logger.debug("AccessCheck: DataSource " + dataSource);
			System.out.println("AccessCheck: DataSource " + dataSource);
			//System.out.println("AccessCheck: DataSource " + dataSource);
			String datasource1 =  getValidDataSource(httpRequest.getParameter("datasource"));
			//System.out.println("AccessCheck: Before Session Datasource " + datasource1);
            logger.debug("AccessCheck: Before Session Datasource " + datasource1);
            System.out.println("AccessCheck: Before Session Datasource " + datasource1);
            
			if ( datasource1 == null || datasource1.length() ==  0 ) {
				if ( dataSource != null ) {
					datasource1 = dataSource;
				}
			}

			String host = httpRequest.getHeader("Host");
			String hostBsp = httpRequest.getHeader("x-forwarded-host");

			String redirectUrl = "https://" + host;
			String redirectUrlBsp = "https://" + hostBsp;
			
			logger.debug("AccessCheck: host " + host);
            logger.debug("AccessCheck: redirectUrl " + redirectUrl);
            
			System.out.println("AccessCheck: host " + host);
			System.out.println("AccessCheck: redirectUrl " + redirectUrl);

			//Checking to see whether the user is already authenticated through OMB and access is denied
            if ( "YES".equals( session.getAttribute(ACCESS_DENIED) ) && requestUri.contains(contextPath + "/accessblocked.jsp") ) {
				session.removeAttribute(ACCESS_DENIED);
			} else if (session.getAttribute(CHECK_TOKEN) == null && (!requestUri.contains(contextPath + "/banana/"))) {

				System.out.println("AccessCheck: Crossed the first check");
				// Referer will be null in 2 cases
				// 1. When the login page is called.
				// 2. When index.html#/dashboard is called directly.
				//if ( ((!requestUri.equals("/gui/")))) {
				if ( ((!requestUri.equals(contextPath + "/")))) {
					// For main page just create a session
					// && (!requestUri.equals("/gui/banana/index.html") //If the
					// user comes without a session calling dashboard he should
					// be denied access
					if (!requestUri.contains(contextPath + "/ReturnLoginViaMax") && !requestUri.equals(contextPath + "/solutionfinder") && !requestUri.contains(contextPath + "/#/dashboard?query=") && !requestUri.contains(contextPath + "/?ticket=") ){
						System.out.println("AccessCheck: Access will be blocked");
						// FIXME DEBUG
						System.out.println("AccessCheck: call doesn't contain returnlogin or solutions finder or queryString");
						if (redirectUrlBsp != null && redirectUrlBsp.indexOf(".gsa.gov") > -1) {
							String whitelistDomain = getWhiteListHost(redirectUrlBsp);
							httpResponse.sendRedirect(whitelistDomain + "/");
						}else if (redirectUrl != null && redirectUrl.indexOf(".gsa.gov") > -1) {
	                        String whitelistDomain = getWhiteListHost(redirectUrl);
	                        httpResponse.sendRedirect(whitelistDomain + "/");
	                    }
						return;
					}

				}

				System.out.println("AccessCheck: Crossed the second check");

			} else if (session.getAttribute(CHECK_TOKEN) == null ) {
				// FIXME DEBUG
				//System.out.println("AccessCheck: Blocked out!");
				if (requestUri.startsWith(contextPath + "/banana/index.html") || requestUri.startsWith(contextPath + "/p3-508.jsp")) {
                    EbuyOpenHelper.setLastRequestURI(httpRequest, contextPath + "/banana/index.html#/dashboard/file/p3-508.json");
                }
                String datasourceTemp = getValidDataSource(httpRequest.getParameter("datasource"));
                if (datasourceTemp != null && datasourceTemp.length() > 0) {
                    session.setAttribute("datasource", (Object)datasourceTemp);
                }
                else {
                    session.removeAttribute("datasource");
                }
                System.out.println("AccessCheck: Blocked out!");
                if (redirectUrlBsp != null && redirectUrlBsp.contains(".gsa.gov")) {
                    final String whitelistDomain2 = getWhiteListHost(redirectUrlBsp);
                    httpResponse.sendRedirect(whitelistDomain2 + "/");
                }
                else if (redirectUrl != null && redirectUrl.contains(".gsa.gov")) {
                    final String whitelistDomain2 = getWhiteListHost(redirectUrl);
                    httpResponse.sendRedirect(whitelistDomain2 + "/");
                }
					return;
			}

			Date date1 = new Date(session.getLastAccessedTime());
			formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
			
			logger.debug("AccessCheck: Session_1 Date and Time                     == " + dateFormat.format(date));
            logger.debug("AccessCheck: Session_2 Session Last_Accessed_time = " + formatter.format(date1));
            logger.debug("AccessCheck: Session_3 Session Get_Max_inactive_interval = " + session.getMaxInactiveInterval());
            
			System.out.println("AccessCheck: Session_1 Date and Time                     == " + dateFormat.format(date)); // 2014/08/06 15:59:48
			System.out.println("AccessCheck: Session_2 Session Last_Accessed_time = " + formatter.format(date1));

			System.out.println("AccessCheck: Session_3 Session Get_Max_inactive_interval = " + session.getMaxInactiveInterval());
		}
		chain.doFilter(request, response);
	}

	private boolean verifyRequestURI(ServletRequest request, String uri) {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		for (String requestURIPath: excludedRequestList) {

			if (uri.startsWith(httpRequest.getContextPath() + requestURIPath)) {
				return true;
			}
		}

		return false;

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	public static String getWhiteListHost(final String domain) {
        return ValidHosts.getWhiteListHost(domain);
    }
    
    public static String getValidDataSource(final String datasource) {
        final ValidDatasources datasources = new ValidDatasources();
        return datasources.getDatasource(datasource);
    }
}
