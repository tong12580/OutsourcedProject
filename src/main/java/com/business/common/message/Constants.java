package com.business.common.message;

/**
 * @author yutong
 * @Description:常量接口
 * @date 2016-3-8 下午06:36:58
 */
public interface Constants {

    /**
     * 站点入口
     */
    String BASE_PATH = "https://xxx.xx.com/";

    /**
     * session key 常量定义
     */
    String SESSION_KEY_USER = "sessionUser"; // 用户session

    /**
     * 图片保存路径
     */
    String FILE_BASE_PATH = "file_base_path";
    /**
     * 图片保存路径
     */
    String STATIC_BASE_PATH = "static_base_path";

    /**
     * 文件映射基路径名称
     */
    String UPLOAD_BASE_FOLDER = "upload";

    /**
     * 用户缓存信息key
     */
    String USER_KEY = "user_key";

    /**
     * 注册手机验证码key
     */
    String CODE_COUNT_NUM = "CODE_COUNT_NUM_";

    /**
     * 固定图片压缩尺寸常量
     */
    int SIZE_HEAD_IMG_W = 200;
    int SIZE_HEAD_IMG_H = 150;

    String MOBILE = "mobile";
}
