package com.github.sunflowerlb.framework.core.dubbo.trace.support;

/**
 * <pre>
 * Created by lb on 14-9-29.下午2:42
 * </pre>
 */
public class TraceConstants {

	public static final String X_TRACE_ROOT_ID = "X-TRACE-ROOT-ID";
	public static final String X_TRACE_PARENT_ID = "X-TRACE-PARENT-ID";
	public static final String X_TRACE_RPC_ID = "X-TRACE-RPC-ID";
	public static final String X_TRACE_ID = "X-TRACE-ID";
	public static final String X_TRACE_SPAN_ID = "X-TRACE-SPAN-ID";

	public static final int DEFAULT_PORT = 0; // 服务的默认端口

	public static final String TRACE_ID = "trace_id";
	public static final String PARENT_ID = "parent_id";
	public static final String RPC_ID = "rpc_id";
	public static final String SPAN_ID = "span_id";
	public static final String IS_SAMPLE = "is_sample";

	public static final String INIT_RPC_ID = "0";

	public static final int EMPTY_SIZE = 0;

	public static final String NULL_STRING = "NULL";

	public static final char SEPARATOR = '#';

	public static final int MAX_SAMPLE_TRACE_NUM = 10;
	public static final int TOP_MAX_SZIE = 10;
	public static final String SERVER_IP_ALL = "ALL";
	public static final int TRACEID_LENGTH = 7;

	public static final String EXCEPTION_KEY = "exception";
	public static final String EXCEPTION_NAME_KEY = "ex.name";
	public static final String EXCEPTION_TYPE = "ex";
	public static final String INFO_TYPE = "info";
	public static final String CONCURRENT_KEY = "concurrent";

	public static final String START_PAGE_NUM = "0";

}
