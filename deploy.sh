#mvn clean install -DskipTests
ksh run.sh stop tomcat $1 pillar  pillar-web/target/pillar.war
ksh run.sh start tomcat $1 pillar  pillar-web/target/pillar.war
ksh run.sh status tomcat $1  pillar  pillar-web/target/pillar.war
