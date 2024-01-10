-- --------------------------------------------------------------------
--																	  -
--				UNIVERSIDAD TECNOLOGICA EMILIANO ZAPATA				  -
--			 TECNOLOGIAS DE LA INFORMACI�N Y COMUNICACION			  -
--					    	AREA SISTEMAS							  -
--																	  -
--		NOMBRE DEL PROYECTO:		SICSA							  -
--		FECHA DE DESARROLLO:		25 DE SEPTIEMBRE DE 2012		  -
--		EQUIPO RESPONSABLE:			EQUIPO UNO						  -
--		VERSION DEL CODIGO:			V1.02							  -
--		BASADO EN:					SQL SERVER 2008 R2 EXPRESS		  -
--																	  -
--		INTEGRANTES DEL EQUIPO UNO:									  -
--			NOMBRE							RESPONSABLE				  -
--			MAURICIO ZARATE BARRERA			BASE DE DATOS			  -
--			GILBERTO GABRIEL TENORIO PEREZ	DESARROLLO DE APLICACIONES-
--			MIRIAM GOMEZ LOPEZ				INGENIERIA DE SOFTWARE	  -
--			MARTIN HERNANDEZ JUAREZ			-						  -
--			LUIS VALLADARES GONZALEZ	    -                    	  -
--			CARLOS FERNANDO CELADA BELLO	-						  -
--			EFREN ... ...					-						  -
--																	  -
--		DESCRIPCION DEL PROYECTO:									  -
--			EL SISTEMA CONSISTE EN LLEVAR EL CONTROL ACAD�MICO		  -
--			MEDIANTE UN PASE DE LISTA USANDO UNA BASE DE DATOS		  -
--																	  -
--		CONTENIDO DE ESTE ARCHIVO:									  -
--			<> DEFINICIONES DE LA BASE DE DATOS (DDL)				  -										  -
-- --------------------------------------------------------------------
USE master;
--DROP DATABASE sicsa_db;
CREATE DATABASE sicsa_db;
GO
USE sicsa_db ;
GO;
-- -----------------------------------------------------
-- Tabla usuario
-- -----------------------------------------------------
--DROP TABLE  usuario ;

CREATE  TABLE  usuario (
  usuario_id INT PRIMARY KEY IDENTITY ,
  usuario VARCHAR(20) NOT NULL ,
  password VARCHAR(12) NOT NULL ,
  tipo VARCHAR(8) NOT NULL,
  -- PRIMARY KEY (usuario_id) ,
  -- UNIQUE -- INDEX id_Usuario_UNIQUE (usuario_id ASC) ,
  -- UNIQUE -- INDEX Usuario_UNIQUE (usuario ASC) 
  );

INSERT INTO usuario(usuario,password,tipo) VALUES ('ErnestoGarcia','ErnGar','admin');
INSERT INTO usuario(usuario,password,tipo) VALUES ('JuanMu�oz','JuaMu�','profesor');
INSERT INTO usuario(usuario,password,tipo) VALUES ('BernardoHuicochea','BerHui','admin');

--SELECT * FROM usuario;

-- -----------------------------------------------------
-- Tabla profesor
-- -----------------------------------------------------
--DROP TABLE  profesor ;

CREATE  TABLE  profesor (
  profesor_id INT IDENTITY NOT NULL ,
  usuario_id INT NOT NULL ,
  nombre VARCHAR(20) NOT NULL ,
  a_materno VARCHAR(20) NOT NULL ,
  telefono INT NOT NULL ,
  direccion VARCHAR(45) NOT NULL ,
  edad INT NOT NULL ,
  a_paterno VARCHAR(20) NOT NULL ,
  PRIMARY KEY (profesor_id) ,
  -- INDEX fk_Profesor_Usuario1 (usuario_id ASC) ,
  -- UNIQUE -- INDEX id_Profesor_UNIQUE (profesor_id ASC) ,
    CONSTRAINT fk_Profesor_Usuario1
    FOREIGN KEY (usuario_id )
    REFERENCES usuario (usuario_id )
     );
     


INSERT INTO profesor(usuario_id,nombre,a_paterno,a_materno,telefono,direccion,edad) VALUES (1,'Ernesto','Garcia','Alguizar','0','xxxxx','0');
INSERT INTO profesor(usuario_id,nombre,a_paterno,a_materno,telefono,direccion,edad) VALUES (2,'Juan Jesus','Mu�oz','Chavez','0','xxxxx','0');
INSERT INTO profesor(usuario_id,nombre,a_paterno,a_materno,telefono,direccion,edad) VALUES (3,'Bernardo','Huicochea','Naranjo','0','xxxxx','0');

--SELECT * FROM profesor;

-- -----------------------------------------------------
-- Tabla cuatrimestre
-- -----------------------------------------------------
-- DROP TABLE  cuatrimestre ;

CREATE  TABLE  cuatrimestre (
  cuatrimestre_id INT IDENTITY ,
  nivel INT NOT NULL ,
  PRIMARY KEY (cuatrimestre_id) );

INSERT INTO cuatrimestre(nivel) VALUES (3);
INSERT INTO cuatrimestre(nivel) VALUES (4);
INSERT INTO cuatrimestre(nivel) VALUES (6);
INSERT INTO cuatrimestre(nivel) VALUES (0);

