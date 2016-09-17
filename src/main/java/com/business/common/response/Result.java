package com.business.common.response;

import com.business.common.json.FastjsonUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("rawtypes")
public class Result implements IResult{
	
  private static final long serialVersionUID = 1L;
  protected int iResType = 0;
  protected String sErrorCode = "1";
  protected String sErrorMessage = "OK";
  protected String sForwardCode = null;
  protected String sReturnCode = "";
  protected Object oResult;
  protected int nLengths = -1;

  public Result()
  {
    this.iResType = 0;
  }

  public Result(int iResType) {
    this.iResType = iResType;
  }

  public Result(int iResType, String sErrorCode, String sErrorMessage) {
    this.iResType = iResType;
    this.sErrorCode = sErrorCode;
    this.sErrorMessage = sErrorMessage;
  }

  public Result(int iResType, String sErrorCode, String sErrorMessage, String sForwardCode) {
    this.iResType = iResType;
    this.sErrorCode = sErrorCode;
    this.sErrorMessage = sErrorMessage;
    this.sForwardCode = sForwardCode;
  }

  public Result(int iResType, String sErrorCode, String sErrorMessage, Object oResult) {
    this.iResType = iResType;
    this.sErrorCode = sErrorCode;
    this.sErrorMessage = sErrorMessage;
    this.oResult = oResult;
  }
  public void copy(IResult rs) {
    this.iResType = rs.getResType();
    this.sErrorCode = rs.getCode();
    this.sErrorMessage = rs.getMessage();
    this.oResult = rs.getResult();
    this.nLengths = rs.getLengths();
  }

  public void setLengths(int nLengths) {
	this.nLengths = nLengths;
  }

  public int getLengths() {
	return this.nLengths;
  }  
  public int getResType() {
    return this.iResType;
  }

  public void setResType(int iResType) {
    this.iResType = iResType;
  }
  public Object getResult() {
    return this.oResult;
  }

  public List<?> getResult(int nIndex) {
    if (this.iResType == 4) {
      return (List<?>)this.oResult;
    }

    if (this.iResType == 6) {
      return (List<?>)((List<?>)this.oResult).get(nIndex);
    }
    return null;
  }

  public void setResult(Object oResult) {
    this.oResult = oResult;
  }
  public String getCode() {
    return this.sErrorCode;
  }

  public void setCode(String sErrorCode) {
    this.sErrorCode = sErrorCode;
  }

  public String getMessage() {
    return this.sErrorMessage;
  }

  public void setMessage(String sErrorMessage) {
    this.sErrorMessage = sErrorMessage;
  }

  public void setReturnCode(String sReturnCode) {
    this.sReturnCode = sReturnCode;
  }

  public String getReturnCode() {
    return this.sReturnCode;
  }

  public String getForwardCode() {
    if (this.sForwardCode == null) {
      return this.sErrorCode;
    }

    return this.sForwardCode;
  }

  public void setForwardCode(String sForwardCode) {
    this.sForwardCode = sForwardCode;
  }

  public void failure(String sErrorMessage) {
    this.sErrorCode = "0";
    this.sErrorMessage = sErrorMessage;
  }

  public void successful(String sErrorMessage) {
    this.sErrorCode = "1";
    this.sErrorMessage = sErrorMessage;
  }

  public boolean isSuccessful() {
    return this.sErrorCode.equals("1");
  }

  public void transfer(Map<?, ?> paramMap)
  {
  }
  
