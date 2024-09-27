# Используем официальный образ Tomcat
FROM tomcat:10.0

# Удаляем стандартное приложение, если необходимо
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Копируем ваше приложение .war в папку webapps Tomcat
COPY /target/app.war /usr/local/tomcat/webapps/


# Открываем порт 8080
EXPOSE 8080

# Запускаем Tomcat
CMD ["catalina.sh", "run"]
