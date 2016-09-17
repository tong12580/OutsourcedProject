package com.business.listener;

import lombok.Getter;

/**
 * Created by yuton on 2016/9/3.
 */
public enum ContextParamDictionary {
    PROJECTPATH("webAppRootKey", "projectRootPath");
    @Getter
    private String paramName;
    @Getter
    private String paramValue;

    ContextParamDictionary(String name, String value) {
        paramName = name;
        paramValue = value;
    }
}
