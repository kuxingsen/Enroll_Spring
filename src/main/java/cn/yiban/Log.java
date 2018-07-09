package cn.yiban;

import org.springframework.stereotype.Component;

/**
 * Created by Éµ±Æ on 2018/2/19.
 */
@Component(value = "log")
public class Log {
    public void began()
    {
        System.out.println("began...");
    }
    public void end()
    {
        System.out.println("end...");
    }
}
