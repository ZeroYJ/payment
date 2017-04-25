#!/usr/bin/env bash
cd /opt/JavaPro/payment
mvn clean compile package -DskipTests
scp target/payment.jar root@fuliaoyi.com:/opt
