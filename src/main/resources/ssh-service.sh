#!/usr/bin/expect -f
set timeout 10
spawn ssh root@fuliaoyi.com
expect "*yes/no" {send "yes\r";exp_continue}
send "cd /opt\r"
send "ps_pid=\$(ps -ef|grep payment.jar|grep -v grep|awk '{print \$2}')\r"
send "kill \$ps_pid\r"
send "exit\r"
expect eof