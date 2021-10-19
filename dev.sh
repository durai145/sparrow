mvn clean install
curl -u tomcat:tomcat http://192.168.1.64:8080/manager/text/undeploy?path=/pillar
curl -u tomcat:tomcat -T  pillar-web/target/pillar.war  http://192.168.1.64:8080/manager/text/deploy?path=/pillar
