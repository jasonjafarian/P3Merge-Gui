package proxy;

import gov.gsa.bsp.core.utils.BSPJBossUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import p3.EbuyOpenHelper;
import p3.ValidDatasources;
import p3.util.SolrRoundRobinHosts;
import p3.util.SolrRoundRobinHosts.RoundRobinHostDetail;

public class ProxyServlet
  extends HttpServlet
{
  private static final Log logger = LogFactory.getLog(ProxyServlet.class);
  private static final long serialVersionUID = 1L;
  private final String USER_AGENT = "Mozilla/5.0";
  public static String basicAuthCredential;
  public static final String log1 = "Destintation Server for Proxy ";
  private List<String> allowedRequestPatterns = new ArrayList();
  private static SolrRoundRobinHosts solrRoundRobinHost = new SolrRoundRobinHosts("https://" + BSPJBossUtils.getSolrPrimary() + ":8764", "https://" + BSPJBossUtils.getSolrSecondary() + ":8764");
  
  public void init()
    throws ServletException
  {
    super.init();
    
    basicAuthCredential = System.getProperty("SOLR_CREDENTIAL_FOR_PROXY", EbuyOpenHelper.getSolrCredentials());
    

    logger.info("Basic Auth Credential for destination server " + basicAuthCredential);
    

    Enumeration<String> parameterNames = getInitParameterNames();
    while (parameterNames.hasMoreElements())
    {
      String parameterName = (String)parameterNames.nextElement();
      if (parameterName.startsWith("allowedRequestPattern"))
      {
        String value = getInitParameter(parameterName);
        logger.info(parameterName + "=" + value);
        this.allowedRequestPatterns.add(value);
      }
    }
  }
  
  public static void addAuthHeader(HttpURLConnection con)
  {
    if ((basicAuthCredential != null) && (basicAuthCredential.length() > 0))
    {
      String authEncoding = new String(Base64.encodeBase64(basicAuthCredential.getBytes()));
      
      con.setRequestProperty("Authorization", "Basic " + authEncoding);
    }
  }
  
  private boolean doMyGet(String queryStringWithPath, HttpServletResponse response, HttpServletRequest request)
    throws IOException
  {
    String solrRequestURL = null;
    String CHECK_TOKEN = "P3Checked";
    
    String solrBaseUrl = EbuyOpenHelper.getRestservername();
    String solrFailoverUrl = null;
    if (EbuyOpenHelper.isBSPEnvironment())
    {
      SolrRoundRobinHosts.RoundRobinHostDetail roundRobinHostDetail = solrRoundRobinHost.next();
      
      solrBaseUrl = roundRobinHostDetail.getPrimaryHost() + EbuyOpenHelper.getQueryPipelinePath();
      solrFailoverUrl = roundRobinHostDetail.getFailoverHost() + EbuyOpenHelper.getQueryPipelinePath();
      
      logger.info(" Round Robin Host Details " + roundRobinHostDetail);
    }
    String queryStringPath = queryStringWithPath.substring(6) + "&shards.tolerant=true";
    

    HttpSession session = request.getSession();
    String dataSource = (String)session.getAttribute("datasource");
    if (session.getAttribute("P3Checked") == null)
    {
      logger.info("Page is getting redirected -- not logged in ");
      response.sendRedirect(request.getContextPath() + "/");
      return false;
    }
    BufferedReader bufferedReader = null;
    HttpURLConnection con = null;
    
    ValidDatasources validDataSources = new ValidDatasources();
    String validDatasource = validDataSources.getDatasource(dataSource);
    
    response.addHeader("DataSource", validDatasource);
    ServletOutputStream sout = response.getOutputStream();
    try
    {
      if (request.getParameterMap().size() == 0)
      {
        sout.write("There are no parameters in the request".getBytes());
        sout.flush();
        
        return true;
      }
      solrRequestURL = solrBaseUrl + queryStringPath + "&shards.tolerant=true";
      try
      {
        URL obj = new URL(solrRequestURL);
        
        con = (HttpURLConnection)obj.openConnection();
        addAuthHeader(con);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        
        int responseCode = con.getResponseCode();
        

        logger.info(" Response From SOLR/FUSION " + responseCode);
        if (responseCode != 200) {
          throw new IOException("Unexpected response code: " + responseCode);
        }
        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      }
      catch (IOException ue)
      {
        if (solrFailoverUrl != null)
        {
          solrRequestURL = solrFailoverUrl + queryStringPath + "&shards.tolerant=true";
          

          obj = new URL(solrRequestURL);
          
          con = (HttpURLConnection)obj.openConnection();
          addAuthHeader(con);
          con.setRequestMethod("GET");
          con.setRequestProperty("User-Agent", "Mozilla/5.0");
          
          int responseCode = con.getResponseCode();
          

          logger.info(" Response From SOLR/FUSION " + responseCode);
          if (responseCode != 200) {
            throw new IOException("Unexpected response code: " + responseCode);
          }
          bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        else
        {
          throw ue;
        }
      }
      String inputLine;
      while ((inputLine = bufferedReader.readLine()) != null)
      {
        inputLine = StringUtils.replaceAll(inputLine, "(?i)<script[^>]*>(.*?)<\\/script[^>]*>|<javascript[^>]*>(.*?)<\\/javascript[^>]*>", "");
        inputLine = StringUtils.replaceAll(inputLine, "<", "&lt");
        inputLine = StringUtils.replaceAll(inputLine, ">", "&gt");
        sout.write(inputLine.getBytes());
        sout.write(StringUtils.replace("\n", "", "").getBytes());
      }
      sout.flush();
      return 1;
    }
    catch (IOException e)
    {
      logger.warn("Call to solr server is unsuccessful with error: " + e);
      
      response.reset();
      return 0;
    }
    catch (Exception e)
    {
      URL obj;
      logger.warn("Call to solr server is unsuccessful With erro : " + e);
      response.reset();
      return 0;
    }
    finally
    {
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (con != null) {
        con.disconnect();
      }
      if (sout != null) {
        sout.close();
      }
    }
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, MalformedURLException, IOException
  {
    String urlWithQueryString = request.getRequestURI().substring(request.getContextPath().length()) + "?" + request.getQueryString();
    



    logger.debug(" URL with Query String " + urlWithQueryString);
    



    String rowsString = request.getParameter("rows");
    if ((rowsString != null) && (rowsString.trim().length() > 0)) {
      try
      {
        int rowsRequesting = Integer.valueOf(rowsString).intValue();
        if (rowsRequesting > 50000) {
          throw new ServletException("Your request is trying to retreive more than 50,000 rows from the solr server");
        }
      }
      catch (NumberFormatException nfe)
      {
        response.sendError(400, "Invalid rows parameter is passed");
        return;
      }
    }
    String facetParam = request.getParameter("facet");
    if ((facetParam != null) && (facetParam.trim().length() > 0) && (!"true".equalsIgnoreCase(facetParam)) && (!"false".equalsIgnoreCase(facetParam)))
    {
      response.sendError(400, "Invalid facet parameter is passed");
      return;
    }
    try
    {
      if ((request.getQueryString() != null) && (request.getQueryString().length() > 0))
      {
        doMyGet(urlWithQueryString, response, request);
      }
      else
      {
        ServletOutputStream sout = response.getOutputStream();
        try
        {
          sout.write("There are no parameters passed".getBytes());
          sout.flush();
        }
        finally
        {
          if (sout != null) {
            sout.close();
          }
        }
        return;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
