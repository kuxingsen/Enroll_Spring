package cn.yiban.API;

/**
 * Created by 傻逼 on 2018/1/29.
 */
public class User {

    private final String YIBAN_OPEN_URL		= "https://openapi.yiban.cn/";

    private static final String YIBAN_USER_ME_INFO	= "user/me";
    private static final String YIBAN_USER_OTHER	= "user/other";
    private static final String YIBAN_USER_REALME	= "user/real_me";

    /**
     * 已经通过授权的访问令牌
     */
    private String token;
    public void setToken(String token){
        this.token=token;
    }

    /**
     * 构造函数
     *
     */
    public User(String token)
    {
        this.token = token;
    }

    public User()
    {

    }

    /**
     * 个人基本信息
     *
     * 不包含实名信息，基本信息其它人可以查看
     *
     * @return	String	JSON字符串
     */
    public String me()
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ME_INFO;
        query += "?access_token=";
        query += token;
        return HTTPSimple.GET(query);
    }

    /**
     * 查看用户信息
     *
     * 通过指定用户ID查看其它用户的基本信息
     *
     * @return	String	JSON字符串
     */
    public String other(int userid)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_OTHER;
        query += "?access_token=";
        query += token;
        query += "&yb_userid=";
        query += String.valueOf(userid);
        return HTTPSimple.GET(query);
    }

    /**
     * 自己的实名信息
     *
     * 只能查看自己的实名信息，不能查看他人的
     *
     * @return	String	JSON字符串
     */
    public String realme()
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_REALME;
        query += "?access_token=";
        query += token;
        return HTTPSimple.GET(query);
    }

}
