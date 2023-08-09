FROM gradle:jdk17-jammy as build

COPY --chown=gradle:gradle . /app/src

WORKDIR /app/src

RUN gradle tradeserver:installDist

FROM eclipse-temurin:17-jre-jammy

COPY --from=build  /app/src/tradeserver/build/install/tradeserver/* /app/tradeserver/lib/

WORKDIR /app/tradeserver

CMD ./lib/tradeserver