--SELECT * FROM cuatrimestre;

-- -----------------------------------------------------
-- Tabla materia
-- -----------------------------------------------------
-- DROP TABLE  materia ;

CREATE  TABLE  materia (
  materia_id INT IDENTITY ,
  nombre VARCHAR(45) NOT NULL ,
  cuatrimestre_id INT NOT NULL ,
  PRIMARY KEY (materia_id) ,
  -- UNIQUE -- INDEX Nombre_UNIQUE (nombre ASC) ,
  -- INDEX fk_Materia_Cuatrimestre1 (cuatrimestre_id ASC) ,
    CONSTRAINT fk_Materia_Cuatrimestre1
    FOREIGN KEY (cuatrimestre_id )
    REFERENCES cuatrimestre (cuatrimestre_id )  
    );
    
    
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Apoyo CDS',4);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Tutoria',3);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Desarrollo de Aplicaciones II',2);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Desarrollo de Aplicaciones II',3);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Ingenier�a del Software',2);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Administraci�n de Proyectos',3);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Administraci�n de Bases de Datos',2);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Bases de Datos II',1);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Apoyo Area Sistemas',4);
INSERT INTO materia(nombre,cuatrimestre_id) VALUES ('Proyecto CDS',4);

--SELECT * FROM materia


-- -----------------------------------------------------
-- Tabla periodo
-- -----------------------------------------------------
-- DROP TABLE  periodo ;

CREATE  TABLE  periodo (
  periodo_id INT IDENTITY , --COMMENT 'Llave primaria de un periodo seccional. (ej 2011 - 2014)' ,
  descripcion VARCHAR(45) NOT NULL , --COMMENT 'Descripción del periodo' ,
  fecha_ini VARCHAR(45) NULL , --COMMENT 'Fecha de inicio del periodo' ,
  fecha_vig VARCHAR(45) NULL , --COMMENT 'Fecha de terminación del periodo' ,
  PRIMARY KEY (periodo_id) ,
  -- UNIQUE -- INDEX Descripcion_UNIQUE (descripcion ASC) 
  );
  
  select * from periodo
  INSERT INTO periodo(descripcion,fecha_ini,fecha_vig) VALUES ('Septiembre-Diciembre','41148','41257');

--SELECT * FROM periodo

-- -----------------------------------------------------
-- Tabla horarios
-- -----------------------------------------------------
-- DROP TABLE  horarios ;

CREATE  TABLE  horarios (
  horarios_id INT IDENTITY ,
  periodo_id INT NOT NULL ,
  profesor_id INT NOT NULL ,
  fecha DATE NOT NULL ,
  PRIMARY KEY (horarios_id) ,
  -- INDEX fk_Horario_Periodo1 (periodo_id ASC) ,
  -- INDEX fk_Horario_Profesor1 (profesor_id ASC) ,
  -- UNIQUE -- INDEX Profesor_id_UNIQUE (profesor_id ASC) ,
    CONSTRAINT fk_Horario_Periodo1
    FOREIGN KEY (periodo_id )
    REFERENCES periodo (periodo_id ),
    CONSTRAINT fk_Horario_Profesor1
    FOREIGN KEY (profesor_id )
    REFERENCES profesor (profesor_id )
    
    );
select * from horarios
INSERT INTO horarios(periodo_id,profesor_id,fecha) VALUES (1,1,'2012-01-01');
INSERT INTO horarios(periodo_id,profesor_id,fecha) VALUES (1,2,'2012-01-01');
INSERT INTO horarios(periodo_id,profesor_id,fecha) VALUES (1,3,'2012-01-01');



-- -----------------------------------------------------
-- Tabla carrera
-- -----------------------------------------------------
-- DROP TABLE  carrera ;

CREATE  TABLE  carrera (
  carrera_id INT IDENTITY ,
  nombre VARCHAR(45) NOT NULL ,
  PRIMARY KEY (carrera_id) ,
  -- UNIQUE -- INDEX id_Carrera_UNIQUE (carrera_id ASC) 
  );

INSERT INTO carrera (nombre) VALUES ('Tecnolog�as de la Informaci�n y Comunicaci�n');

--SELECT * FROM carrera

-- -----------------------------------------------------
-- Tabla especialidad
-- -----------------------------------------------------
-- DROP TABLE  especialidad ;

CREATE  TABLE  especialidad (
  especialidad_id INT IDENTITY ,
  nombre VARCHAR(45) NOT NULL ,
  carrera_id INT NOT NULL ,
  PRIMARY KEY (especialidad_id) ,
  -- UNIQUE -- INDEX id_Especialidad_UNIQUE (especialidad_id ASC) ,
  -- UNIQUE -- INDEX Nombre_UNIQUE (nombre ASC) ,
  -- INDEX fk_Especialidad_Carrera1 (carrera_id ASC) ,
    CONSTRAINT fk_Especialidad_Carrera1
    FOREIGN KEY (carrera_id )
    REFERENCES carrera (carrera_id )
    
    );
    
    INSERT INTO especialidad (nombre,carrera_id) VALUES ('Sistemas Informaticos',1);

--SELECT * FROM especialidad



-- -----------------------------------------------------
-- Tabla grupo
-- -----------------------------------------------------
-- DROP TABLE  grupo ;

