## RegExValidateUtils的使用

### 简介
提供手机号、身份证格式、身份证严格信息、邮箱验证、ipv4和v6的校验、固定电话等正则表达式的验证

手机号验证
`	public static boolean isMobile(String mobile) `

电话验证，包含固话和手机号
`	public static boolean isTelephone(String telephone) `

电话验证，包含固话和手机号
`	public static boolean isPhone(String phone) `

身份证验证，判断是否15位数字或者17位数字+1位校验码
`	public static boolean isIDCard(String idCard) `

身份证严格验证，按照国家标准校验身份证，包括格式，第一级地区码，生日有效性，校验码有效性
`	public static boolean isIDCardStrict(String idCard) `

邮箱验证
`	public static boolean isEmail(String email) `

兼容ipv4和v6的校验
`	public static boolean isIPAddress(String ip) `
