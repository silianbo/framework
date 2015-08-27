/**
 * 
 */
package com.lb.framework.usage.exception;

import com.google.gson.Gson;
import com.lb.framework.tools.util.JsonUtil;

/**
 * 
 * @author lb
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gson gs = new Gson();
		String json = gs.toJson(ErrOnly.LOGIN_FAIL);
		System.out.println(json);
		System.out.println(JsonUtil.toJSONString(ErrOnly.LOGIN_FAIL));
		
		System.out.println(gs.toJson(ErrCode.LOGIN_FAIL.parse()));
	}

}
