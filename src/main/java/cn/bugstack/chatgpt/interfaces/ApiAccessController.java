package cn.bugstack.chatgpt.interfaces;

import cn.bugstack.chatgpt.domain.security.service.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 API访问准入管理；当访问 OpenAI 接口时，需要进行准入验证。
 */
@RestController
public class ApiAccessController {

    private Logger logger = LoggerFactory.getLogger(ApiAccessController.class);

    /**
     * http://localhost:8080/authorize?username=xiaoze&password=557902
     */
    @RequestMapping("/authorize")
    public ResponseEntity<Map<String, String>> authorize(String username, String password) {
        Map<String, String> map = new HashMap<>();
        // 模拟账号和密码校验
        if (!"xiaoze".equals(username) || !"557902".equals(password)) {
            map.put("msg", "用户名密码错误");
            return ResponseEntity.ok(map);
        }
        // 校验通过生成token
        JwtUtil jwtUtil = new JwtUtil();//JwtUtil类来处理JWT令牌的生成和验证
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", username);
        String jwtToken = jwtUtil.encode(username, 5 * 60 * 1000, chaim);//这里的5*60*1000表示五分钟后token过期
        //encode就是用于返回令牌的,username用于指认用户
        map.put("msg", "授权成功");
        map.put("token", jwtToken);
        // 返回token码
        return ResponseEntity.ok(map);//ok(map)方法是一个便捷的方法，用于快速创建一个带有200 OK状态码和给定响应体
    }



    /**
     * http://localhost:8080/verify?token=
     */
    @RequestMapping("/verify")
    public ResponseEntity<String> verify(String token) {
        logger.info("验证 token：{}", token);
        return ResponseEntity.status(HttpStatus.OK).body("验证token成功");
    }

    @RequestMapping("/success")
    public String success(){
        return "欢迎来到xiaoze的网站";
    }

}
