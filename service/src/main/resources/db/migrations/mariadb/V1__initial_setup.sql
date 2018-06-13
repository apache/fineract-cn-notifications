--
-- Licensed to the Apache Software Foundation (ASF) under one
-- or more contributor license agreements.  See the NOTICE file
-- distributed with this work for additional information
-- regarding copyright ownership.  The ASF licenses this file
-- to you under the Apache License, Version 2.0 (the
-- "License"); you may not use this file except in compliance
-- with the License.  You may obtain a copy of the License at
--
--   http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an
-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
-- KIND, either express or implied.  See the License for the
-- specific language governing permissions and limitations
-- under the License.
--

-- -----------------------------------------------------
-- Table `fineract-cn-notification`.`email_gateway_configurations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fineract-cn-notification`.`email_gateway_configurations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `identifer` VARCHAR(45) NULL DEFAULT NULL,
  `host` VARCHAR(45) NOT NULL,
  `port` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `app_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `fineract-cn-notification`.`sms_gateway_configurations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fineract-cn-notification`.`sms_gateway_configurations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `identifier` VARCHAR(45) NULL DEFAULT NULL,
  `accountsid` VARCHAR(255) NOT NULL,
  `auth_token` VARCHAR(255) NOT NULL,
  `option` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `fineract-cn-notification`.`templates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fineract-cn-notification`.`templates` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- ------------------------------------------------------

CREATE TABLE sample (
  id BIGINT NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(8) NOT NULL,
  payload VARCHAR(512) NULL,
  CONSTRAINT notification_pk PRIMARY KEY (id)
);

CREATE TABLE template_sample (
  id BIGINT NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(8) NOT NULL,
  payload VARCHAR(512) NULL,
  CONSTRAINT template_sample_pk PRIMARY KEY (id)
);