-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-05-2024 a las 22:47:32
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `postlte`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `abo_ventas`
--

CREATE TABLE `abo_ventas` (
  `id` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `caja` int(11) NOT NULL DEFAULT 1,
  `id_credito` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `abo_ventas`
--

INSERT INTO `abo_ventas` (`id`, `monto`, `fecha`, `caja`, `id_credito`, `id_usuario`) VALUES
(1, '10.00', '2024-05-25 20:45:14', 1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajas`
--

CREATE TABLE `cajas` (
  `id` int(11) NOT NULL,
  `monto_inicial` decimal(10,2) NOT NULL,
  `monto_final` decimal(10,2) NOT NULL DEFAULT 0.00,
  `f_apertura` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `f_cierre` datetime DEFAULT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'ABIERTO',
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cajas`
--

INSERT INTO `cajas` (`id`, `monto_inicial`, `monto_final`, `f_apertura`, `f_cierre`, `estado`, `id_usuario`) VALUES
(1, '100.00', '0.00', '2024-05-25 20:37:30', NULL, 'ABIERTO', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `nombre` varchar(180) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `dni`, `nombre`, `telefono`, `direccion`) VALUES
(1, '2421678520', 'Illa Beauly', '310-459-9905', '7th Floor'),
(2, '3637516794', 'Jackquelin Klimmek', '272-474-3920', 'Apt 154'),
(3, '440375284', 'Isabelle Pearcehouse', '802-637-7479', '6th Floor'),
(4, '2921515655', 'Bondon Durant', '386-701-8419', 'Room 1042'),
(5, '3155607948', 'Melloney Fawloe', '864-643-0662', 'Suite 88'),
(6, '8291030200', 'Gates Brent', '744-991-1464', 'Suite 72'),
(7, '4869454997', 'Chadwick Brownsell', '215-706-2447', 'Suite 12'),
(8, '578298939', 'Nicole Sute', '980-880-3898', 'PO Box 69794'),
(9, '6822573423', 'Hendrik Vasyutichev', '614-187-0814', 'Apt 1071'),
(10, '2291011842', 'Floria Furlong', '268-409-4880', 'PO Box 31390'),
(11, '6467255691', 'Mathilda Pallas', '544-182-9214', '8th Floor'),
(12, '6789108397', 'Frank Greensall', '533-646-7405', 'Apt 1178'),
(13, '8840978290', 'Mufi Gurry', '876-206-4749', 'Apt 416'),
(14, '8636373630', 'Sidoney Hambleton', '674-861-1947', 'PO Box 48089'),
(15, '6817429492', 'Idelle Kingstne', '434-801-9933', '20th Floor'),
(16, '220431474', 'Thia Melior', '479-506-7049', 'Apt 1057'),
(17, '5270181989', 'Kaitlin Rudram', '414-328-9323', 'Suite 47'),
(18, '1425389545', 'Gawain Sowerbutts', '913-577-2147', '8th Floor'),
(19, '2353796915', 'Tristam Stenhouse', '613-723-8789', 'Room 1666'),
(20, '6682417977', 'Newton De Maine', '830-859-1729', 'PO Box 21993'),
(21, '4851808733', 'Arleen Smitheram', '239-682-8804', 'PO Box 34489'),
(22, '3625567079', 'Addison McIlwain', '857-903-6237', 'Apt 1643'),
(23, '4154246494', 'Janean Dehm', '453-470-7691', 'Room 1586'),
(24, '8451925892', 'Marty Kuhlen', '352-331-0478', '18th Floor'),
(25, '6801396642', 'Gaven Dawley', '722-548-6920', '19th Floor'),
(26, '4369862497', 'Maudie Lockier', '827-951-8059', 'Room 909'),
(27, '2902314332', 'Levy Cutsforth', '166-153-5629', 'Suite 42'),
(28, '1818728823', 'Myrta Wyldish', '538-935-8145', '1st Floor'),
(29, '7127911014', 'Greta Berkowitz', '357-276-5971', 'PO Box 80274'),
(30, '3053004742', 'Matti Manwell', '603-408-8005', 'PO Box 65039'),
(31, '5371544093', 'Bertrando Ales0', '197-375-8934', 'Apt 1439'),
(32, '5357776531', 'Nealy Richley', '660-455-2726', 'Suite 40'),
(33, '3171838044', 'Reider Wasiel', '446-967-4661', '8th Floor'),
(34, '5298183728', 'Denys Humpherson', '548-176-9696', 'Suite 97'),
(35, '625421239', 'Nissa Qualtro', '584-995-0617', 'Suite 47'),
(36, '8525425807', 'Ashely Towlson', '845-513-8857', 'PO Box 34556'),
(37, '6438212702', 'Herve Maciejewski', '449-209-6419', 'Suite 85'),
(38, '7369711591', 'Isadore Gethins', '708-948-0193', 'PO Box 35587'),
(39, '3766676689', 'Holden Fielders', '437-927-6415', 'Suite 41'),
(40, '1246760494', 'Adan Gocher', '576-867-9814', 'PO Box 7516'),
(41, '8218223666', 'Allina Rymill', '864-420-3194', 'Room 1594'),
(42, '1177332041', 'Winthrop Mitcham', '770-953-7044', '18th Floor'),
(43, '2668679165', 'Cherice Fulcher', '174-105-6719', '10th Floor'),
(44, '1565760230', 'Papagena Colbridge', '812-989-0747', 'Apt 204'),
(45, '217105445', 'Jennine Danes', '745-586-3364', 'PO Box 38061'),
(46, '8571985420', 'Lois Damrel', '809-395-0658', 'Room 1660'),
(47, '2197342423', 'Cull Camilleri', '454-790-9326', 'Room 1349'),
(48, '6225537856', 'Fanny Pringley', '748-377-0854', 'Suite 74'),
(49, '8543478521', 'Broderic Izacenko', '366-610-0718', 'Room 1816'),
(50, '6032488229', 'Nananne Dornan', '391-425-0131', 'Suite 44');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `id` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` varchar(20) NOT NULL,
  `estado` varchar(30) NOT NULL DEFAULT 'COMPLETADO',
  `id_proveedor` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `compras`
--

INSERT INTO `compras` (`id`, `total`, `fecha`, `estado`, `id_proveedor`, `id_usuario`) VALUES
(1, '32.00', '2024-04-25', 'COMPLETADO', 7, 1),
(2, '79.00', '2024-05-25', 'COMPLETADO', 14, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `config`
--

CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `ruc` varchar(15) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `direccion` text NOT NULL,
  `mensaje` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `config`
--

INSERT INTO `config` (`id`, `ruc`, `nombre`, `telefono`, `direccion`, `mensaje`) VALUES
(1, '65479877', 'Tecnology', '957847894', 'Lima - Perú', 'Gracias por la compra');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cred_ventas`
--

CREATE TABLE `cred_ventas` (
  `id` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cred_ventas`
--

INSERT INTO `cred_ventas` (`id`, `id_venta`) VALUES
(1, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `id` int(11) NOT NULL,
  `id_pro` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `subTotal` double NOT NULL,
  `id_venta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `id_pro`, `cantidad`, `precio`, `subTotal`, `id_venta`) VALUES
(1, 500, 3, '14.00', 42, 1),
(2, 498, 2, '12.00', 24, 1),
(3, 496, 2, '11.00', 22, 1),
(4, 493, 2, '11.00', 22, 1),
(5, 478, 1, '11.00', 11, 2),
(6, 477, 2, '10.00', 20, 2),
(7, 452, 1, '13.00', 13, 3),
(8, 459, 6, '14.00', 84, 3),
(9, 497, 2, '10.00', 20, 4),
(10, 492, 2, '12.00', 24, 4),
(11, 490, 5, '11.00', 55, 4),
(12, 488, 1, '10.00', 10, 4),
(13, 492, 2, '12.00', 24, 5),
(14, 488, 12, '10.00', 120, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_compras`
--

CREATE TABLE `detalle_compras` (
  `id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `subTotal` decimal(10,2) NOT NULL,
  `id_pro` int(11) NOT NULL,
  `id_compra` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_compras`
--

INSERT INTO `detalle_compras` (`id`, `cantidad`, `precio`, `subTotal`, `id_pro`, `id_compra`) VALUES
(1, 1, '11.00', '11.00', 493, 1),
(2, 1, '11.00', '11.00', 490, 1),
(3, 1, '10.00', '10.00', 488, 1),
(4, 1, '14.00', '14.00', 362, 2),
(5, 3, '13.00', '39.00', 367, 2),
(6, 2, '13.00', '26.00', 369, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `codigo` varchar(50) NOT NULL,
  `nombre` text NOT NULL,
  `proveedor` int(11) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `precio` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `codigo`, `nombre`, `proveedor`, `stock`, `precio`) VALUES
(1, '6524410332', 'Bar Nature Valley', 18, 61, '14.00'),
(2, '8373648050', 'Cognac - Courvaisier', 11, 51, '10.00'),
(3, '1046110170', 'Sobe - Orange Carrot', 8, 42, '10.00'),
(4, '1439812159', 'Shichimi Togarashi Peppeers', 15, 99, '13.00'),
(5, '3213111344', 'Lamb - Pieces, Diced', 5, 56, '11.00'),
(6, '911236443', 'Bread - Sour Sticks With Onion', 4, 32, '12.00'),
(7, '7699625275', 'Juice - Cranberry 284ml', 18, 96, '10.00'),
(8, '8022241391', 'Muffin Mix - Blueberry', 1, 79, '12.00'),
(9, '4824413151', 'Cheese - Valancey', 9, 23, '13.00'),
(10, '4524925189', 'Liqueur Banana, Ramazzotti', 12, 86, '14.00'),
(11, '2381248624', 'Coconut - Shredded, Unsweet', 4, 42, '14.00'),
(12, '2474163255', 'Oil - Peanut', 4, 44, '13.00'),
(13, '1636153974', 'Pork - Ham, Virginia', 3, 99, '11.00'),
(14, '8490327627', 'Artichokes - Knobless, White', 8, 93, '13.00'),
(15, '7117715072', 'Container Clear 8 Oz', 19, 36, '10.00'),
(16, '3130031545', 'Crush - Grape, 355 Ml', 8, 79, '10.00'),
(17, '8315541722', 'Bread - Multigrain, Loaf', 13, 29, '11.00'),
(18, '2153872151', 'Pastry - Apple Muffins - Mini', 16, 28, '14.00'),
(19, '1532722271', 'Calvados - Boulard', 15, 78, '13.00'),
(20, '8335175675', 'V8 - Vegetable Cocktail', 13, 22, '12.00'),
(21, '8755566683', 'Squash - Sunburst', 17, 50, '13.00'),
(22, '7153132642', 'Sesame Seed Black', 17, 62, '14.00'),
(23, '2624716891', 'Soap - Mr.clean Floor Soap', 8, 67, '12.00'),
(24, '3805841514', 'Tomatoes - Orange', 15, 78, '10.00'),
(25, '5572988362', 'Garlic - Elephant', 1, 72, '12.00'),
(26, '87860648', 'Nut - Almond, Blanched, Whole', 15, 92, '14.00'),
(27, '5024611607', 'Lettuce - Sea / Sea Asparagus', 14, 78, '11.00'),
(28, '5302363787', 'Cookie - Oatmeal', 7, 81, '12.00'),
(29, '5961834696', 'Nut - Walnut, Pieces', 19, 35, '11.00'),
(30, '3589830753', 'Sauce - Sesame Thai Dressing', 10, 66, '10.00'),
(31, '6368707570', 'Lobster - Base', 2, 63, '10.00'),
(32, '7143228592', 'Pepper - Red Bell', 10, 63, '11.00'),
(33, '5651459808', 'Beer - Muskoka Cream Ale', 1, 37, '14.00'),
(34, '2457371319', 'Jam - Blackberry, 20 Ml Jar', 3, 41, '11.00'),
(35, '3222206335', 'Hagen Daza - Dk Choocolate', 8, 89, '14.00'),
(36, '7777999932', 'Olives - Morracan Dired', 10, 64, '12.00'),
(37, '146731680', 'Thyme - Dried', 2, 73, '13.00'),
(38, '7292339380', 'Cake - French Pear Tart', 9, 60, '13.00'),
(39, '6418895506', 'Wine - Mondavi Coastal Private', 6, 58, '11.00'),
(40, '1447944577', 'Bread - English Muffin', 13, 32, '12.00'),
(41, '1418278262', 'Orange Roughy 4/6 Oz', 3, 78, '12.00'),
(42, '8452152904', 'Wine - Lou Black Shiraz', 16, 60, '12.00'),
(43, '1456467632', 'Pork - Bones', 9, 31, '14.00'),
(44, '3032705082', 'Cassis', 6, 79, '13.00'),
(45, '741112488', 'Extract - Almond', 5, 99, '11.00'),
(46, '7398487595', 'Asparagus - Mexican', 11, 36, '10.00'),
(47, '5899692840', 'Coffee - 10oz Cup 92961', 13, 67, '11.00'),
(48, '8868889388', 'Oil - Shortening - All - Purpose', 6, 52, '13.00'),
(49, '7537662569', 'Wine - Chateau Bonnet', 14, 73, '14.00'),
(50, '4548213115', 'Cleaner - Lime Away', 14, 93, '11.00'),
(51, '1893059216', 'Coriander - Ground', 17, 85, '10.00'),
(52, '5494035141', 'Macaroons - Homestyle Two Bit', 4, 29, '13.00'),
(53, '7112413176', 'Pepsi, 355 Ml', 12, 67, '13.00'),
(54, '7605545188', 'Melon - Cantaloupe', 6, 62, '10.00'),
(55, '7989281914', 'Scallops - U - 10', 7, 98, '13.00'),
(56, '6769391761', 'Octopus', 2, 57, '13.00'),
(57, '3448669182', 'Allspice - Jamaican', 2, 83, '10.00'),
(58, '3396253981', 'Campari', 19, 30, '14.00'),
(59, '1047284344', 'Milk Powder', 5, 83, '14.00'),
(60, '2991164797', 'Pepper - Green Thai', 6, 47, '14.00'),
(61, '6627709041', 'Coffee - Irish Cream', 17, 66, '13.00'),
(62, '5089548119', 'Mace', 14, 49, '14.00'),
(63, '8090613007', 'Beef - Short Loin', 14, 61, '14.00'),
(64, '7506341005', 'Wine - Red, Marechal Foch', 1, 44, '12.00'),
(65, '8693679218', 'Creme De Menth - White', 16, 59, '10.00'),
(66, '428161402', 'Garam Masala Powder', 14, 92, '12.00'),
(67, '156842911', 'Pepper - Chipotle, Canned', 13, 80, '10.00'),
(68, '3635717753', 'Cheese - Havarti, Roasted Garlic', 5, 97, '14.00'),
(69, '2598629467', 'Wine - Gewurztraminer Pierre', 7, 100, '14.00'),
(70, '7882237023', 'Octopus', 15, 53, '12.00'),
(71, '3555613462', 'Oil - Food, Lacquer Spray', 14, 38, '14.00'),
(72, '5468159346', 'Alize Gold Passion', 4, 55, '10.00'),
(73, '5737010294', 'Coffee - Beans, Whole', 20, 62, '14.00'),
(74, '7173892680', 'Corn - Cream, Canned', 11, 38, '13.00'),
(75, '6602304517', 'Wine - Port Late Bottled Vintage', 7, 71, '13.00'),
(76, '5368471009', 'Chocolate Eclairs', 15, 56, '10.00'),
(77, '1743375239', 'Fib N9 - Prague Powder', 2, 32, '13.00'),
(78, '8799367920', 'Icecream - Dstk Super Cone', 2, 66, '12.00'),
(79, '3223788985', 'Beans - Soya Bean', 3, 90, '12.00'),
(80, '4607812904', 'Uniform Linen Charge', 7, 86, '14.00'),
(81, '6640628365', 'Macaroons - Homestyle Two Bit', 9, 91, '11.00'),
(82, '7396568954', 'Pepper - Green, Chili', 13, 23, '13.00'),
(83, '1298678152', 'Bread Country Roll', 2, 52, '13.00'),
(84, '868666879', 'Garam Marsala', 20, 67, '10.00'),
(85, '7793212573', 'Brandy - Bar', 19, 29, '11.00'),
(86, '5640277977', 'Tea - Grapefruit Green Tea', 15, 32, '13.00'),
(87, '7612570136', 'Lentils - Red, Dry', 10, 84, '14.00'),
(88, '142073480', 'Beef - Sushi Flat Iron Steak', 17, 32, '10.00'),
(89, '4548184368', 'Peas Snow', 19, 69, '13.00'),
(90, '137676488', 'Chicken - Thigh, Bone In', 8, 94, '10.00'),
(91, '3052950541', 'Sauce - Soy Low Sodium - 3.87l', 14, 59, '13.00'),
(92, '3678424636', 'Longos - Lasagna Veg', 19, 92, '13.00'),
(93, '2067061995', 'Fish - Bones', 20, 34, '11.00'),
(94, '703282590', 'Vinegar - Balsamic', 8, 64, '11.00'),
(95, '2270700851', 'Wine - Dubouef Macon - Villages', 1, 59, '12.00'),
(96, '3928560556', 'Pineapple - Regular', 14, 20, '14.00'),
(97, '3047824160', 'Beef - Salted', 18, 35, '13.00'),
(98, '3633129293', 'Wine - White, Lindemans Bin 95', 10, 74, '13.00'),
(99, '2056947726', 'Wine - Barolo Fontanafredda', 9, 90, '10.00'),
(100, '2908873074', 'Lady Fingers', 18, 33, '14.00'),
(101, '6182338226', 'Tea - Vanilla Chai', 18, 51, '13.00'),
(102, '6539701232', 'Liquid Aminios Acid - Braggs', 4, 33, '14.00'),
(103, '3436073052', 'Garlic', 14, 33, '12.00'),
(104, '3867845876', 'Beef - Prime Rib Aaa', 2, 80, '10.00'),
(105, '4634183758', 'Sauce - Hp', 8, 21, '11.00'),
(106, '6813290037', 'Pear - Packum', 19, 21, '12.00'),
(107, '3967374051', 'Mix - Cocktail Ice Cream', 8, 68, '12.00'),
(108, '3450445858', 'Cheese - La Sauvagine', 1, 89, '10.00'),
(109, '4003682362', 'Bread Base - Toscano', 7, 37, '13.00'),
(110, '302329356', 'Tomatoes - Roma', 11, 55, '14.00'),
(111, '4695712848', 'Cup Translucent 9 Oz', 1, 43, '11.00'),
(112, '374739378', 'Onions - Red', 14, 84, '12.00'),
(113, '3535717672', 'Beef - Ox Tongue', 19, 86, '12.00'),
(114, '502516310', 'Sauce - Vodka Blush', 4, 41, '13.00'),
(115, '2915807274', 'Plastic Arrow Stir Stick', 9, 68, '14.00'),
(116, '364459146', 'Table Cloth 144x90 White', 13, 80, '14.00'),
(117, '6720155259', 'Broccoli - Fresh', 3, 89, '12.00'),
(118, '3650444055', 'Gatorade - Xfactor Berry', 13, 77, '10.00'),
(119, '5642452945', 'Beef - Chuck, Boneless', 4, 76, '14.00'),
(120, '2981166831', 'Sobe - Orange Carrot', 19, 42, '10.00'),
(121, '3667253403', 'Hipnotiq Liquor', 4, 80, '14.00'),
(122, '8309691106', 'Mini - Vol Au Vents', 4, 88, '13.00'),
(123, '5504396640', 'Pastry - Banana Muffin - Mini', 16, 73, '12.00'),
(124, '2093743926', 'Pastrami', 19, 29, '14.00'),
(125, '3098272323', 'Nut - Pecan, Pieces', 6, 58, '14.00'),
(126, '449443745', 'Pie Filling - Apple', 4, 23, '10.00'),
(127, '4252923288', 'Soho Lychee Liqueur', 17, 85, '12.00'),
(128, '5295259616', 'Sole - Fillet', 20, 63, '14.00'),
(129, '8575325294', 'Beer - Sleemans Honey Brown', 17, 92, '11.00'),
(130, '6375196129', 'Lid - 16 Oz And 32 Oz', 17, 61, '10.00'),
(131, '5249030753', 'Creamers - 10%', 11, 75, '13.00'),
(132, '4547113471', 'Shrimp - Black Tiger 16/20', 6, 61, '13.00'),
(133, '4451901819', 'Halibut - Steaks', 6, 85, '12.00'),
(134, '3854547486', 'Banana - Green', 3, 87, '10.00'),
(135, '366238127', 'Mushroom - Morel Frozen', 12, 98, '10.00'),
(136, '3241816688', 'V8 - Berry Blend', 18, 32, '14.00'),
(137, '4748148418', 'Wine - Blue Nun Qualitatswein', 1, 35, '11.00'),
(138, '5005792213', 'Pasta - Orecchiette', 1, 91, '10.00'),
(139, '6496606966', 'Maple Syrup', 8, 96, '10.00'),
(140, '2801376251', 'Nantuket Peach Orange', 19, 43, '12.00'),
(141, '7108103252', 'Butcher Twine 4r', 3, 58, '13.00'),
(142, '2775158063', 'Liners - Baking Cups', 2, 84, '12.00'),
(143, '5231278590', 'Nut - Hazelnut, Whole', 13, 82, '10.00'),
(144, '4603679114', 'Langers - Cranberry Cocktail', 17, 34, '14.00'),
(145, '4466629543', 'Food Colouring - Red', 10, 84, '14.00'),
(146, '2936209540', 'Oyster - In Shell', 7, 43, '11.00'),
(147, '665572280', 'Rum - Spiced, Captain Morgan', 10, 53, '14.00'),
(148, '4288809013', 'Container - Clear 16 Oz', 19, 52, '11.00'),
(149, '7798560870', 'Soup - French Onion', 19, 42, '13.00'),
(150, '5876350814', 'Seedlings - Clamshell', 20, 74, '13.00'),
(151, '7189767490', 'Tumeric', 5, 57, '11.00'),
(152, '2923056610', 'Pop Shoppe Cream Soda', 19, 65, '12.00'),
(153, '1065780800', 'Turkey - Breast, Bone - In', 11, 83, '14.00'),
(154, '1754155877', 'Table Cloth 53x69 White', 11, 96, '11.00'),
(155, '7478426693', 'Lamb - Whole Head Off,nz', 13, 61, '10.00'),
(156, '4932641428', 'Nut - Chestnuts, Whole', 20, 38, '13.00'),
(157, '1511021809', 'Mushroom - Porcini Frozen', 2, 75, '14.00'),
(158, '4253901650', 'Parsley Italian - Fresh', 6, 46, '10.00'),
(159, '4330536315', 'Salmon - Atlantic, Fresh, Whole', 6, 91, '13.00'),
(160, '298835445', 'Beer - Pilsner Urquell', 14, 77, '12.00'),
(161, '4175991209', 'Nestea - Ice Tea, Diet', 11, 32, '11.00'),
(162, '2219767570', 'Wine - Alsace Riesling Reserve', 18, 45, '12.00'),
(163, '4039809813', 'Bread - Pumpernickel', 3, 33, '10.00'),
(164, '1989204637', 'Longos - Chicken Curried', 9, 83, '10.00'),
(165, '7742683403', 'Chicken Giblets', 9, 93, '10.00'),
(166, '8493214965', 'Marzipan 50/50', 6, 77, '14.00'),
(167, '4156322613', 'Chocolate - Pistoles, Lactee, Milk', 17, 26, '10.00'),
(168, '8781094048', 'Pastry - Butterscotch Baked', 8, 70, '11.00'),
(169, '8803981191', 'Blueberries - Frozen', 5, 95, '13.00'),
(170, '7331095518', 'Wine - Zonnebloem Pinotage', 10, 68, '10.00'),
(171, '8135332013', 'Cookies - Englishbay Chochip', 7, 83, '11.00'),
(172, '6088681477', 'Mace Ground', 10, 71, '13.00'),
(173, '155147074', 'Octopus', 18, 37, '14.00'),
(174, '844894516', 'Cookies Almond Hazelnut', 4, 39, '14.00'),
(175, '5092101433', 'Amarula Cream', 14, 60, '10.00'),
(176, '2699073586', 'Pasta - Rotini, Colour, Dry', 18, 66, '10.00'),
(177, '1353767186', 'Bread - Assorted Rolls', 17, 91, '14.00'),
(178, '6996302767', 'Shrimp - Black Tiger 8 - 12', 5, 82, '11.00'),
(179, '5261629933', 'Amaretto', 4, 85, '14.00'),
(180, '4526292036', 'Juice - Apple, 341 Ml', 11, 42, '13.00'),
(181, '980844531', 'Beer - Steamwhistle', 8, 48, '14.00'),
(182, '7822294163', 'Juice - Grape, White', 2, 46, '12.00'),
(183, '4711666978', 'Pepper - Pablano', 8, 71, '14.00'),
(184, '7228500984', 'Berry Brulee', 9, 21, '11.00'),
(185, '3881420290', 'Cardamon Ground', 10, 90, '12.00'),
(186, '3929967039', 'Veal - Tenderloin, Untrimmed', 16, 84, '12.00'),
(187, '1639841288', 'Carbonated Water - Wildberry', 19, 66, '12.00'),
(188, '7709395217', 'Juice - Propel Sport', 19, 29, '12.00'),
(189, '2692954806', 'Puree - Mango', 14, 82, '14.00'),
(190, '8743165369', 'Longos - Penne With Pesto', 10, 81, '11.00'),
(191, '6811815717', 'Beef Wellington', 11, 36, '13.00'),
(192, '3708373794', 'Pastry - Banana Muffin - Mini', 3, 51, '11.00'),
(193, '1322018242', 'Lamb - Leg, Diced', 5, 35, '11.00'),
(194, '7910069237', 'Lamb - Shoulder', 17, 80, '14.00'),
(195, '5020367323', 'Cranberries - Fresh', 15, 45, '13.00'),
(196, '8143958033', 'Soup - Campbells, Lentil', 15, 77, '13.00'),
(197, '7866317654', 'Scallops - U - 10', 15, 29, '13.00'),
(198, '1373560451', 'Wine - Red, Gallo, Merlot', 8, 66, '10.00'),
(199, '390533897', 'Lamb - Shoulder, Boneless', 10, 65, '10.00'),
(200, '5358543521', 'Potatoes - Mini Red', 3, 94, '11.00'),
(201, '365513484', 'Bread - White Epi Baguette', 1, 64, '10.00'),
(202, '5902618088', 'Arctic Char - Fresh, Whole', 1, 37, '11.00'),
(203, '6282221889', 'Crab - Blue, Frozen', 1, 79, '10.00'),
(204, '5327362228', 'Water - Aquafina Vitamin', 2, 21, '14.00'),
(205, '7760274094', 'Lamb - Pieces, Diced', 19, 48, '10.00'),
(206, '57229052', 'Lettuce - Iceberg', 17, 93, '13.00'),
(207, '664724356', 'Long Island Ice Tea', 18, 100, '13.00'),
(208, '3885873460', 'Chocolate - Unsweetened', 13, 49, '10.00'),
(209, '5573852173', 'Wine - Magnotta - Cab Sauv', 7, 57, '12.00'),
(210, '837140478', 'Sugar - White Packet', 6, 43, '10.00'),
(211, '1652972043', 'Beer - Alexander Kieths, Pale Ale', 12, 94, '11.00'),
(212, '1512786720', 'Soup - Knorr, Veg / Beef', 9, 53, '13.00'),
(213, '5914615077', 'Muffin Mix - Carrot', 10, 40, '14.00'),
(214, '1524047920', 'Paper Cocktail Umberlla 80 - 180', 13, 69, '11.00'),
(215, '5711261754', 'Yogurt - Plain', 17, 97, '13.00'),
(216, '759195421', 'Hipnotiq Liquor', 12, 82, '10.00'),
(217, '2242429789', 'Wine - Pinot Noir Stoneleigh', 15, 100, '11.00'),
(218, '1635966323', 'Pate - Cognac', 20, 66, '13.00'),
(219, '2604862503', 'Mushroom - Crimini', 11, 43, '11.00'),
(220, '7365286422', 'Dawn Professionl Pot And Pan', 17, 49, '14.00'),
(221, '7957359614', 'Lentils - Green Le Puy', 19, 57, '14.00'),
(222, '7632613634', 'Soup - Base Broth Chix', 14, 79, '12.00'),
(223, '3184664540', 'Temperature Recording Station', 1, 43, '10.00'),
(224, '3308763919', 'Wine - Niagara,vqa Reisling', 4, 65, '11.00'),
(225, '1923051730', 'Chocolate Eclairs', 9, 69, '14.00'),
(226, '4505073732', 'Wine - Masi Valpolocell', 3, 20, '14.00'),
(227, '483491913', 'Egg Patty Fried', 13, 71, '14.00'),
(228, '223275390', 'Chinese Foods - Plain Fried Rice', 20, 21, '13.00'),
(229, '6070190157', 'Wine - Carmenere Casillero Del', 2, 35, '10.00'),
(230, '3381716034', 'Wine - Cotes Du Rhone', 20, 89, '14.00'),
(231, '5950712337', 'Mayonnaise', 2, 23, '14.00'),
(232, '6791134278', 'Lotus Root', 3, 78, '14.00'),
(233, '3924549068', 'Nut - Peanut, Roasted', 8, 71, '13.00'),
(234, '1344527336', 'Puree - Mocha', 16, 92, '14.00'),
(235, '6525337876', 'Lamb - Shanks', 4, 73, '14.00'),
(236, '616989524', 'Butter - Salted, Micro', 11, 25, '12.00'),
(237, '6298563770', 'Sesame Seed', 5, 64, '12.00'),
(238, '994603935', 'Tabasco Sauce, 2 Oz', 11, 39, '14.00'),
(239, '6763935571', 'Almonds Ground Blanched', 6, 59, '12.00'),
(240, '7609729113', 'Bacardi Limon', 2, 87, '10.00'),
(241, '8668126383', 'Paper Towel Touchless', 5, 22, '12.00'),
(242, '2461904049', 'Pastry - Baked Cinnamon Stick', 3, 74, '10.00'),
(243, '8365474732', 'Spinach - Packaged', 12, 63, '14.00'),
(244, '810119864', 'Pears - Bartlett', 14, 62, '10.00'),
(245, '5829082309', 'Beef - Chuck, Boneless', 1, 50, '11.00'),
(246, '2611312661', 'Oil - Canola', 11, 45, '10.00'),
(247, '2154425634', 'French Pastries', 13, 96, '12.00'),
(248, '1387605652', 'Shiratamako - Rice Flour', 6, 71, '14.00'),
(249, '7471509587', 'Cheese - Parmesan Cubes', 13, 92, '13.00'),
(250, '4176568864', 'Wine - Prem Select Charddonany', 14, 50, '14.00'),
(251, '4034878427', 'Veal - Round, Eye Of', 6, 56, '11.00'),
(252, '2147757148', 'Steampan - Half Size Shallow', 11, 60, '14.00'),
(253, '1771097358', 'Crush - Grape, 355 Ml', 18, 59, '11.00'),
(254, '8448546501', 'Cheese - Brie,danish', 5, 35, '10.00'),
(255, '8775594535', 'Pork - Shoulder', 12, 57, '10.00'),
(256, '7717172685', 'Pumpkin - Seed', 6, 44, '13.00'),
(257, '993023426', 'Bread - Crumbs, Bulk', 17, 26, '12.00'),
(258, '6885158931', 'Soup - Beef Conomme, Dry', 14, 37, '13.00'),
(259, '4003658617', 'Dawn Professionl Pot And Pan', 1, 35, '13.00'),
(260, '3280327972', 'Bread - Pullman, Sliced', 11, 49, '11.00'),
(261, '4518500547', 'Cod - Black Whole Fillet', 1, 98, '14.00'),
(262, '744965679', 'Bread - Triangle White', 14, 23, '12.00'),
(263, '3435982338', 'Beans - Fava, Canned', 2, 28, '11.00'),
(264, '129391356', 'Milk - 2%', 11, 78, '12.00'),
(265, '2337206713', 'Soup - Clam Chowder, Dry Mix', 4, 74, '10.00'),
(266, '7108614228', 'Beef - Ox Tail, Frozen', 3, 45, '11.00'),
(267, '1665938261', 'Tomato Puree', 19, 69, '11.00'),
(268, '5305424856', 'Sauce - Fish 25 Ozf Bottle', 10, 85, '12.00'),
(269, '8016595142', 'Pasta - Bauletti, Chicken White', 6, 78, '14.00'),
(270, '6551268283', 'Beef - Inside Round', 12, 36, '10.00'),
(271, '3406704077', 'Peach - Fresh', 11, 84, '11.00'),
(272, '588756999', 'Veal - Liver', 17, 100, '10.00'),
(273, '1683327291', 'Energy - Boo - Koo', 17, 98, '12.00'),
(274, '6386604564', 'Cilantro / Coriander - Fresh', 16, 36, '13.00'),
(275, '5461476756', 'Oil - Pumpkinseed', 2, 81, '13.00'),
(276, '496207718', 'Peach - Halves', 19, 40, '13.00'),
(277, '4879306958', 'Fork - Plastic', 1, 66, '12.00'),
(278, '3968267770', 'Chocolate - Mi - Amere Semi', 7, 69, '10.00'),
(279, '5086672053', 'Bread - Pumpernickel', 4, 62, '13.00'),
(280, '1607875723', 'Wine - Lamancha Do Crianza', 15, 82, '13.00'),
(281, '1167157335', 'Steam Pan - Half Size Deep', 3, 73, '12.00'),
(282, '7021421039', 'Chicken - Base', 12, 63, '12.00'),
(283, '6109481645', 'Oranges - Navel, 72', 6, 31, '12.00'),
(284, '4616247061', 'Pepsi, 355 Ml', 20, 36, '11.00'),
(285, '6850168595', 'Muffin Mix - Carrot', 8, 56, '11.00'),
(286, '8378484505', 'Praline Paste', 19, 80, '12.00'),
(287, '1534094098', 'Energy - Boo - Koo', 10, 27, '10.00'),
(288, '2264182318', 'Salt - Seasoned', 11, 47, '14.00'),
(289, '8157549766', 'Bread - Assorted Rolls', 3, 67, '11.00'),
(290, '1742030836', 'Corn Syrup', 6, 79, '13.00'),
(291, '4981238561', 'Soap - Hand Soap', 12, 30, '11.00'),
(292, '6211671381', 'Beef - Chuck, Boneless', 2, 100, '10.00'),
(293, '5988003708', 'Goulash Seasoning', 19, 76, '14.00'),
(294, '4241144085', 'Mint - Fresh', 10, 86, '11.00'),
(295, '8151555977', 'Rice - Aborio', 11, 77, '13.00'),
(296, '761895714', 'Hummus - Spread', 15, 48, '10.00'),
(297, '5504458714', 'Table Cloth - 53x69 Colour', 2, 37, '10.00'),
(298, '5689765996', 'Lamb - Loin Chops', 6, 72, '14.00'),
(299, '8444200045', 'Sprouts - Baby Pea Tendrils', 17, 86, '11.00'),
(300, '6800477058', 'Lamb Shoulder Boneless Nz', 2, 24, '12.00'),
(301, '1870947861', 'Flower - Commercial Bronze', 20, 39, '10.00'),
(302, '477340290', 'Cake - Box Window 10x10x2.5', 18, 86, '13.00'),
(303, '851360040', 'Silicone Paper 16.5x24', 2, 53, '12.00'),
(304, '5621465903', 'Carrots - Mini, Stem On', 17, 39, '14.00'),
(305, '654937933', 'Steam Pan - Half Size Deep', 4, 47, '10.00'),
(306, '252017219', 'Clementine', 9, 88, '13.00'),
(307, '5070856713', 'Cheese - Ricotta', 17, 92, '14.00'),
(308, '4911177927', 'Beans - Kidney, Red Dry', 9, 21, '12.00'),
(309, '3072113674', 'Maintenance Removal Charge', 7, 84, '10.00'),
(310, '1023336507', 'Cabbage - Savoy', 8, 76, '11.00'),
(311, '6081159817', 'Dry Ice', 5, 51, '10.00'),
(312, '5246579232', 'Flavouring - Orange', 5, 90, '12.00'),
(313, '4256719382', 'Fond - Neutral', 11, 20, '13.00'),
(314, '1032729164', 'Coffee Cup 12oz 5342cd', 8, 92, '10.00'),
(315, '4124824005', 'Chocolate - Semi Sweet, Calets', 20, 98, '11.00'),
(316, '1504348779', 'Syrup - Monin, Amaretta', 13, 21, '12.00'),
(317, '2803441577', 'Cup - Translucent 7 Oz Clear', 11, 55, '10.00'),
(318, '6471638460', 'Cake - Pancake', 5, 27, '10.00'),
(319, '2543750117', 'Onions - Spanish', 8, 57, '13.00'),
(320, '3856205524', 'Bandage - Fexible 1x3', 3, 40, '14.00'),
(321, '3288004187', 'Shrimp - 16 - 20 Cooked, Peeled', 19, 52, '14.00'),
(322, '3952227152', 'Muffin - Zero Transfat', 11, 78, '11.00'),
(323, '4489561843', 'Soup - Campbells, Chix Gumbo', 15, 74, '13.00'),
(324, '7352070384', 'Vaccum Bag - 14x20', 5, 90, '11.00'),
(325, '8481127411', 'Sparkling Wine - Rose, Freixenet', 17, 77, '14.00'),
(326, '4912169286', 'Island Oasis - Magarita Mix', 5, 73, '10.00'),
(327, '5663515840', 'Beef - Ground, Extra Lean, Fresh', 6, 69, '13.00'),
(328, '5806523608', 'Shiratamako - Rice Flour', 19, 79, '10.00'),
(329, '5835890012', 'Nacho Chips', 8, 92, '10.00'),
(330, '394200428', 'Initation Crab Meat', 11, 95, '12.00'),
(331, '1711791885', 'Bread - Mini Hamburger Bun', 8, 84, '14.00'),
(332, '639931462', 'Pumpkin', 11, 92, '14.00'),
(333, '2033177239', 'Magnotta Bel Paese Red', 4, 83, '14.00'),
(334, '2037709906', 'Food Colouring - Red', 15, 49, '10.00'),
(335, '320366149', 'Gherkin - Sour', 2, 30, '11.00'),
(336, '7674160415', 'Bread - Assorted Rolls', 10, 69, '14.00'),
(337, '3650376031', 'Sparkling Wine - Rose, Freixenet', 13, 45, '14.00'),
(338, '7524460790', 'Wine - Merlot Vina Carmen', 10, 99, '14.00'),
(339, '6431279465', 'Onions - Red', 16, 65, '10.00'),
(340, '8519181444', 'Lemonade - Natural, 591 Ml', 10, 54, '14.00'),
(341, '6733775637', 'Wine - Mondavi Coastal Private', 6, 32, '13.00'),
(342, '5280167743', 'Puree - Guava', 17, 74, '13.00'),
(343, '648191419', 'Wine - Soave Folonari', 19, 25, '14.00'),
(344, '6336848078', 'Yeast Dry - Fleischman', 9, 55, '11.00'),
(345, '6661409766', 'Onions - White', 12, 29, '12.00'),
(346, '4695813677', 'Pepper - Red Bell', 3, 26, '14.00'),
(347, '2459400863', 'Basil - Thai', 19, 89, '11.00'),
(348, '3768998947', 'Sugar - White Packet', 8, 52, '11.00'),
(349, '1424571782', 'Veal - Loin', 17, 70, '11.00'),
(350, '6620844601', 'Pop Shoppe Cream Soda', 7, 20, '10.00'),
(351, '3609914567', 'Split Peas - Green, Dry', 12, 71, '13.00'),
(352, '6772475095', 'Garbag Bags - Black', 9, 81, '14.00'),
(353, '6086378516', 'Blackberries', 19, 88, '11.00'),
(354, '890606501', 'Turkey - Breast, Smoked', 5, 29, '13.00'),
(355, '5389399043', 'Vinegar - Cider', 6, 64, '10.00'),
(356, '2548843348', 'Nantucket Apple Juice', 15, 25, '12.00'),
(357, '7419552276', 'Crawfish', 18, 95, '14.00'),
(358, '6522843907', 'Tomatoes - Cherry, Yellow', 16, 60, '12.00'),
(359, '3061736562', 'Cake - Lemon Chiffon', 10, 46, '10.00'),
(360, '1247476496', 'Wine - Riesling Alsace Ac 2001', 11, 22, '13.00'),
(361, '5154101804', 'Sour Puss Raspberry', 6, 75, '11.00'),
(362, '6547336763', 'Vinegar - Balsamic, White', 2, 97, '14.00'),
(363, '7694260996', 'The Pop Shoppe - Root Beer', 9, 34, '10.00'),
(364, '5040426909', 'Flour - Whole Wheat', 10, 67, '12.00'),
(365, '3979640642', 'Duck - Breast', 19, 37, '12.00'),
(366, '7121250546', 'Broom - Angled', 10, 62, '10.00'),
(367, '3628584767', 'Tuna - Sushi Grade', 1, 23, '13.00'),
(368, '4537100059', 'Pate - Cognac', 6, 49, '13.00'),
(369, '2788629372', 'Rhubarb', 20, 76, '13.00'),
(370, '5803832987', 'Mcgillicuddy Vanilla Schnap', 18, 64, '11.00'),
(371, '7861617474', 'Vinegar - Cider', 2, 46, '12.00'),
(372, '1915780041', 'Jam - Raspberry,jar', 8, 77, '13.00'),
(373, '868453346', 'Sauce - Demi Glace', 20, 44, '14.00'),
(374, '5755540056', 'Icecream - Dibs', 3, 49, '11.00'),
(375, '6715967992', 'Pasta - Penne Primavera, Single', 15, 35, '14.00'),
(376, '3884605800', 'Beef - Texas Style Burger', 6, 97, '13.00'),
(377, '6514657691', 'Swiss Chard', 19, 77, '12.00'),
(378, '5611450615', 'Trout - Smoked', 11, 97, '12.00'),
(379, '1861160212', 'Juice - Apple Cider', 10, 36, '13.00'),
(380, '2298726490', 'The Pop Shoppe - Black Cherry', 14, 97, '13.00'),
(381, '2191798153', 'Wine - Chateau Aqueria Tavel', 13, 99, '14.00'),
(382, '3653331230', 'Pants Custom Dry Clean', 15, 78, '10.00'),
(383, '1919095485', 'Wine - Chardonnay Mondavi', 16, 32, '13.00'),
(384, '273402666', 'Red Snapper - Fresh, Whole', 3, 56, '14.00'),
(385, '1013354152', 'Octopus - Baby, Cleaned', 6, 61, '12.00'),
(386, '2350626184', 'Magnotta - Bel Paese White', 15, 94, '10.00'),
(387, '7635934865', 'Cheese - St. Andre', 17, 100, '13.00'),
(388, '2957967252', 'Sauce - Balsamic Viniagrette', 15, 31, '14.00'),
(389, '401368810', 'Wine - Chateau Timberlay', 12, 80, '11.00'),
(390, '5463258503', 'Squash - Pepper', 14, 98, '10.00'),
(391, '814274642', 'Grouper - Fresh', 2, 22, '11.00'),
(392, '8674006251', 'Chilli Paste, Hot Sambal Oelek', 12, 24, '13.00'),
(393, '8401914208', 'Muffin Mix - Chocolate Chip', 9, 31, '12.00'),
(394, '4598497864', 'Pork - Backfat', 12, 49, '14.00'),
(395, '8126296696', 'Tea - Mint', 1, 38, '11.00'),
(396, '1562515212', 'Longos - Lasagna Veg', 18, 43, '10.00'),
(397, '5200975852', 'Barramundi', 8, 34, '13.00'),
(398, '1881602606', 'Buffalo - Tenderloin', 16, 24, '10.00'),
(399, '1697496865', 'Butter - Pod', 18, 71, '12.00'),
(400, '7899551869', 'Ham - Cooked', 13, 21, '12.00'),
(401, '5641469246', 'Truffle - Whole Black Peeled', 18, 27, '11.00'),
(402, '73257243', 'Southern Comfort', 14, 42, '11.00'),
(403, '7761276895', 'Mousse - Banana Chocolate', 10, 29, '13.00'),
(404, '2602147289', 'Strawberries', 13, 21, '10.00'),
(405, '4686763045', 'Cheese - Mascarpone', 4, 51, '11.00'),
(406, '4442708834', 'Cheese - Cambozola', 6, 48, '10.00'),
(407, '4429135406', 'Soup - Cream Of Potato / Leek', 13, 70, '12.00'),
(408, '7116089899', 'Syrup - Kahlua Chocolate', 14, 38, '13.00'),
(409, '8586900307', 'Oil - Sesame', 11, 38, '11.00'),
(410, '1724304649', 'Dry Ice', 20, 21, '10.00'),
(411, '7606638019', 'Salmon - Smoked, Sliced', 2, 66, '10.00'),
(412, '1206252364', 'Gherkin - Sour', 7, 88, '11.00'),
(413, '1143036860', 'Juice - Ocean Spray Cranberry', 11, 48, '13.00'),
(414, '6439934237', 'Cod - Salted, Boneless', 20, 22, '10.00'),
(415, '6561646893', 'Ecolab - Solid Fusion', 17, 67, '14.00'),
(416, '4512544213', 'Wine - Magnotta - Pinot Gris Sr', 19, 100, '10.00'),
(417, '4884831925', 'Bread Fig And Almond', 5, 91, '11.00'),
(418, '2365263588', 'Lamb - Shoulder', 8, 57, '13.00'),
(419, '6077636674', 'Rabbit - Whole', 14, 88, '13.00'),
(420, '3643687537', 'Apricots Fresh', 13, 86, '13.00'),
(421, '6018878761', 'Red Snapper - Fresh, Whole', 7, 50, '10.00'),
(422, '7182828759', 'Spice - Chili Powder Mexican', 2, 75, '13.00'),
(423, '1836118136', 'Cake - French Pear Tart', 2, 27, '10.00'),
(424, '1536068738', 'Pie Pecan', 12, 57, '14.00'),
(425, '4662838240', 'Oxtail - Cut', 17, 73, '14.00'),
(426, '2058996505', 'Oil - Food, Lacquer Spray', 4, 62, '10.00'),
(427, '7201961099', 'Crackers - Graham', 16, 90, '10.00'),
(428, '728949571', 'Grenadine', 11, 61, '10.00'),
(429, '1535554991', 'Butter - Pod', 20, 82, '14.00'),
(430, '640485104', 'Wine - Bouchard La Vignee Pinot', 5, 42, '12.00'),
(431, '8402569873', 'Sea Urchin', 16, 24, '12.00'),
(432, '952368011', 'Soup - Campbells Bean Medley', 2, 91, '13.00'),
(433, '476543160', 'Cocoa Powder - Dutched', 18, 94, '10.00'),
(434, '6068065356', 'Cheese - Bocconcini', 19, 82, '11.00'),
(435, '3600004446', 'Garbage Bag - Clear', 13, 40, '13.00'),
(436, '1642362588', 'Container - Clear 32 Oz', 8, 63, '13.00'),
(437, '5912720277', 'Tomatoes - Plum, Canned', 3, 70, '12.00'),
(438, '2664349229', 'Sprouts - Peppercress', 1, 43, '14.00'),
(439, '171842963', 'Poppy Seed', 5, 62, '10.00'),
(440, '8720180247', 'Liqueur - Melon', 18, 49, '11.00'),
(441, '6264851253', 'Sping Loaded Cup Dispenser', 19, 54, '10.00'),
(442, '2236819882', 'Squid U5 - Thailand', 18, 35, '13.00'),
(443, '8061841300', 'Pasta - Penne Primavera, Single', 8, 34, '11.00'),
(444, '530671217', 'Soup - Cream Of Broccoli', 5, 80, '13.00'),
(445, '4383412324', 'Cheese - Oka', 8, 21, '12.00'),
(446, '851314521', 'Mousse - Banana Chocolate', 9, 47, '14.00'),
(447, '5193141146', 'Ginger - Ground', 1, 69, '11.00'),
(448, '3754333557', 'Veal - Knuckle', 7, 99, '14.00'),
(449, '3937838203', 'Muffin Batt - Carrot Spice', 9, 43, '10.00'),
(450, '3202324806', 'Wine - Winzer Krems Gruner', 13, 64, '10.00'),
(451, '5269675639', 'Nut - Walnut, Pieces', 5, 72, '10.00'),
(452, '2288900601', 'C - Plus, Orange', 5, 78, '13.00'),
(453, '283974468', 'Orange - Blood', 4, 27, '10.00'),
(454, '7420393254', 'Juice - Orange, 341 Ml', 17, 91, '12.00'),
(455, '8083657086', 'Watercress', 6, 68, '11.00'),
(456, '1091260156', 'Lemon Grass', 16, 70, '11.00'),
(457, '7451382031', 'Dates', 19, 72, '12.00'),
(458, '8269197640', 'Beer - True North Strong Ale', 4, 89, '10.00'),
(459, '360701107', 'Pepper - Scotch Bonnet', 3, 48, '14.00'),
(460, '3249348005', 'Chilli Paste, Sambal Oelek', 4, 74, '11.00'),
(461, '8754455407', 'Salt - Rock, Course', 3, 78, '10.00'),
(462, '5817254809', 'Soup - Cream Of Broccoli, Dry', 8, 49, '12.00'),
(463, '6365606409', 'Cheese - St. Paulin', 13, 86, '14.00'),
(464, '7196448498', 'Radish', 2, 29, '13.00'),
(465, '3651475798', 'Pastry - Mini French Pastries', 2, 96, '14.00'),
(466, '8227321897', 'Spinach - Spinach Leaf', 13, 92, '14.00'),
(467, '3935155454', 'Carbonated Water - Blackberry', 8, 36, '12.00'),
(468, '6075686584', 'Chocolate - Pistoles, White', 5, 35, '13.00'),
(469, '5825036962', 'Ostrich - Prime Cut', 4, 74, '12.00'),
(470, '8814705611', 'Everfresh Products', 1, 48, '14.00'),
(471, '5834294353', 'Wine - Tribal Sauvignon', 15, 46, '12.00'),
(472, '2071420995', 'Pepper - Cayenne', 1, 66, '14.00'),
(473, '828864842', 'Ginger - Fresh', 17, 97, '13.00'),
(474, '2366500424', 'Artichoke - Fresh', 19, 80, '13.00'),
(475, '6214701046', 'Potatoes - Pei 10 Oz', 3, 48, '12.00'),
(476, '2204279293', 'Beans - Kidney, Canned', 1, 78, '11.00'),
(477, '3796520336', 'Nescafe - Frothy French Vanilla', 2, 25, '10.00'),
(478, '3779234905', 'Seedlings - Clamshell', 12, 79, '11.00'),
(479, '4193491956', 'Chocolate - Feathers', 19, 93, '12.00'),
(480, '5310767809', 'Soup Campbells Mexicali Tortilla', 16, 49, '13.00'),
(481, '5754333047', 'Onions - Spanish', 1, 56, '14.00'),
(482, '3666007846', 'Syrup - Monin - Blue Curacao', 4, 26, '11.00'),
(483, '4070826427', 'Beer - Alexander Kieths, Pale Ale', 7, 39, '11.00'),
(484, '1619149921', 'Yoghurt Tubes', 2, 42, '12.00'),
(485, '2143428680', 'Ketchup - Tomato', 15, 66, '10.00'),
(486, '7756542885', 'Venison - Denver Leg Boneless', 13, 34, '11.00'),
(487, '6896507227', 'Sauce - Apple, Unsweetened', 19, 84, '11.00'),
(488, '4030660049', 'Olives - Kalamata', 20, 31, '10.00'),
(489, '923522792', 'Nut - Peanut, Roasted', 18, 40, '10.00'),
(490, '3103294770', 'Pasta - Rotini, Colour, Dry', 2, 62, '11.00'),
(491, '6761885023', 'Juice - Lagoon Mango', 18, 99, '10.00'),
(492, '8782980633', 'Cocktail Napkin Blue', 11, 31, '12.00'),
(493, '7619318989', 'Sauce - Gravy, Au Jus, Mix', 2, 21, '11.00'),
(494, '561362873', 'Skirt - 24 Foot', 3, 92, '10.00'),
(495, '5817826159', 'Soup - Knorr, Classic Can. Chili', 17, 20, '10.00'),
(496, '3957166282', 'Soup Knorr Chili With Beans', 7, 21, '11.00'),
(497, '3634999383', 'Beef - Rib Roast, Cap On', 4, 18, '10.00'),
(498, '3936111242', 'Peach - Fresh', 20, 74, '12.00'),
(499, '5710822291', 'Beef Wellington', 14, 96, '13.00'),
(500, '4565201985', 'V8 - Berry Blend', 10, 39, '14.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` int(11) NOT NULL,
  `ruc` varchar(15) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `ruc`, `nombre`, `telefono`, `direccion`) VALUES
(1, '2369957220', 'Terri Yewen', '491-919-5066', 'Suite 95'),
(2, '6554185991', 'Cayla Bracci', '425-932-3767', 'PO Box 56638'),
(3, '8163890270', 'Penelope Legge', '422-355-9905', 'Suite 30'),
(4, '4337628790', 'Danit Burder', '209-689-1485', 'Room 1875'),
(5, '5608528249', 'Ulrick Oakinfold', '589-737-0111', 'Apt 714'),
(6, '1686680805', 'Hewie Leversha', '939-107-9929', 'Room 518'),
(7, '5901667763', 'Wesley Dodsworth', '986-810-8037', 'Suite 25'),
(8, '2617132067', 'Monique Frantzen', '468-687-2644', 'Suite 56'),
(9, '612546120', 'Ulrikaumeko Tumber', '978-997-7995', '19th Floor'),
(10, '2751368066', 'Ambrosio Fayerbrother', '471-337-5153', 'Apt 1232'),
(11, '3246194586', 'Ludovika Argrave', '848-472-2834', 'Suite 15'),
(12, '4600781078', 'Roldan Izkovitz', '254-311-5207', 'Apt 350'),
(13, '8579291662', 'Misty Shelley', '505-677-6604', 'Room 632'),
(14, '1848568661', 'Jacenta Stroud', '774-251-8030', 'PO Box 66919'),
(15, '1268780692', 'Darby Saffer', '211-930-3724', 'Suite 76'),
(16, '6200343118', 'Jordanna Trapp', '189-111-9517', 'Apt 1603'),
(17, '1485197223', 'Robby Spellesy', '704-829-6686', '14th Floor'),
(18, '4300421210', 'Gerianna Bernardo', '695-253-0129', 'Suite 55'),
(19, '7155455415', 'Aluino Rouch', '760-931-6096', 'Room 523'),
(20, '3435574019', 'Gwynne Macconachy', '304-797-0484', 'PO Box 47234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `correo` varchar(200) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `pass`, `rol`) VALUES
(1, 'SISTEMAS FREE', 'admin@gmail.com', 'admin', 'Administrador'),
(3, 'ANA LOPEZ', 'ana.info199@gmaiil.com', '12345', 'Asistente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` int(11) NOT NULL,
  `cliente` int(11) NOT NULL,
  `vendedor` varchar(60) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` varchar(20) NOT NULL,
  `metodo` varchar(20) NOT NULL,
  `caja` int(11) NOT NULL DEFAULT 1,
  `estado` varchar(20) NOT NULL DEFAULT 'COMPLETADO',
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `cliente`, `vendedor`, `total`, `fecha`, `metodo`, `caja`, `estado`, `id_usuario`) VALUES
(1, 1, 'SISTEMAS FREE', '110.00', '2024-03-25', 'CONTADO', 1, 'COMPLETADO', 1),
(2, 17, 'SISTEMAS FREE', '31.00', '2024-04-25', 'CONTADO', 1, 'COMPLETADO', 1),
(3, 17, 'SISTEMAS FREE', '97.00', '2024-05-25', 'CONTADO', 1, 'COMPLETADO', 1),
(4, 14, 'SISTEMAS FREE', '109.00', '2024-05-25', 'CONTADO', 1, 'COMPLETADO', 1),
(5, 4, 'SISTEMAS FREE', '144.00', '2024-05-25', 'CREDITO', 1, 'COMPLETADO', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `abo_ventas`
--
ALTER TABLE `abo_ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_credito` (`id_credito`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `cajas`
--
ALTER TABLE `cajas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_proveedor` (`id_proveedor`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cred_ventas`
--
ALTER TABLE `cred_ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_venta` (`id_venta`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_pro` (`id_pro`);

--
-- Indices de la tabla `detalle_compras`
--
ALTER TABLE `detalle_compras`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pro` (`id_pro`),
  ADD KEY `id_compra` (`id_compra`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `proveedor` (`proveedor`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente` (`cliente`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `abo_ventas`
--
ALTER TABLE `abo_ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cajas`
--
ALTER TABLE `cajas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `compras`
--
ALTER TABLE `compras`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `config`
--
ALTER TABLE `config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cred_ventas`
--
ALTER TABLE `cred_ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `detalle_compras`
--
ALTER TABLE `detalle_compras`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=501;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `abo_ventas`
--
ALTER TABLE `abo_ventas`
  ADD CONSTRAINT `abo_ventas_ibfk_1` FOREIGN KEY (`id_credito`) REFERENCES `cred_ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `abo_ventas_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cajas`
--
ALTER TABLE `cajas`
  ADD CONSTRAINT `cajas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `compras_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cred_ventas`
--
ALTER TABLE `cred_ventas`
  ADD CONSTRAINT `cred_ventas_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD CONSTRAINT `detalle_ibfk_1` FOREIGN KEY (`id_pro`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detalle_ibfk_2` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `detalle_compras`
--
ALTER TABLE `detalle_compras`
  ADD CONSTRAINT `detalle_compras_ibfk_1` FOREIGN KEY (`id_pro`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detalle_compras_ibfk_2` FOREIGN KEY (`id_compra`) REFERENCES `compras` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`proveedor`) REFERENCES `proveedor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
