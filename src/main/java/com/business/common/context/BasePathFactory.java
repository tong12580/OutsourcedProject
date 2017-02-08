package com.business.common.context;

import com.business.listener.ContextParamDictionary;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: BasePathFactory
 * @Description: 路径工厂类
 * @since 2011-6-2 下午04:39:46
 */
public class BasePathFactory {

    /**
     * @description 获取根路径
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        return getWebRootPath(request);
    }

    public static String getDomainPath(HttpServletRequest request){
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/";
    }

    /**
     * @Description: ProjectRootPath
     * @return
     */
    public static String getProjectRootPath() {
        return System.getProperty(ContextParamDictionary.PROJECT_PATH.getParamValue());
    }

    /**
     * @return String
     * @Title: getBaseFilePath
     * @author yuTong
     * @date 2015-8-17 下午02:27:46
     */
    public static String getBaseFilePath() {
        String path = BasePathFactory.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath();
        if (path.indexOf("WEB-INF") > 0) {
            path = path.substring(0, path.indexOf("WEB-INF/classes"));
        } else {
            return null;
        }
        return path;
    }

    /**
     * @return String
     * @Title: getClassPath
     * @Description: 获得classpath(........../WebRoot/WEB-INF/classes/)
     */
    public static String getClassPath() {
        return BasePathFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    /**
     * @param request
     * @return String
     * @Title: getWebRootPath
     * @Description: 获取URL请求路径
     */
    public static String getWebRootPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath() + "/";
    }

    /**
     * basePath路径
     *
     * @param request
     * @return
     */
    public static String getServerPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * @param resourceName
     * @return String
     * @Title: getResourcePath
     * @Description: 获取资源文件路径
     */
    public static String getResourcePath(String resourceName) {
//		return BasePathFactory.class.getResource("//" + resourceName).getPath();
        return BasePathFactory.class.getClassLoader().getResource(resourceName).getPath();
    }
}
