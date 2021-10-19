mvn clean install
curl -u tomcat:$1 http://heaerieglobalsolutions.com:8080/manager/text/undeploy?path=/pillar
curl -u tomcat:$1 -T  pillar-web/target/pillar.war  http://heaerieglobalsolutions.com:8080/manager/text/deploy?path=/pillar
