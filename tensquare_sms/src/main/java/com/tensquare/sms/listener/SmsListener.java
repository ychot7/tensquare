package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信监听类
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    @RabbitHandler
    public void sendSms(Map<String, String> map) {
        System.out.println("手机号：" + map.get("mobile"));
        System.out.println("验证码：" + map.get("code"));
        System.out.println("模板编号 " + template_code);
        System.out.println("签名 " +  sign_name);
        System.out.println("{\"code\":\"" + map.get("code") + "\"}");
        try {
            smsUtil.sendSms(map.get("mobile"),
                    template_code, sign_name,
                    "{\"code\":\"" + map.get("code") + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


    /*@RabbitHandler
    public void sendSms(Map<String, String> map) {
        System.out.println("手机号" + map.get("mobile"));
        System.out.println("验证码" + map.get("code"));
    }*/
}
