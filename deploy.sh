#!/usr/bin/env bash
cd /opt/JavaPro/payment
echo "mvn package..."
mvn clean compile package -DskipTests
echo "scp payment.jar..."
scp target/payment.jar root@fuliaoyi.com:/opt
echo "connect fuliaoyi.com..."
ssh-service.sh
echo "deploy complete..."