CREATE USER 'category'@'%' IDENTIFIED BY 'geheim';
CREATE DATABASE category;
GRANT ALL PRIVILEGES ON category.* TO 'category'@'%';
FLUSH PRIVILEGES;