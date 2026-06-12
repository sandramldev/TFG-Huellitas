-- Usuario Oracle DataBase

        -- 1. Crear usuario
        CREATE USER huellitas
        IDENTIFIED BY xxxx -- Clave personal
        DEFAULT TABLESPACE users
        TEMPORARY TABLESPACE temp
        QUOTA UNLIMITED ON users;

        -- 2. Dar permisos b谩sicos
        GRANT CONNECT, RESOURCE TO huellitas;

        -- 3. Permisos extra para desarrollo
        GRANT CREATE VIEW, CREATE SEQUENCE TO huellitas;

-- Tablas de la base de datos Huellitas
CREATE TABLE personas(
persona_id NUMBER GENERATED ALWAYS AS IDENTITY,
nombre VARCHAR2(25) NOT NULL,
apellidos VARCHAR2(50),
telefono VARCHAR2(15),
email VARCHAR2(100) UNIQUE NOT NULL,
CONSTRAINT PK_PERSONAS_PERSONA_ID PRIMARY KEY (persona_id)
);

CREATE TABLE roles_usuarios (
rol_usuario_id NUMBER GENERATED ALWAYS AS IDENTITY,
rol_nombre VARCHAR2(25) NOT NULL UNIQUE,
CONSTRAINT PK_ROLES_USUARIOS_ROL_ID PRIMARY KEY (rol_usuario_id)
);

CREATE TABLE usuarios (
usuario_id NUMBER GENERATED ALWAYS AS IDENTITY,
persona_id NUMBER UNIQUE NOT NULL,
rol_usuario_id NUMBER NOT NULL,
password VARCHAR2(255) NOT NULL,
CONSTRAINT PK_USUARIOS_USUARIO_ID PRIMARY KEY (usuario_id),
CONSTRAINT FK_PERSONAS_PERSONA_ID FOREIGN KEY (persona_id) 
REFERENCES personas(persona_id)
ON DELETE CASCADE,
CONSTRAINT FK_ROLES_USUARIOS_ROL_USUARIO_ID FOREIGN KEY (rol_usuario_id)
REFERENCES roles_usuarios (rol_usuario_id)
);
/*
En la clave for谩nea FK_ROLES_USUARIOS_ROL_USUARIO_ID no se especifica ninguna acci贸n ON DELETE,  Oracle aplica el comportamiento por defecto (RESTRICT),
impidiendo la eliminaci贸n de un rol que est茅 asociado a usuarios. De este modo se evita la eliminaci贸n accidental de usuarios.
*/


CREATE TABLE clientes(
cliente_id NUMBER GENERATED ALWAYS AS IDENTITY,
persona_id NUMBER UNIQUE NOT NULL,
CONSTRAINT PK_CLIENTES_CLIENTE_ID PRIMARY KEY (cliente_id),
CONSTRAINT FK_CLIENTES_PERSONA_ID FOREIGN KEY (persona_id)
REFERENCES personas(persona_id)
ON DELETE CASCADE
);

CREATE TABLE mascotas(
mascota_id NUMBER GENERATED ALWAYS AS IDENTITY,
cliente_id NUMBER NOT NULL,
nombre VARCHAR2(25) NOT NULL,
fecha_nacimiento DATE,
fallecimiento DATE,
CONSTRAINT PK_MASCOTAS_MASCOTA_ID PRIMARY KEY (mascota_id),
CONSTRAINT FK_MASCOTAS_CLIENTE_ID FOREIGN KEY (cliente_id)
REFERENCES clientes (cliente_id)
ON DELETE CASCADE
);

CREATE TABLE citas(
cita_id NUMBER GENERATED ALWAYS AS IDENTITY,
mascota_id NUMBER NOT NULL,
cita_tipo VARCHAR2(25),
fecha_hora TIMESTAMP,
CONSTRAINT PK_CITAS_CITA_ID PRIMARY KEY (cita_id),
CONSTRAINT FK_MASCOTAS_MASCOTA_ID FOREIGN KEY (mascota_id)
REFERENCES mascotas(mascota_id)
ON DELETE CASCADE
);

COMMIT;

-- DESC: Visualizar el contenido de las tablas
desc personas; 
desc roles_usuarios; 
desc usuarios; 
desc clientes; 
desc mascotas; 
desc citas; 

SELECT constraint_name, constraint_type
FROM user_constraints WHERE table_name = 'USUARIOS';


-- Comprobaci贸n del funcionamiento de modelo y FK mediante INSERT manual
 -- INSERT personas
 INSERT INTO personas (nombre, apellidos, telefono, email)
