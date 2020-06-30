USE BANCO;

INSERT INTO Clientes (Nombre, DNI, Telefono, Direccion) 
VALUES 
('pepe', '101010102v', '666666666', 'calle'),
('bro', '101010101v', '77777777','avenida'),
('patato', '12345678V', '666555666', 'plaza'),
('alejandro', '12345645W', '789456123','calle mindungui');


INSERT INTO Cuentas (Balance, IDCliente) 
VALUES 
(100,1),
(2000,1),
(3000,3),
(2000,4),
(40000,2),
(100,3),
(10000,4),
(200,2);


INSERT INTO Transacciones (Importe, IDCuenta, IDTipoTransf)
VALUES 
(500,2,1),
(1000,1,1),
(10000,3,1),
(10,4,2),
(20,4,2),
(40,6,2),
(500,7,2),
(100,8,2);