FROM gradle:jdk17-corretto

WORKDIR /app
COPY app .
RUN gradle clean build

CMD ["gradle", "bootRun"]
