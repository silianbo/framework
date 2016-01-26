/**
 * 
 */
package com.github.sunflowerlb.framework.usage.service.inner;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.github.sunflowerlb.framework.core.validator.constraints.Mobile;
import com.github.sunflowerlb.framework.usage.domain.Product;

/**
 * 
 * @author lb
 */
public interface IUsageService {

    /**
     * message={}可以指定不同的key
     * @param email
     * @param password
     * @param phoneNum
     */
    void register(@Email String email, @NotBlank String password, @NotNull(message="{usage.NotNull}") Long phoneNum);
    
    void login(@NotBlank(message="userName不能为空") String userName, @NotBlank(message="password不能为空") String password);
    
    void addProduct(@Valid Product product);

    void sendVerCode(@Mobile String phone);
}
