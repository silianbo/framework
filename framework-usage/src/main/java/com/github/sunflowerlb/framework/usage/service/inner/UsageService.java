/**
 * 
 */
package com.github.sunflowerlb.framework.usage.service.inner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.sunflowerlb.framework.usage.domain.Product;

/**
 * 
 * @author lb
 */
@Service
public class UsageService implements IUsageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.usage.service.IUsageService#register(java.lang.String
     * , java.lang.String, java.lang.Long)
     */
    @Override
    public void register(String email, String password, Long phoneNum) {
        logger.info("UsageService.register,email:{}, password:{}, phoneNum:{}", email, password, phoneNum);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.usage.service.inner.IUsageService#login(java.lang
     * .String, java.lang.String)
     */
    @Override
    public void login(String userName, String password) {
        logger.info("UsageService.login,userName:{}, password:{}", userName, password);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ihome.framework.usage.service.inner.IUsageService#addProduct(com.
     * ihome.framework.usage.domain.Product)
     */
    @Override
    public void addProduct(Product product) {
        logger.info("UsageService.addProduct,product:{}", product);
    }

    /* (non-Javadoc)
     * @see com.ihome.framework.usage.service.inner.IUsageService#sendVerCode(java.lang.String)
     */
    @Override
    public void sendVerCode(String phone) {
        logger.info("UsageService.sendVerCode,phone:{}", phone);        
    }
}