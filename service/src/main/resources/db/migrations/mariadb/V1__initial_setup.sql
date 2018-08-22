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
-- Table sms_gateway_configurations
-- -----------------------------------------------------
CREATE TABLE sms_gateway_configurations (
  id INT(11) NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(45) NULL DEFAULT NULL,
  account_sid VARCHAR(255) NOT NULL,
  auth_token VARCHAR(255) NOT NULL,
  sender_number VARCHAR(255) NOT NULL,
  state VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table email_gateway_configurations
-- -----------------------------------------------------
CREATE TABLE email_gateway_configurations (
  id INT(11) NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(45) NULL DEFAULT NULL,
  host VARCHAR(45) NOT NULL,
  port VARCHAR(45) NOT NULL,
  username VARCHAR(45) NOT NULL,
  app_password VARCHAR(45) NOT NULL,
  protocol VARCHAR(45),
  smtp_auth VARCHAR (45),
  start_tls VARCHAR (45),
  state VARCHAR(45),
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table sms_templates
-- -----------------------------------------------------
CREATE TABLE sms_templates (
  id INT(11) NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(45) NULL DEFAULT NULL,
  event VARCHAR(45) NULL DEFAULT NULL,
  url VARCHAR(255) NOT NULL,
  created_on TIMESTAMP(3) null,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table email_templates
-- -----------------------------------------------------
CREATE TABLE email_templates (
  id INT(11) NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(45) NULL DEFAULT NULL,
  service VARCHAR(45) NULL DEFAULT NULL,
  url VARCHAR(255) NOT NULL,
  created_on TIMESTAMP(3) null,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
