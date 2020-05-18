package p3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class InitializeServlet extends HttpServlet {

	private Log logger= LogFactory.getLog(this.getClass());
	public void init() throws ServletException
	{
		super.init();
		EbuyOpenHelper.setLhost(getInitParameter("ldapHost"));
		EbuyOpenHelper.setUtilid(getInitParameter("ldapBindUserId"));
		EbuyOpenHelper.setUtilpwd(getInitParameter("ldapBindPassword"));
		//EbuyOpenHelper.setOmbmaxlink(getInitParameter("ombmaxlink"));
		EbuyOpenHelper.setOmbmaxredirectlink(getInitParameter("ombmaxredirectlink"));
		EbuyOpenHelper.setZipfileuploadlocation(getInitParameter("zipfileuploadlocation"));
		EbuyOpenHelper.setRestservername(getInitParameter("restservername"));
		//EbuyOpenHelper.setRestservername2(getInitParameter("restservername2"));
		EbuyOpenHelper.setFileuploadserviceurl(getInitParameter("fileuploadserviceurl"));
		EbuyOpenHelper.setFileuploadauthurl(getInitParameter("fileuploadauthurl"));
		EbuyOpenHelper.setAuth(getInitParameter("ldapAuth"));
		EbuyOpenHelper.setDashboardLocation(getInitParameter("dashboardLocation"));
		logger.info("EbuyOpenHelper dashboardlocation= " + EbuyOpenHelper.getDashboardLocation());
		System.out.println("EbuyOpenHelper dashboardlocation= " + EbuyOpenHelper.getDashboardLocation());
        EbuyOpenHelper.setBSPEnvironment("true".equalsIgnoreCase(this.getInitParameter("bspEnvironment")));

		System.out.println("EbuyOpenHelper dashboardlocation= " + EbuyOpenHelper.getDashboardLocation());
		//EbuyOpenHelper.setPrimarySOLRServer(getInitParameter("primarySOLRServer"));
		//EbuyOpenHelper.setSecondarySOLRServer(getInitParameter("secondarySOLRServer"));
		EbuyOpenHelper.setSolrCredentials(getInitParameter("solrCredentials"));
		EbuyOpenHelper.setQueryPipelinePath(this.getInitParameter("queryPipelinePath"));
        EbuyOpenHelper.setCasServerUrl(this.getInitParameter("casServerUrl"));
        EbuyOpenHelper.setCasServerProdUrl(this.getInitParameter("casServerProdUrl"));
		logger.info("Parameters used by LdapAuthentication are set");
		System.out.println("Parameters used by LdapAuthentication are set");
	}
}
