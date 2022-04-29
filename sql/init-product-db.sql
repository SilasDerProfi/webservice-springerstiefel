CREATE USER 'product'@'%' IDENTIFIED BY 'geheim';
CREATE DATABASE product;
GRANT ALL PRIVILEGES ON product.* TO 'product'@'%';
FLUSH PRIVILEGES;

CREATE TABLE product (
	id INT NOT NULL AUTO_INCREMENT,
	details VARCHAR(255),
	name VARCHAR(255),
	price DOUBLE,
	category_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX FK1mtsbur82frn64de7balymq9s ON product (category_id ASC);