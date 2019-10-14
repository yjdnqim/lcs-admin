package com.alipay.sdk;


public class AlipayConfig {
	/**
	 * 商品的标题/交易标题/订单标题/订单关键字等。
	 */
	public static final String SUBJECT = "猿咖啡";
	
	public static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";
	
	/**
     * 支付宝网关（固定）
     */								//"https://mapi.alipay.com/gateway.do"
    public static final String URL="https://openapi.alipay.com/gateway.do";

    /**
     * 授权url(获取用户信息)
     */
    public static final String ALIPAY_URL = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
    
    /**
     *   服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     *   http://ntqcoffee.resolr.com
     */
 	public static String NOTIFY_URL = "/coffeepay/interceptor/notify.do";
 	
 	/**
 	 *  页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
 	 *  http://ntqcoffee.resolr.com
 	 */
 	public static String RETURN_URL = "/coffeepay/interceptor/returnUrl.do";
 	
	/**
	 *日志记录目录
	 */
 	public static String log_path = "/log";
    
 	/**
 	 * 
 	 */
 	public static String TIMEOUT_EXPRESS = "5m";//5分钟
 	
    /**
     * 应用id
     */
    //public static final String APP_ID="2018111062084775";

    /**
     * 应用私钥  pkcs8格式的 有数据
     */
    //public static final String RSA_PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDiV1KT477SD1bigdInVarUGVKNNZh2jJI62R4i+ZXyQqQOvFqNUkOmWBPLDSxXAF5YCh3/789R++9JmE4/8S9ZFa1rejEQB52ExkkYH20Wb9E4b+mrraHGT9O0eH38bOTwWz2q9AkOk2EE2eJIYFnx1oUYqrcgE1J/uOv6lqU7OdZFSYpLPAuWPgPvg1+53uftPZLpJOab8IvrBpUcpKpVX7Bi94+APXd8InNyPtFinwkvuzxsh4GD25jd6TyuJCZdclyGcPh1ximteJaa3kOC6wcX54daNk+ZQ5/ePY4QpoE0bvHOMjtpgKO4JXFDR7y39cPpmKegtK6rmJzDderAgMBAAECggEAL2QCVI+teCNi1+Q3E5Z8b2Sb2cyHoOG4VEVbAYOGDgLE54oVCKsCE1x3Mo8+vNflQBHBAtsFz9JnMcLuLFcaTxIjm3l475waXXgsCsso/x2GqA6dujQgVzRldsRvoaFXjdiWpZjpeOP4oalMGEAWG4nbjpSt8pvXPN20suDAmf7scFR9ACE2759tXpTeKjxaFG715/ncHO57t3BM+mzwQHHp29lX3DHQYcr9YFBvQV7wwU5AgK4wnGLLIv0d/XaY182hSj7B9KiDz3OJP035ME+mbZrbVMoahzAGqCImkK1f6+tM4JZfKOfHITjUjotXNnNHlL2zoKis7sHcK8X/QQKBgQC4Z6oo3bYfnMK42dMPE6+HSvuWo0wZOc2Kl6rsONsvTCLbQ7aExXMFm8dGh4i+nmfkSvYvtXjZiDF+IqBRo8DCvuozgbXOP8/qKCmaxn4l4iSfD0fDR69Oc//nm5k+oFQcBnJrrt0ruUi7+xgGBoY1rw95Rpy8i1GLK+BlSnOpGQKBgQC2mwTuPU1dgUFHV4wNvMQdm8rpiQlfzl6OcEePvhHUaKqy9GjYsf+kf8sp6WybWTkI3U7E916uEHTdEL5VwKDp1999kVPpR4k8i70lw3dyJ5mcG5uCXlFWNwDzzRjYvTqCZzNfZ7qiuFgSOLYnfrDUKzKHawu59Ic3msmInJZrYwKBgGLMn91/VHnBeo6hnjRAvtXE9eDyou4NCzQLKLehisXfi/lgYSTnyUWi/fSq3zgKFO26wAY1u4cJMkM7eBvlmXx932szPt4/UD1LwWa8nI/dhvN1s2RMPv/QpLZJpqRoMEhHp/xL1xH7Fp4o3Ok1i6itn6PBkm5uUoU3P+XlKuzZAoGAYQTUBdBEPp3iFQmKu/HDMQgJMzM/68s1qfA11UVjK7vAlfwNiLRp4viS9N20eoVZLECJDVVw1IKh1KKEvgLue8/aSljggF0lqSmFgX7tA5XOo4Z3hTifdnMa6m3Z+a3wXECApvvxyWqwQF4pSaqiQNgCrLhPLqmg/gYbdZ3cvYkCgYAmAzkhPGLbWLWADg1nIKaW15PF0zTAXjQ1uKEvUuEzLZ3IoZLRX3Sb3gCA+6vfLJ/K6r6cSj43yGl6cbSgNRG77hKIzUaew3h96Fo+tzuinmX4dfiK3gvCSe34jmVuGIfCWvLOzsbJw3u4MaKrUQwXjY190Yl+DfXTjsaSeGmWdQ==";
 
   /**
     * 应用公钥
     */
    //public static final String RSA_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg4ldSk+O+0g9W4oHSJ1Wq1BlSjTWYdoySOtkeIvmV8kKkDrxajVJDplgTyw0sVwBeWAod/+/PUfvvSZhOP/EvWRWta3oxEAedhMZJGB9tFm/ROG/pq62hxk/TtHh9/Gzk8Fs9qvQJDpNhBNniSGBZ8daFGKq3IBNSf7jr+palOznWRUmKSzwLlj4D74Nfud7n7T2S6STmm/CL6waVHKSqVV+wYvePgD13fCJzcj7RYp8JL7s8bIeBg9uY3ek8riQmXXJchnD4dcYprXiWmt5DgusHF+eHWjZPmUOf3j2OEKaBNG7xzjI7aYCjuCVxQ0e8t/XD6ZinoLSuq5icw3XqwIDAQAB";

    /**
     * 支付宝公钥
     */
    //public static final String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiZnwdLiuRF2JjlwfFabqHaJ22KPn64uj1c/N+f+J03M8HCds91FVYvX8Khu4s6gqAte+TjqqY0FZxBqtSGwZGpQyOYdav4ZTtW5M0MBu9lNzs1EA3eVVMyUTsBdIl1eRyNde2vyztREETiZoipkpgT2teZ56saeI62PigYUdGcJyUW8kdK1IlDF6HLNhUr//beseV9YplUDAolb6MDc7JZFK8ff4knoPnoFxqeqobOlVLpSFkJW8wVqJp4sKNoI3grHuh5Moj2xEc2549P4L6GYG70E20M9RSl+3XKeeBtyEaYny2Oap/msP8gewwofq44NbiQlJdH6yMlo/8l5LHQIDAQAB";
    		
    /**
     * 参数返回格式
     */
    public static final String FORMAT="json";

    /**
     * 编码集
     */
    public static final String CHARSET="UTF-8";

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA和RSA2
     */
    public static final String SIGN_TYPE="RSA2";
}
