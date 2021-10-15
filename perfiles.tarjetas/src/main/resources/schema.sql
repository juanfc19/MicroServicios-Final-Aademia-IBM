DROP TABLE IF EXISTS Edades;
CREATE TABLE Edades (
    edadId INT AUTO_INCREMENT PRIMARY KEY,
    edad INT NOT NULL
);

DROP TABLE IF EXISTS Tarjetas;
CREATE TABLE Tarjetas (
    tarjetaId INT AUTO_INCREMENT PRIMARY KEY,
    nombreTarjeta NVARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS Pasiones;
CREATE TABLE Pasiones (
    pasionId INT AUTO_INCREMENT PRIMARY KEY,
    nombrePasion NVARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS Salarios;
CREATE TABLE Salarios (
    salarioId INT AUTO_INCREMENT PRIMARY KEY,
    monto DOUBLE NOT NULL
);

DROP TABLE IF EXISTS Perfiles;
CREATE TABLE Perfiles (
    pasionId INT NOT NULL ,
    salarioMinId INT NOT NULL,
    salarioMaxId INT NOT NULL,
    edadMinId INT NOT NULL,
    edadMaxId INT NOT NULL,
    tarjetaId INT NOT NULL,
    FOREIGN KEY (pasionId) REFERENCES Pasiones (pasionId),
    FOREIGN KEY (salarioMinId) REFERENCES Salarios (salarioId),
    FOREIGN KEY (salarioMaxId) REFERENCES Salarios (salarioId),
    FOREIGN KEY (edadMinId) REFERENCES Edades (edadId),
    FOREIGN KEY (edadMaxId) REFERENCES Edades (edadId),
    FOREIGN KEY (tarjetaId) REFERENCES Tarjetas (tarjetaId)
);
