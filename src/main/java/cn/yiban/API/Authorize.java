package cn.yiban.API;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * �װ���Ȩ��֤�ӿ�
 *
 * ͨ���װ࿪��ƽ̨����õ���AppID��AppSecret�����صĻص�URL������Ȩ
 * ֻ����Ȩ�ɹ���ȡ���������ƺ���ܶ���Ȩ���й�����������ȡ����Ȩ��
 * ��Ȩ��������Apache��HttpClient�������⡣
 */
public class Authorize
{

    /**
     * �������ƹ�����
     *
     * ͨ������ά�����ѯ��ط������Ƶ���Ϣ
     * ʹ�� Authorize::getManInstance()����ȡ��ʵ��
     */
    public class Man
    {
        private String token;

        public Man(String token)
        {
            this.token = token;
        }

        /**
         * ��ѯ��Ȩ״̬
         *
         * ��������Ƶ���Ч�ڵ���Ϣ
         *
         * @return String ��Ȩ��ϢJSON��
         */
        public String query()
        {
            List <NameValuePair> param = new ArrayList <NameValuePair>();
            param.add(new BasicNameValuePair("client_id", appKey));
            param.add(new BasicNameValuePair("access_token", token));
            System.out.println(YIBAN_OPEN_URL + API_TOKEN_QUERY);
            System.out.println(appKey);
            System.out.println(token);
            return HTTPSimple.POST(YIBAN_OPEN_URL + API_TOKEN_QUERY, param);
        }

        /**
         * ������Ȩ
         *
         * ����ͨ����Ȩ�ķ�������
         *
         * @return String ����״̬JSON��
         */
        public String revoke()
        {
            List <NameValuePair> param = new ArrayList <NameValuePair>();
            param.add(new BasicNameValuePair("client_id", appKey));
            param.add(new BasicNameValuePair("access_token", token));
            return HTTPSimple.POST(YIBAN_OPEN_URL + API_TOKEN_REVOKE, param);
        }
    }

    /**
     * ������ն����ͣ���վ���ֻ����ͻ��ˣ�
     */
    public enum DISPLAY_TAG_T {WEB, MOBILE, CLIENT};

    /**
     * ��Ȩ�������ƹ���ʵ��
     */
    private Man manInstance;

    /**
     * ����Ӧ�� APPID
     */
    private String appKey;

    /**
     * ����Ӧ�õ� AppSecret
     */
    private String appSecret;

    private final String TEXT_ENCODING		= "UTF-8";

    private final String YIBAN_OPEN_URL		= "https://openapi.yiban.cn/";

    private final String API_OAUTH_CODE		= "oauth/authorize";
    private final String API_OAUTH_TOKEN	= "oauth/access_token";
    private final String API_TOKEN_QUERY	= "oauth/token_info";
    private final String API_TOKEN_REVOKE	= "oauth/revoke_token";

    /**
     * ���캯��
     *
     * ����Ӧ�ý���ʱ��AppKey��AppSecretֵ
     *
     * @param	String Ӧ�õ�AppID
     * @param	String Ӧ�õ�AppSecret
     */
    public Authorize(String appkey, String appsecret)
    {
        this.appKey		= appkey;
        this.appSecret	= appsecret;
    }

    /**
     * �������ƹ���ʵ������
     *
     * �Թ��������Ƴ�ʼ��������ʵ��
     *
     * @param	String ��������
     * @return	Authorize.Man
     */
    public Man getManInstance(String token)
    {
        if (manInstance == null)
        {
            manInstance = new Man(token);
        }
        return manInstance;
    }

    /**
     * �������ƹ���ʵ������
     *
     * ���Ѿ�ʹ��token��Man�����ʼ��
     * ���Բ�������ֱ��ȡ���ö���
     *
     * @return	Authorize.Man
     */
    public Man getManInstance()
    {
        return manInstance;
    }

    /**
     * ������Ȩ��֤��ַ
     *
     * �ͻ����ض�����Ȩ��ַ
     * ��ȡ��Ȩ��֤��CODE����ȡ�÷�������
     *
     * @param	String �ص���ַ
     * @param	String ����վα�����
     * @param	String ��Ȩҳ�ն����ͣ�Ĭ��web�����
     * @return	String ��Ȩ��֤ҳ���ַ
     */
    public String forwardurl(String redirect_uri, String state, DISPLAY_TAG_T display)
    {
        String query = "?client_id=";
        try
        {
            query += URLEncoder.encode(appKey, TEXT_ENCODING);
            query += "&redirect_uri=";
            query += URLEncoder.encode(redirect_uri, TEXT_ENCODING);
            query += "&state=";
            query += URLEncoder.encode(state, TEXT_ENCODING);
            query += "&display=";
            switch (display)
            {
                case CLIENT:
                {
                    URLEncoder.encode("client", TEXT_ENCODING);
                    break;
                }
                case MOBILE:
                {
                    URLEncoder.encode("mobile", TEXT_ENCODING);
                    break;
                }
                case WEB:
                {
                    URLEncoder.encode("web", TEXT_ENCODING);
                    break;
                }
                default:
                {
                    throw new IllegalArgumentException();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return YIBAN_OPEN_URL + API_OAUTH_CODE + query;
    }

    /**
     * ͨ����Ȩ��CODE��ȡ��������
     *
     * Ӧ�÷�����ֻ��Ҫ���ô˽ӿ�
     * �Զ������ض���
     *
     * @param	String ��ȨCODE
     * @param	String Ӧ�ûص���ַ
     * @return	String �������ƹ�ϣ����JSON��
     */
    public String querytoken(String code, String redirect_uri)
    {
        List <NameValuePair> param = new ArrayList <NameValuePair>();
        param.add(new BasicNameValuePair("client_id", appKey));
        param.add(new BasicNameValuePair("client_secret", appSecret));
        param.add(new BasicNameValuePair("code", code));
        param.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_OAUTH_TOKEN, param);
    }
}