package com.ai.assist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailParams {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private Integer mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.transport.protocol}")
    private String mailTransportProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private Boolean mailSmtpStarttlsEnable;

    @Value("${spring.mail.debug}")
    private Boolean mailDebug;

    public String getMailHost() {
        return mailHost;
    }

    public Integer getMailPort() {
        return mailPort;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailTransportProtocol() {
        return mailTransportProtocol;
    }

    public Boolean getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public Boolean getMailSmtpStarttlsEnable() {
        return mailSmtpStarttlsEnable;
    }

    public Boolean getMailDebug() {
        return mailDebug;
    }
}
