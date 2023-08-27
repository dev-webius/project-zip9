FROM node:18

COPY . /tmp/client

WORKDIR /tmp/client
RUN npm install -g serve
RUN npm run build

ENTRYPOINT ["npm", "run", "serve"]
