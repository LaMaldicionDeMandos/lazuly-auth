#!/usr/bin/env bash

export DB_URI=jdbc:mysql://localhost/lazuly-users
export HOST=http://localhost
export JWT_SECRET=jwtsecret
export INTERNAL_SECRET=internalsecret
export PROFILE=staging

mvn spring-boot:run -Dspring.profiles.active=$PROFILE