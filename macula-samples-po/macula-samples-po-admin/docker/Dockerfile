FROM pdsd-registry.infinitus.com.cn/tools/javabase:0.8.2
COPY app.jar /scmp-cloud-po-admin.jar
COPY dist /dist/public
RUN chmod 777 /dist/public/env.sh
CMD /dist/public/env.sh java $JAVA_OPTS $SW_OPTS -jar /scmp-cloud-po-admin.jar