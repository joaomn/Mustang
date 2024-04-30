# Use uma imagem base com Java 17 e Maven 4
FROM maven:latest AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie os arquivos de configuração e o arquivo pom.xml
COPY pom.xml .
COPY src ./src

# Limpe o cache do Maven
#RUN mvn dependency:purge-local-repository

# Construa a aplicação
RUN  mvn clean install -U -e -DskipTests=true

# Crie a imagem final usando o JRE 17
FROM openjdk:17-jdk-slim-buster

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR da etapa de construção
COPY --from=build /app/target/mustang-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta que a aplicação está ouvindo
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner iniciar
CMD ["java", "-jar", "app.jar"]