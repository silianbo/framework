## 敏感信息日志

### Log中增加的敏感信息日志方法
Log中的敏感信息日志方法，利用[验证工具类](regexvalidate.md)进行匹配后，进行相应的转换
#### 1、通用转换方法
	/**
	 * 通用转换方法
	 * @param key key
	 * @param value 需要转换的值
	 * @param type 转换类型
	 * @return
	 */
	public Log kv(String key, Object value, ConvertorTypeEnum type)

###### 1.1、邮箱转换： jacky@qq.com -> j\*\*\*\*@qq.com
`	kv("email", "jacky@qq.com", ConvertorTypeEnum.EMAIL) `

###### 1.2、身份证转换: 11010120141001261X -> 110101\*\*\*\*\*\*\*\*\*61X 
`	kv("IDCard", "11010120141001261X", ConvertorTypeEnum.IDCARD) `

###### 1.3、手机号码转换: 13798213421 -> 1379\*\*\*\*\421
`	kv("mobile", "13798213421", ConvertorTypeEnum.MOBILE)	`

###### 1.4、电话或手机号码转换, 手机号码同上，电话： (0755)86071510 -> (0755)860\*\*\*10
`	kv("phone", "(0755)86071510", ConvertorTypeEnum.TELEPHONE)	`

###### 1.5、银行卡号转换：10-24位的银行卡号，转换：6226054213548754 -> \*\*\*\*\*\*\*\*\*\*\*\*8754  
6226 0542 1354 8754 -> \*\*\*\* \*\*\*\* \*\*\*\* 8754
`	kv("bankcard", "6226054213548754", ConvertorTypeEnum.BANKCARD)	`
`	kv("bankcard", "6226 0542 1354 8754", ConvertorTypeEnum.BANKCARD)	`

###### 1.6、标识转换，匹配value，安装手机号，email，身份证（严格模式）、银行卡号等进行匹配和转换，其他模式使用普通加密转换器
需要调用多次正则匹配
`	kv(key, value, ConvertorTypeEnum.IDENTITY) `

###### 1.7、如果以上都无法用正则匹配到，使用默认转化器转化： 01201410012 -> 0\*\*\*\*\*\*\*\*\*2
`	kv(key, value, ConvertorTypeEnum.IDENTITY) `

###### 1.8、对象转换
对象对应类上，敏感字段需要加上注解,如下
<pre><code>
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
</code></pre>
然后日志记录时，指定ConvertTypeEnum为OBJECT,注解标注字段会使用对应的转化器进行转换，
自定义转换器的时候，在注解指定convertor属性为对应的类即可。
`	kv(key, value, ConvertorTypeEnum.OBJECT) `

对象日志记录支持注解的对象被包含在集合类型或数组中。
也就是说,只要person中含有注解字段，以下方式都支持：
`	kv(key, person, ConvertorTypeEnum.OBJECT) `
`	kv(key, person[], ConvertorTypeEnum.OBJECT) `
`	kv(key, personList, ConvertorTypeEnum.OBJECT) `
`	kv(key, personMap, ConvertorTypeEnum.OBJECT) `

###### 1.9、忽略大对象字段日志
只需要在字段上加上SensitiveIgnoreOut注解，日志中会忽略该字段的输出
<pre><code>
	@SensitiveIgnoreOut
    private String documentContent; 
</code></pre>

#### 2、如果以上不满足，可以自定义转换方法，实现ICryptoConvertor接口
使用自定义转换器加密值
`	public Log kv(String key, Object value, ICryptoConvertor convertor) `


接口示例：

	public class MyConvertor implements ICryptoConvertor{
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
#### 3、如果项目中需要动态启用或者禁用敏感信息加密
支持动态更改配置的内容在ihome-framework-core中，应用只需要按照以下操作就可以启用/禁用脱敏功能：
* 引入bean配置，` <import resource="classpath*:framework-core/ihome-framework-log.xml"/> `
* 增加disconf配置文件，senslog.properties，内容：
sensinfo.crypto.enable=true

