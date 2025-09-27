package com.zzyl.generator;

import com.zzyl.common.constant.Constants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class VelocityDemoTest {

    public static void main(String[] args) throws IOException {

        Properties p = new Properties();
        // velocity资源加载器，告诉模板引擎到类路径下寻找资源（比如模板）
        p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // 定义字符集
        p.setProperty(Velocity.INPUT_ENCODING, Constants.UTF8);
        // 初始化velocity引擎
        Velocity.init(p);

        // 创建velocity上下文对象
        VelocityContext context = new VelocityContext();
        // 数据模型，这里的key需要跟模板中的变量对应上，不然填充不了数据
        context.put("message", "加油朋友！！！");
        // 获取模板
        Template template = Velocity.getTemplate("vms/index.html.vm", "UTF-8");
        // 输出
        FileWriter fileWriter = new FileWriter("zzyl-generator\\src\\main\\resources\\index.html");
        // 合并模板和数据模型
        template.merge(context, fileWriter);
        // 关闭流
        fileWriter.close();
    }
}