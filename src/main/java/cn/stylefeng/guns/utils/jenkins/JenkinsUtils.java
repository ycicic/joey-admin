package cn.stylefeng.guns.utils.jenkins;

import cn.hutool.http.HttpRequest;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * jenkins API工具类
 *
 * @author ycc
 */
@Slf4j
public class JenkinsUtils {

    private final String jenkinsUrl;
    private final String jenkinsJobName;
    private final String jenkinsUserName;
    private final String jenkinsUserPassword;

    public JenkinsUtils() {
        String jenkinsUrl = ConfigContext.me().getSysConfigValueWithDefault("JENKINS_URL", String.class, "127.0.0.1:8080");
        String jenkinsJobName = ConfigContext.me().getSysConfigValueWithDefault("JENKINS_JOB_NAME", String.class, "");
        String jenkinsUserName = ConfigContext.me().getSysConfigValueWithDefault("JENKINS_USER_NAME", String.class, "");
        String jenkinsUserPassword = ConfigContext.me().getSysConfigValueWithDefault("JENKINS_USER_PASSWORD", String.class, "");
        this.jenkinsUrl = jenkinsUrl;
        this.jenkinsJobName = jenkinsJobName;
        this.jenkinsUserName = jenkinsUserName;
        this.jenkinsUserPassword = jenkinsUserPassword;
    }

    /**
     * 获取Jenkins配置文件
     *
     * @return Jenkins配置文件
     */
    public File getJenkinsConfigXml() {
        File configXml;
        try {
            configXml = File.createTempFile("config", ".xml");
            configXml.deleteOnExit();
            HttpRequest.get(jenkinsUrl + "/job/" + jenkinsJobName + "/config.xml")
                    .basicAuth(jenkinsUserName, jenkinsUserPassword)
                    .execute()
                    .writeBodyForFile(configXml, null);
        } catch (Exception e) {
            configXml = null;
            log.error("获取Jenkins配置文件失败", e);
        }
        return configXml;
    }

    public void buildWithParameters(Map<String, Object> parameters) {
        String body = HttpRequest.post(jenkinsUrl + "/job/" + jenkinsJobName + "/buildWithParameters")
                .basicAuth(jenkinsUserName, jenkinsUserPassword)
                .form(parameters)
                .execute()
                .body();
        log.info("推送构建完成 {}", body);
    }

    public Integer getLastBuildNumber() {
        String json = HttpRequest.get(jenkinsUrl + "/job/" + jenkinsJobName + "/api/json?pretty=true&tree=lastBuild[number]").basicAuth(jenkinsUserName, jenkinsUserPassword).execute().body();
        return JSONObject.parseObject(json).getJSONObject("lastBuild").getInteger("number");
    }

    public String getResultByJobNumber(Integer number) {
        String json = HttpRequest.get(jenkinsUrl + "/job/" + jenkinsJobName + "/" + number + "/api/json?pretty=true&tree=result").basicAuth(jenkinsUserName, jenkinsUserPassword).execute().body();
        return JSONObject.parseObject(json).getString("result");
    }

    public String getUrlByJobNumber(Integer number) {
        if (null == number) {
            return null;
        }
        return jenkinsUrl + "/job/" + jenkinsJobName + "/" + number;
    }

    public List<String> getGitBranches() {
        String joeyGitBranchesUrl = ConfigContext.me().getSysConfigValueWithDefault("JOEY_GIT_BRANCHES_URL", String.class, "[]");
        String gitPrivateToken = ConfigContext.me().getSysConfigValueWithDefault("GIT_PRIVATE_TOKEN", String.class, "");
        String body = HttpRequest.get(joeyGitBranchesUrl).header("private-token", gitPrivateToken).execute().body();
        return JSONArray.parseArray(body, JSONObject.class).stream().map((obj) -> obj.getString("name")).collect(Collectors.toList());
    }

    public String getBuildTimeTrendReqUrl() {
        return jenkinsUrl + "/job/" + jenkinsJobName + "/buildTimeTrend";
    }

}

