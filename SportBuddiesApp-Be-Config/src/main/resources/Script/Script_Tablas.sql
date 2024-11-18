-- CREATE DATABASE sportbuddies;

USE sportbuddies;


--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (3,'Jose','$2a$10$zuhyIlUPCe6SZmcerMrzn.5W6U2eIzgipU50pN5clOC111zO9BV26',1,'Plasencia','plasencia@gmaildos.com',NULL,'C/ Rus de la puerta','Araba/Álava','Amurrio','30100','IT','000000000'),(4,'Maria','$2a$10$Oab4M5yLiznerUFXD94PMO8.W5IjT6YFBzeJ1OlavCqgAKJnpm7wi',1,'Garcia','MA@gmail.comm',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'plasenciavillarejo@gmail.com','not-password',1,'Plasencia','plasenciavillarejo@gmail.com','https://lh3.googleusercontent.com/a/ACg8ocLgfd-5-Xwx7Jg9kmwgioG9OgQGQulJ4dyUOXN49jA5aRgV2w=s96-c',NULL,NULL,NULL,NULL,NULL,NULL),(8,'Destripador716','$2a$10$4S7/AQvWMEZUzkTI6INV3.9R3dkEt4jJ8fACIRHp.QiLI02Kn87FS',1,NULL,'plasaaae@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'a','$2a$10$.mfkYFqa8UyDzeSLUiHDauA.NAaQygmaRbBQovMY4c4lB7PonKklW',1,NULL,'asdf@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'asdfaaaa','$2a$10$roYpLj3Z31v.Ql7il6xaMOLS1V7cSxXWcTNL3gpyMewWHDa8SJcoC',1,NULL,'asd@gmmmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'sdfgsdfg','$2a$10$k/HwVVk7OkUJTbK6rMSAW.VcBR1soDxGc7pF1AtxkL5TjIkJz9lYW',1,NULL,'asdf@gmailasd.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_in_role`
--

DROP TABLE IF EXISTS `usuarios_in_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_in_role` (
  `usuario_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`role_id`),
  UNIQUE KEY `usuario_id` (`usuario_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_in_role`
--

LOCK TABLES `usuarios_in_role` WRITE;
/*!40000 ALTER TABLE `usuarios_in_role` DISABLE KEYS */;
INSERT INTO `usuarios_in_role` VALUES (3,3),(3,4),(8,4),(9,4),(10,4),(11,4),(12,4),(18,4),(19,4),(20,4),(21,4),(22,4);
/*!40000 ALTER TABLE `usuarios_in_role` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes_oauth`
--

LOCK TABLES `clientes_oauth` WRITE;
/*!40000 ALTER TABLE `clientes_oauth` DISABLE KEYS */;
INSERT INTO `clientes_oauth` VALUES (4,'gateway-app','$2a$10$.Ud6UXjF6ScEieX7erFVU.dWPYyfnhYSjbSqVRpRR4k2r68GVMQeO','client-be','client_secret_basic','refresh_token,authorization_code','http://127.0.0.1:8090/authorized,http://127.0.0.1:8090/login/oauth2/code/client-be','http://127.0.0.1:8090/logout','openid,profile',12,1),(5,'client-angular','$2a$10$3M0wg0e2LAKomzpTIjaJWOIKOjMFCRvpOinc6RG2WQhwNdIsMzjcu','client-angular','client_secret_basic','refresh_token,authorization_code','http://localhost:4200/authorize','http://127.0.0.1:8090/logout','openid,profile',12,1),(6,'prueba-id','$2a$10$.Ud6UXjF6ScEieX7erFVU.dWPYyfnhYSjbSqVRpRR4k2r68GVMQeO','prueba-cliente','client_secret_basic','refresh_token,authorization_code','http://127.0.0.1:8090/authorized,http://127.0.0.1:8090/login/oauth2/code/prueba-cliente','http://127.0.0.1:8090/logout','openid,profile',12,12),(14,'asdf','$2a$10$jpxs4vwduIn2IHkhb4KPTOvI8YjALlD1z1GZUZb5LBjxoPd8GtOBu','asdf','asdf','authorization_code,refresh_token','123','123','openid,profile',12,12);
/*!40000 ALTER TABLE `clientes_oauth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigo_verificacion`
--

DROP TABLE IF EXISTS `codigo_verificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigo_verificacion` (
  `id_codigo_verificacion` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) NOT NULL,
  `tiempo_expiracion` timestamp NOT NULL,
  `usuario_fk` bigint DEFAULT NULL COMMENT 'FK con el id del usuario',
  PRIMARY KEY (`id_codigo_verificacion`),
  KEY `fk_codigo_verificacion_usuario` (`usuario_fk`),
  CONSTRAINT `fk_codigo_verificacion_usuario` FOREIGN KEY (`usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigo_verificacion`
--

LOCK TABLES `codigo_verificacion` WRITE;
/*!40000 ALTER TABLE `codigo_verificacion` DISABLE KEYS */;
INSERT INTO `codigo_verificacion` VALUES (1,'zjy4tBZ0','2024-11-14 12:43:02',3),(2,'i6CxszAw','2024-11-13 18:18:40',4);
/*!40000 ALTER TABLE `codigo_verificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deportes`
--

DROP TABLE IF EXISTS `deportes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deportes` (
  `id_deporte` bigint NOT NULL AUTO_INCREMENT,
  `actividad` varchar(100) NOT NULL,
  PRIMARY KEY (`id_deporte`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deportes`
--

LOCK TABLES `deportes` WRITE;
/*!40000 ALTER TABLE `deportes` DISABLE KEYS */;
INSERT INTO `deportes` VALUES (1,'Futbol Sala'),(2,'Futbol');
/*!40000 ALTER TABLE `deportes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provincias`
--

DROP TABLE IF EXISTS `provincias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincias` (
  `id_provincia` bigint NOT NULL,
  `sigla_provincia` varchar(10) DEFAULT NULL,
  `nombre_provincia` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_provincia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincias`
--

LOCK TABLES `provincias` WRITE;
/*!40000 ALTER TABLE `provincias` DISABLE KEYS */;
INSERT INTO `provincias` VALUES (1,'VI','Araba/Álava'),(2,'AB','Albacete'),(3,'A','Alicante/Alacant'),(4,'AL','Almería'),(5,'O','Asturias'),(6,'AV','Ávila'),(7,'BA','Badajoz'),(8,'IB','Illes Balears'),(9,'B','Barcelona'),(10,'BU','Burgos'),(11,'CC','Cáceres'),(12,'CA','Cádiz'),(13,'CS','Castellón/Castelló'),(14,'CR','Ciudad Real'),(15,'CO','Córdoba'),(16,'C','A Coruña'),(17,'CU','Cuenca'),(18,'GI','Girona'),(19,'GR','Granada'),(20,'GU','Guadalajara'),(21,'SS','Gipuzkoa'),(22,'H','Huelva'),(23,'HU','Huesca'),(24,'J','Jaén'),(25,'LE','León'),(26,'L','Lleida'),(27,'LO','La Rioja'),(28,'LU','Lugo'),(29,'MA','Málaga'),(30,'MU','Murcia'),(31,'NA','Navarra'),(32,'OU','Ourense'),(33,'P','Palencia'),(34,'GC','Las Palmas'),(35,'PO','Pontevedra'),(36,'SA','Salamanca'),(37,'TF','Santa Cruz de Tenerife'),(38,'S','Cantabria'),(39,'SG','Segovia'),(40,'SE','Sevilla'),(41,'SO','Soria'),(42,'T','Tarragona'),(43,'TE','Teruel'),(44,'TO','Toledo'),(45,'V','Valencia/València'),(46,'VA','Valladolid'),(47,'BI','Bizkaia'),(48,'ZA','Zamora'),(49,'Z','Zaragoza'),(50,'CE','Ceuta'),(51,'ML','Melilla');
/*!40000 ALTER TABLE `provincias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `municipios`
--

DROP TABLE IF EXISTS `municipios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `municipios` (
  `id_municipio` bigint NOT NULL,
  `nombre_municipio` varchar(200) DEFAULT NULL,
  `municipio_provincia_fk` bigint NOT NULL,
  PRIMARY KEY (`id_municipio`),
  KEY `fk_municipio_provincia` (`municipio_provincia_fk`),
  CONSTRAINT `fk_municipio_provincia` FOREIGN KEY (`municipio_provincia_fk`) REFERENCES `provincias` (`id_provincia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipios`
--

LOCK TABLES `municipios` WRITE;
/*!40000 ALTER TABLE `municipios` DISABLE KEYS */;
INSERT INTO `municipios` VALUES (1,'Alegría-Dulantzi',1),(2,'Amurrio',1),(3,'Aramaio',1),(4,'Armiñón',1),(5,'Arrazua-Ubarrundia',1),(6,'Artziniega',1),(7,'Asparrena',1),(8,'Ayala/Aiara',1),(9,'Baños de Ebro/Mañueta',1),(10,'Barrundia',1),(11,'Berantevilla',1),(12,'Bernedo',1),(13,'Campezo/Kanpezu',1),(14,'Elburgo/Burgelu',1),(15,'Elciego',1),(16,'Elvillar/Bilar',1),(17,'Erriberagoitia/Ribera Alta',1),(18,'Harana/Valle de Arana',1),(19,'Iruña Oka/Iruña de Oca',1),(20,'Iruraiz-Gauna',1),(21,'Kripan',1),(22,'Kuartango',1),(23,'Labastida/Bastida',1),(24,'Lagrán',1),(25,'Laguardia',1),(26,'Lanciego/Lantziego',1),(27,'Lantarón',1),(28,'Lapuebla de Labarca',1),(29,'Laudio/Llodio',1),(30,'Legutio',1),(31,'Leza',1),(32,'Moreda de Álava',1),(33,'Navaridas',1),(34,'Okondo',1),(35,'Oyón-Oion',1),(36,'Peñacerrada-Urizaharra',1),(37,'Samaniego',1),(38,'San Millán/Donemiliaga',1),(39,'Urkabustaiz',1),(40,'Valdegovía/Gaubea',1),(41,'Villabuena de Álava/Eskuernaga',1),(42,'Vitoria-Gasteiz',1),(43,'Yécora/Iekora',1),(44,'Zalduondo',1),(45,'Zambrana',1),(46,'Zigoitia',1),(47,'Zuia',1);
/*!40000 ALTER TABLE `municipios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_tarjeta`
--

DROP TABLE IF EXISTS `pago_tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_tarjeta`
--

LOCK TABLES `pago_tarjeta` WRITE;
/*!40000 ALTER TABLE `pago_tarjeta` DISABLE KEYS */;
INSERT INTO `pago_tarjeta` VALUES (1,'pi_3QHWKyKPTU50YCkC1vhwQd8v','EUR','2024-11-04',3,'2024-11-04',1,NULL),(2,'pi_3QHkDmKPTU50YCkC1xJyi6ZC','EUR','2024-11-05',3,NULL,0,NULL),(3,'pi_3QHnP8KPTU50YCkC0XuSGiE3','EUR','2024-11-05',3,NULL,0,NULL),(4,'pi_3QHnYuKPTU50YCkC1ynuc1e8','EUR','2024-11-05',3,NULL,0,NULL),(6,'pi_3QHpL9KPTU50YCkC0V1bo5fv','EUR','2024-11-05',3,NULL,0,NULL),(7,'pi_3QHpO4KPTU50YCkC1X5P2JcM','EUR','2024-11-05',3,NULL,0,NULL),(8,'pi_3QHpVnKPTU50YCkC1rqIB1rO','EUR','2024-11-05',3,'2024-11-05',1,NULL),(9,'pi_3QI5RnKPTU50YCkC0Rqb3GfQ','EUR','2024-11-06',3,'2024-11-06',1,NULL),(10,'pi_3QI6bJKPTU50YCkC0owYpIBR','EUR','2024-11-06',5,'2024-11-07',1,NULL),(11,'pi_3QI7CmKPTU50YCkC02GKZIAy','EUR','2024-11-06',3,'2024-11-06',1,NULL),(12,'pi_3QI7DnKPTU50YCkC1SXYYYqk','EUR','2024-11-06',3,'2024-11-06',1,NULL),(13,'pi_3QIAwmKPTU50YCkC0o2L2DrN','EUR','2024-11-06',3,'2024-11-08',1,NULL),(14,'pi_3QIXtsKPTU50YCkC1W3wcqub','EUR','2024-11-07',5,NULL,0,93),(15,'pi_3QIqCAKPTU50YCkC0HlcQJD5','EUR','2024-11-08',3,'2024-11-08',1,NULL);
/*!40000 ALTER TABLE `pago_tarjeta` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `reservas_actividad`
--

DROP TABLE IF EXISTS `reservas_actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas_actividad` (
  `id_reserva_actividad` bigint NOT NULL AUTO_INCREMENT,
  `fecha_reserva` date DEFAULT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `requerimientos` json DEFAULT NULL,
  `usuarios_max_requeridos` bigint NOT NULL,
  `actividad` varchar(255) NOT NULL,
  `usuario_actividad_fk` bigint NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `codigo_postal` bigint DEFAULT NULL,
  `urgencia` varchar(20) DEFAULT NULL,
  `abono_pista` decimal(5,2) DEFAULT NULL,
  `plazas_restantes` bigint DEFAULT NULL,
  PRIMARY KEY (`id_reserva_actividad`),
  KEY `fk_usuario_actividad` (`usuario_actividad_fk`),
  CONSTRAINT `fk_usuario_actividad` FOREIGN KEY (`usuario_actividad_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas_actividad`
--

LOCK TABLES `reservas_actividad` WRITE;
/*!40000 ALTER TABLE `reservas_actividad` DISABLE KEYS */;
INSERT INTO `reservas_actividad` VALUES 
(1,'2024-09-13','10:00:00','12:00:00','[\"Camiseta Roja equipo 1\", \"Camiseta Verde Equipo B\", \"Árbitro\"]',10,'Fútbol',3,'Calle Ejemplo 123','Araba/Álava','Amurrio',28001,'Alta',2.50,10),
(2,'2024-09-14','10:00:00','12:00:00','[\"Equipo A: Camiseta Roja\", \"Equipo B: Camiseta Verde\"]',10,'Futbol Sala',4,'C/ Palomar 12','Araba/Álava','Amurrio',28001,'Media',1.00,10),
(3,'2024-12-14','20:00:31','21:00:31','[\"Prueba equipo Uno\", \"Prueba Equipo Dos\"]',10,'Fútbol',3,'Avenida Marques de Espinardo, 10 , 5ºB','Araba/Álava','Amurrio',30100,'Alta',2.50,9),
(4,'2024-12-15','20:00:31','21:00:31','[\"Prueba equipo Uno\", \"Prueba Equipo Dos\"]',10,'Fútbol',3,'Avenida Marques de Espinardo, 10 , 5ºB','Araba/Álava','Amurrio',30100,'Alta',2.50,10),
(5,'2024-12-14','17:00:31','18:00:31','[\"Equipo uno falta dos jugadores\", \"Equipación Roja\"]',10,'Fútbol',3,'Avenida florida','Araba/Álava','Amurrio',4200,'Alta',4.50,9),
(6, '2024-12-14', '09:00:00', '10:30:00', '[\"Equipo Azul\", \"Equipo Amarillo\"]', 8, 'Fútbol', 3, 'Plaza Mayor 5', 'Araba/Álava', 'Amurrio', 28001, 'Alta', 2.00, 8),
(7, '2024-12-14', '11:00:00', '12:30:00', '[\"Equipo Rojo\", \"Equipo Verde\"]', 10, 'Fútbol Sala', 3, 'Calle Mayor 22', 'Araba/Álava', 'Amurrio', 28002, 'Media', 3.00, 10),
(8, '2024-12-14', '15:00:00', '16:30:00', '[\"Jugadores pendientes de confirmación\"]', 6, 'Baloncesto', 3, 'Calle La Rioja 8', 'Araba/Álava', 'Amurrio', 30001, 'Baja', 1.50, 6),
(9, '2024-12-14', '13:00:00', '14:00:00','[\"Prueba Uno\", \"Prueba Dos\"]',1,'Futbol',3,'Avenida de los pinos','Araba/Álava','Amurrio',30100,'Alta',3.50,0),
(10, '2024-12-14', '16:00:00', '17:30:00', '[\"Equipo Azul\", \"Equipo Blanco\"]', 12, 'Fútbol', 3, 'Paseo de la Castellana 45', 'Araba/Álava', 'Amurrio', 28003, 'Alta', 4.00, 12),
(11, '2024-12-14', '18:00:00', '19:30:00', '[\"Partido amistoso\"]', 10, 'Tenis', 3, 'Calle Tenis 10', 'Araba/Álava', 'Amurrio', 30100, 'Alta', 5.00, 10),
(12, '2024-12-14', '20:30:00', '22:00:00', '[\"Liga local - Fase de grupos\"]', 8, 'Fútbol Sala', 3, 'Avenida del Deporte 25', 'Araba/Álava', 'Amurrio', 28004, 'Alta', 6.00, 8),
(13, '2024-12-14', '08:00:00', '09:30:00', '[\"Entrenamiento equipo juvenil\"]', 15, 'Fútbol', 3, 'Calle Delicias 10', 'Araba/Álava', 'Amurrio', 29001, 'Alta', 2.50, 15),
(14, '2024-12-14', '10:00:00', '11:00:00', '[\"Partido amistoso entre clubes\"]', 20, 'Pádel', 3, 'Calle del Pádel 7', 'Araba/Álava', 'Amurrio', 30002, 'Media', 1.00, 20),
(15, '2024-12-14', '14:00:00', '15:30:00', '[\"Competición escolar\"]', 10, 'Baloncesto', 3, 'Polideportivo Norte', 'Araba/Álava', 'Amurrio', 28005, 'Alta', 3.00, 10),
(16, '2024-12-14', '19:00:00', '20:30:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(17, '2024-12-14', '21:00:00', '22:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(18, '2024-12-14', '22:00:00', '23:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(19, '2024-12-14', '23:00:00', '00:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(20, '2024-12-14', '00:00:00', '01:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(21, '2024-12-14', '01:00:00', '02:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(22, '2024-12-14', '02:00:00', '03:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(23, '2024-12-14', '03:00:00', '04:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10),
(24, '2024-12-14', '04:00:00', '05:00:00', '[\"Semifinal de torneo local\"]', 10, 'Fútbol', 3, 'Estadio Municipal', 'Araba/Álava', 'Amurrio', 28006, 'Alta', 4.50, 10);
 

/*!40000 ALTER TABLE `reservas_actividad` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `reservas_usuario`
--

DROP TABLE IF EXISTS `reservas_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas_usuario` (
  `id_reserva` bigint NOT NULL AUTO_INCREMENT,
  `fecha_reserva` date DEFAULT NULL,
  `hora_inicio_reserva` time DEFAULT NULL,
  `hora_fin_reserva` time DEFAULT NULL,
  `usuario_reserva_fk` bigint NOT NULL,
  `deporte_reserva_fk` bigint NOT NULL,
  `reserva_actividad_fk` bigint NOT NULL,
  `abonado` tinyint(1) NOT NULL,
  `metodo_pago` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `usuario_reserva_fk` (`usuario_reserva_fk`),
  KEY `deporte_reserva_fk` (`deporte_reserva_fk`),
  KEY `reserva_actividad_fk` (`reserva_actividad_fk`),
  CONSTRAINT `reservas_usuario_ibfk_1` FOREIGN KEY (`usuario_reserva_fk`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `reservas_usuario_ibfk_2` FOREIGN KEY (`deporte_reserva_fk`) REFERENCES `deportes` (`id_deporte`),
  CONSTRAINT `reservas_usuario_ibfk_3` FOREIGN KEY (`reserva_actividad_fk`) REFERENCES `reservas_actividad` (`id_reserva_actividad`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas_usuario`
--

LOCK TABLES `reservas_usuario` WRITE;
/*!40000 ALTER TABLE `reservas_usuario` DISABLE KEYS */;
INSERT INTO `reservas_usuario` VALUES (1,'2024-09-13','20:00:31','21:00:31',3,1,1,1,NULL),(5,'2024-09-14','10:00:00','12:00:00',3,2,2,0,NULL),(69,'2024-12-14','17:00:00','18:00:00',3,2,5,1,'Paypal'),(82,'2024-12-14','20:00:00','21:00:00',5,2,3,1,'Paypal'),(93,'2024-12-14','17:00:00','18:00:00',5,2,5,1,'Tarjeta'),(102,'2024-12-14','17:00:00','18:00:00',4,2,5,0,NULL),(103,'2024-12-14','20:00:00','21:00:00',3,2,3,0,NULL),(104,'2024-12-14','13:00:00','14:00:00',3,2,9,0,NULL);
/*!40000 ALTER TABLE `reservas_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal`
--

DROP TABLE IF EXISTS `paypal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal`
--

LOCK TABLES `paypal` WRITE;
/*!40000 ALTER TABLE `paypal` DISABLE KEYS */;
INSERT INTO `paypal` VALUES (1,'https://api.sandbox.paypal.com/v1/payments/sale/1AK69427MR972534F/refund',100.00,'USD',1,'2024-10-23',NULL,3),(2,'https://api.sandbox.paypal.com/v1/payments/sale/3A034683HN256940J/refund',400.00,'EUR',1,'2024-10-23',NULL,3),(3,'https://api.sandbox.paypal.com/v1/payments/sale/39M36221SC770054N/refund',20.00,'EUR',1,'2024-10-23',NULL,3),(4,'https://api.sandbox.paypal.com/v1/payments/sale/56L3917154594240G/refund',200.00,'EUR',1,'2024-10-24',NULL,3),(5,'https://api.sandbox.paypal.com/v1/payments/sale/26N72083C6341160J/refund',5.00,'USD',1,'2024-10-24',NULL,3),(8,'https://api.sandbox.paypal.com/v1/payments/sale/463424825E6407301/refund',2.50,'EUR',1,'2024-10-24',NULL,3),(9,'https://api.sandbox.paypal.com/v1/payments/sale/3KN87627HN770151U/refund',4.50,'EUR',1,'2024-10-24',NULL,3),(10,'https://api.sandbox.paypal.com/v1/payments/sale/6HE04178H6039270A/refund',2.50,'EUR',1,'2024-10-22',NULL,3),(11,'https://api.sandbox.paypal.com/v1/payments/sale/2FC88825SY999842U/refund',2.50,'EUR',1,'2024-10-24',NULL,3),(12,'https://api.sandbox.paypal.com/v1/payments/sale/7N327105SS877824F/refund',2.50,'EUR',1,'2024-10-24',NULL,3),(13,'https://api.sandbox.paypal.com/v1/payments/sale/1EM32640FV460432M/refund',2.50,'EUR',1,'2024-10-25',NULL,3),(14,'https://api.sandbox.paypal.com/v1/payments/sale/0VA27683G2578344Y/refund',2.50,'EUR',1,'2024-10-25',NULL,3),(15,'https://api.sandbox.paypal.com/v1/payments/sale/1SC64568AN3830722/refund',2.50,'EUR',1,'2024-10-29',NULL,3),(16,'https://api.sandbox.paypal.com/v1/payments/sale/61891213B9159551B/refund',2.50,'EUR',1,'2024-10-30',NULL,3),(17,'https://api.sandbox.paypal.com/v1/payments/sale/117548950P303745R/refund',2.50,'EUR',1,'2024-10-30',NULL,3),(20,'https://api.sandbox.paypal.com/v1/payments/sale/0G579738VL9225720/refund',2.50,'EUR',1,'2024-11-05',NULL,3),(21,'https://api.sandbox.paypal.com/v1/payments/sale/1K4540558N2754932/refund',2.50,'EUR',1,'2024-11-05',NULL,3),(22,'https://api.sandbox.paypal.com/v1/payments/sale/619226898F662073R/refund',2.50,'EUR',1,'2024-11-05',NULL,3),(23,'https://api.sandbox.paypal.com/v1/payments/sale/1CT85072RW328534X/refund',2.50,'EUR',1,'2024-11-05',NULL,3),(24,'https://api.sandbox.paypal.com/v1/payments/sale/1T338634DH400032T/refund',2.50,'EUR',0,NULL,82,5),(25,'https://api.sandbox.paypal.com/v1/payments/sale/26A69742SK562544V/refund',2.50,'EUR',1,'2024-11-06',NULL,3),(26,'https://api.sandbox.paypal.com/v1/payments/sale/59E22314RE3509641/refund',2.50,'EUR',1,'2024-11-06',NULL,3),(27,'https://api.sandbox.paypal.com/v1/payments/sale/30B50275LC362113P/refund',2.50,'EUR',1,'2024-11-06',NULL,3),(28,'https://api.sandbox.paypal.com/v1/payments/sale/1G904158Y0505253J/refund',2.50,'EUR',1,'2024-11-06',NULL,3),(29,'https://api.sandbox.paypal.com/v1/payments/sale/8D463316EV6315907/refund',2.50,'EUR',1,'2024-11-06',NULL,3),(30,'https://api.sandbox.paypal.com/v1/payments/sale/96X35845NV378060G/refund',2.50,'EUR',1,'2024-11-06',NULL,3),(31,'https://api.sandbox.paypal.com/v1/payments/sale/6X746805UB384062R/refund',4.50,'EUR',1,'2024-11-07',NULL,5);
/*!40000 ALTER TABLE `paypal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planes_de_pago`
--

DROP TABLE IF EXISTS `planes_de_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planes_de_pago` (
  `id_plan_pago` bigint NOT NULL AUTO_INCREMENT,
  `nombre_plan` varchar(50) DEFAULT NULL,
  `limite_reservas` bigint DEFAULT NULL,
  `precio_plan` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id_plan_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planes_de_pago`
--

LOCK TABLES `planes_de_pago` WRITE;
/*!40000 ALTER TABLE `planes_de_pago` DISABLE KEYS */;
INSERT INTO `planes_de_pago` VALUES (1,'free',9999,0.00),(2,'student',5,2.99),(3,'basic',3,4.99),(4,'premium',5,8.99),(5,'unlimited',10,14.99);
/*!40000 ALTER TABLE `planes_de_pago` ENABLE KEYS */;
UNLOCK TABLES;





--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `idRol_UNIQUE` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (3,'ADMIN'),(4,'USER'),(5,'OIDC_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suscripciones`
--

DROP TABLE IF EXISTS `suscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suscripciones`
--

LOCK TABLES `suscripciones` WRITE;
/*!40000 ALTER TABLE `suscripciones` DISABLE KEYS */;
INSERT INTO `suscripciones` VALUES (1,3,'2024-09-19','2024-10-19',4.99,'Paypal','Activo'),(2,5,'2024-09-19','2025-10-19',4.99,'Paypal','Activo'),(3,4,'2024-09-19','2025-10-19',4.99,'Paypal','Activo');
/*!40000 ALTER TABLE `suscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_estados`
--

DROP TABLE IF EXISTS `tipo_estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_estados` (
  `id_tipo` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(50) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_estados`
--

LOCK TABLES `tipo_estados` WRITE;
/*!40000 ALTER TABLE `tipo_estados` DISABLE KEYS */;
INSERT INTO `tipo_estados` VALUES (1,'Activo','La suscripción está activa y el usuario puede usar todos los servicios.'),(2,'Pendiente','El pago está en proceso, pero no se ha confirmado aún. Puede ser por un intento de pago que no ha sido verificado'),(3,'Expirado','La suscripción ha caducado y el usuario no puede acceder a los servicios hasta que renueve su pago'),(4,'Cancelado','El usuario ha cancelado su suscripción y no puede acceder a los servicios'),(5,'Rechazado','El pago fue rechazado (por ejemplo, tarjeta de crédito denegada)'),(6,'Vencido','Similar a \"expirado\", pero puede usarse para indicar que se pasó la fecha de renovación sin pago'),(7,'Devuelto','El pago fue devuelto por alguna razón (por ejemplo, disputa o solicitud del usuario)');
/*!40000 ALTER TABLE `tipo_estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_plan_pago`
--

DROP TABLE IF EXISTS `usuario_plan_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_plan_pago`
--

LOCK TABLES `usuario_plan_pago` WRITE;
/*!40000 ALTER TABLE `usuario_plan_pago` DISABLE KEYS */;
INSERT INTO `usuario_plan_pago` VALUES (1,1,3,22,'2024-10-19'),(2,2,3,10,'2024-12-19'),(4,3,3,49,'2024-12-19');
/*!40000 ALTER TABLE `usuario_plan_pago` ENABLE KEYS */;
UNLOCK TABLES;


