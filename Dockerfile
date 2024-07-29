# 基础镜像
FROM openjdk:8-jre-slim
# 作者
MAINTAINER xiaoze
# 配置
ENV PARAMS=""
# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 添加应用
ADD /target/chatgpt-web.jar /chatgpt-web.jar
# 执行镜像
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /chatgpt-web.jar $PARAMS"]