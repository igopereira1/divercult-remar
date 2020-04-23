FROM openjdk:8

WORKDIR /usr/lib/grails
RUN wget http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.5.zip
RUN unzip grails-2.4.5.zip && rm grails-2.4.5.zip
ENV GRAILS_HOME /usr/lib/grails/grails-2.4.5/
ENV PATH $PATH:$GRAILS_HOME/bin