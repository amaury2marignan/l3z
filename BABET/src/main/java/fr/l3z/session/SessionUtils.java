package fr.l3z.session;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

	private static final String USER_ID = "userId";
	private static final String FAMILY_ID = "familyId";
	private static final String ADMIN_CONNECT = "adminConnect";

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static Long getFamilyId() {
		HttpSession session = getSession();
		if (session != null)
			return (Long) session.getAttribute(FAMILY_ID);
		else
			return null;
	}

	public static void setFamilyId(Long familyId) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute(FAMILY_ID, familyId);
	}
	
	public static Long getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (Long) session.getAttribute(USER_ID);
		else
			return null;
	}
	
	public static void setUserId(Long userId) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute(USER_ID, userId);
	}
	
	public static Long getAdminConnect() {
		HttpSession session = getSession();
		if (session != null)
			return (Long) session.getAttribute(ADMIN_CONNECT );
		else
			return null;
	}

	public static void setAdminConnect(Long adminConnect) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute(ADMIN_CONNECT, adminConnect);
	}

	public static boolean isFamilyLogged() {
		return getFamilyId() != null;
	}
	
	public static boolean isAdminLogged() {
		if((getAdminConnect()==0)|(getAdminConnect()==null)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isUserLogged() {
		return getUserId()!=null;
	}
	
	public static void logout() {
		getSession().invalidate();
	}
}
