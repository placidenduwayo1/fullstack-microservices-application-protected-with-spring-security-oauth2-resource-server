FROM node:14.21.3-bullseye-slim As build

RUN mkdir -p /frontend-app

WORKDIR /frontend-app

COPY package.json ./frontend-app

RUN npm install

COPY ./ ./frontend-app

RUN npm build

FROM nginx:latest

COPY --from=build /frontend-app/dist/angular13-project-train-front /usr/share/nginx/html

EXPOSE 8080
