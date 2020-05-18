package p3;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CasRedirectServlet extends HttpServlet{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(CasRedirectServlet.class);
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        String queryString = req.getParameter("query");
        logger.debug("CasRedirect: queryString =  " + queryString);
        //System.out.println("CasRedirect: queryString =  " + queryString);
        logger.debug("CasRedirect: URL =  " + req.getRemoteHost());
        //System.out.println("CasRedirect: URL =  " + req.getRemoteHost());
        String requestUri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession();

        logger.debug("CasRedirect: Context path = " + httpRequest.getContextPath());
        //System.out.println("CasRedirect: Context path = " + httpRequest.getContextPath());
        logger.debug("CasRedirect: referer  == " + httpRequest.getHeader("referer"));
        //System.out.println("CasRedirect: referer  == " + httpRequest.getHeader("referer"));
        logger.debug("CasRedirect: requestUri == " + requestUri);
        //System.out.println("CasRedirect: requestUri == " + requestUri);
        String ticket = req.getParameter("ticket");
        logger.debug("CasRedirect: p3-ticket = " + ticket);
        //System.out.println("CasRedirect: p3-ticket = " + ticket);

        String host = httpRequest.getHeader("Host");
        String hostBsp = httpRequest.getHeader("x-forwarded-host");

        String redirectUrl = "https://" + host;
        String redirectUrlBsp = "https://" + hostBsp;
        logger.debug("CasRedirect: Host = " + host);
        //System.out.println("CasRedirect: Host = " + host);

        String contextPath = req.getContextPath();
        String casServerOMBMaxUrl = EbuyOpenHelper.getCasServerUrl();
        if (EbuyOpenHelper.isProdHost(req)) {
        	casServerOMBMaxUrl = EbuyOpenHelper.getCasServerProdUrl();
        }
        String redirectOMBMaxLink = redirectUrlBsp + contextPath + EbuyOpenHelper.getOmbmaxredirectlink();
        String cas_validate = casServerOMBMaxUrl + "serviceValidate?ticket=" + ticket + "&service=" + redirectOMBMaxLink;
        
        if (queryString != null) {
            cas_validate = casServerOMBMaxUrl + "serviceValidate?ticket=" + ticket + "&service=" + redirectOMBMaxLink + "?query=" + queryString;
        }
        logger.debug("CasRedirect: cas_validate string = " + cas_validate);
        System.out.println("CasRedirect: cas_validate string = " + cas_validate);
        Client client = Client.create();
        WebResource webResource = null;
        try {
            webResource = client.resource(cas_validate);
        }
        catch (IllegalArgumentException iae) {
            logger.error(iae.getMessage());
            System.out.println(iae.getMessage());
            iae.printStackTrace();
            res.sendError(400, "Illegal arguments passed to CAS Service");
            return;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            res.sendError(400, "Call to CAS Service failed");
            return;
        }
       
      /*  if (host != null && host.contains("acqit.helix.gsa.gov")) {
            logger.debug("CasRedirect: inside cas_val Host = " + host);
            cas_validate = "https://login.test.max.gov/cas/serviceValidate?ticket=" + ticket + "&service=" + EbuyOpenHelper.getOmbmaxredirectlink();
            // FIXME might be a weird null check
            if (queryString != null) {
                cas_validate = "https://login.test.max.gov/cas/serviceValidate?ticket=" + ticket + "&service=" + EbuyOpenHelper.getOmbmaxredirectlink() + "?query=" + queryString;
            }
        } else {
            cas_validate = "https://login.max.gov/cas/serviceValidate?ticket=" + ticket + "&service=" + EbuyOpenHelper.getOmbmaxredirectlink();
            // FIXME might be a weird null check
            if (queryString != null) {
                cas_validate = "https://login.max.gov/cas/serviceValidate?ticket=" + ticket + "&service=" + EbuyOpenHelper.getOmbmaxredirectlink() + "?query=" + queryString;
            }
        }
        logger.debug("CasRedirect: cas_validate string = " + cas_validate);
        Client client = Client.create();

        WebResource webResource = client.resource(cas_validate);*/
        ClientResponse response = webResource.get(ClientResponse.class);
        logger.debug("CasRedirect: Status " + response.getStatus());
        System.out.println("CasRedirect: Status " + response.getStatus());
        String casresponse = response.getEntity(String.class);
        String User = parse_tag(casresponse, "cas:user");
        String saml = parse_tag(casresponse, "maxAttribute:samlAuthenticationStatementAuthMethod");
        String loa = parse_tag(casresponse, "maxAttribute:EAuth-LOA");
        String group = parse_tag(casresponse, "maxAttribute:GroupList");
        String user_classification = parse_tag(casresponse, "maxAttribute:User-Classification");
        String emailAddress = parse_tag(casresponse, "maxAttribute:Email-Address");
        String dashboardLocation = EbuyOpenHelper.getDashboardLocation();
        
        logger.debug("CasRedirect: User = " + User);
        logger.debug("CasRedirect: Saml = " + saml);
        logger.debug("CasRedirect: loa = " + loa);
        logger.debug("CasRedirect: group = " + group);
        logger.debug("CasRedirect: User_classification = " + user_classification);
        logger.info("CasRedirect: Email Address = " + emailAddress);
        logger.debug("CasRedirect: Completed");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        logger.debug("CasRedirect: Date and Time  == " + dateFormat.format(date));

	String datasource = (String) session.getAttribute("datasource");
	logger.debug("CasRedirect: datasource = " + datasource );
		
	String datasourceQueryString = "";
		
	if ( datasource != null && datasource.length() > 0 ) {
		ValidDatasources datasources = new ValidDatasources();
        datasourceQueryString = "?datasource=" + datasources.getDatasource(datasource);
		session.removeAttribute("datasource");
	}
	
	String lastRequestURI = EbuyOpenHelper.getLastRequestURI(req);
    List webinspectEmailAddresses = new ArrayList();
    webinspectEmailAddresses.add("alexander.pearre@gsa.gov");
    webinspectEmailAddresses.add("ramamohana.mannam@gsa.gov");
    webinspectEmailAddresses.add("bradford.may@gsa.gov");
    webinspectEmailAddresses.add("brian.santiago@gsa.gov");
    logger.info("CasRedirect : Webinspect Email " + webinspectEmailAddresses.contains(emailAddress));
    

        Boolean pivUser = saml.contains("urn:max:fips-201-pivcard")
                && user_classification.contains("FEDERAL");

	Boolean mfaUser = saml.contains("urn:max:am:secureplus:MobileTwoFactorUnregistered:assurancelevel3")
				&& user_classification.contains("FEDERAL");
	
	Boolean federatedUser = saml.contains("urn:max:am:secureplus:federated-saml2:assurancelevel3") 
				&& user_classification.contains("FEDERAL");

	logger.debug( "CasRedirect: Multifactor Authentication = " + mfaUser );

	Boolean contractor = user_classification.contains("CONTRACTOR");

	Boolean twoFactorAuth = saml.contains("urn:max:am:secureplus:MobileTwoFactorUnregistered:assurancelevel3");
    Boolean federatedAuth = saml.contains("urn:max:am:secureplus:federated-saml2:assurancelevel3");
    Boolean pivAuth = saml.contains("urn:max:fips-201-pivcard");
    Boolean validAuthenticationLevels = twoFactorAuth || federatedAuth || pivAuth;
    Boolean p3Admin = group.contains("AGY-GSA-FAS.PRICESPAID.ADMIN") && validAuthenticationLevels;
    Boolean p3DevUser = group.contains("AGY-GSA-FAS.PRICESPAID-P3.AUTHORIZED.USERS.DEV") && (validAuthenticationLevels || webinspectEmailAddresses.contains(emailAddress));
    Boolean p3TestUser = group.contains("AGY-GSA-FAS.PRICESPAID-P3.AUTHORIZED.USERS.TEST") && (validAuthenticationLevels || webinspectEmailAddresses.contains(emailAddress));
    Boolean p3StagingUser = group.contains("AGY-GSA-FAS.PRICESPAID-P3.AUTHORIZED.USERS.STAGING") && (validAuthenticationLevels || webinspectEmailAddresses.contains(emailAddress));
    Boolean p3ProdUser = group.contains("AGY-GSA-FAS.PRICESPAID-AGY-GSA-FAS.PRICESPAID-P3.AUTHORIZED.USERS.PROD") && validAuthenticationLevels;
    if (host != null && host.contains(".gsa.gov")) {
        if (redirectUrlBsp.contains("p3.cap.gsa.gov")) {
            final String validHost = "https://p3.cap.gsa.gov";
            if (pivUser || mfaUser || federatedUser) {
                session.setAttribute("P3Checked", (Object)"P3Checked");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Federal User");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Valid User via PIV/CAC");
                if (lastRequestURI != null) {
                    EbuyOpenHelper.removeLastRequestURI(req);
                    res.sendRedirect(validHost + lastRequestURI);
                    return;
                }
                if (datasourceQueryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + datasourceQueryString + dashboardLocation);
                    return;
                }
                if (queryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation + "?query=" + StringUtils.replaceAll(queryString, "", ""));
                    return;
                }
                res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
                return;
            }
            else if (contractor && (p3Admin || p3ProdUser)) {
                session.setAttribute("P3Checked", (Object)"P3Checked");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Contractor who is in p3Admin or p3ProdUser group");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Query string detected with p3Admin or P3ProdUser");
                if (lastRequestURI != null) {
                    EbuyOpenHelper.removeLastRequestURI(req);
                    res.sendRedirect(validHost + lastRequestURI);
                    return;
                }
                if (datasourceQueryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + datasourceQueryString + dashboardLocation);
                    return;
                }
                if (queryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation + "?query=" + StringUtils.replaceAll(queryString, "", ""));
                    return;
                }
                res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
                return;
            }
        }
        else if (redirectUrlBsp.contains("p3-staging.cap.gsa.gov") || redirectUrlBsp.contains("p3.staging-acqit.helix.gsa.gov")) {
            if (p3StagingUser) {
                session.setAttribute("P3Checked", (Object)"P3Checked");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Contractor who is in p3StagingUser group");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Query string detected with p3StagingUser");
                final String validHost = "https://p3-staging.cap.gsa.gov";
                if (lastRequestURI != null) {
                    EbuyOpenHelper.removeLastRequestURI(req);
                    res.sendRedirect(validHost + lastRequestURI);
                    return;
                }
                if (datasourceQueryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + datasourceQueryString + dashboardLocation);
                    return;
                }
                if (queryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation + "?query=" + StringUtils.replaceAll(queryString, "", ""));
                    return;
                }
                res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
                return;
            }
        }
        else if (redirectUrlBsp.contains("p3-test.cap.gsa.gov")) {
            if (p3TestUser) {
                session.setAttribute("P3Checked", (Object)"P3Checked");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Contractor who is in p3TestUser group");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Query string detected with p3TestUser");
                final String validHost = "https://p3-test.cap.gsa.gov";
                if (lastRequestURI != null) {
                    EbuyOpenHelper.removeLastRequestURI(req);
                    res.sendRedirect(validHost + lastRequestURI);
                    return;
                }
                if (datasourceQueryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + datasourceQueryString + dashboardLocation);
                    return;
                }
                if (queryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation + "?query=" + StringUtils.replaceAll(queryString, "", ""));
                    return;
                }
                res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
                return;
            }
        }
        else if (redirectUrlBsp.contains("p3-dev.cap.gsa.gov")) {
            if (p3DevUser) {
                session.setAttribute("P3Checked", (Object)"P3Checked");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Contractor who is in p3DevUser group");
                CasRedirectServlet.logger.debug((Object)"CasRedirect: Query string detected with p3DevUser");
                final String validHost = "https://p3-dev.cap.gsa.gov";
                if (lastRequestURI != null) {
                    EbuyOpenHelper.removeLastRequestURI(req);
                    res.sendRedirect(validHost + lastRequestURI);
                    return;
                }
                if (datasourceQueryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + datasourceQueryString + dashboardLocation);
                    return;
                }
                if (queryString != null) {
                    res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation + "?query=" + StringUtils.replaceAll(queryString, "", ""));
                    return;
                }
                res.sendRedirect(validHost + contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
                return;
            }
        }
        else if (redirectUrlBsp.startsWith("https://p3") && redirectUrlBsp.contains("-acqit.helix.gsa.gov") && p3StagingUser) {
            session.setAttribute("P3Checked", (Object)"P3Checked");
            CasRedirectServlet.logger.debug((Object)"CasRedirect: Contractor who is in p3StagingUser group");
            CasRedirectServlet.logger.debug((Object)"CasRedirect: Query string detected with p3StagingUser");
            if (lastRequestURI != null) {
                EbuyOpenHelper.removeLastRequestURI(req);
                res.sendRedirect(lastRequestURI);
                return;
            }
            if (datasourceQueryString != null) {
                res.sendRedirect(contextPath + "/banana/dashboard-include.jsp" + datasourceQueryString + dashboardLocation);
                return;
            }
            if (queryString != null) {
                res.sendRedirect(contextPath + "/banana/dashboard-include.jsp" + dashboardLocation + "?query=" + StringUtils.replaceAll(queryString, "", ""));
                return;
            }
            res.sendRedirect(contextPath + "/banana/dashboard-include.jsp" + dashboardLocation);
            return;
        }
    }
    CasRedirectServlet.logger.debug((Object)"CasRedirect: Blocked out!");
    final String redirectHost = EbuyOpenHelper.getRedirectHost(req);
    session.setAttribute("P3AccessDenied", (Object)"YES");
    session.removeAttribute("P3Checked");
    res.sendRedirect(redirectHost + contextPath + "/accessblocked.jsp");
    req.setAttribute("validation", (Object)"NOT-VALIDATED");
    req.setAttribute("msg", (Object)"Access Denied: You must be a Federal employee and use a PIV/CAC Card to access this application. Please go to OMB MAX ,logout, and then login using your PIV/CAC card.");
    CasRedirectServlet.logger.debug((Object)("Invalidated" + session.getAttribute("P3Checked")));

    }
    /*
      Used for parsing xml.  Search str for first occurance of
      <tag>.....</tag> and return text (striped of leading and
      trailing whitespace) between tags.  Return "" if tag not
      found.
    */
    public static String parse_tag(String str,String tag){
        int tag1_pos1 = str.indexOf("<"+tag);

        if (tag1_pos1 == -1){
            return "";
        }

        int tag1_pos2 = str.indexOf(">",tag1_pos1);


        if (tag1_pos2 == -1){
            return "";
        }

        int tag2_pos1 = str.indexOf("</"+tag,tag1_pos2);

        if (tag2_pos1 == -1){
            return "";
        }


        return str.substring(tag1_pos2+1, tag2_pos1);
    }
}
