package com.github.sunflowerlb.framework.core.log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.sunflowerlb.framework.core.log.SensitiveObjectLogUtils.SensitiveObjectLogConvertorHolder;
import com.github.sunflowerlb.framework.tools.util.RegExValidateUtils;

/**
 * 
 * @author lb
 *
 */
public class CryptoConvertConfig {
	private static final Map<String, ICryptoConvertor> convertors = new HashMap<String, ICryptoConvertor>();
	private static final Set<String> DEFAULT_KEYS = new HashSet<String>();
	/**
	 * email转换key
	 */
	public static final String EMAIL_CONVERT = "_email_convert_";
	/**
	 * 电话+手机转换key
	 */
	public static final String PHONE_CONVERT = "_phone_convert_";
	/**
	 * idcard转换key
	 */
	public static final String IDCARD_CONVERT = "_idcard_convert_";
	/**
	 * 身份标示(手机号，邮箱，用户名，身份证)转换key
	 */
	public static final String IDENTITY_CONVERT = "_identity_convert_";
	/**
	 * 默认转换key
	 */
	public static final String DEFAULT_CONVERT = "_default_convert_";
	/**
	 * 银行卡转换key
	 */
	public static final String BANK_CARD_CONVERT = "_bank_card_convert_";

	static {
		DEFAULT_KEYS.add(EMAIL_CONVERT);
		DEFAULT_KEYS.add(PHONE_CONVERT);
		DEFAULT_KEYS.add(IDCARD_CONVERT);
		DEFAULT_KEYS.add(IDENTITY_CONVERT);
		DEFAULT_KEYS.add(DEFAULT_CONVERT);
		DEFAULT_KEYS.add(BANK_CARD_CONVERT);
		registConvertor(EMAIL_CONVERT, new DefaultEmailCryptoConvertor());
		registConvertor(PHONE_CONVERT, new DefaultPhoneCryptoConvertor());
		registConvertor(IDCARD_CONVERT, new DefaultIDCardCryptoConvertor());
		registConvertor(IDENTITY_CONVERT, new DefaultIdentityCryptoConvertor());
		registConvertor(DEFAULT_CONVERT, new DefaultCryptoConvertor());
		registConvertor(BANK_CARD_CONVERT, new DefaultBankCardCryptoConvertor());
	}

	/**
	 * 获取转换实现
	 * 
	 * @param name
	 *            转换实现名
	 * @return
	 */
	public static ICryptoConvertor getConvertor(String name) {
		ICryptoConvertor convertor = convertors.get(name);
		if (convertor == null && DEFAULT_KEYS.contains(name)) {
			switch (name) {
			case EMAIL_CONVERT:
				convertor = new DefaultEmailCryptoConvertor();
				break;
			case PHONE_CONVERT:
				convertor = new DefaultPhoneCryptoConvertor();
				break;
			case IDCARD_CONVERT:
				convertor = new DefaultIDCardCryptoConvertor();
				break;
			case IDENTITY_CONVERT:
				convertor = new DefaultIdentityCryptoConvertor();
				break;
			case DEFAULT_CONVERT:
				convertor = new DefaultCryptoConvertor();
				break;
			case BANK_CARD_CONVERT:
				convertor = new DefaultBankCardCryptoConvertor();
			default:
				break;
			}
			if (convertor != null) {
				registConvertor(name, convertor);
			}
		}
		return convertor;
	}

	/**
	 * 获取Convertor,如果没有使用默认Convertor
	 * 
	 * @param name
	 *            名称
	 * @return
	 */
	public static ICryptoConvertor getDefault(String name) {
		ICryptoConvertor convertor = getConvertor(name);
		if (convertor == null) {
			convertor = getConvertor(DEFAULT_CONVERT);
		}
		return convertor;
	}

	public static ICryptoConvertor getEmailConvertor() {
		return getConvertor(EMAIL_CONVERT);
	}

	public static ICryptoConvertor getPhoneConvertor() {
		return getConvertor(PHONE_CONVERT);
	}

	public static ICryptoConvertor getIDCardConvertor() {
		return getConvertor(IDCARD_CONVERT);
	}

	public static ICryptoConvertor getIdentityConvertor() {
		return getConvertor(IDENTITY_CONVERT);
	}

	public static ICryptoConvertor getBankCardConvertor() {
		return getConvertor(BANK_CARD_CONVERT);
	}

