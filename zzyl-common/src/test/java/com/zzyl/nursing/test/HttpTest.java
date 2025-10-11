package com.zzyl.nursing.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {

    @Test
    public void testGet() {
        String result = HttpUtil.get("https://www.baidu.com");
        System.out.println(result);
    }

    @Test
    public void testGetByParam() {
        // 构建查询参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 5);
        // 分页查询护理项目
        String result = HttpUtil.get("http://localhost:8080/nursing/project/list", paramMap);
        System.out.println(result);
    }

    @Test
    public void testCreateRequest() {
        // 构建查询参数
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 5);
        // 分页查询护理项目
        HttpResponse response = HttpUtil.createRequest(Method.GET, "http://localhost:9000/nursing/project/list")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImY2MzgxNWNhLWZjZTgtNDM4YS05ZTM4LWI2MzYzM2UyZmYzYSJ9.r_oPLuZF7SAHENIfVlI7KXQJzx1IVoERjUg75HPKn53lFgbPlOISMwTu2RFvtK_VlOGwSBjogb_ylRGqhTZ4Yg")
                .form(paramMap)
                .execute();
        if(response.isOk()){
            System.out.println(response.body());
        }
    }

    @Test
    public void testPost() {
        String url = "http://localhost:8080/nursing/project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));
        System.out.println(result);
    }

    @Test
    public void testCreatePost() {
        String url = "http://localhost:9000/nursing/project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);

        // 发送请求
        HttpResponse response = HttpUtil.createPost(url).body(JSONUtil.toJsonStr(paramMap))
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImY2MzgxNWNhLWZjZTgtNDM4YS05ZTM4LWI2MzYzM2UyZmYzYSJ9.r_oPLuZF7SAHENIfVlI7KXQJzx1IVoERjUg75HPKn53lFgbPlOISMwTu2RFvtK_VlOGwSBjogb_ylRGqhTZ4Yg")
                .execute();
        if(response.isOk()) {
            System.out.println(response.body());
        }

    }
}