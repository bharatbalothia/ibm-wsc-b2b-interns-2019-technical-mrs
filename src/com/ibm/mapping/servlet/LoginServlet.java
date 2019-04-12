package com.ibm.mapping.servlet;

import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ibm.mapping.bean.UserValidate;
import com.ibm.mapping.service.DataService;
import com.ibm.mapping.service.DataServiceImpl;
import com.ibm.mapping.util.BlueGroupLDAPLogin;

/**
 * 
 * @author pradeep
 *
 */
public class LoginServlet extends HttpServlet implements Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginServlet.class);

	/**
	 * Initialize application routine
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String msg = "--- Application init";
		msg += initLog4j(config);
		logger.info(msg);
		logger.info("--- initialization completed");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserValidate userValidate = BlueGroupLDAPLogin.validateUser(
				request.getParameter("UserName"),
				request.getParameter("Password"), GROUP);
		DataService service;

		try {
			service = new DataServiceImpl(jdbcClassName, jdbcUrl,
					jdbcSchemaName, jdbcUserName, jdbcPassword);

			List<String> projectNames = service
					.getProjectNames("SELECT PROJECTNAME FROM QC.PROJECTS WHERE STATUS='A'");

			HttpSession session = request.getSession(false);
			if (session != null)
				session.setAttribute("name", userValidate.getName());
			session.setAttribute("email", request.getParameter("UserName"));
			session.setAttribute("projectNames", projectNames);
			session.setAttribute("groupName", userValidate.getGroupName());
			int[] day = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
					14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
					29, 30, 31 };
			request.setAttribute("Data", day);
			
			if (userValidate.isValidUser()) {
				//RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				
				//rd.forward(request, response);
				response.sendRedirect("/TechMRS/MrsGenerator.jsp");
			}

			else {
				request.setAttribute("error", LOGON_ERROR_VALUE);
				if (userValidate.getErrorCode() == 1) {
					request.setAttribute("error", LDAP_ERROR_VALUE);
				}
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.include(request, response);
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/index.jsp").forward(req,
				resp);

	}

	/**
	 * Initialize log4j framework from configuration file. Log4j configuration
	 * file name and path is in web.xml, servlet parameters.
	 * 
	 * @param config
	 * @throws ServletException
	 */
	private String initLog4j(ServletConfig config) throws ServletException {
		String contextRoot = getServletContext().getRealPath("/");

		String fileOutputRoot = config.getInitParameter(LOG4J_FILE_ROOT);
		if (fileOutputRoot == null || fileOutputRoot.trim().length() == 0
				|| !(new File(fileOutputRoot).isDirectory())) {
			fileOutputRoot = contextRoot + File.separator
					+ LOG4J_DFLT_FILE_ROOT;
		}
		String systemPropertyName = LOG4J_DFLT_SYSTEM_PROPERTY_NAME;
		String realName = config
				.getInitParameter(LOG4J_DFLT_SYSTEM_PROPERTY_NAME);
		if (realName != null && realName.length() > 0) {
			systemPropertyName = realName;
		}

		System.setProperty(systemPropertyName, fileOutputRoot);

		String configFile = getInitParameter(LOG4J_CONFIG);
		String configPath = contextRoot + configFile;

		if (configFile == null || configFile.trim().length() == 0
				|| !(new File(configPath)).isFile()) {
			String msg1 = "ERROR: Cannot read the log4j configuration file. "
					+ "Please check servlet init-param [log4j-properties] in deployment descriptor web.xml";
			throw new ServletException(msg1);
		}

		// Look up another init parameter that tells whether to watch this
		// configuration file for changes.
		String watch = config.getInitParameter(LOG4J_CONFIG_WATCH);

		// Use the props file to load up configuration parameters for log4j
		if (watch != null && watch.equalsIgnoreCase("true")) {
			try {
				PropertyConfigurator.configureAndWatch(configPath);
			} catch (Exception ex) {
				ex.getMessage();
			}
		} else {
			PropertyConfigurator.configure(configPath);
		}
		return "\nLogging framework initialized\n\tLog file root: "
				+ fileOutputRoot;
	}
}