VALUES ('San', 'M','666666666', 'san@gmail.com');

INSERT INTO personas (nombre, apellidos, telefono, email)
VALUES ('Ana', 'L','666666667', 'ana@gmail.com');

  
-- INSERT roles_usuarios 
   INSERT INTO roles_usuarios (rol_nombre) VALUES ('usuario');
INSERT INTO roles_usuarios (rol_nombre) VALUES ('admin');
   
-- INSERT usuarios
    INSERT INTO usuarios (persona_id, rol_usuario_id, password)
    VALUES (1, 1, '$2a$10$HASH_Manual');

-- INSERT clientes
    INSERT INTO clientes (persona_id) VALUES (1);

-- INSERT mascotas: Para las fechas se utiliza TO_DATE para incluir fecha y formato de la fecha
    INSERT INTO mascotas (cliente_id, nombre, fecha_nacimiento, fallecimiento)
    VALUES (1, 'Ross', (TO_DATE('2010-10-10', 'YYYY-MM-DD')), (TO_DATE('2024-8-31', 'YYYY-MM-DD')));

-- INSERT mascotas  
    INSERT INTO mascotas (cliente_id, nombre, fecha_nacimiento, fallecimiento)
    VALUES (1, 'PRUEBA', DATE '2022-01-01', NULL);

 -- INSERT citas: Para la fecha se utiliza TIMESTAMP para datos de fecha y hora m谩s detallados         
    INSERT INTO citas (mascota_id, cita_tipo, fecha_hora) 
    VALUES (1, 'Vacunaci贸n', TIMESTAMP '2026-12-05 10:30:00'); 

        
-- Consulta relacional entre clientes y mascotas con JOIN 
SELECT p.nombre AS persona, m.nombre AS mascota
FROM personas p JOIN clientes c 
ON p.persona_id = c.persona_id 
JOIN mascotas m ON c.cliente_id = m.cliente_id; 
        
-- TRUNCATE: Borrar contenido de las tablas teniendo en cuenta las tablas hijas
TRUNCATE TABLE citas;
TRUNCATE TABLE mascotas;
TRUNCATE TABLE clientes; 
TRUNCATE TABLE usuarios; 
TRUNCATE TABLE personas; 
TRUNCATE TABLE roles_usuarios; 
            
-- Reiniciar id tablas despu茅s de un borrado de datos (TRUNCATE) 
ALTER TABLE citas MODIFY cita_id GENERATED AS IDENTITY RESTART START WITH 1;
ALTER TABLE mascotas MODIFY mascota_id GENERATED AS IDENTITY RESTART START WITH 1;
ALTER TABLE clientes MODIFY cliente_id GENERATED AS IDENTITY RESTART START WITH 1;
ALTER TABLE usuarios MODIFY usuario_id GENERATED AS IDENTITY RESTART START WITH 1;
ALTER TABLE personas MODIFY persona_id GENERATED AS IDENTITY RESTART START WITH 1;
ALTER TABLE roles_usuarios MODIFY rol_usuario_id GENERATED AS IDENTITY RESTART START WITH 1;
            
-- DROP: Borrar total de la tabla y su contenido teniendo en cuenta las tablas hijas 
DROP TABLE citas; 
DROP TABLE mascotas;
DROP TABLE clientes; 
DROP TABLE usuarios; 
DROP TABLE personas; 
DROP TABLE roles_usuarios;


-- Ver el contenido de las tablas 
select *from personas;
select *from roles_usuarios; 
select *from usuarios;
select *from clientes; 
select *from mascotas; 
select *from citas;

-- Incluir password en la tabla usuarios
/*ALTER TABLE usuarios
ADD password VARCHAR(255) NOT NULL;*/


-- Secuencia de registro
/*En el desarrollo del proyecto, cada nuevo registro de la entidad Personas obtiene su identificador a trav閟 de una secuencia (PERSONAS_SEQ.NEXTVAL).
Es importante destacar que las secuencias no garantizan valores consecutivos, ya que:
No reutilizan identificadores eliminados.
Pueden reservar bloques de valores para mejorar el rendimiento.
No se ven afectadas por operaciones DELETE sobre la tabla.
Por este motivo, al eliminar un registro y crear uno nuevo, el identificador asignado puede presentar saltos num閞icos.
Este comportamiento es intencionado y correcto, y no supone ning鷑 problema funcional ni de integridad, ya que el objetivo principal del identificador es asegurar la unicidad del registro y no su continuidad num閞ica.
*/
SELECT sequence_name, cache_size, last_number
FROM user_sequences;

