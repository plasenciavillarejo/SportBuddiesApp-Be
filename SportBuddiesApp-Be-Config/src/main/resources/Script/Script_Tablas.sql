-- CREATE DATABASE sportbuddies;
USE sportbuddies;


--
-- Table structure for table `usuarios`
--
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `url_imagen` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `codigo_postal` varchar(5) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `numero_telefono` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `idusuarios_UNIQUE` (`id_usuario`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuarios`
--
INSERT INTO `usuarios` VALUES (3,'Jose','$2a$10$zuhyIlUPCe6SZmcerMrzn.5W6U2eIzgipU50pN5clOC111zO9BV26',1,'Plasencia','plasencia@gmaildos.com',NULL,'C/ Rus de la puerta','Araba/Álava','Amurrio','30100','IT','000000000'),(4,'Maria','$2a$10$Oab4M5yLiznerUFXD94PMO8.W5IjT6YFBzeJ1OlavCqgAKJnpm7wi',1,'Garcia','MA@gmail.comm',NULL,'C/asdf','Araba/Álava','Aramaio','30111','GB','111111111'),(5,'plasenciavillarejo@gmail.com','not-password',1,'Plasencia','plasenciavillarejo@gmail.com','https://lh3.googleusercontent.com/a/ACg8ocLgfd-5-Xwx7Jg9kmwgioG9OgQGQulJ4dyUOXN49jA5aRgV2w=s96-c',NULL,NULL,NULL,NULL,NULL,NULL),(8,'Destripador716','$2a$10$4S7/AQvWMEZUzkTI6INV3.9R3dkEt4jJ8fACIRHp.QiLI02Kn87FS',1,NULL,'plasaaae@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'plasencia','$2a$10$Svzy0kBcFWfwkAtAsiraP.kYpNHcGqgAPv4qGpjd5YW/mpq.qHrRm',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'mariano.rojas','$2a$10$9UHavSo1gSAPBwcFxmPo.ej64AVuCnaDeX.ebnzInD8d7cO5GVK4S',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'prueba.id.mariano','$2a$10$GBIsYRgdv4ZVJonJzycliezUb0T29cxuIPf3afj0iedF1UQtRT1ka',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

--
-- Table structure for table `usuarios_in_role`
--

DROP TABLE IF EXISTS `usuarios_in_role`;
CREATE TABLE `usuarios_in_role` (
  `usuario_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`role_id`),
  UNIQUE KEY `usuario_id` (`usuario_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuarios_in_role`
--
INSERT INTO `usuarios_in_role` VALUES (3,3),(3,4),(4,4),(8,4),(9,4),(10,4),(11,4),(12,4),(18,4),(19,4),(20,4),(21,4),(22,4),(23,4),(24,4),(25,4);

--
-- Table structure for table `autorizacion_consentimiento`
--

DROP TABLE IF EXISTS `autorizacion_consentimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autorizacion_consentimiento` (
  `id_cliente_registrado` varchar(255) NOT NULL,
  `nombre_principal` varchar(255) NOT NULL,
  `authorities` varchar(1000) NOT NULL,
  PRIMARY KEY (`id_cliente_registrado`,`nombre_principal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autorizacion_consentimiento`
--

LOCK TABLES `autorizacion_consentimiento` WRITE;
/*!40000 ALTER TABLE `autorizacion_consentimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `autorizacion_consentimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes_oauth`
--

DROP TABLE IF EXISTS `clientes_oauth`;
CREATE TABLE `clientes_oauth` (
  `id_cliente_oauth` bigint NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) NOT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `nombre_cliente` varchar(255) NOT NULL,
  `metodos_autenticacion` varchar(1000) NOT NULL,
  `tipos_autorizacion` varchar(1000) NOT NULL,
  `redireccion_uris` varchar(1000) DEFAULT NULL,
  `redireccion_uris_logout` varchar(1000) DEFAULT NULL,
  `permisos` varchar(1000) NOT NULL,
  `access_token` bigint DEFAULT NULL,
  `refresh_token` bigint DEFAULT NULL,
  PRIMARY KEY (`id_cliente_oauth`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `clientes_oauth`
--
INSERT INTO `clientes_oauth` VALUES (4,'gateway-app','$2a$10$.Ud6UXjF6ScEieX7erFVU.dWPYyfnhYSjbSqVRpRR4k2r68GVMQeO','client-be','client_secret_basic','refresh_token,authorization_code','http://127.0.0.1:8090/authorized,http://127.0.0.1:8090/login/oauth2/code/client-be','http://127.0.0.1:8090/logout','openid,profile',12,1),(5,'client-angular','$2a$10$3M0wg0e2LAKomzpTIjaJWOIKOjMFCRvpOinc6RG2WQhwNdIsMzjcu','client-angular','client_secret_basic','refresh_token,authorization_code','http://localhost:4200/authorize','http://127.0.0.1:8090/logout','openid,profile',12,1),(6,'prueba-id','$2a$10$GHDSGyEHGt9YvmbM6BtY1umSRgJ0DSZsLLDGqPAtMe7fdJwl11xLi','prueba-cliente','client_secret_basic','refresh_token,authorization_code','http://127.0.0.1:8090/authorized,http://127.0.0.1:8090/login/oauth2/code/prueba-cliente','http://127.0.0.1:8090/logout','openid,profile',12,12);

--
-- Table structure for table `codigo_verificacion`
--

DROP TABLE IF EXISTS `codigo_verificacion`;
CREATE TABLE `codigo_verificacion` (
  `id_codigo_verificacion` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) NOT NULL,
  `tiempo_expiracion` timestamp NOT NULL,
  `usuario_fk` bigint DEFAULT NULL COMMENT 'FK con el id del usuario',
  PRIMARY KEY (`id_codigo_verificacion`),
  KEY `fk_codigo_verificacion_usuario` (`usuario_fk`),
  CONSTRAINT `fk_codigo_verificacion_usuario` FOREIGN KEY (`usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `codigo_verificacion`
--
INSERT INTO `codigo_verificacion` VALUES (1,'zjy4tBZ0','2024-11-14 12:43:02',3),(2,'i6CxszAw','2024-11-13 18:18:40',4);


--
-- Table structure for table `deportes`
--

DROP TABLE IF EXISTS `deportes`;
CREATE TABLE `deportes` (
  `id_deporte` bigint NOT NULL AUTO_INCREMENT,
  `actividad` varchar(100) NOT NULL,
  PRIMARY KEY (`id_deporte`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `deportes`
--
INSERT INTO `deportes` VALUES (1,'Futbol Sala'),(2,'Futbol');


--
-- Table structure for table `provincias`
--

DROP TABLE IF EXISTS `provincias`;
CREATE TABLE `provincias` (
  `id_provincia` bigint NOT NULL,
  `sigla_provincia` varchar(10) DEFAULT NULL,
  `nombre_provincia` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_provincia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `provincias`
--
INSERT INTO `provincias` VALUES (1,'VI','Araba/Álava'),(2,'AB','Albacete'),(3,'A','Alicante/Alacant'),(4,'AL','Almería'),(5,'O','Asturias'),(6,'AV','Ávila'),(7,'BA','Badajoz'),(8,'IB','Illes Balears'),(9,'B','Barcelona'),(10,'BU','Burgos'),(11,'CC','Cáceres'),(12,'CA','Cádiz'),(13,'CS','Castellón/Castelló'),(14,'CR','Ciudad Real'),(15,'CO','Córdoba'),(16,'C','A Coruña'),(17,'CU','Cuenca'),(18,'GI','Girona'),(19,'GR','Granada'),(20,'GU','Guadalajara'),(21,'SS','Gipuzkoa'),(22,'H','Huelva'),(23,'HU','Huesca'),(24,'J','Jaén'),(25,'LE','León'),(26,'L','Lleida'),(27,'LO','La Rioja'),(28,'LU','Lugo'),(29,'MA','Málaga'),(30,'MU','Murcia'),(31,'NA','Navarra'),(32,'OU','Ourense'),(33,'P','Palencia'),(34,'GC','Las Palmas'),(35,'PO','Pontevedra'),(36,'SA','Salamanca'),(37,'TF','Santa Cruz de Tenerife'),(38,'S','Cantabria'),(39,'SG','Segovia'),(40,'SE','Sevilla'),(41,'SO','Soria'),(42,'T','Tarragona'),(43,'TE','Teruel'),(44,'TO','Toledo'),(45,'V','Valencia/València'),(46,'VA','Valladolid'),(47,'BI','Bizkaia'),(48,'ZA','Zamora'),(49,'Z','Zaragoza'),(50,'CE','Ceuta'),(51,'ML','Melilla');

--
-- Table structure for table `municipios`
--

DROP TABLE IF EXISTS `municipios`;
CREATE TABLE `municipios` (
  `id_municipio` bigint NOT NULL,
  `nombre_municipio` varchar(200) DEFAULT NULL,
  `municipio_provincia_fk` bigint NOT NULL,
  PRIMARY KEY (`id_municipio`),
  KEY `fk_municipio_provincia` (`municipio_provincia_fk`),
  CONSTRAINT `fk_municipio_provincia` FOREIGN KEY (`municipio_provincia_fk`) REFERENCES `provincias` (`id_provincia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `municipios`
--
INSERT INTO `municipios` VALUES (1,'Alegría-Dulantzi',1),(2,'Amurrio',1),(3,'Aramaio',1),(4,'Armiñón',1),(5,'Arrazua-Ubarrundia',1),(6,'Artziniega',1),(7,'Asparrena',1),(8,'Ayala/Aiara',1),(9,'Baños de Ebro/Mañueta',1),(10,'Barrundia',1),(11,'Berantevilla',1),(12,'Bernedo',1),(13,'Campezo/Kanpezu',1),(14,'Elburgo/Burgelu',1),(15,'Elciego',1),(16,'Elvillar/Bilar',1),(17,'Erriberagoitia/Ribera Alta',1),(18,'Harana/Valle de Arana',1),(19,'Iruña Oka/Iruña de Oca',1),(20,'Iruraiz-Gauna',1),(21,'Kripan',1),(22,'Kuartango',1),(23,'Labastida/Bastida',1),(24,'Lagrán',1),(25,'Laguardia',1),(26,'Lanciego/Lantziego',1),(27,'Lantarón',1),(28,'Lapuebla de Labarca',1),(29,'Laudio/Llodio',1),(30,'Legutio',1),(31,'Leza',1),(32,'Moreda de Álava',1),(33,'Navaridas',1),(34,'Okondo',1),(35,'Oyón-Oion',1),(36,'Peñacerrada-Urizaharra',1),(37,'Samaniego',1),(38,'San Millán/Donemiliaga',1),(39,'Urkabustaiz',1),(40,'Valdegovía/Gaubea',1),(41,'Villabuena de Álava/Eskuernaga',1),(42,'Vitoria-Gasteiz',1),(43,'Yécora/Iekora',1),(44,'Zalduondo',1),(45,'Zambrana',1),(46,'Zigoitia',1),(47,'Zuia',1);


--
-- Table structure for table `pago_tarjeta`
--

DROP TABLE IF EXISTS `pago_tarjeta`;

CREATE TABLE `pago_tarjeta` (
  `id_pago_tarjeta` bigint NOT NULL AUTO_INCREMENT,
  `id_devolucion` varchar(100) NOT NULL COMMENT 'El id que es generado por la aplicacion stripe para realizar las devoluciones',
  `moneda` varchar(10) NOT NULL,
  `fecha_cobro` date DEFAULT NULL COMMENT 'Fecha en la que se realizao el cobro',
  `usuario_fk` bigint DEFAULT NULL COMMENT 'ID del usuario que realizar el pago',
  `fecha_devolucion` date DEFAULT NULL COMMENT 'Fecha en la que se realizao la devolucion',
  `reembolsado` tinyint(1) DEFAULT NULL,
  `reserva_usuario_fk` bigint DEFAULT NULL COMMENT 'Id de la reserva de usuario inscrita',
  PRIMARY KEY (`id_pago_tarjeta`),
  KEY `fk_usuario_pago_tarjeta` (`usuario_fk`),
  KEY `fk_usuario_pago_tarjeta_idx` (`reserva_usuario_fk`),
  CONSTRAINT `fk_usuario_pago_tarjeta` FOREIGN KEY (`usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- #### IMPORTANTE: EN EL ENTORNO DE PRUEBAS EL PAGO CON TARJETA NO HACE FALTA MIGRAR NINGÚN DATO YA QUE NO ES RELEVANTE #### --


--
-- Table structure for table `reservas_actividad`
--
DROP TABLE IF EXISTS `reservas_actividad`;
CREATE TABLE `reservas_actividad` (
  `id_reserva_actividad` bigint NOT NULL AUTO_INCREMENT,
  `fecha_reserva` date DEFAULT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `requerimientos` json DEFAULT NULL,
  `usuarios_max_requeridos` bigint NOT NULL,
  `actividad` varchar(255) NOT NULL,
  `usuario_actividad_fk` bigint NOT NULL COMMENT 'ID del usuario que se ha inscrito en la actividad',
  `direccion` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `codigo_postal` bigint DEFAULT NULL,
  `urgencia` varchar(20) DEFAULT NULL,
  `abono_pista` decimal(10,2) DEFAULT NULL,
  `plazas_restantes` bigint DEFAULT NULL,
  PRIMARY KEY (`id_reserva_actividad`),
  KEY `fk_usuario_actividad` (`usuario_actividad_fk`),
  CONSTRAINT `fk_usuario_actividad` FOREIGN KEY (`usuario_actividad_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- #### IMPORTANTE: EN EL ENTORNO DE PRUEBAS LAS RESERVA ACTIVIDAD NO HACE FALTA MIGRAR NINGÚN DATO YA QUE NO ES RELEVANTE #### --

--
-- Table structure for table `reservas_usuario`
--

DROP TABLE IF EXISTS `reservas_usuario`;

CREATE TABLE `reservas_usuario` (
  `id_reserva` bigint NOT NULL AUTO_INCREMENT,
  `fecha_reserva` date DEFAULT NULL,
  `hora_inicio_reserva` time DEFAULT NULL,
  `hora_fin_reserva` time DEFAULT NULL,
  `usuario_reserva_fk` bigint NOT NULL COMMENT 'ID del Usuario que ha realizado la reserva',
  `deporte_reserva_fk` bigint NOT NULL COMMENT 'ID del deporte que ha reservado',
  `reserva_actividad_fk` bigint NOT NULL COMMENT 'ID de la actividad que ha reservado',
  `abonado` tinyint(1) NOT NULL COMMENT 'Indica si el usuario ha realizado el pago',
  `metodo_pago` varchar(100) DEFAULT NULL COMMENT 'Método con el que ha pagado',
  PRIMARY KEY (`id_reserva`),
  KEY `usuario_reserva_fk` (`usuario_reserva_fk`),
  KEY `deporte_reserva_fk` (`deporte_reserva_fk`),
  KEY `reserva_actividad_fk` (`reserva_actividad_fk`),
  CONSTRAINT `reservas_usuario_ibfk_1` FOREIGN KEY (`usuario_reserva_fk`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `reservas_usuario_ibfk_2` FOREIGN KEY (`deporte_reserva_fk`) REFERENCES `deportes` (`id_deporte`),
  CONSTRAINT `reservas_usuario_ibfk_3` FOREIGN KEY (`reserva_actividad_fk`) REFERENCES `reservas_actividad` (`id_reserva_actividad`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- #### IMPORTANTE: EN EL ENTORNO DE PRUEBAS LAS RESERVA DE USUARIO NO HACE FALTA MIGRAR NINGÚN DATO YA QUE NO ES RELEVANTE #### --

--
-- Table structure for table `paypal`
--

DROP TABLE IF EXISTS `paypal`;
CREATE TABLE `paypal` (
  `id_paypal` bigint NOT NULL AUTO_INCREMENT,
  `url_refund` varchar(255) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL COMMENT 'Precio que se ha abonado',
  `moneda` varchar(100) DEFAULT NULL COMMENT 'Nombre de la moneda con la que se hizo el pago `EUR`, `USD`, `LIB` ',
  `reembolsado` tinyint(1) DEFAULT NULL,
  `fecha_reembolso` date DEFAULT NULL COMMENT 'Fecha en la que se realizao el reembolso',
  `reserva_usuario_fk` bigint DEFAULT NULL COMMENT 'ID de la reservas del usuario',
  `usuario_fk` bigint DEFAULT NULL COMMENT 'ID del usuario que hace la reserva dentro de la tabla usuarios',
  PRIMARY KEY (`id_paypal`),
  KEY `fk_reserva_usuario` (`reserva_usuario_fk`),
  KEY `fk_usuario_` (`usuario_fk`),
  CONSTRAINT `fk_reserva_usuario` FOREIGN KEY (`reserva_usuario_fk`) REFERENCES `reservas_usuario` (`id_reserva`),
  CONSTRAINT `fk_usuario_` FOREIGN KEY (`usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- #### IMPORTANTE: EN EL ENTORNO DE PRUEBAS EL PAGO CON PAYPAL NO HACE FALTA MIGRAR NINGÚN DATO YA QUE NO ES RELEVANTE #### --


--
-- Table structure for table `planes_de_pago`
--

DROP TABLE IF EXISTS `planes_de_pago`;
CREATE TABLE `planes_de_pago` (
  `id_plan_pago` bigint NOT NULL AUTO_INCREMENT,
  `nombre_plan` varchar(50) DEFAULT NULL,
  `limite_reservas` bigint DEFAULT NULL,
  `precio_plan` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id_plan_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `planes_de_pago`
--
INSERT INTO `planes_de_pago` VALUES (1,'free',9999,0.00),(2,'student',5,2.99),(3,'basic',3,4.99),(4,'premium',5,8.99),(5,'unlimited',10,14.99);


--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `idRol_UNIQUE` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `roles`
--
INSERT INTO `roles` VALUES (3,'ADMIN'),(4,'USER'),(5,'OIDC_USER');

--
-- Table structure for table `suscripciones`
--

DROP TABLE IF EXISTS `suscripciones`;
CREATE TABLE `suscripciones` (
  `id_sucripcion` bigint NOT NULL AUTO_INCREMENT,
  `suscripcion_usuario_fk` bigint DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `precio_total` decimal(5,2) DEFAULT NULL,
  `metodo_pago` varchar(50) DEFAULT NULL,
  `estado_pago` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_sucripcion`),
  KEY `fk_sucripcion_usuario` (`suscripcion_usuario_fk`),
  CONSTRAINT `fk_sucripcion_usuario` FOREIGN KEY (`suscripcion_usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `suscripciones`
--
INSERT INTO `suscripciones` VALUES (1,3,'2024-09-19','2024-10-19',4.99,'Paypal','Activo'),(2,5,'2024-09-19','2025-10-19',4.99,'Paypal','Activo'),(3,4,'2024-09-19','2025-10-19',4.99,'Paypal','Activo');


--
-- Table structure for table `tipo_estados`
--

DROP TABLE IF EXISTS `tipo_estados`;
CREATE TABLE `tipo_estados` (
  `id_tipo` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(50) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `tipo_estados`
--
INSERT INTO `tipo_estados` VALUES (1,'Activo','La suscripción está activa y el usuario puede usar todos los servicios.'),(2,'Pendiente','El pago está en proceso, pero no se ha confirmado aún. Puede ser por un intento de pago que no ha sido verificado'),(3,'Expirado','La suscripción ha caducado y el usuario no puede acceder a los servicios hasta que renueve su pago'),(4,'Cancelado','El usuario ha cancelado su suscripción y no puede acceder a los servicios'),(5,'Rechazado','El pago fue rechazado (por ejemplo, tarjeta de crédito denegada)'),(6,'Vencido','Similar a \"expirado\", pero puede usarse para indicar que se pasó la fecha de renovación sin pago'),(7,'Devuelto','El pago fue devuelto por alguna razón (por ejemplo, disputa o solicitud del usuario)');

--
-- Table structure for table `usuario_plan_pago`
--

DROP TABLE IF EXISTS `usuario_plan_pago`;
CREATE TABLE `usuario_plan_pago` (
  `id_usuario_plan_pago` bigint NOT NULL AUTO_INCREMENT,
  `usuario_plan_pago_suscripcion_fk` bigint DEFAULT NULL,
  `plan_pago_fk` bigint DEFAULT NULL,
  `reservas_restantes` bigint DEFAULT NULL,
  `fecha_renovacion` date DEFAULT NULL,
  PRIMARY KEY (`id_usuario_plan_pago`),
  KEY `fk_usuario_plan_pago_suscripcion` (`usuario_plan_pago_suscripcion_fk`),
  KEY `fk_plan_pago` (`plan_pago_fk`),
  CONSTRAINT `fk_plan_pago` FOREIGN KEY (`plan_pago_fk`) REFERENCES `planes_de_pago` (`id_plan_pago`),
  CONSTRAINT `fk_usuario_plan_pago_suscripcion` FOREIGN KEY (`usuario_plan_pago_suscripcion_fk`) REFERENCES `suscripciones` (`id_sucripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuario_plan_pago`
--
INSERT INTO `usuario_plan_pago` VALUES (1,1,3,22,'2024-10-19'),(2,2,3,10,'2024-12-19'),(4,3,3,49,'2024-12-19');


--
-- Table structure for table `confirmacion_usuarios`
--

CREATE TABLE `confirmacion_usuarios` (
 `id_confirmacion`  bigint NOT NULL AUTO_INCREMENT,
 `usuario_fk` bigint NOT NULL COMMENT 'ID del Usuario que ha asistido a la actividad',
 `reserva_actividad_fk` bigint NOT NULL COMMENT 'ID de la actividad al que ha asistido',
 `fecha_reserva` date DEFAULT NULL,
 `hora_inicio` time DEFAULT NULL,
 `hora_fin` time DEFAULT NULL,
 PRIMARY KEY (`id_confirmacion`),
 FOREIGN KEY (usuario_fk) REFERENCES usuarios(id_usuario),
 FOREIGN KEY (reserva_actividad_fk) REFERENCES reservas_actividad(id_reserva_actividad)
);

--
-- Table structure for table `usuarios_passkey`
--

CREATE TABLE usuarios_passkey (
 `id_usuario_passkey` bigint NOT NULL AUTO_INCREMENT,
 `usuarios_fk` bigint NOT NULL,
 `credencial_id` VARCHAR(255) NOT NULL,
 `llave_publica` VARCHAR(255) NOT NULL,
 `algoritmo` VARCHAR(20) NOT NULL,
 `fechaCreacion` date NOT NULL,
 PRIMARY KEY (`id_usuario_passkey`),
 FOREIGN KEY (`usuarios_fk`) REFERENCES usuarios(id_usuario)
);

INSERT INTO `usuarios_passkey` VALUES (40,3,'OwrflXuuQT4tc7Eddd3pFA','MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEE/Az8ad+KL8z6iQNJ5aGwBbFpKr0ig9Flz+W8CvrE7K5XdrHM56XAhxNlamgbNGJQyCPQVWCoyCtJb4UUo+3rA==','ES256','2024-12-28'),(41,24,'d7+amUec/kS6XcIIQWEjSQ','MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEg6193dOxng+p2qwWV3Lrzp16Gf3gW/p4P8/ss0WRK0ep0v9UkfEAs/cQRbL+YUiUzunaywwViz87jGh2RXty8g==','ES256','2025-01-18');

--
-- Table structure for table `usuarios_passkey`
--

CREATE TABLE code_challange (
 `id_code_challange` bigint NOT NULL AUTO_INCREMENT,
 `code_challange` VARCHAR(255) NOT NULL,
 PRIMARY KEY (`id_code_challange`)
 );

