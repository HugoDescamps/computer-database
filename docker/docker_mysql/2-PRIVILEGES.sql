  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'%' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `dev-computer-database-db`.* TO 'admincdb'@'%' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