CREATE  TABLE  grupo (
  grupo_id INT IDENTITY ,
  grado INT NOT NULL ,
  grupo VARCHAR(5) NOT NULL ,
  PRIMARY KEY (grupo_id) );

INSERT INTO grupo (grado,grupo) VALUES (3,'A');
INSERT INTO grupo (grado,grupo) VALUES (4,'A');
INSERT INTO grupo (grado,grupo) VALUES (6,'IIE');
INSERT INTO grupo (grado,grupo) VALUES (0,'X');


SELECT * FROM grupo

-- -----------------------------------------------------
-- Tabla edificio
-- -----------------------------------------------------
-- DROP TABLE  edificio ;

CREATE  TABLE  edificio (
  edificio_id INT IDENTITY ,
  descripcion VARCHAR(15) NOT NULL ,
  PRIMARY KEY (edificio_id) );

INSERT INTO edificio(descripcion) VALUES ('CEVISET');
INSERT INTO edificio(descripcion) VALUES ('Docencia 1');
INSERT INTO edificio(descripcion) VALUES ('CECADEC');
INSERT INTO edificio(descripcion) VALUES ('Docencia 4');

--SELECT * FROM edificio

-- -----------------------------------------------------
-- Tabla aula
-- -----------------------------------------------------
-- DROP TABLE  aula ;

CREATE  TABLE  aula (
  aula_id INT IDENTITY ,
  descripcion VARCHAR(15) NOT NULL ,
  PRIMARY KEY (aula_id) );


INSERT INTO aula(descripcion) VALUES ('aula 1');
INSERT INTO aula(descripcion) VALUES ('CC2');
INSERT INTO aula(descripcion) VALUES ('CI');
INSERT INTO aula(descripcion) VALUES ('CA 2');
INSERT INTO aula(descripcion) VALUES ('CA 1');
INSERT INTO aula(descripcion) VALUES ('aula 3');
INSERT INTO aula(descripcion) VALUES ('CEVISET');

--SELECT * FROM aula


-- -----------------------------------------------------
-- Tabla ubicacion
-- -----------------------------------------------------
-- DROP TABLE  ubicacion ;

CREATE  TABLE  ubicacion (
  ubicacion_id INT IDENTITY ,
  edificio_id INT NOT NULL ,
  aula_id INT NOT NULL ,
  PRIMARY KEY (ubicacion_id) ,
  -- INDEX fk_Ubicacion_Edificio1 (edificio_id ASC) ,
  -- INDEX fk_Ubicacion_Aula1 (aula_id ASC) ,
  -- UNIQUE -- INDEX id_Edificio_UNIQUE (edificio_id ASC) ,
  -- UNIQUE -- INDEX id_Aula_UNIQUE (aula_id ASC) ,
    CONSTRAINT fk_Ubicacion_Edificio1
    FOREIGN KEY (edificio_id )
    REFERENCES edificio (edificio_id )
    
    ,
    CONSTRAINT fk_Ubicacion_Aula1
    FOREIGN KEY (aula_id )
    REFERENCES aula (aula_id )
    
    );
    
    
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (1,7);
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (2,1);
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (3,3);
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (2,2);
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (4,4);
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (4,5);
INSERT INTO ubicacion(edificio_id,aula_id) VALUES (2,6);

--SELECT * FROM ubicacion



-- -----------------------------------------------------
-- Tabla clase
-- -----------------------------------------------------
-- DROP TABLE  clase ;

