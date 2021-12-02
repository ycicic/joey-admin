package cn.stylefeng.guns.utils.jenkins.config;

import cn.stylefeng.guns.utils.jenkins.JenkinsUtils;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jenkins配置类
 *
 * @author ycc
 */
@Configuration
public class JenkinsConfiguration {

    @Bean
    public JenkinsUtils jenkinsUtils() {
        return new JenkinsUtils();
    }

}
