package p3;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AccessCheckFilter
  implements Filter
{
  private static final Log logger = LogFactory.getLog(AccessCheckFilter.class);
  public static final String CHECK_TOKEN = "P3Checked";
  public static final String ACCESS_DENIED = "P3AccessDenied";
  final List<String> excludedRequestList = Collections.unmodifiableList(Arrays.asList(new String[] { "/bootstrap/js/", "/p3/js/", "/p3/art/", "/p3/css/", "/banana/css/", "/banana/vendor/", "/p3/fonts/", "/banana/help.js", "/p3/app/components/", "/banana/app/app.js", "/banana/app/directives/", "/banana/app/controllers/", "/banana/app/services/", "/banana/config.js", "/banana/app/dashboards/", "/banana/app/partials/", "/banana/app/components/", "/banana/app/panels/", "/solr/p3coll", "/banana/img/", "/banana/logstash_logs", "/p3/classic/" }));
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    HttpServletResponse httpResponse = (HttpServletResponse)response;
    String requestUri = httpRequest.getRequestURI();
    
    String contextPath = httpRequest.getContextPath();
    if ((requestUri != null) && (!verifyRequestURI(request, requestUri)))
    {
      DateFormat formatter = DateFormat.getDateTimeInstance(2, 2);
      String queryString = ((HttpServletRequest)request).getQueryString();
      HttpSession session = httpRequest.getSession();
      String dashboardLocation = EbuyOpenHelper.getDashboardLocation();
      
      String referer = httpRequest.getHeader("referer");
      logger.debug("AccessCheck: referer  == " + referer);
      
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date = new Date();
      logger.debug("AccessCheck: Date and Time  == " + dateFormat.format(date));
      
      logger.debug("AccessCheck: requestUri  == " + requestUri);
      logger.debug("AccessCheck: Session   == " + session);
      logger.debug("AccessCheck: session.getAttribute(CHECK_TOKEN) == " + session.getAttribute("P3Checked"));
      logger.debug("httpRequest.getContextPath() == " + httpRequest.getContextPath());
      
      String dataSource = (String)session.getAttribute("datasource");
      logger.debug("AccessCheck: DataSource " + dataSource);
      
      String datasource1 = getValidDataSource(httpRequest.getParameter("datasource"));
      logger.debug("AccessCheck: Before Session Datasource " + datasource1);
      if (((datasource1 == null) || (datasource1.length() == 0)) && 
        (dataSource != null)) {
        datasource1 = dataSource;
      }
      String host = httpRequest.getHeader("Host");
      String hostBsp = httpRequest.getHeader("x-forwarded-host");
      
      String redirectUrl = "https://" + host;
      String redirectUrlBsp = "https://" + hostBsp;
      
      logger.debug("AccessCheck: host " + host);
      logger.debug("AccessCheck: redirectUrl " + redirectUrl);
      if (("YES".equals(session.getAttribute("P3AccessDenied"))) && (requestUri.contains(contextPath + "/accessblocked.jsp")))
      {
        session.removeAttribute("P3AccessDenied");
      }
      else if ((session.getAttribute("P3Checked") == null) && (!requestUri.contains(contextPath + "/banana/")))
      {
        System.out.println("AccessCheck: Crossed the first check");
        if (!requestUri.equals(contextPath + "/")) {
          if ((!requestUri.contains(contextPath + "/ReturnLoginViaMax")) && (!requestUri.equals(contextPath + "/solutionfinder")) && (!requestUri.contains(contextPath + "/#/dashboard?query=")) && (!requestUri.contains(contextPath + "/?ticket=")))
          {
            System.out.println("AccessCheck: Access will be blocked");
            
            System.out.println("AccessCheck: call doesn't contain returnlogin or solutions finder or queryString");
            if ((redirectUrlBsp != null) && (redirectUrlBsp.indexOf(".gsa.gov") > -1))
            {
              String whitelistDomain = getWhiteListHost(redirectUrlBsp);
              httpResponse.sendRedirect(whitelistDomain + "/");
            }
            else if ((redirectUrl != null) && (redirectUrl.indexOf(".gsa.gov") > -1))
            {
              String whitelistDomain = getWhiteListHost(redirectUrl);
              httpResponse.sendRedirect(whitelistDomain + "/");
            }
            return;
          }
        }
        System.out.println("AccessCheck: Crossed the second check");
      }
      else if (session.getAttribute("P3Checked") == null)
      {
        if ((requestUri.startsWith(contextPath + "/banana/index.html")) || (requestUri.startsWith(contextPath + "/p3-508.jsp"))) {
          EbuyOpenHelper.setLastRequestURI(httpRequest, contextPath + "/banana/index.html#/dashboard/file/p3-508.json");
        }
        String datasourceTemp = getValidDataSource(httpRequest.getParameter("datasource"));
        if ((datasourceTemp != null) && (datasourceTemp.length() > 0)) {
          session.setAttribute("datasource", datasourceTemp);
        } else {
          session.removeAttribute("datasource");
        }
        System.out.println("AccessCheck: Blocked out!");
        if ((redirectUrlBsp != null) && (redirectUrlBsp.contains(".gsa.gov")))
        {
          String whitelistDomain = getWhiteListHost(redirectUrlBsp);
          httpResponse.sendRedirect(whitelistDomain + "/");
        }
        else if ((redirectUrl != null) && (redirectUrl.contains(".gsa.gov")))
        {
          String whitelistDomain = getWhiteListHost(redirectUrl);
          httpResponse.sendRedirect(whitelistDomain + "/");
        }
        return;
      }
      Date date1 = new Date(session.getLastAccessedTime());
      formatter = DateFormat.getDateTimeInstance(2, 2);
      logger.debug("AccessCheck: Session_1 Date and Time                     == " + dateFormat.format(date));
      logger.debug("AccessCheck: Session_2 Session Last_Accessed_time = " + formatter.format(date1));
      
      logger.debug("AccessCheck: Session_3 Session Get_Max_inactive_interval = " + session.getMaxInactiveInterval());
    }
    chain.doFilter(request, response);
  }
  
  private boolean verifyRequestURI(ServletRequest request, String uri)
  {
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    for (String requestURIPath : this.excludedRequestList) {
      if (uri.startsWith(httpRequest.getContextPath() + requestURIPath)) {
        return true;
      }
    }
    return false;
  }
  
  public void destroy() {}
  
  public void init(FilterConfig arg0)
    throws ServletException
  {}
  
  public static String getWhiteListHost(String domain)
  {
    return ValidHosts.getWhiteListHost(domain);
  }
  
  public static String getValidDataSource(String datasource)
  {
    ValidDatasources datasources = new ValidDatasources();
    return datasources.getDatasource(datasource);
  }
}
