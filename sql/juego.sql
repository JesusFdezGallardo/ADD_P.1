-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-02-2022 a las 20:01:18
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `juego`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preguntas`
--

CREATE TABLE `preguntas` (
  `id_Pregunta` int(10) NOT NULL,
  `Enunciado` varchar(200) NOT NULL,
  `Respuesta1` varchar(200) NOT NULL,
  `Respuesta2` varchar(200) NOT NULL,
  `Respuesta3` varchar(200) NOT NULL,
  `Solucion` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `preguntas`
--

INSERT INTO `preguntas` (`id_Pregunta`, `Enunciado`, `Respuesta1`, `Respuesta2`, `Respuesta3`, `Solucion`) VALUES
(1, '¿Cual es la capital de Francia?', 'Paris', 'Lyon', 'Marsella', 1),
(2, '¿Cual es la capital de España?', 'Madrid', 'Barcelona', 'Toledo', 1),
(3, '¿Cual es la raiz cuadrada de 9?', '3', '6', '2', 1),
(4, '¿Cuantos dedos hay en una mano?', '5', '6', '2', 1),
(5, '¿Cuanto suma 2 + 2?', '3', '4', '6', 2),
(7, '¿Cual es la capital de Argentina?', 'Buenos Aires', 'Cordoba', 'Badajoz', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_Usuario` int(10) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `aciertos` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_Usuario`, `nombre`, `aciertos`) VALUES
(1, 'Pepe', 4),
(2, 'Jesus', 6),
(3, 'Laura', 1),
(4, 'Jose', 3),
(5, 'Antonio', 4),
(7, 'Gala', 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `preguntas`
--
ALTER TABLE `preguntas`
  ADD PRIMARY KEY (`id_Pregunta`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_Usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `preguntas`
--
ALTER TABLE `preguntas`
  MODIFY `id_Pregunta` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_Usuario` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
