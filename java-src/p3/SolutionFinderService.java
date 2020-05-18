package p3;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SolutionFinderService extends HttpServlet implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String datasource = req.getParameter("datasource");
		String contextPath = req.getContextPath();
        String dashboardLocation = EbuyOpenHelper.getDashboardLocation();
        String host = req.getHeader("Host");
        String hostBsp = req.getHeader("x-forwarded-host");
        String redirectUrl = "https://" + host;
        String redirectUrlBsp = "https://" + hostBsp;
        String whitelistDomain = ValidHosts.getWhiteListHost(redirectUrlBsp);

		System.out.println("Data Source Name****"+datasource);

		HttpSession session = req.getSession();
		
		Object accessChecked = session.getAttribute( AccessCheckFilter.CHECK_TOKEN );
		String redirectHost = EbuyOpenHelper.getRedirectHost(req);
		if(datasource!=null){
			session.setAttribute("datasource", datasource);
			ValidDatasources datasources = new ValidDatasources();
            String validDatasource = datasources.getDatasource(datasource);
            
			if ( accessChecked != null ) {
				//res.sendRedirect("/gui/banana/dashboard-include.jsp?datasource="+datasource);
				System.out.println("whitelistDomain + contextPath + banana/dashboard-include.jsp?datasource= + validDatasource + dashboardLocation" + whitelistDomain + contextPath + "/banana/dashboard-include.jsp" + "?datasource=" + validDatasource + dashboardLocation);
				res.sendRedirect(whitelistDomain + contextPath + "/banana/dashboard-include.jsp" + "?datasource=" + validDatasource + dashboardLocation);
            }
            else if (redirectHost != null && redirectHost.length() > 0) {
                res.sendRedirect(EbuyOpenHelper.getOMBMaxLoginLinkWithExternalDNS(req));
            } else {
				//res.sendRedirect( EbuyOpenHelper.getOmbmaxlink() );
            	System.out.println("whitelistDomain:" + whitelistDomain + "/");
				res.sendRedirect(whitelistDomain + "/");
			}
		}else{
			if ( accessChecked != null ) {
			    res.sendRedirect(whitelistDomain + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
	        }
	        else if (redirectHost != null && redirectHost.length() > 0) {
	            res.sendRedirect(EbuyOpenHelper.getOMBMaxLoginLinkWithExternalDNS(req));
	        } else {
				//res.sendRedirect( EbuyOpenHelper.getOmbmaxlink() );
				res.sendRedirect(whitelistDomain + "/");
			}
		}
		
	}
	

}
