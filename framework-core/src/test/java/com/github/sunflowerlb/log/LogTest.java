package com.github.sunflowerlb.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.sunflowerlb.framework.core.log.ConvertorTypeEnum;
import com.github.sunflowerlb.framework.core.log.CryptoConvertConfig;
import com.github.sunflowerlb.framework.core.log.ICryptoConvertor;
import com.github.sunflowerlb.framework.core.log.Log;
import com.github.sunflowerlb.framework.core.log.sensitive.SensitiveBankCard;
import com.github.sunflowerlb.framework.core.log.sensitive.SensitiveEmail;
import com.github.sunflowerlb.framework.core.log.sensitive.SensitiveField;
import com.github.sunflowerlb.framework.core.log.sensitive.SensitiveIDCard;
import com.github.sunflowerlb.framework.core.log.sensitive.SensitiveIgnoreOut;
import com.github.sunflowerlb.framework.core.log.sensitive.SensitiveMobile;

public class LogTest {

	public static void main(String[] args) {
		test();
	}
	//@Test
	public static void test() {
		//		String s = Log.op("createOrderOp").msg("create order success!").kv("name", "zhengxiahong").kv("id", 12345).toString();
	    String s = Log.op("createOrderOp").toString();
		assertTrue(s.endsWith("createOrderOp|{}|"));
		
		assertTrue(Log.op("log email").kv("email", "jacky@qq.com", ConvertorTypeEnum.EMAIL).toString().endsWith("{email=j****@qq.com}|"));
		assertTrue(Log.op("log idcard").kv("IDCard", "11010120141001261X", ConvertorTypeEnum.IDCARD).toString().endsWith("{IDCard=110101*********61X}|" ));
		assertTrue(Log.op("log mobile").kv("mobile", "13798213421", ConvertorTypeEnum.MOBILE).toString().endsWith("{mobile=1379****421}|"));
		assertTrue(Log.op("log phone").kv("phone", "(0755)86071510", ConvertorTypeEnum.TELEPHONE).toString().endsWith("{phone=(0755)860***10}|" ));
		assertTrue(Log.op("log identity").kv("user", "jacky@qq.com", ConvertorTypeEnum.IDENTITY).toString().endsWith("{user=j****@qq.com}|"));
		assertTrue(Log.op("log identity").kv("user", "13798213421", ConvertorTypeEnum.IDENTITY).toString().endsWith("{user=1379****421}|"));
		assertTrue(Log.op("log identity").kv("user", "my_name_is_hh", ConvertorTypeEnum.IDENTITY).toString().endsWith("{user=m***********h}|"));
		assertTrue(Log.op("log identity").kv("user", "11010120141001261X", ConvertorTypeEnum.IDENTITY).toString().endsWith("{user=110101*********61X}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226 0542 1354 8754", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=**** **** **** 8754}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226054213548754", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=************8754}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226 0542 13", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=**** **42 13}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226054213", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=******4213}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226 0542 3", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=6*********3}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "622605423", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=6*******3}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226 0542 1354 8754 2254 1136", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=**** **** **** **** **** 1136}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "622605421354875422541136", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=********************1136}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226 0542 1354 8754 2254 11365", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=6****************************5}|"));
		assertTrue(Log.op("log bankcard").kv("bancard", "6226054213548754225411365", ConvertorTypeEnum.BANKCARD).toString().endsWith("{bancard=6***********************5}|"));
		
		
		
		assertTrue(Log.op("log customer crypto").kv("abc", "11010120141001261X", new ICryptoConvertor(){
			@Override
			public String convert(Object value) {
				//前后留3位
				String valueStr = String.valueOf(value);
				int length = valueStr.length();
				StringBuilder sb = new StringBuilder(valueStr.length());
				sb.insert(0, valueStr);
				for(int i = 3; i < length - 3; i++){
					sb.setCharAt(i, '*');
				}
				return sb.toString();
			}
		}).toString().endsWith("{abc=110************61X}|"));
		/*ICryptoConvertor myConvertor = new ICryptoConvertor(){
			@Override
			public String convert(Object value) {
				//前留4位后留2位
				String valueStr = String.valueOf(value);
				int length = valueStr.length();
				StringBuilder sb = new StringBuilder(valueStr.length());
				sb.insert(0, valueStr);
				for(int i = 4; i < length - 2; i++){
					sb.setCharAt(i, '*');
				}
				return sb.toString();
			}
		};
		CryptoConvertConfig.registConvertor("my_convertor", myConvertor);
		assertTrue(Log.op("log customer crypto2").kv("abc", "11010120141001261X","my_convertor").toString().endsWith("{abc=1101************1X}|"));
		assertTrue(Log.op("log customer crypto2").kv("abc", "asdfeas","my_convertor").toString().endsWith("{abc=asdf*as}|"));
		assertTrue(Log.op("log customer crypto2").kv("abc", "asdfs","my_convertor").toString().endsWith("{abc=asdfs}|"));
		assertTrue(Log.op("log customer crypto2").kv("abc", "asd","my_convertor").toString().endsWith("{abc=asd}|"));*/
		
		
		Test[] persons = new Test[2]; 
		persons[0] = new Test("sz110023", "13755555249", "jddcdlld@foxmail.com", "6226000000009282", "11010120141001261X", "jacky4reg"); 
		persons[1] = new Test("sz110023", "13755555249", "jddcdlld@foxmail.com", "6226000000009282", "11010120141001261X", "jacky4reg"); 
		
		//System.out.println(persons instanceof Object[]);
		
		List<Test> personList = new ArrayList<Test>(); 
		personList.add(new Test("sz110023", "13755555249", "jddcdlld@foxmail.com", "6226000000009282", "11010120141001261X", "jacky4reg"));
		personList.add(new Test("sz110023", "13755555249", "jddcdlld@foxmail.com", "6226000000009282", "11010120141001261X", "jacky4reg"));
		
		Map<String, Test> maps = new HashMap<>(); 
		maps.put(""+persons[0].hashCode(), persons[0]);
		maps.put(""+persons[1].hashCode(), persons[1]);
		
		//System.out.println(SensitiveObjectLogUtils.convert(persons[0]));
		
		//System.out.println(SensitiveObjectLogUtils.convert(personList));
		
		//System.out.println(SensitiveObjectLogUtils.convert(persons));
		
		//System.out.println(SensitiveObjectLogUtils.convert(maps));
		
		//System.out.println(SensitiveObjectLogUtils.convert(Arrays.asList("sz110023", "13755555249", "jddcdlld@foxmail.com", "6226000000009282", "11010120141001261X", "jacky4reg")));
		
		persons[0].setCustomStr("11010120141001261X");
		//System.out.println(SensitiveObjectLogUtils.convert(persons));
		
		System.out.println(Log.op("log object").kv("person", persons[0], ConvertorTypeEnum.OBJECT).toString());
		System.out.println(Log.op("log list object").kv("person", personList, ConvertorTypeEnum.OBJECT).toString());
		System.out.println(Log.op("log array object").kv("persons", persons, ConvertorTypeEnum.OBJECT).toString());
		System.out.println(Log.op("log map object").kv("persons", maps, ConvertorTypeEnum.OBJECT).toString());
		
		
		assertTrue(Log.op("log object").kv("person", persons[0], ConvertorTypeEnum.OBJECT).toString()
				.endsWith("{person={\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"1101************1X\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}}}|"));
		assertTrue(Log.op("log list object").kv("persons", personList, ConvertorTypeEnum.OBJECT).toString()
				.endsWith("{persons=[{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"null\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}},"
						+ "{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"null\",\"email\":\"j*******@foxmail.com\",\"idCard\":\"110101*********61X\","
						+ "\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}}]}|"));
		assertTrue(Log.op("log array object").kv("persons", persons, ConvertorTypeEnum.OBJECT).toString()
				.endsWith("{persons=[{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"1101************1X\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}},"
						+ "{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"null\",\"email\":\"j*******@foxmail.com\",\"idCard\":\"110101*********61X\","
						+ "\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}}]}|"));
		assertTrue(Log.op("log map object").kv("persons", maps, ConvertorTypeEnum.OBJECT).toString()
				.endsWith("{persons={\"611437735\":{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"null\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}},"
						+ "\"97730845\":{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"1101************1X\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}}}}|"));

		
		A a = new A(); 
		a.setMobile("13755555249");
		B b = new B(); 
		b.setEmail("jddcdlld@foxmail.com");
		a.setB(b);
		b.setA(a);
		
		//System.out.println(SensitiveObjectLogUtils.convert(Arrays.asList(a, b)));
		
		ICryptoConvertor convertor= CryptoConvertConfig.getConvertor(Test.class.getName());
		if(convertor != null){
			System.out.println(convertor.convert(persons[0]));
		}
		
		persons[0].setAge(10);
		
		NestedTest nestedTest = new NestedTest(); 
		nestedTest.setValue("jacky");
		nestedTest.setMyEmail("jackyabc1123@qq.com");
		nestedTest.setTest(persons[0]);
		nestedTest.setTests(persons);
		nestedTest.setTestList(personList);
		nestedTest.setTestMap(maps);
		Test2 test2 = new Test2(); 
		test2.setIdCard("11010120141001261X");
		nestedTest.setTest2(test2);
		Test3<Test4> test3 = new Test3<Test4>(); 
		Test4 test4 = new Test4(); 
		test4.setMobile("13755555249");
		test4.setIdCard("11010120141001261X");
		test4.setBankCard("6226000000009282");
		Test5 test5 = new Test5() ; 
		test5.setMobile("13755555249");
		test3.setT(test4);
		test3.setTest5(test5);
		nestedTest.setTest3(test3);
		System.out.println(Log.op("log object").kv("nested object", nestedTest, ConvertorTypeEnum.OBJECT).toString());
		assertTrue(Log.op("log object").kv("nested object", nestedTest, ConvertorTypeEnum.OBJECT).toString()
				.endsWith("{\"myEmail\":\"j***********@qq.com\","
						+ "\"test\":{\"age\":10,\"bankCard\":\"************9282\",\"customStr\":\"1101************1X\","
						+ "\"email\":\"j*******@foxmail.com\",\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\","
						+ "\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{}},"
						+ "\"test2\":{\"idCard\":\"110101*********61X\"},\"test3\":{\"t\":{\"bankCard\":\"************9282\","
						+ "\"idCard\":\"110101*********61X\",\"mobile\":\"1375****249\"},\"test5\":{\"mobile\":\"1375****249\"}},"
						+ "\"testList\":[{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"null\","
						+ "\"email\":\"j*******@foxmail.com\",\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\","
						+ "\"mobile\":\"1375****249\",\"number\":\"sz110023\",\"test5\":{\"mobile\":\"null\"}},"
						+ "{\"age\":0,\"bankCard\":\"************9282\",\"customStr\":\"null\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\","
						+ "\"number\":\"sz110023\",\"test5\":{\"mobile\":\"null\"}}],\"testMap\":{\"611437735\":{\"age\":0,"
						+ "\"bankCard\":\"************9282\",\"customStr\":\"null\",\"email\":\"j*******@foxmail.com\","
						+ "\"idCard\":\"110101*********61X\",\"identity\":\"j*******g\",\"mobile\":\"1375****249\","
						+ "\"number\":\"sz110023\",\"test5\":{\"mobile\":\"null\"}},\"97730845\":{\"$ref\":\"$.test\"}},"
						+ "\"tests\":[{\"$ref\":\"$.test\"},{\"$ref\":\"$.testMap.611437735\"}],\"value\":\"jacky\"}}|"));
		
	}
	
	public static class Test3<T> {
		private T t; 
		
		private Test5 test5; 
		
		public T getT() {
			return t;
		}
		
		public void setT(T t) {
			this.t = t;
		}
		
		public Test5 getTest5() {
			return test5;
		}
		
		public void setTest5(Test5 test5) {
			this.test5 = test5;
		}
	}
	
	public static class Test5 {
		@SensitiveMobile
		private String mobile; 
		
		public String getMobile() {
			return mobile;
		}
		
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
	}
	
	public static class Test4 {
		@SensitiveMobile
		private String mobile; 
		
		@SensitiveIDCard
		private String idCard; 
		
		@SensitiveBankCard 
		private String bankCard; 
		
		public String getIdCard() {
			return idCard;
		}
		
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		
		public String getBankCard() {
			return bankCard;
		}
		
		public void setBankCard(String bankCard) {
			this.bankCard = bankCard;
		}
		
		public String getMobile() {
			return mobile;
		}
		
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
	}
	
	public static class Test2 {
		@SensitiveIDCard
		private String idCard; 
		
		public String getIdCard() {
			return idCard;
		}
		
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		
	}
	public static class NestedTest {
		private String value ; 
		
		@SensitiveEmail
		private String myEmail; 
		
		private Test test; 
		private Test[] tests ; 
		private List<Test> testList; 
		private Map<String, Test> testMap; 
		
		private Test2 test2; 
		
		private Test3<Test4> test3; 
		
		public Test3<Test4> getTest3() {
			return test3;
		}
		
		public void setTest3(Test3<Test4> test3) {
			this.test3 = test3;
		}
		public Test2 getTest2() {
			return test2;
		}
		
		public void setTest2(Test2 test2) {
			this.test2 = test2;
		}
		
		public String getMyEmail() {
			return myEmail;
		}
		public void setMyEmail(String myEmail) {
			this.myEmail = myEmail;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public Test getTest() {
			return test;
		}
		public void setTest(Test test) {
			this.test = test;
		}
		
		public Test[] getTests() {
			return tests;
		}
		public void setTests(Test[] tests) {
			this.tests = tests;
		}
		
		public List<Test> getTestList() {
			return testList;
		}
		
		public void setTestList(List<Test> testList) {
			this.testList = testList;
		}
		
		public Map<String, Test> getTestMap() {
			return testMap;
		}
		
		public void setTestMap(Map<String, Test> testMap) {
			this.testMap = testMap;
		}
	}
	public static class A {
		@SensitiveMobile
		private String mobile; 
		
		private B b; 
		
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
		public B getB() {
			return b;
		}
		
		public void setB(B b) {
			this.b = b;
		}
	}
	
	public static class B {
		@SensitiveEmail
		private String email; 
		
		private A a; 
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		public A getA() {
			return a;
		}
		
		public void setA(A a) {
			this.a = a;
		}
		
	}
	
	public static class Test {
		
		private String number; 
		@SensitiveMobile
		private String mobile;
		@SensitiveEmail
		private String email;
		@SensitiveBankCard
		private String bankCard; 
		@SensitiveIDCard
		private String idCard;
		@SensitiveField
		private String identity; 
		
		@SensitiveField(convertor=MyConvertor.class)
		private String customStr; 
		
		private int age; 
		
		@SensitiveIgnoreOut
		private String ignoreField; 
		
		public Test(){
			
		}
		
		public String getIgnoreField() {
			return ignoreField;
		}
		
		public void setIgnoreField(String ignoreField) {
			this.ignoreField = ignoreField;
		}
		
		@SensitiveIgnoreOut
		public Test4 getTest4ForIgnoreField(){
			return new Test4(); 
		}
		
		public Test5 getTest5(){
			return new Test5(); 
		}
		public int getAge() {
			return age;
		}
		
		public void setAge(int age) {
			this.age = age;
		}
		
		public String getNumber() {
			return number;
		}
		
		public Test(String number, String mobile, String email, String bankCard, String idCard, String identity) {
			super();
			this.number = number;
			this.mobile = mobile;
			this.email = email;
			this.bankCard = bankCard;
			this.idCard = idCard;
			this.identity = identity;
		}
		
		public String getCustomStr() {
			return customStr;
		}
		
		public void setCustomStr(String customStr) {
			this.customStr = customStr;
		}
		
		public void setNumber(String number) {
			this.number = number;
		}
		
		public String getMobile() {
			return mobile;
		}
		
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getBankCard() {
			return bankCard;
		}
		public void setBankCard(String bankCard) {
			this.bankCard = bankCard;
		}
		
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		
		public String getIdentity() {
			return identity;
		}
		public void setIdentity(String identity) {
			this.identity = identity;
		}
		
	}
	
	
	public static class MyConvertor implements ICryptoConvertor{
		@Override
		public String convert(Object value) {
			//前留4位后留2位
			String valueStr = String.valueOf(value);
			int length = valueStr.length();
			StringBuilder sb = new StringBuilder(valueStr.length());
			sb.insert(0, valueStr);
			for(int i = 4; i < length - 2; i++){
				sb.setCharAt(i, '*');
			}
			return sb.toString();
		}
	}

	/**
	 * 没有引入junit。。。。。。。
	 * @param endsWith
	 */
	private static void assertTrue(boolean endsWith) {
		if(!endsWith){
			throw new IllegalStateException("not true"); 
		}
	}


}
