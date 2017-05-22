#!/usr/bin/env bash
cd /opt/JavaPro/payment
echo "mvn package..."
mvn clean compile package -DskipTests
echo "scp paymentdev.jar..."
scp target/paymentdev.jar root@fuliaoyi.com:/opt
echo "connect fuliaoyi.com..."
ssh-service.sh
echo "deploy complete..."