CREATE DATABASE dbistuerCarroCompra;

USE dbistuerCarroCompra;

CREATE TABLE usuari(
id int AUTO_INCREMENT,
nom varchar(25),
cognom varchar(50),
login varchar(25) UNIQUE NOT NULL,
pass varchar(25) NOT NULL,
mail varchar(50),
PRIMARY KEY (id)
);

CREATE TABLE producte(
id int AUTO_INCREMENT,
idUsuari int NOT NULL,
nom varchar(20),
disponibilitat integer,
descripcio varchar(2000),
preu float(6,2),
iniciVenda date,
PRIMARY KEY (id),
FOREIGN KEY (idUsuari) REFERENCES usuari(id)
);

CREATE TABLE carrito(
id int AUTO_INCREMENT,
idUsuari int,
idProducte int,
cantitat int,
PRIMARY KEY (id),
FOREIGN KEY (idUsuari) REFERENCES usuari(id),
FOREIGN KEY (idProducte) REFERENCES producte(id)
);


/* INSERT USUARI*/

INSERT INTO usuari(nom,cognom,login,pass,mail) VALUES('Dani','Bistuer Marco','dbistuer','1234','dbistuer@msn.com');
INSERT INTO usuari(nom,cognom,login,pass,mail) VALUES('pep','garcia','pgarcia','1234','pgarcia@gmail.com');
INSERT INTO usuari(nom,cognom,login,pass,mail) VALUES('joan','marquez','jmarquez','1234','jmarquez@gmail.com');


/* INSERT PRODUCTE*/
INSERT INTO producte(idUsuari,nom,disponibilitat,descripcio,preu,inicivenda)
VALUES(1,'pepino',3,'Producte agricola sense pesticides in aditus.',1.2,'2019-03-01');
INSERT INTO producte(idUsuari,nom,disponibilitat,descripcio,preu,inicivenda)
VALUES(1,'patata',5,'Producte agricola sense pesticides in aditus.',1.0,'2019-02-01');

