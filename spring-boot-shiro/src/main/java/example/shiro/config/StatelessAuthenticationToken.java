package example.shiro.config;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * 在shiro中有一个我们常用的UsernamePasswordToken，
 * 因为我们需要这里需要自定义一些属性值，比如：消息摘要，参数Map。
 * 用于授权的Token对象
 * 用户身份即用户名；
 * 凭证即客户端传入的消息摘要。
 *
 * @Time :Created by Hyper on 2018/10/3 16:55
 */

public class StatelessAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    //用户身份即用户名；
    private String username;

    //参数.
    private Map<String, ?> params;

    //凭证即客户端传入的消息摘要。
    private String clientDigest;

    public StatelessAuthenticationToken() {
    }

    public StatelessAuthenticationToken(String username, Map<String, ?> params, String clientDigest) {
        super();
        this.username = username;
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public StatelessAuthenticationToken(String username, String clientDigest) {
        super();
        this.username = username;
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

}
