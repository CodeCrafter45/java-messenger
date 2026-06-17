FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN javac Server.java ClientHandler.java

EXPOSE 5000

CMD ["java", "Server"]