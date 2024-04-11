# Fullstack application Java / Angular
# Description

- Gestions des employées habitants à des adresses, ces emloyées sont effectés à des projets chez les sociétés
- backend: architecture orientée microservices, architecture technique pour chaque microservice métier: clean-architecture
- fronted: application Angular version 16, le pattern RxJS
- Containeurisation de chaque service de l'application avec docker et docker-compose

# Backend

## microservices métiers
- ***api-business-microservice-address***
- ***api-business-microservice-company***
- ***api-business-microservice-employee***
- ***api-business-microservice-project***

## microservice pour la sécurité
- **api-spring-security-oauth2-service** pour l'authentication des utilisateurs
- ce service gère les authentications basées sur des tokens JWT d'authentification
- il encode et decode les tokens JWT evec JwtEncoder / JwtDecoder et signe le JWT avec l'algo RSA à deux clés publique et privée 

## communication et authentification entre microservices
- ***Feign Client*** pour de la communication entre microservices
-  ***RequestInterceptor*** pour l'authentifier les microservices qui communiquent entre eux
- ***Circuit-Breaker Resilience4J*** pour le fault tolerance et gérer  des scénarios alternatifs

## microservices utilitaires
- **api-microservices-congig-service** pour  centraliser et externaliser les configurations
- **api-microservices-registry-service** pour l'enregistrement des microservices dans l'annuaire
- **api-gateway-proxy-service** pour le proxy entre le frontend (angular application) et le backend

# Fronted
- reactive angular application avec la librairie RxJs

# Architecture globale:
![demo](https://github.com/placidenduwayo1/fullstack-microservices-application-protected-with-spring-security-oauth2-resource-server/assets/124048212/d845c2be-3894-4347-9e09-27f3b0050aea)










