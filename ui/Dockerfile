FROM node:12-alpine

WORKDIR /ui

COPY package.json /ui/package.json
RUN npm install --only=prod

COPY . /ui
RUN npm run build

EXPOSE 3000
ENTRYPOINT [ "npm", "start" ]
