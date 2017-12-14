#!/usr/bin/env bash

export DB_URI=jdbc:mysql://localhost/lazuly-users
export DB_USERNAME=root
export DB_PASSWORD=
export HOST=http://localhost
export JWT_SECRET=jwtsecret
export INTERNAL_SECRET=internalsecret
export PROFILE=staging

mvn spring-boot:run -Dspring.profiles.active=$PROFILE