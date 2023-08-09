FROM gradle:jdk17-jammy as build

COPY --chown=gradle:gradle . /app/src

WORKDIR /app/src

RUN gradle stockexchange:installDist

FROM eclipse-temurin:17-jre-jammy

COPY --from=build  /app/src/stockexchange/build/install/stockexchange/* /app/stockexchange/lib/

WORKDIR /app/stockexchange

CMD ./lib/stockexchange