  private void readList(StringBuffer sData, List<?> listRows) {
	    FastjsonUtil.beginMap(sData);
	    FastjsonUtil.makePair(sData, "length", listRows.size());

	    if (this.nLengths == -1) {
	      this.nLengths = listRows.size();
	    }
	    if (listRows.size() > 0) {
	      Map<?,?> mapFirstRow = (Map<?,?>)listRows.get(0);
	      Object oRowCount = mapFirstRow.get("row_count");
	      if (oRowCount != null) {
	        this.nLengths = Integer.parseInt(oRowCount.toString());
	      }
	    }
	    FastjsonUtil.makePair(sData, "lengths", this.nLengths, ",");

	    StringBuffer sRecords = new StringBuffer();
	    FastjsonUtil.beginArray(sRecords);

	    boolean bFirstRow = true;
	    Iterator<?> itRows = listRows.iterator();
	    while (itRows.hasNext()) {
	      StringBuffer sRecord = new StringBuffer();
	      FastjsonUtil.beginMap(sRecord);

	      boolean bFirstColumn = true;
	      Map<?,?> oRecord = (Map<?,?>)itRows.next();
	      transfer(oRecord);
	      Iterator<?> itMap = oRecord.entrySet().iterator();
	      while (itMap.hasNext()) {
	        Map.Entry<?,?> element = (Map.Entry<?,?>)itMap.next();
	        String strKey = element.getKey().toString();

	        String strValue = "";
	        Object oValue = element.getValue();
	        if (oValue != null) {
	          strValue = element.getValue().toString();
	        }

	        if (bFirstColumn) {
	          FastjsonUtil.makePair(sRecord, strKey, strValue);
	          bFirstColumn = false;
	        }
	        else {
	          FastjsonUtil.makePair(sRecord, strKey, strValue, ",");
	        }
	      }
	      FastjsonUtil.endMap(sRecord);

	      if (bFirstRow) {
	        FastjsonUtil.makeArray(sRecords, sRecord);
	        bFirstRow = false;
	      }
	      else {
	        FastjsonUtil.makeArray(sRecords, sRecord, ",");
	      }
	    }
	    FastjsonUtil.endArray(sRecords);
	    FastjsonUtil.makePair(sData, "records", sRecords, ",");
	    FastjsonUtil.endMap(sData);
	  }
  
//  public StringBuffer getListJson() {
//	    if (this.iResType == 4) {
//	      List<?> listRows = (List<?>)this.oResult;
//
//	      StringBuffer sRecords = new StringBuffer();
//	      FastjsonUtil.beginArray(sRecords);
//
//	      boolean bFirstRow = true;
//	      Iterator<?> itRows = listRows.iterator();
//	      while (itRows.hasNext()) {
//	        StringBuffer sRecord = new StringBuffer();
//	        FastjsonUtil.beginMap(sRecord);
//
//	        boolean bFirstColumn = true;
//	        Map<?,?> oRecord = (Map<?, ?>)itRows.next();
//	        Iterator<?> itMap = oRecord.entrySet().iterator();
//	        while (itMap.hasNext()) {
//	          Map.Entry<?, ?> element = (Map.Entry<?, ?>)itMap.next();
//	          String strKey = element.getKey().toString();
//
//	          String strValue = "";
//	          Object oValue = element.getValue();
//	          if (oValue != null) {
//	            strValue = element.getValue().toString();
//	          }
//
//	          if (bFirstColumn) {
//	            FastjsonUtil.makePair(sRecord, strKey, strValue);
//	            bFirstColumn = false;
//	          }
//	          else {
//	            FastjsonUtil.makePair(sRecord, strKey, strValue, ",");
//	          }
//	        }
//	        FastjsonUtil.endMap(sRecord);
//
//	        if (bFirstRow) {
//	          FastjsonUtil.makeArray(sRecords, sRecord);
//	          bFirstRow = false;
//	        }
//	        else {
//	          FastjsonUtil.makeArray(sRecords, sRecord, ",");
//	        }
//	      }
//	      FastjsonUtil.endArray(sRecords);
//
//	      return sRecords;
//	    }
//
//	    StringBuffer sRecords = new StringBuffer();
//	    sRecords.append("");
//	    return sRecords;
//	  }
  
