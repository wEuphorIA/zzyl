package com.itheima.velocity;

import com.zzyl.generator.util.VelocityInitializer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileWriter;
import java.io.IOException;

public class VelocityTest {
    public static void main(String[] args) throws IOException {
        // 1.初始化模板引擎
        VelocityInitializer.initVelocity();

        // 2.创建Velocity上下文对象
        VelocityContext context = new VelocityContext();
        // 准备数据模型
        context.put("message", "加油小朋友");

        // 3.获取模板
        Template template = Velocity.getTemplate("vms/index.html.vm", "UTF-8");

        // 4.合并模板和数据模型
        FileWriter fw = new FileWriter("zzyl-generator\\src\\main\\resources\\index.html");
        template.merge(context, fw);

        // 5.关闭流
        fw.close();
    }
}
