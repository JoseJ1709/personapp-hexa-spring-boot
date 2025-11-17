USE persona_db;
INSERT INTO profesion (id, nom, des)
VALUES
     (1, 'Ingeniero de Software', 'Desarrollo de aplicaciones y sistemas'),
     (2, 'Médico General', 'Atención primaria en salud'),
     (3, 'Arquitecto', 'Diseño y construcción de edificaciones'),
     (4, 'Contador Público', 'Gestión contable y financiera'),
     (5, 'Abogado', 'Asesoría legal y representación judicial');

INSERT INTO
    `persona_db`.`persona`(`cc`,`nombre`,`apellido`,`genero`,`edad`)
VALUES
    (123456789,'Pepe','Perez','M',30),
    (987654321,'Pepito','Perez','M',null),
    (321654987,'Pepa','Juarez','F',30),
    (147258369,'Pepita','Juarez','F',10),
    (963852741,'Fede','Perez','M',18);

INSERT INTO telefono (num, oper, duenio)
VALUES
     ('3001234567', 'Claro', 123456789),       -- Teléfono de Pepe
     ('3109876543', 'Movistar', 987654321),    -- Teléfono de Pepito
     ('3204567890', 'Tigo', 321654987),        -- Teléfono de Pepa
     ('3157891234', 'Claro', 147258369),       -- Teléfono de Pepita
     ('3006549876', 'Movistar', 963852741);    -- Teléfono de Fede

INSERT INTO estudios (id_prof, cc_per, fecha, univer) VALUES
      (1, 123456789, '2015-06-15', 'Universidad Nacional'),      -- Pepe estudió Ing. Software
      (2, 987654321, '2018-12-10', 'Universidad Javeriana'),     -- Pepito estudió Medicina
      (3, 321654987, '2012-11-20', 'Universidad de los Andes'),  -- Pepa estudió Arquitectura
      (4, 147258369, '2019-05-25', 'Universidad del Rosario'),   -- Pepita estudió Contaduría
      (5, 963852741, '2016-08-30', 'Universidad Externado');     -- Fede estudió Derecho
