# Docker build
RUN echo 'Running Docker build. Welcome to the Dockerfile, we got fun and games.'

RUN docker build -t voltsofdoom/voltsofdoom:v1.0 .

RUN echo 'Docker build complete.'