CREATE  TABLE  clase (
  clase_id INT IDENTITY ,
  nombre VARCHAR(45) NOT NULL ,
  horario_id INT NOT NULL ,
  ubicacion_id INT  NOT NULL ,
  materia_id INT NOT NULL ,
  grupo_id INT NOT NULL ,
  hora_inicio TIME NOT NULL ,
  hora_fin TIME NOT NULL ,
  dia VARCHAR(10) NOT NULL ,
  PRIMARY KEY (clase_id) ,
  -- INDEX fk_Clase_Horario1 (horario_id ASC) ,
  -- INDEX fk_Clase_Ubicacion1 (ubicacion_id ASC) ,
  -- INDEX fk_Clase_Materia1 (materia_id ASC) ,
  -- INDEX fk_Clase_Grupo1 (grupo_id ASC) ,
  -- UNIQUE -- INDEX id_Clase_UNIQUE (clase_id ASC) ,
    CONSTRAINT fk_Clase_Horario1
    FOREIGN KEY (horario_id )
    REFERENCES horarios (horarios_id ),
    CONSTRAINT fk_Clase_Ubicacion1
    FOREIGN KEY (ubicacion_id )
    REFERENCES ubicacion (ubicacion_id ),
    CONSTRAINT fk_Clase_Materia1
    FOREIGN KEY (materia_id )
    REFERENCES materia (materia_id ),
    CONSTRAINT fk_Clase_Grupo1
    FOREIGN KEY (grupo_id )
    REFERENCES grupo (grupo_id )
    );
    
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Bases de Datos',1,6,7,2,'8:00','9:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Bases de Datos',1,6,7,2,'9:00','10:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Tutoria',1,2,2,2,'10:00','11:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'11:00','12:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'12:00','13:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'13:00','14:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo Area Sistemas',1,1,9,4,'14:00','15:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo Area Sistemas',1,1,9,4,'15:00','16:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'8:00','9:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'9:00','10:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Bases de Datos',1,5,7,2,'10:00','11:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Bases de Datos',1,5,7,2,'11:00','12:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo Area Sistemas',1,1,9,4,'12:00','13:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo Area Sistemas',1,1,9,4,'13:00','14:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo Area Sistemas',1,1,9,4,'14:00','15:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'15:00','16:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Bases de Datos',1,6,7,2,'8:00','9:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Bases de Datos',1,6,7,2,'9:00','10:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'10:00','11:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'11:00','12:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'12:00','13:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'13:00','14:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'8:00','9:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Bases de Datos II',1,4,8,1,'9:00','10:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'10:00','11:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'11:00','12:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'12:00','13:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'13:00','14:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'14:00','15:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Proyeto CDS',1,1,10,4,'15:00','16:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'8:00','9:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'9:00','10:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'10:00','11:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'11:00','12:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'12:00','13:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'13:00','14:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'14:00','15:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'8:00','9:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'9:00','10:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'10:00','11:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'11:00','12:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'12:00','13:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'13:00','14:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Tutoria',2,2,2,3,'14:00','15:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'8:00','9:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'9:00','10:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'10:00','11:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'11:00','12:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,3,2,'12:00','13:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,3,2,'13:00','14:00','Miercoles');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'8:00','9:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'9:00','10:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,3,3,2,'10:00','11:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,3,3,2,'11:00','12:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'12:00','13:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'13:00','14:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'14:00','15:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,4,3,'15:00','16:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,4,3,'16:00','17:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,4,3,'17:00','18:00','Jueves');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'8:00','9:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'9:00','10:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'10:00','11:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,3,4,2,'11:00','12:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,3,4,2,'12:00','13:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,3,4,2,'13:00','14:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Apoyo CDS',2,1,1,4,'14:00','15:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,4,3,'15:00','16:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,4,3,'16:00','17:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Desarrollo de Aplicaciones II',2,4,4,3,'17:00','18:00','Viernes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Ingenieria del Software',3,5,5,2,'13:00','14:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Ingenieria del Software',3,5,5,2,'14:00','15:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Ingenieria del Software',3,5,5,2,'15:00','16:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Proyectos',3,6,6,3,'17:00','18:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Proyectos',3,6,6,3,'18:00','19:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Proyectos',3,6,6,3,'19:00','20:00','Lunes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Ingenieria del Software',3,6,5,2,'12:00','13:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Ingenieria del Software',3,6,5,2,'13:00','14:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Proyectos',3,7,6,3,'15:00','16:00','Martes');
INSERT INTO clase (nombre,horario_id,ubicacion_id,materia_id,grupo_id,hora_inicio,hora_fin,dia) VALUES ('Administracion de Proyectos',3,7,6,3,'17:00','18:00','Martes');


-- -----------------------------------------------------
-- Tabla carrera_profesor
-- -----------------------------------------------------
--DROP TABLE  carrera_profesor ;

CREATE  TABLE  carrera_profesor (
  carrera_profesor_id INT IDENTITY NOT NULL ,
  carrera_id INT NOT NULL ,
  profesor_id INT NOT NULL ,
  PRIMARY KEY (carrera_profesor_id) ,
  -- INDEX fk_Carrera_has_Profesor_Profesor1 (profesor_id ASC) ,
  -- INDEX fk_Carrera_has_Profesor_Carrera1 (carrera_id ASC) ,
  -- UNIQUE -- INDEX Prof_carr_id_UNIQUE (carrera_profesor_id ASC) ,
    CONSTRAINT fk_Carrera_has_Profesor_Carrera1
    FOREIGN KEY (carrera_id )
    REFERENCES carrera (carrera_id )
    ,
    CONSTRAINT fk_Carrera_has_Profesor_Profesor1
    FOREIGN KEY (profesor_id )
    REFERENCES profesor (profesor_id )
        );
    
    
    INSERT INTO carrera_profesor(carrera_id,profesor_id) VALUES (1,1);
	INSERT INTO carrera_profesor(carrera_id,profesor_id) VALUES (1,2);
	INSERT INTO carrera_profesor(carrera_id,profesor_id) VALUES (1,3);

--SELECT * FROM carrera_profesor



-- -----------------------------------------------------
-- Tabla especialidad_materia
-- -----------------------------------------------------
--DROP TABLE  especialidad_materia ;

CREATE  TABLE  especialidad_materia (
	especialidad_materia_id INT IDENTITY NOT NULL ,
  especialidad_id INT  ,
  materia_id INT NOT NULL ,
    -- INDEX fk_Especialidad_has_Materia_Materia1 (materia_id ASC) ,
  -- INDEX fk_Especialidad_has_Materia_Especialidad1 (especialidad_id ASC) ,
  PRIMARY KEY (especialidad_materia_id) ,
    CONSTRAINT fk_Especialidad_has_Materia_Especialidad1
    FOREIGN KEY (especialidad_id )
    REFERENCES especialidad (especialidad_id )  
    ,
    CONSTRAINT fk_Especialidad_has_Materia_Materia1
    FOREIGN KEY (materia_id )
    REFERENCES materia (materia_id )
        );
    
    
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,1);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,2);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,3);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,4);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,5);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,6);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,7);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,8);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,9);
INSERT INTO especialidad_materia (especialidad_id,materia_id) VALUES (1,10);

