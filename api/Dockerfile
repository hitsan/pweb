FROM node:20.11-buster-slim AS base
CMD ["bash"]

FROM base AS dev
ARG UID
ARG GID
ARG USER
RUN userdel -r node
RUN groupadd -g ${GID} ${USER} \
 && useradd -u ${UID} -g ${GID} -m ${USER} \
 && mkdir -p /home/${USER}/.ssh \
 && chown -R ${USER}:${USER} /home/${USER}
RUN apt-get -y update \
 && apt-get -y --no-install-recommends install \
    default-jre \
    git \
    ssh \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*
USER ${USER}
RUN ssh-keyscan github.com >> /home/${USER}/.ssh/known_hosts

FROM base AS build
COPY . /work
WORKDIR /work
RUN npm ci \
 && npm run build

FROM node:20.11-buster-slim AS production
COPY --from=build /work/build /api
CMD ["node", "main.js"]