FROM gradle:jdk17-jammy

COPY . /app/tradeServer

WORKDIR /app/tradeServer

RUN gradle installDist

ENTRYPOINT ./build/install/tradeServer/bin/tradeServer