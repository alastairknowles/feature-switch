CREATE SCHEMA IF NOT EXISTS feature_switch;
CREATE USER 'tide'@'%' IDENTIFIED BY 'feature';
GRANT ALL ON feature_switch.* TO 'tide'@'%' IDENTIFIED BY 'feature';
