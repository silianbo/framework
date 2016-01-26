/**
 * 
 */
package com.github.sunflowerlb.framework.usage.exception;

import com.github.sunflowerlb.framework.tools.util.JsonUtil;
import com.google.gson.Gson;

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