--SELECT * FROM especialidad_materia 


-- -----------------------------------------------------
-- Tabla grupo_carrera
-- -----------------------------------------------------
-- DROP TABLE  grupo_carrera ;

CREATE  TABLE  grupo_carrera (
  grupo_carrera_id INT IDENTITY NOT NULL ,
  grupo_id INT NOT NULL ,
  carrera_id INT NOT NULL ,
  especialidad_id INT NOT NULL ,
    PRIMARY KEY (grupo_carrera_id) ,
  -- INDEX fk_Grupo_has_Carrera_Carrera1 (carrera_id ASC) ,
  -- INDEX fk_Grupo_has_Carrera_Grupo1 (grupo_id ASC) ,
  -- INDEX fk_Grupo_has_Carrera_Especialidad1 (especialidad_id ASC) ,
    CONSTRAINT fk_Grupo_has_Carrera_Grupo1
    FOREIGN KEY (grupo_id )
    REFERENCES grupo (grupo_id )
    
    ,
    CONSTRAINT fk_Grupo_has_Carrera_Carrera1
    FOREIGN KEY (carrera_id )
    REFERENCES carrera (carrera_id )
    
    ,
    CONSTRAINT fk_Grupo_has_Carrera_Especialidad1
    FOREIGN KEY (especialidad_id )
    REFERENCES especialidad (especialidad_id )
    
    );
    
INSERT INTO grupo_carrera (grupo_id,carrera_id,especialidad_id) VALUES (1,1,1);
INSERT INTO grupo_carrera (grupo_id,carrera_id,especialidad_id) VALUES (2,1,1);
INSERT INTO grupo_carrera (grupo_id,carrera_id,especialidad_id) VALUES (3,1,1);



--SELECT * FROM grupo_carrera


-- -----------------------------------------------------
-- Tabla unidades
-- -----------------------------------------------------
-- DROP TABLE  unidades ;

CREATE  TABLE  unidades (
  unidades_id INT IDENTITY , --COMMENT 'Identificador de la unidad de una materia' ,
  fecha_inicio DATETIME NULL , --COMMENT 'Fecha donde comienza una unidad de una materia' ,
  parcial INT NULL , --COMMENT 'Nombre de la unidad o parcial de una unidad' ,
  materia_id INT NOT NULL , --COMMENT 'Referencia de una materia' ,
  PRIMARY KEY (unidades_id) ,
  -- INDEX fk_Unidades_Materia1 (materia_id ASC) ,
    CONSTRAINT fk_Unidades_Materia1
    FOREIGN KEY (materia_id )
    REFERENCES materia (materia_id )
    
    );
    
    



-- -----------------------------------------------------
-- Tabla lista_asistencia
-- -----------------------------------------------------
--DROP TABLE  lista_asistencia ;

CREATE  TABLE  lista_asistencia (
  lista_asistencia_id INT IDENTITY , --COMMENT 'Llave primaria de la lista de asistencia de una clase de un profesor' ,
  carrera_id INT NOT NULL , --COMMENT 'Referencia de la carrera de una lista de asistencia' ,
  materia_id INT NOT NULL , --COMMENT 'Referencia de la materia de esa lista de asistencia' ,
  periodo_id INT NOT NULL , --COMMENT 'Referencia del periodo en el que se toma una lista de asistencia' ,
  profesor_id INT NOT NULL , --COMMENT 'Referencia del profesor responsable de la lista de asistencia' ,
  grupo_id INT NOT NULL , --COMMENT 'Referencia del grupo al que se le asigna la lista de asistencia' ,
  horas_por_semana INT NOT NULL DEFAULT 0 , --COMMENT 'Número de horas que son impartidas de esta materia a un grupo' ,
  PRIMARY KEY (lista_asistencia_id) ,
  -- INDEX fk_ListaAsistencia_Materia1 (materia_id ASC) ,
  -- INDEX fk_ListaAsistencia_Periodo1 (periodo_id ASC) ,
  -- INDEX fk_ListaAsistencia_Profesor1 (profesor_id ASC) ,
  -- INDEX fk_ListaAsistencia_Grupo1 (grupo_id ASC) ,
  -- INDEX fk_ListaAsistencia_Carrera1 (carrera_id ASC) ,
    CONSTRAINT fk_ListaAsistencia_Materia1
    FOREIGN KEY (materia_id )
    REFERENCES materia (materia_id )
    
    ,
    CONSTRAINT fk_ListaAsistencia_Periodo1
    FOREIGN KEY (periodo_id )
    REFERENCES periodo (periodo_id )
    
    ,
    CONSTRAINT fk_ListaAsistencia_Profesor1
    FOREIGN KEY (profesor_id )
    REFERENCES profesor (profesor_id )
    
    ,
    CONSTRAINT fk_ListaAsistencia_Grupo1
    FOREIGN KEY (grupo_id )
    REFERENCES grupo (grupo_id )
    
    ,
    CONSTRAINT fk_ListaAsistencia_Carrera1
    FOREIGN KEY (carrera_id )
    REFERENCES carrera (carrera_id )
    
    );
    
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,1,1,2,4,26);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,2,1,1,2,1);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,2,1,2,3,1);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,3,1,2,2,7);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,4,1,2,3,6);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,5,1,3,2,5);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,6,1,3,3,5);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,7,1,1,2,6);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,8,1,1,1,7);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,9,1,1,4,5);
INSERT INTO lista_asistencia (carrera_id,materia_id,periodo_id,profesor_id,grupo_id,horas_por_semana) VALUES (1,10,1,1,4,11);