	/**
	 * 注册convertor
	 * 
	 * @param name
	 *            名称
	 * @param convertor
	 *            实现
	 */
	public static void registConvertor(String name, ICryptoConvertor convertor) {
		requiredParam(name, "registConvertor", "name");
		requiredParam(convertor, "registConvertor", "convertor");
		convertors.put(name, convertor);
	}

	/**
	 * 反注册convertor
	 * 
	 * @param name
	 *            名称
	 * @return
	 */
	public static boolean unregistConvert(String name) {
		if (convertors.containsKey(name)) {
			convertors.remove(name);
			return true;
		} else {
			return false;
		}
	}

	public static class DefaultBankCardCryptoConvertor implements ICryptoConvertor {
		@Override
		public String convert(Object value) {
			String valueStr = String.valueOf(value);
			if (RegExValidateUtils.isBankCard(valueStr)) {
				return convertBankCard(valueStr);
			}
			return connvertCommon(valueStr);
		}
	}

	/**
	 * 身份标示Convertor
	 * 
	 * @author lb
	 *
	 */
	public static class DefaultIdentityCryptoConvertor implements ICryptoConvertor {
		@Override
		public String convert(Object value) {
			String valueStr = String.valueOf(value);
			if (RegExValidateUtils.isMobile(valueStr)) {
				return convertMobile(valueStr);
			} /*
				 * else if(RegExValidateUtils.isTelephone(valueStr)){ return
				 * convertTelephone(valueStr); }
				 */
			else if (RegExValidateUtils.isEmail(valueStr)) {
				return convertEmail(valueStr);
			} else if (RegExValidateUtils.isIDCardStrict(valueStr)) {
				return convertIDCard(valueStr);
			} else if (RegExValidateUtils.isBankCard(valueStr)) {
				return convertBankCard(valueStr);
			} else {
				return connvertCommon(valueStr);
			}
		}
	}

	/**
	 * IDCard的Convertor
	 * 
	 * @author zhengrun
	 *
	 */
	public static class DefaultIDCardCryptoConvertor implements ICryptoConvertor {
		@Override
		public String convert(Object value) {
			String valueStr = String.valueOf(value);
			if (RegExValidateUtils.isIDCardStrict(valueStr)) {
				return convertIDCard(valueStr);
			} else {
				return connvertCommon(valueStr);
			}
		}
	}

	/**
	 * Phone的Convertor
	 * 
	 * @author zhengrun
	 *
	 */
	public static class DefaultPhoneCryptoConvertor implements ICryptoConvertor {
		@Override
		public String convert(Object value) {
			String valueStr = String.valueOf(value);
			if (RegExValidateUtils.isMobile(valueStr)) {
				return convertMobile(valueStr);
			} else if (RegExValidateUtils.isTelephone(valueStr)) {
				return convertTelephone(valueStr);
			} else {
				return connvertCommon(valueStr);
			}
		}

	}

	/**
	 * email的convertor
	 * 
	 * @author zhengrun
	 *
	 */
	public static class DefaultEmailCryptoConvertor implements ICryptoConvertor {
		@Override
		public String convert(Object value) {
			String valueStr = String.valueOf(value);
			if (RegExValidateUtils.isEmail(valueStr)) {
				return convertEmail(valueStr);
			} else {
				return connvertCommon(valueStr);
			}
		}
	}

	/**
	 * 默认convertor
	 * 
	 * @author zhengrun
	 *
	 */
	public static class DefaultCryptoConvertor implements ICryptoConvertor {
		@Override
		public String convert(Object value) {
			return connvertCommon(String.valueOf(value));
		}
	}

	/**
	 * 默认敏感对象转换类
	 * @author zhengrun
	 *
	 */
	public static class DefaultSensitiveObjectFastJsonConvertor extends JavaBeanSerializer
			implements ICryptoConvertor, ValueFilter, PropertyPreFilter{
		private Map<String, ICryptoConvertor> convertorMap = new HashMap<>();
		private Set<String> ignoreOutputSet = new HashSet<>(); 
		
		private Class<?> clazz; 

		public DefaultSensitiveObjectFastJsonConvertor(Class<?> clazz) {
			super(clazz);
			this.clazz = clazz ; 
		}

		public DefaultSensitiveObjectFastJsonConvertor(Class<?> clazz, String... aliasList) {
			super(clazz, aliasList);
			this.clazz = clazz ; 
		}

		public DefaultSensitiveObjectFastJsonConvertor(Class<?> clazz, Map<String, String> aliasMap) {
			super(clazz, aliasMap);
			this.clazz = clazz ; 
		}
		
		@Override
		public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType)
				throws IOException {
			if (!serializer.getValueFilters().contains(this)) {
				serializer.getValueFilters().add(this);
			}
			if(!this.ignoreOutputSet.isEmpty() && !serializer.getPropertyPreFilters().contains(this)){
				serializer.getPropertyPreFilters().add(this); 
			}
			super.write(serializer, object, fieldName, fieldType);
		}

