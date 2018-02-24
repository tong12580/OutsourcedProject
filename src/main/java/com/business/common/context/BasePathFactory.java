package com.business.common.context;

import com.business.listener.ContextParamDictionary;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 路径工厂类
 * BasePathFactory
 *
 * @since 2011-6-2 下午04:39:46
 */
public class BasePathFactory {

    /**
     * @description 获取网站路径
     */
    public static String getBasePath(HttpServletRequest request) {
        return getWebRootPath(request);
    }

    /**
     * 获取项目根路径
     */
    public static String getProjectRootPath() {
        return System.getProperty(ContextParamDictionary.PROJECT_PATH.getParamValue());
    }

    /**
     * 获取URL请求路径
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getWebRootPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * basePath路径
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getServerPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取资源文件路径
     * getResourcePath
     *
     * @param resourceName String
     * @return String
     */
    public static String getResourcePath(String resourceName) {
        return Objects.requireNonNull(BasePathFactory.class.getClassLoader().getResource(resourceName)).getPath();
    }
}