  public StringBuffer toJson() {
	    StringBuffer sJson = new StringBuffer();

	    FastjsonUtil.beginMap(sJson);
	    FastjsonUtil.makePair(sJson, "flag", this.sErrorCode);
	    FastjsonUtil.makePair(sJson, "prompt", this.sErrorMessage, ",");
	    FastjsonUtil.makePair(sJson, "code", this.sReturnCode, ",");

	    switch (this.iResType) {
	    case 0:
	      FastjsonUtil.makePair(sJson, "length", 0, ",");
	      break;
	    case 1:
	    case 2:
	      FastjsonUtil.makePair(sJson, "extend", this.oResult.toString(), ",");
	      FastjsonUtil.makePair(sJson, "length", 0, ",");
	      break;
	    case 3:
	      FastjsonUtil.makePair(sJson, "extend", FastjsonUtil.toJson(this.oResult), ",");
	      FastjsonUtil.makePair(sJson, "length", 0, ",");
	      break;
	    case 4:
	      FastjsonUtil.makePair(sJson, "length", 1, ",");

	      StringBuffer sMultiData = new StringBuffer();
	      FastjsonUtil.beginArray(sMultiData);

	      StringBuffer sData = new StringBuffer();
	      List listRows = (List)this.oResult;
	      readList(sData, listRows);

	      FastjsonUtil.makeArray(sMultiData, sData);
	      FastjsonUtil.endArray(sMultiData);

	      FastjsonUtil.makePair(sJson, "data", sMultiData, ",");
	      break;
	    case 6:
	      List listRecords = (List)this.oResult;
	      FastjsonUtil.makePair(sJson, "length", listRecords.size(), ",");

	      StringBuffer sMultiData1 = new StringBuffer();
	      FastjsonUtil.beginArray(sMultiData1);

	      boolean bFirstData = true;
	      Iterator itRecords = listRecords.iterator();
	      while (itRecords.hasNext()) {
	        StringBuffer sData1 = new StringBuffer();

	        List listRows1 = null;
	        listRows1 = (List)itRecords.next();
	        readList(sData1, listRows1);

	        if (bFirstData == true) {
	          FastjsonUtil.makeArray(sMultiData1, sData1);
	          bFirstData = false;
	        }
	        else {
	          FastjsonUtil.makeArray(sMultiData1, sData1, ",");
	        }
	      }
	      FastjsonUtil.endArray(sMultiData1);
	      FastjsonUtil.makePair(sJson, "data", sMultiData1, ",");
	      break;
	    case 7:
	      FastjsonUtil.makePair(sJson, "length", 1, ",");

	      StringBuffer sMultiData11 = new StringBuffer();
	      FastjsonUtil.beginArray(sMultiData11);

	      StringBuffer sData1 = new StringBuffer();
	      List listObjects = (List)this.oResult;
	      List listRows1 = readListObject(listObjects);
	      readList(sData1, listRows1);

	      FastjsonUtil.makeArray(sMultiData11, sData1);
	      FastjsonUtil.endArray(sMultiData11);

	      FastjsonUtil.makePair(sJson, "data", sMultiData11, ",");
	      break;
	    case 9:
	      sJson = new StringBuffer();
	      sJson.append(this.oResult);
	      return sJson;
	    case 10:
	      FastjsonUtil.makePair(sJson, "data", this.oResult, ",");
	    case 5:
	    case 8:
	    }
	    FastjsonUtil.endMap(sJson);

	    return sJson;
	  }
  
	  private List readListObject(List listObjects) {
		    List<Map<?, ?>> listRows = new ArrayList<Map<?, ?>>();
		    Iterator itObject = listObjects.iterator();
		    while (itObject.hasNext()) {
		      Map mapRow = readObject(itObject.next());
		      listRows.add(mapRow);
		    }
		    return listRows;
		  }
	  
  @SuppressWarnings("unchecked")
  private Map<?,?> readObject(Object obj) {
	    Class<?> clazz = obj.getClass();
	    List<Field>  fields = new ArrayList<Field>();
	    Class<?>  currentClazz = clazz;
	    while (currentClazz != null) {
	      Field[] fs = currentClazz.getDeclaredFields();
	      for (Field f : fs) {
	        fields.add(f);
	      }
	      currentClazz = currentClazz.getSuperclass();
	    }

		Map mapRow = new LinkedHashMap();
	    for (Field field : fields) {
	      try
	      {
	        PropertyDescriptor thisPropertyDescriptor = new PropertyDescriptor(field.getName(), obj.getClass());
	        Method getMethod = thisPropertyDescriptor.getReadMethod();
	        Object oValue = getMethod.invoke(obj, new Object[0]);
	        if (oValue == null) {
	          oValue = "";
	        }
	        mapRow.put(field.getName(), oValue.toString());
	      }
	      catch (IllegalArgumentException e) {
	        e.printStackTrace();
	      }
	      catch (IllegalAccessException e) {
	        e.printStackTrace();
	      }
	      catch (InvocationTargetException e) {
	        e.printStackTrace();
	      }
	      catch (IntrospectionException e) {
	        e.printStackTrace();
	      }
	    }
	    return mapRow;
	  }  
 }