--SELECT * FROM lista_asistencia

-- -----------------------------------------------------
-- Tabla tipos_estado_asistencia
-- -----------------------------------------------------
-- DROP TABLE  tipos_estado_asistencia ;

CREATE  TABLE  tipos_estado_asistencia (
  tipos_estado_asistencia_id INT IDENTITY , --COMMENT 'Llave primaria de los tipos de estado de asistencia de la tabla asistencia.' ,
  estado CHAR NULL , --COMMENT 'Tipos de estado de asistencia' ,
  descripcion VARCHAR(50) NULL , --COMMENT 'Descripción del tipo de estado que se mostrará en un globo de información al pasar el mouse sobre el tipo de estado en la interface.' ,
  PRIMARY KEY (tipos_estado_asistencia_id) ,
  -- UNIQUE -- INDEX Estado_UNIQUE (estado ASC) 
  );
  
  
INSERT INTO tipos_estado_asistencia(estado,descripcion) VALUES ('.','Asistencia');
INSERT INTO tipos_estado_asistencia(estado,descripcion) VALUES ('/','Falta');
INSERT INTO tipos_estado_asistencia(estado,descripcion) VALUES ('X','Retardo');
INSERT INTO tipos_estado_asistencia(estado,descripcion) VALUES ('J','Justificaci�n');


--SELECT * FROM tipos_estado_asistencia



-- -----------------------------------------------------
-- Tabla alumno
-- -----------------------------------------------------
-- DROP TABLE  alumno ;

CREATE  TABLE  alumno (
  alumno_id INT IDENTITY , --COMMENT 'Llave primaria del alumno' ,
  nombre VARCHAR(50) NOT NULL , --COMMENT 'Nombre del alumno' ,
  a_paterno VARCHAR(45) NOT NULL , --COMMENT 'Apellido paterno del alumno' ,
  a_materno VARCHAR(45) NOT NULL , --COMMENT 'Apellido materno del alumno' ,
  matricula VARCHAR(45) NOT NULL , --COMMENT 'matrícula de identificación de la institución del alumno' ,
  carrera_id INT NOT NULL , --COMMENT 'Referencia de la carrera única del alumno' ,
  PRIMARY KEY (alumno_id) ,
  -- UNIQUE -- INDEX Matricula_UNIQUE (matricula ASC) ,
  -- INDEX fk_Alumno_Carrera1 (carrera_id ASC) ,
    CONSTRAINT fk_Alumno_Carrera1
    FOREIGN KEY (carrera_id )
    REFERENCES carrera (carrera_id )
    
    );
    
    
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Victor Daniel','Anica','Vazquez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Alely','Antonio','Naranjo','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Angel','Ayala','Razo','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Carlos Fernando','Celada ','Bello','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Abigail','Cossio','Isidro','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Aaron','Delgado','Vilchis','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Maria Isabel','Dirsio','Sanchez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Nestor Israel','Esquivel','Martinez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Miguel Adrian','Gabriel','Morales','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Israel','Garcia','Gomez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Efren','Garcia','Zagal','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Rosa Maria','Gomez ','Acosta','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Miriam','Gomez ','Lopez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Gerardo','Gonzales','Moreno','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Julio Cesar','Hernandez','De La Torre','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Martin','Hernandez','Juarez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Miguel Antonio','Martinez','Quintero','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Miriam','Mateos','Medina','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Celma Patricia','Nu�ez','Espada','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Gabriela','Rodriguez','Salgado','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Enrique Jair','Rojas','Martinez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Gilberto Gabriel','Tenorio','Perez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Luis','Valladares','Gonzalez','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Julio Cesar','Vera','Segura','xxxxx',1);
INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) VALUES ('Mauricio','Zarate','Barrera','xxxxx',1);

--SELECT * FROM alumno


-- -----------------------------------------------------
-- Tabla historial_alumno
-- -----------------------------------------------------
-- DROP TABLE  historial_alumno ;

