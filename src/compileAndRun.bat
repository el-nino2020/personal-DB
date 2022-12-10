set CLASSPATH=.;..\lib\commons-dbutils-1.7.jar;..\lib\commons-lang3-3.12.0.jar;..\lib\guava-31.1-jre.jar;..\lib\hamcrest-core-1.3.jar;..\lib\junit-4.13.2.jar;..\lib\mysql-connector-java-8.0.28.jar

javac -encoding "UTF-8" -cp %CLASSPATH% view\View.java

cls

java -cp %CLASSPATH% view.View
