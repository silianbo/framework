package com.lb.framework.web.http.converter;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lb.framework.web.servlet.json.WebJsonUtil;

/**
 * 顺银定制的http消息转换器
 * <p>
 * 支持JSONP跨域，支持处理防篡改信息
 * 
 * @author 464281
 */
public class JsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    /** json请求在业务处理成功时，重新创建防篡改表单的请求参数名称 */
//    private static final String RECREATE_FOR_JSON_SUCCESS_PARAMETER_NAME = "recreate";

//    private ServletAntiTamperFormManager servletAntiTamperFormManager;

    /**
     */
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        obj = decorateObject(obj);
        String text = JSON.toJSONString(obj, getFeatures());
        text = WebJsonUtil.toJSONPString(text);
        byte[] bytes = text.getBytes(getCharset());

        OutputStream out = outputMessage.getBody();
        out.write(bytes);
    }

    /**
     * 包装json返回对象
     * 
     * @param obj json返回对象
     * @return 包装后的json返回对象，不会为<code>NULL</code>
     */
//    protected Object decorateObject(Object obj) throws IOException {
//        if (!(obj instanceof JsonMessage) || obj instanceof JsonTokenMessage) {
//            return obj;
//        }
//
//        // 未进行防篡改校验或校验不通过，不处理
//        AntiTamperServerForm serverForm = servletAntiTamperFormManager.findCheckedServerForm();
//        if (null == serverForm) {
//            return obj;
//        }
//
//        // 当(业务处理失败)、(业务处理成功且浏览器端强制要求重新创建防篡改表单)，均会重新创建
//        boolean isSuccess = ((JsonMessage) obj).isSuccess();
//        if (isSuccess) {
//            HttpServletRequest request = HttpServletHolder.getCurrentRequest();
//
//            String recreateForJsonSuccess = request.getParameter(RECREATE_FOR_JSON_SUCCESS_PARAMETER_NAME);
//            if (!StringUtils.endsWithIgnoreCase(recreateForJsonSuccess, "true")) {
//                return obj;
//            }
//        }
//
//        AntiTamperForm newServerForm = null;
//        try {
//            newServerForm = servletAntiTamperFormManager.reCreateForm(serverForm);
//        } catch (FormAntiTamperException e) {
//            throw new IOException("重新创建防篡改表单出现异常", e);
//        }
//
//        JsonTokenMessage jtMessage = new JsonTokenMessage((JsonMessage) obj);
//        jtMessage.setFormToken(newServerForm.getToken());
//        return jtMessage;
//    }
//
//    public void setServletAntiTamperFormManager(ServletAntiTamperFormManager servletAntiTamperFormManager) {
//        this.servletAntiTamperFormManager = servletAntiTamperFormManager;
//    }
}