CREATE  TABLE  historial_alumno (
  historial_alumno_id INT IDENTITY NOT NULL , --COMMENT 'Llave primaria de los historiales de un alumno' ,
  alumno_id INT NOT NULL , --COMMENT 'Referencia de un alumno' ,
  grupo_carrera_id INT NOT NULL , --COMMENT 'Referencia del grupo_carrera de un alumno' ,
  periodo_id INT NOT NULL , --COMMENT 'Periodo o generación en el que se encuentra un alumno' ,
  PRIMARY KEY (historial_alumno_id) ,
  -- INDEX fk_Alumno_has_Cuatrimestre_Alumno1 (alumno_id ASC) ,
  -- INDEX fk_HistorialAlumno_Grupo1 (grupo_id ASC) ,
  -- INDEX fk_HistorialAlumno_Periodo1 (periodo_id ASC) ,
    CONSTRAINT fk_Alumno_has_Cuatrimestre_Alumno1
    FOREIGN KEY (alumno_id )
    REFERENCES alumno (alumno_id )
    ,
    CONSTRAINT fk_HistorialAlumno_Grupo1
    FOREIGN KEY (grupo_carrera_id )
    REFERENCES grupo_carrera (grupo_carrera_id )
    ,
    CONSTRAINT fk_HistorialAlumno_Periodo1
    FOREIGN KEY (periodo_id )
    REFERENCES periodo (periodo_id )
    );
    
    
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (1,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (2,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (3,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (4,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (5,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (6,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (7,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (8,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (9,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (10,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (11,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (12,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (13,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (14,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (15,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (16,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (17,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (18,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (19,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (20,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (21,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (22,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (23,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (24,1,2);
INSERT INTO historial_alumno (alumno_id,periodo_id,grupo_carrera_id) VALUES (25,1,2);


--SELECT * FROM historial_alumno



-- -----------------------------------------------------
-- Tabla asistencia
-- -----------------------------------------------------
-- DROP TABLE  asistencia ;

CREATE  TABLE  asistencia (
  asistencia_id INT IDENTITY , --COMMENT 'Llave primaria de una asistencia al día de un alumno, contenido de una lista de asistencia.' ,
  lista_asistencia_id INT NOT NULL , --COMMENT 'Referencia padre de la lista de asistencia' ,
  fecha DATE NOT NULL , --COMMENT 'Fecha de la asistencia tomada' ,
  unidad_id INT NOT NULL , --COMMENT 'Referencia de una asistencia categorizada por unidades o parciales' ,
  estado_id INT NOT NULL , --COMMENT 'Valor obtenido del estado de la asistencia de un alumno' ,
  historial_alumno_id INT NOT NULL , --COMMENT 'Referencia del historial de un alumno con relación a sus asistencias' ,
  PRIMARY KEY (asistencia_id) ,
  -- INDEX fk_Asistencia_ListaAsistencia1 (lista_asistencia_id ASC) ,
  -- INDEX fk_Asistencia_TiposEstadoAsistencia1 (estado_id ASC) ,
  -- INDEX fk_Asistencia_Unidades1 (unidad_id ASC) ,
  -- INDEX fk_Asistencia_HistorialAlumno1 (historial_alumno_id ASC) ,
    CONSTRAINT fk_Asistencia_ListaAsistencia1
    FOREIGN KEY (lista_asistencia_id )
    REFERENCES lista_asistencia (lista_asistencia_id )
    ,
    CONSTRAINT fk_Asistencia_TiposEstadoAsistencia1
    FOREIGN KEY (estado_id )
    REFERENCES tipos_estado_asistencia (tipos_estado_asistencia_id )
    ,
    CONSTRAINT fk_Asistencia_Unidades1
    FOREIGN KEY (unidad_id )
    REFERENCES unidades (unidades_id )
    
    ,
    CONSTRAINT fk_Asistencia_HistorialAlumno1
    FOREIGN KEY (historial_alumno_id )
    REFERENCES historial_alumno (historial_alumno_id )
    
    );
    
       
    
-- ------------------- --
-- Procedimientos CRUD --
-- ------------------- --


--
--alumno
--
     -- alta
     CREATE PROCEDURE pau_alumno_alta
    @nombre VARCHAR(30), 
    @a_paterno VARCHAR(30),
    @a_materno VARCHAR(30),
    @matricula VARCHAR(30),
    @carrera_id INT
    
	AS 
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO alumno (nombre,a_paterno,a_materno,matricula,carrera_id) 
		VALUES (@nombre,@a_paterno,@a_materno,@matricula,@carrera_id);
	END
	-- EXECUTE pau_alumno_alta 'Victor Daniel','Anica','Vazquez','xxxxx',1;
	
	--eliminar
     CREATE PROCEDURE pau_alumno_eliminar
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		DELETE FROM alumno WHERE alumno_id = @id;
     END
     
     -- consulta
     CREATE PROCEDURE pau_alumno_consulta
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		SELECT * FROM alumno WHERE alumno_id = @id;
     END
     -- EXEC pau_alumno_consulta 1;
     
     -- modificaci�n
     CREATE PROCEDURE pau_alumno_modificar
     @id INT,
     @nom VARCHAR (30),
     @a_pate VARCHAR (30),
     @a_mate VARCHAR (30),
     @matril VARCHAR (20),
     @id_carrera INT
   
     AS
   
		UPDATE alumno set nombre=@nom,
		a_paterno=@a_pate,
		a_materno=@a_mate,
		matricula=@matril,
		carrera_id=@id_carrera
	
		WHERE alumno_id = @id;
	--EXECUTE pau_alumno_modificar 2,'MARTIN', 'HER', 'JUA', 'QWER', 1 

--
--profesor
--
	-- alta
	CREATE PROCEDURE pau_profesor_alta
    @usuario_id INT, 
    @nombre VARCHAR(30), 
    @a_paterno VARCHAR(30), 
    @a_materno VARCHAR(30),
    @telefono INT,
    @direccion VARCHAR(30),
    @edad INT
	AS 
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO profesor(usuario_id,nombre,a_paterno,a_materno,telefono,direccion,edad) 
		VALUES (@usuario_id,@nombre,@a_paterno,@a_materno,@telefono,@direccion,@edad);
	END
	GO

	--EXECUTE pau_profesor_alta 1,'Ernesto','Garcia','Alguizar','0','xxxxx','0';
	
	--eliminar
     CREATE PROCEDURE pau_profesor_eliminar
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		DELETE FROM profesor WHERE profesor_id = @id;
     END
     -- consulta
     CREATE PROCEDURE pau_profesor_consulta
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		SELECT * FROM profesor WHERE profesor_id = @id;
     END
     -- EXEC pau_profesor_consulta 1;
     
     -- modificar
     CREATE PROCEDURE pau_profesor_modificar
     @id INT,
     @user_id INT,
     @nom VARCHAR(30),
     @paterno VARCHAR(30),
     @materno VARCHAR(30),
     @tel INT,
     @dire VARCHAR (30),
     @a�os INT
     AS
     BEGIN
     SET NOCOUNT ON;
		UPDATE profesor SET usuario_id=@user_id,
		nombre=@nom,
		a_paterno=@paterno,
		a_materno=@materno,
		telefono=@tel,
		direccion=@dire,
		edad=@a�os
		WHERE profesor_id=@id;
     END
     
--    
--usuario
--
	 -- alta
	 CREATE PROCEDURE pau_usuario_alta
    @usuario VARCHAR(30), 
    @password VARCHAR(30)
    
	AS 
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO usuario(usuario,password)
		VALUES (@usuario,@password);
	END
	GO

	--EXECUTE pau_usuario_alta 'ErnestoGarcia','ErnGar';
	GO
	 	
	 -- eliminar
     CREATE PROCEDURE pau_usuario_eliminar
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		DELETE FROM usuario WHERE usuario_id = @id;
     END
     
     -- consulta
     CREATE PROCEDURE pau_usuario_consulta
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		SELECT * FROM usuario WHERE usuario_id = @id;
     END
     -- EXEC pau_usuario_consulta 1;
     
     -- modificaci�n
     CREATE PROCEDURE pau_usuario_modificar
     @id INT,
     @user VARCHAR(30),
     @pass VARCHAR(15)
     AS
     BEGIN
     SET NOCOUNT ON;
		UPDATE usuario SET usuario=@user,
		password=@pass
		WHERE usuario_id=@id;
     END
     
      --EXECUTE pau_usuario_modificar 2,'gerardo','puto';
     

--
--asignatura/materia
--
	-- alta
	CREATE PROCEDURE pau_materia_alta
    @nombre VARCHAR(30), 
    @cuatrimestre_id INT
    
	AS 
	BEGIN
		SET NOCOUNT ON;
		INSERT INTO materia(nombre,cuatrimestre_id)
		VALUES (@nombre,@cuatrimestre_id);
	END
	GO

--EXECUTE pau_materia_alta 'Apoyo CDS',4;
	
	--eliminar
     CREATE PROCEDURE pau_materia_eliminar
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		DELETE FROM materia WHERE materia_id = @id;
     END
     
      -- consulta
     CREATE PROCEDURE pau_materia_consulta
     @id INT
     AS
     BEGIN
     SET NOCOUNT ON;
		SELECT * FROM materia WHERE materia_id = @id;
     END
     -- EXEC pau_materia_consulta 1;
     
     -- modificaci�n
     CREATE PROCEDURE pau_materia_modificar
     @id INT,
     @nom VARCHAR (30),
     @cuatri INT
     AS
     BEGIN
     SET NOCOUNT ON;
		UPDATE materia SET nombre=@nom,
		cuatrimestre_id=@cuatri
		WHERE materia_id=@id;
		
		
     END
     --EXECUTE pau_materia_modificar 1,'gerardo',3;
     
     -- ------------------------------
     -- TRIGGER USUARIO PROSESOR  
     -- ------------------------------
     --  Crea automaticamente el usuario al registrar un nuevo profesor
     --
     
CREATE TRIGGER du_usuario_profesor
	ON profesor
	INSTEAD OF INSERT
	AS 
		DECLARE @pass VARCHAR(10)
		DECLARE @nombreProf VARCHAR(20)
		DECLARE @a_paterno VARCHAR(20)
		DECLARE @a_materno VARCHAR(20)
		DECLARE @usuario VARCHAR(20)
		DECLARE @tel INT
		DECLARE @edad INT
		DECLARE @dir NVARCHAR(40)
		DECLARE @maxUsuario INT

		SELECT @tel = (SELECT telefono FROM inserted)
		SELECT @edad = (SELECT edad FROM inserted)
		SELECT @dir = (SELECT direccion FROM inserted)
		SELECT @a_paterno = (SELECT a_paterno FROM inserted)
		SELECT @a_materno = (SELECT a_materno FROM inserted)
		SELECT @nombreProf = (SELECT nombre FROM inserted)
		SELECT @pass=(select SUBSTRING(nombre,1,3)+SUBSTRING(a_paterno,1,3) as contrase�a
			  from inserted)
		SELECT @usuario=(SELECT nombre+a_paterno from inserted)			  
		INSERT usuario (usuario,password)
		VALUES (@usuario,@pass)
		SELECT @maxUsuario=(select max(usuario_id) sigid from usuario)

		INSERT profesor (usuario_id,nombre,a_paterno,a_materno,telefono,edad,direccion)
		VALUES (@maxUsuario,@nombreProf,@a_paterno,@a_materno,@tel,@edad,@dir)
	GO