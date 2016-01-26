package com.github.sunflowerlb.framework.core.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志规范的工具封装
 * 
 * @author lb
 */
public class Log {
	
	private static boolean isSensInfoCrytoEnable = true; 
	/**
	 * 操作类型
	 */
	private String operation;

	/**
	 * 日志信息
	 */
	private String message = EMPTY_STR;

	/**
	 * 额外的参数
	 */
	private Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * 日志格式 [operation][message],{key1=value1,key2=value2,key3=value3}
	 */
	private static final String LOG_FORMAT = "%d|%s|%s|%s";

	/**
	 * 空字符串
	 */
	private static final String EMPTY_STR = "";
	
	public static void setIsSensInfoCryptoEnable(boolean isEnable){
		isSensInfoCrytoEnable = isEnable; 
	}
	
	public static boolean isSensInfoCryptoEnable(){
		return isSensInfoCrytoEnable; 
	}
	/**
	 * 
	 * @param operation 操作
	 */
	public Log(String operation) {
		this.operation = operation;
	}

	/**
	 * 创建Log对象
	 * @param op 操作
	 * @return
	 */
	public static Log op(String op) {
		return new Log(op);
	}

	/**
	 * 
	 * @param message 信息
	 * @return
	 */
	public Log msg(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 键值对
	 * @param key key
	 * @param value 值
	 * @return
	 */
	public Log kv(String key, Object value) {
		params.put(key, value);
		return this;
	}

	
	
	/**
	 * 
	 * @param maps 
	 * @return
	 */
	public Log kvs(Map<String, Object> maps) {
		params.putAll(maps);
		return this;
	}
	
	/**
	 * 通用转换方法
	 * @param key key
	 * @param value 需要转换的值
	 * @param type 转换类型
	 * @return
	 */
	public Log kv(String key, Object value, ConvertorTypeEnum type){
		try{
			switch (type) {
				case EMAIL:
					return kvOfEmail(key, value);
				case MOBILE:
					return kvOfMobile(key, value);
				case TELEPHONE:
					return kvOfPhone(key, value);
				case IDCARD:
					return kvOfIDCard(key, value);
				case BANKCARD:
					return kvOfBankCard(key, value);
				case OBJECT:
					return kvOfObject(key, value);
				case DEFAULT:
				case CUSTOME_DEFINE:
				case IDENTITY:
				default:
					return kvOfIdentity(key, value);
			}
		}catch(Throwable ex){
			if(isSensInfoCryptoEnable()){
				params.put(key, CryptoConvertConfig.getDefault(CryptoConvertConfig.DEFAULT_CONVERT).convert(value));
			}else {
				params.put(key, value);
			}
			return this;
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param convertor
	 * @return
	 */
	public Log kv(String key, Object value, ICryptoConvertor convertor){
		try{
			return kvOfCrypto(key, value, convertor);
		}catch(Throwable ex){
			if(isSensInfoCryptoEnable()){
				params.put(key, CryptoConvertConfig.getDefault(CryptoConvertConfig.DEFAULT_CONVERT).convert(value));
			}else {
				params.put(key, value);
			}
			return this;
		}
	}
	private Log kvOfObject(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key, SensitiveObjectLogUtils.convert(value)); 
		}else {
			params.put(key, value); 
		}
		return this; 
	}
	
	/**
	 * 邮箱转换
	 * @param key key
	 * @param value 值
	 * @return
	 */
	private Log kvOfEmail(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key, CryptoConvertConfig.getEmailConvertor().convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}

	/**
	 * 银行卡转换
	 * @param key key
	 * @param value 值
	 * @return
	 */
	private Log kvOfBankCard(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key,  CryptoConvertConfig.getBankCardConvertor().convert(value));
		}else {
			params.put(key, value); 
		}
		return this; 
	}
	/**
	 * 身份证转换
	 * @param key key
	 * @param value 值
	 * @return
	 */
	private Log kvOfIDCard(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key, CryptoConvertConfig.getIDCardConvertor().convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}
	
	/**
	 * 手机号码 转换
	 * @param key key
	 * @param value 值
	 * @return
	 */
	private Log kvOfMobile(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key, CryptoConvertConfig.getPhoneConvertor().convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}
	
	/**
	 * 电话或手机号码转换
	 * @param key key
	 * @param value value
	 * @return
	 */
	private Log kvOfPhone(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key, CryptoConvertConfig.getPhoneConvertor().convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}
	
	/**
	 * 标识转换，匹配value，对email，手机号，身份证进行匹配和转换，其他模式使用普通加密转换器
	 * @param key key
	 * @param value 值
	 * @return
	 */
	private Log kvOfIdentity(String key, Object value){
		if(isSensInfoCryptoEnable()){
			params.put(key, CryptoConvertConfig.getIdentityConvertor().convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}
	
	/**
	 * 使用默认的转换器转化
	 * @param key
	 * @param value
	 * @return
	 */
	private Log kvOfCrypto(String key, Object value){
		if (isSensInfoCryptoEnable()) {
			params.put(key, CryptoConvertConfig.getDefault(null).convert(value)); 
		}else {
			params.put(key, value); 
		}
		return this; 
	}
	/**
	 * 使用自定义转换器加密值
	 * @param key key
	 * @param value 值
	 * @param convertor 转换器
	 * @return
	 */
	private Log kvOfCrypto(String key, Object value, ICryptoConvertor convertor){
		if(isSensInfoCryptoEnable()){
			params.put(key,convertor != null ? convertor.convert(value) : 
				   CryptoConvertConfig.getDefault(null).convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}
	
	/**
	 * 使用自定义转换器加密值
	 * @param key key
	 * @param value 值
	 * @param convertorKey 在CryptoConvertConfig中注册的转换器key
	 * @return
	 */
	private Log kvOfCrypto(String key, Object value, String convertorKey){
		if(isSensInfoCryptoEnable()){
			params.put(key, CryptoConvertConfig.getDefault(convertorKey).convert(value));
		}else {
			params.put(key, value);
		}
		return this; 
	}
	
	@Override
	public String toString() {
		return String.format(LOG_FORMAT, System.currentTimeMillis(), operation, params.toString(), message);
	}

	public static void main(String[] args) {
		String s = Log.op("createOrderOp").msg("create order success!").kv("name", "zhengxiahong").kv("id", 12345).toString();
		System.out.println(s);
	}

}
