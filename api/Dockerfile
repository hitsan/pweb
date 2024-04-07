FROM ubuntu:20.04 AS install
ARG COURSIER_VERSION
ARG SCALA_VERSION
ARG MILL_VERSION
RUN apt-get update \
 && apt-get install -y \
    curl \
    gzip \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*
RUN curl -fL https://github.com/coursier/coursier/releases/download/v${COURSIER_VERSION}/cs-x86_64-pc-linux.gz | gzip -d > cs
RUN chmod +x cs
RUN ./cs install scala:${SCALA_VERSION} scalac:${SCALA_VERSION} mill:${MILL_VERSION}

FROM openjdk:11.0.10-slim AS dev
ARG UID
ARG GID
ARG USER
ENV PATH="/home/${USER}/.local/share/coursier/bin:${PATH}"
RUN groupadd -g ${GID} ${USER} \
 && useradd -u ${UID} -g ${GID} -m ${USER} \
 && mkdir -p /home/${USER}/.ssh \
 && chown -R ${USER}:${USER} /home/${USER}
COPY --from=install --chown=${USER}:${USER} cs /usr/local/bin/cs
COPY --from=install --chown=${USER}:${USER} /root/.local /home/${USER}/.local
RUN apt-get -y update \
 && apt-get -y --no-install-recommends install \
    build-essential \
    libz-dev \
    zlib1g-dev \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*
# git and ssh are not installed
USER ${USER}
# RUN ssh-keyscan github.com >> /home/${USER}/.ssh/known_hosts
RUN mill --version \
 && scala -version \
 && scalac -version