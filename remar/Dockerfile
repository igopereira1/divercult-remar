FROM loaufscar/projeto-remar
RUN rm -rf /usr/local/tomcat/webapps
RUN mkdir /usr/local/tomcat/webapps
COPY manager.war /usr/local/tomcat/webapps
COPY ROOT.war /usr/local/tomcat/webapps
COPY server.xml /usr/local/tomcat/conf/server.xml