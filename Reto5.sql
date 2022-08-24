SHOW DATABASES;

CREATE DATABASE reto5;

use reto5;

CREATE TABLE Productos(
codigo Integer auto_increment,
nombre text not null,
precio REAL not null,
inventario int not null,
PRIMARY KEY(codigo)
    )
    
    SELECT TABLES;
    
insert into productos values (1,'Manzanas',5000.0,25);
insert into productos values (2,'Limones',2300.0,15);
insert into productos values (3,'Peras',2700.0,33);
insert into productos values (4 ,'Arandanos', 9300.0 ,5);
insert into productos values (5 ,'Tomates', 2100.0 ,42);
insert into productos values (6,'Fresas', 4100.0 ,3);
insert into productos values (7 ,'Helado', 4500.0 ,41);
insert into productos values (8 ,'Galletas', 500.0, 8);
insert into productos values (9 ,'Chocolates', 3500.0, 80);
insert into productos values (10 ,'Jamon', 15000.0 ,10);
    