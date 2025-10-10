# 基础镜像
FROM openjdk:11.0-jre-buster
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV OSS_ACCESS_KEY_ID LTAI5tSDKrWrMZ3oTUWKeJTp
ENV OSS_ACCESS_KEY_SECRET TJogvdFuzw1UqBCHmchPQ2e4fKwXzc
# 拷贝jar包
COPY zzyl-admin.jar /app.jar
# 入口
ENTRYPOINT ["java", "-jar", "/app.jar"]