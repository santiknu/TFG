-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BASE_DATOS_TFG` DEFAULT CHARACTER SET utf8 ;
USE `BASE_DATOS_TFG` ;

-- -----------------------------------------------------
-- Table `mydb`.`VEHICULOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`VEHICULOS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `MODELO` VARCHAR(30) NOT NULL,
  `MARCA` VARCHAR(45) NOT NULL,
  `MATRICULA` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `MATRICULA_UNIQUE` (`MATRICULA` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CLIENTES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`CLIENTES` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NOMBRE` VARCHAR(30) NOT NULL,
  `TELEFONO` VARCHAR(12) NOT NULL,
  `EMAIL` VARCHAR(45) NULL,
  `LAVADOS` INT NULL,
  `VEHICULOS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `VEHICULOS_ID`),
  INDEX `fk_CLIENTES_VEHICULOS1_idx` (`VEHICULOS_ID` ASC) VISIBLE,
  CONSTRAINT `fk_CLIENTES_VEHICULOS1`
    FOREIGN KEY (`VEHICULOS_ID`)
    REFERENCES `mydb`.`VEHICULOS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PROVEEDORES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PROVEEDORES` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NOMBRE` VARCHAR(45) NOT NULL,
  `TIPO` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EMPLEADOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`EMPLEADOS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `APELLIDOS` VARCHAR(45) NOT NULL,
  `NOMBRE` VARCHAR(45) NOT NULL,
  `CARGO` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`HERRAMIENTAS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`HERRAMIENTAS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TIPO` VARCHAR(45) NOT NULL,
  `NOMBRE` VARCHAR(45) NOT NULL,
  `REPUESTOS` TINYINT NOT NULL,
  `AVERIAS` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PRODUCTOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PRODUCTOS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TIPO` VARCHAR(45) NOT NULL,
  `NOMBRE` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`REGISTROS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`REGISTROS` (
  `FECHA` DATE NOT NULL,
  `HORA ENTRADA` INT NOT NULL,
  `HORA SALIDA` INT NOT NULL,
  `EMPLEADOS` INT NOT NULL,
  `CLIENTES` INT NULL,
  PRIMARY KEY (`FECHA`, `HORA ENTRADA`, `HORA SALIDA`),
  INDEX `FK_EMPLEADOS_idx` (`EMPLEADOS` ASC) VISIBLE,
  INDEX `FK_CLIENTES_idx` (`CLIENTES` ASC) VISIBLE,
  CONSTRAINT `FK_EMPLEADOS_REGISTROS`
    FOREIGN KEY (`EMPLEADOS`)
    REFERENCES `mydb`.`EMPLEADOS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CLIENTES`
    FOREIGN KEY (`CLIENTES`)
    REFERENCES `mydb`.`CLIENTES` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`COMPRAS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`COMPRAS` (
  `FECHA` DATE NOT NULL,
  `PROVEEDOR` INT NOT NULL,
  `EMPLEADO` INT NOT NULL,
  `HERRAMIENTA` INT NULL,
  `PRODUCTO` INT NULL,
  `PRECIO` DECIMAL(4,2) NOT NULL,
  PRIMARY KEY (`FECHA`),
  INDEX `FK_PROVEEDORES_idx` (`PROVEEDOR` ASC) VISIBLE,
  INDEX `FK_EMPLEADOS_idx` (`EMPLEADO` ASC) VISIBLE,
  INDEX `FK_HERRAMIENTAS_idx` (`HERRAMIENTA` ASC) VISIBLE,
  INDEX `FK_PRODUCTOS_idx` (`PRODUCTO` ASC) VISIBLE,
  CONSTRAINT `FK_PROVEEDORES`
    FOREIGN KEY (`PROVEEDOR`)
    REFERENCES `mydb`.`PROVEEDORES` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_EMPLEADOS_COMPRAS`
    FOREIGN KEY (`EMPLEADO`)
    REFERENCES `mydb`.`EMPLEADOS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_HERRAMIENTAS`
    FOREIGN KEY (`HERRAMIENTA`)
    REFERENCES `mydb`.`HERRAMIENTAS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRODUCTOS`
    FOREIGN KEY (`PRODUCTO`)
    REFERENCES `mydb`.`PRODUCTOS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
