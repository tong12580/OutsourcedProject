package com.business.common.context;

import com.business.listener.ContextParamDictionary;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guosheng.zhu
 * @ClassName: BasePathFactory
 * @Description: 路径工厂类
 * @date 2011-6-2 下午04:39:46
 */
public class BasePathFactory {

    /**
     * 获取根路径
     *
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        return getWebRootPath(request);
    }

    /**
     * @Description: ProjectRootPath
     * @return
     */
    public static String getProjectRootPath() {
        return System.getProperty(ContextParamDictionary.PROJECTPATH.getParamValue());
    }

    /**
     * @param @return
     * @return String
     * @Title: getBaseFilePath
     * @Description: TODO
     * @author xiao.he
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
     * @param @return
     * @return String
     * @Title: getClassPath
     * @Description: 获得classpath(........../WebRoot/WEB-INF/classes/)
     */
    public static String getClassPath() {
        return BasePathFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    /**
     * @param @param  request
     * @param @return
     * @return String
     * @Title: getWebRootPath
     * @Description: 获取URL请求路径
     */
    public static String getWebRootPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath() + "/";
    }

    /**
     * @param @param  resourceName
     * @param @return
     * @return String
     * @Title: getResourcePath
     * @Description: 获取资源文件路径
     */
    public static String getResourcePath(String resourceName) {
//		return BasePathFactory.class.getResource("//" + resourceName).getPath();
        return BasePathFactory.class.getClassLoader().getResource(resourceName).getPath();
    }
}
