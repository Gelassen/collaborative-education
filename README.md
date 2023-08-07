# Collaborative Education
MVP that implement the idea of collaborative education. <br><br>

Nowadays there is exponential growth of information around us and constant pressure to learn new things. How to filter what is a valuable and what is not? <br><br>

Recommendations. Let's people with the same needs and goals filtrate what works and does not. The same as google search engine counts references on the content before show it to you on search result page, we will count votes for educational course. <br><br>

<a href="https://gelassen.github.io/blog/2020/06/06/mvp-on-collaborative-edu.html">Released a MVP that implement the idea of collaborative education.</a> <br>
<a href="https://gelassen.github.io/blog/2020/06/13/insights-from-mvp.html">Insights on 1st MVP after Product Manager interview.</a> <br><br>

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
