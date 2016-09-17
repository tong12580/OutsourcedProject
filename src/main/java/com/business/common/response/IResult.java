package com.business.common.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IResult extends Serializable {
    Object getResult();

    void setResult(Object paramObject);

    String getMessage();

    void setCode(String paramString);

    void setReturnCode(String paramString);

    void setMessage(String paramString);

    void copy(IResult paramIResult);

    void setLengths(int paramInt);

    int getLengths();

    boolean isSuccessful();

    void failure(String paramString);

    void successful(String paramString);

    int getResType();

    void setResType(int paramInt);

    void transfer(Map<?, ?> paramMap);

    List<?> getResult(int paramInt);

//  public abstract StringBuffer getListJson();

    StringBuffer toJson();

    String getCode();

    String getReturnCode();
}