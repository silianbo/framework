package com.github.sunflowerlb.framework.core.dubbo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.github.sunflowerlb.framework.core.log.LogConst;
import com.github.sunflowerlb.framework.core.log.LogTools;

/**
 * 实现在dubbo调用的时候，在attachments中加上该次调用的uniqueId
 * @author lb
 */
@Activate(group = { Constants.CONSUMER, Constants.PROVIDER })
public class DubboTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        Map<String, String> attachments = context.getAttachments();

        // 保存uniqueId到attachment和mdc
        String uniqueId = attachments.get(LogConst.UID);
        // 当为consumer,且当前线程也是provider的情况的时候,从mdc里面取uid,主要是应对那种在同1个controller/service方法里面
        // 有多次dubbo调用的情况
        if(StringUtils.isBlank(uniqueId) && context.isConsumerSide()) {
            String isThreadProvider = MDC.get(LogConst.IS_THREAD_PROVIDER);
            if(StringUtils.isNotBlank(isThreadProvider) && isThreadProvider.equals(LogConst.TRUE)) {
                uniqueId = MDC.get(LogConst.UID);
            }
        }
        if(StringUtils.isBlank(uniqueId)) {
            uniqueId = LogTools.generateUID();
        }
        context.setAttachment(LogConst.UID, uniqueId);
        MDC.put(LogConst.UID, uniqueId);
        // 如果是provider的话,置放1个标志在mdc
        if(context.isProviderSide()) {
            MDC.put(LogConst.IS_THREAD_PROVIDER, LogConst.TRUE);
        }
        return invoker.invoke(invocation);
    }

}