		@Override
		public void writeReference(JSONSerializer serializer, Object object) {
			if (!serializer.getValueFilters().contains(this)) {
				serializer.getValueFilters().add(this);
			}
			if(!this.ignoreOutputSet.isEmpty() && !serializer.getPropertyPreFilters().contains(this)){
				serializer.getPropertyPreFilters().add(this); 
			}
			super.writeReference(serializer, object);
		}

		public void add(String name, ICryptoConvertor convertor) {
			convertorMap.put(name, convertor);
		}
		
		public void ignoreOutPut(String name){
			this.ignoreOutputSet.add(name);
		}

		@Override
		public boolean apply(JSONSerializer serializer, Object source, String name) {
			if (source == null) {
				return true;
			}

			if (clazz != null && !clazz.isInstance(source)) {
				return true;
			}

			if (this.ignoreOutputSet.contains(name)) {
				return false;
			}

			return true;
		}

		@Override
		public Object process(Object object, String name, Object value) {
			if (clazz != null && clazz.isInstance(object)) {
				ICryptoConvertor convertor = this.convertorMap.get(name);
				if (convertor != null) {
					return convertor.convert(value);
				}
			}
			return value;
		}

		@Override
		public String convert(Object value) {
			return  SensitiveObjectLogConvertorHolder.INSTANCE.toJSONString(value);
		}
	}

	private static String convertBankCard(String valueStr) {
		int length = valueStr.length();
		StringBuilder sb = new StringBuilder(length);
		sb.insert(0, valueStr);
		int count = 0;
		for (int i = length - 1; i >= 0; i--) {
			if (Character.isDigit(sb.charAt(i))) {
				if (count < 4) {
					++count;
					continue;
				} else {
					sb.setCharAt(i, '*');
				}
			}
		}
		return sb.toString();
	}

	private static String convertIDCard(String valueStr) {
		// 前留6位，后留3位
		int length = valueStr.length();
		StringBuilder sb = new StringBuilder(length);
		sb.insert(0, valueStr);

		for (int i = 6; i < length - 3; i++) {
			sb.setCharAt(i, '*');
		}
		return sb.toString();
	}

	private static String convertTelephone(String valueStr) {
		int length = valueStr.length();
		StringBuilder sb = new StringBuilder(valueStr.length());
		sb.insert(0, valueStr);
		for (int i = length - 3; i > length - 6; i--) {
			sb.setCharAt(i, '*');
		}
		return sb.toString();
	}

	private static String convertMobile(String valueStr) {
		int length = valueStr.length();
		StringBuilder sb = new StringBuilder(valueStr.length());
		sb.insert(0, valueStr);
		for (int i = length - 4; i > length - 8; i--) {
			sb.setCharAt(i, '*');
		}
		return sb.toString();
	};

	private static String convertEmail(String valueStr) {
		int indexOfAt = valueStr.indexOf('@');
		StringBuilder sb = new StringBuilder(valueStr.length());
		sb.insert(0, valueStr);
		for (int i = 1; i < indexOfAt; i++) {
			sb.setCharAt(i, '*');
		}
		return sb.toString();
	}

	private static String connvertCommon(String valueStr) {
		if (isEmpty(valueStr)) {
			return valueStr;
		} else {
			if (valueStr.length() == 1) {
				return valueStr;
			}
			if (valueStr.length() == 2) {
				return valueStr.substring(0, 1) + "*";
			}
			StringBuilder sb = new StringBuilder(valueStr.length());
			sb.insert(0, valueStr);
			for (int i = 1; i < valueStr.length() - 1; i++) {
				sb.setCharAt(i, '*');
			}
			return sb.toString();
		}
	}

	private static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		String s = String.valueOf(value).trim();
		if ("".equals(s) || "null".equalsIgnoreCase(s)) {
			return true;
		}
		return false;
	}

	private static void requiredParam(Object param, String method, String parameter) {
		if (null == param) {
			throw new IllegalArgumentException(
					"Required parameter: {param: " + parameter + ", method: " + method + "}");
		}
	}
}
