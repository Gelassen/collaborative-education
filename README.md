# Collaborative Education

Server

1. Setup environment 

console: sudo apt-get install nodejs

console: sudo apt update

console: sudo apt install mysql-server 
[for more details: https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-20-04]

console: npm install

console: mysql -u <username> -p
  
console: <mysql console> CREATE DATABASE db_collaborative_edu;

console: <mysql console> exit

console: cd path/to/backend

console: mysql -u <username> -p db_collaborative_edu < schema_db_collaborative_edu.sql

2. Run and operate with server

console: tmux

console: sudo mysql restart

console: sudo mysql -u <user> -p
  
console: <mysql console> USE <db_name>;
  
console: <mysql console> exit
  
console: cd <path/to/backend>

console: node app.js

console: <enter ctrl + b and then d to detach tmux seesion to keep it run after leave ssh session; tmux attach to return back to it>
[for more details: https://askubuntu.com/questions/8653/how-to-keep-processes-running-after-ending-ssh-session]

console: exit 
[it is important to leave ssh session appropriately over exit command]
