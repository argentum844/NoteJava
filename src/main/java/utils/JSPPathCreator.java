package utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JSPPathCreator {
    private final static String JSP_DEFAULT_FORMAT = "/WEB-INF/jsp/%s.jsp";
    private final static String JSP_USER_FORMAT = "/WEB-INF/jsp/user/%s.jsp";
    private final static String JSP_ADMIN_FORMAT = "/WEB-INF/jsp/admin/%s.jsp";

    public static String getDefaultPath(String jsp) {
        return String.format(JSP_DEFAULT_FORMAT, jsp);
    }

    public static String getUserPath(String jsp) {
        return String.format(JSP_USER_FORMAT, jsp);
    }

    public static String getAdminPath(String jsp) {
        return String.format(JSP_ADMIN_FORMAT, jsp);
    }
}