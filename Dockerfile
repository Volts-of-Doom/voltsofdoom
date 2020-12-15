# Docker build

# Container image that runs your code
FROM openjdk:8

# Copies your code file from your action repository to the filesystem path `/` of the container
COPY . /src/main/docker/voltsofdoom

WORKDIR /src/main/docker/voltsofdoom
RUN javac VoltsOfDoomCoreSystem.java

# Code file to execute when the docker container starts up (`entrypoint.sh`)
ENTRYPOINT ["/src/main/docker/voltsofdoom"]
