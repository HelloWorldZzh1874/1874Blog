package com.zzh.utils;

import com.zzh.common.exception.EmailUncorrectedException;
import com.zzh.common.utils.StringUtils;
import com.zzh.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zzh.common.constant.CommonConst.*;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/715:41
 */

@Component
public class MailUtil {
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

        /**
         * template模板引擎
         */
        @Autowired
        private TemplateEngine templateEngine;


    /**
     * 检测邮箱是否合法
     *
     * @param email 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }


    /**
     * 发送简单邮件
     *
     * @param mail 邮箱实体
     * @param type 发送模板类型
     */
    public void send(EmailDTO mail, Integer type) {
        // 目标邮箱
        String to = mail.getEmail();
        // 邮件标题
        String title = mail.getSubject();

        // 检查邮箱格式
        boolean checkEmail = checkEmail(to);
        if (!checkEmail) {
            throw new EmailUncorrectedException("邮箱格式错误!");
        }
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(title);
            mimeMessage.setHeader("X-Priority", "3");
            mimeMessage.setHeader("X-MSMail-Priority", "Normal");
            //以outlook名义发送邮件，不会被当作垃圾邮件
            mimeMessage.setHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.5512");
            mimeMessage.setHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.5512");
            mimeMessage.setHeader("ReturnReceipt", "1");
            // 使用 thymeleaf 模板发送
            Context context = new Context();
            String html;
            // 判断邮箱类型，选择相应的邮箱模板
            // switch case无法使用Const中的常量，使用if-else
            if (type.equals(WRONG_PWD)) {
                context.setVariable("context", mail.getContent());
                if (StringUtils.isNotEmpty(mail.getWebUrl())) {
                    context.setVariable("webUrl", mail.getWebUrl());
                }
                html = templateEngine.process(COMMON_EMAIL_TEMP, context);
                mimeMessageHelper.setText(html, true);
            } else if (type.equals(REGISTER_CODE_EMAIL) || type.equals(FORGET_PWD_CODE_EMAIL)) {
                if (StringUtils.isNotEmpty(mail.getCode())) {
                    context.setVariable("type",type);
                    context.setVariable("code", mail.getCode());
                }
                context.setVariable("username", mail.getEmail());
                html = templateEngine.process(CODE_EMAIL_TEMP, context);
                mimeMessageHelper.setText(html, true);
            } else if(type.equals(NOTICE_MAIL)){
                context.setVariable("username", mail.getContent());
                html = templateEngine.process(NOTICE_EMAIL_TEMP, context);
                mimeMessageHelper.setText(html, true);
            } else {
                throw new EmailUncorrectedException("发送邮箱错误,请稍后再试!");
            }
            mailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
        }
    }
}
