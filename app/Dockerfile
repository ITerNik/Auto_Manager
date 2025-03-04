FROM gradle:jdk17-corretto

WORKDIR /app
COPY . .
RUN gradle clean build

CMD ["gradle", "bootRun"]
