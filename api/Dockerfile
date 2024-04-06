FROM ubuntu:20.04 AS install
ARG SCALA_VERSION
ARG MILL_VERSION
ADD https://github.com/lampepfl/dotty/releases/download/${SCALA_VERSION}/scala3-${SCALA_VERSION}.zip scala3-${SCALA_VERSION}.zip
ADD https://github.com/com-lihaoyi/mill/releases/download/${MILL_VERSION}/${MILL_VERSION} mill
RUN apt-get update \
 && apt-get install -y \
    unzip \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*
RUN unzip scala3-${SCALA_VERSION}.zip \
 && mv scala3-${SCALA_VERSION} scala
RUN chmod 777 mill

FROM openjdk:11.0.10-slim AS user
ARG UID
ARG GID
ARG USER
RUN groupadd -g ${GID} ${USER} \
 && useradd -u ${UID} -g ${GID} -m ${USER} \
 && mkdir -p /home/${USER}/.ssh \
 && chown -R ${USER}:${USER} /home/${USER}
RUN apt-get -y update \
 && apt-get -y --no-install-recommends install \
    curl \
    git \
    ssh \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*
USER ${USER}
RUN ssh-keyscan github.com >> /home/${USER}/.ssh/known_hosts

FROM user AS dev
COPY --from=install mill /usr/local/bin/mill
COPY --from=install scala /usr/local/bin/scala
ENV PATH $PATH:/usr/local/bin/scala/bin
RUN mill --version