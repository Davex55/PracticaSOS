USE BANCO;

INSERT INTO Clientes (nombre, dirección, dni) 
VALUES 
('pepe', 'calle' '101010101v'),
('bro', 'avenida', '101010101v'),
('patato', 'plaza', '12345678V'),
('alejandro', 'calle mindungui', '12323445W');


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


-- INSERT INTO Transacciones (Importe, IDCuenta, IDTipoTransf, Fecha)
-- VALUES 
-- (500,2,1,