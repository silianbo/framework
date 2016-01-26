package com.github.sunflowerlb.framework.web.servlet.json;


/**
 * 带有令牌的Json处理返回信息对象
 * 
 * @author 464281
 */
public class JsonTokenMessage extends JsonMessage {

	private String formToken;

	public JsonTokenMessage(JsonMessage jsonMessage) {
		setCode(jsonMessage.getCode()).setMessage(jsonMessage.getMessage())
				.setData(jsonMessage.getData());
	}

	public String getFormToken() {
		return formToken;
	}

	public JsonTokenMessage setFormToken(String formToken) {
		this.formToken = formToken;
		return this;
	}
}
