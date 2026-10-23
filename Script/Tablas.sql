

DELETE FROM tablas WHERE idtabla='voculares' AND idelemento='login_credentials';
INSERT INTO tablas(idtabla,idelemento,dato1,dato2) VALUES('voculares','login_credentials','HcJV6W85L8d9','ctello@csn.coop');

//Se usa para crear identidad
DELETE FROM tablas WHERE idtabla='voculares' AND idelemento ='signer_data';
INSERT INTO tablas(idtabla,idelemento,dato2,dato3) VALUES('voculares','signer_data','5bd09a7c08228b112371bdc3','67bf9b6e29787732747805e2');

DELETE FROM TABLAS WHERE idtabla='voculares' AND idelemento='user-ws';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2) VALUES('voculares','user-ws','User for api autenticate','vocularesqa','$2a$10$k2jH1kbE.3P9audrn41HpePZOiZ6723YUaG.N/2K3vO3qAlnmdPmO');

DELETE FROM TABLAS WHERE idtabla='voculares' AND idelemento='flow_id_adicional';
INSERT INTO TABLAS(idtabla,idelemento,dato1,dato2) VALUES('voculares','flow_id_adicional','');

DELETE FROM TABLAS WHERE idtabla='voculares' AND idelemento='flow_id_adicional';
INSERT INTO TABLAS(idtabla,idelemento,dato1,dato2) VALUES('voculares','flow_id_adicional_hija','');

DELETE FROM TABLAS WHERE idtabla='voculares' AND idelemento='flow_id_adicional';
INSERT INTO TABLAS(idtabla,idelemento,dato1,dato2) VALUES('voculares','flow_id_adicional_hija2','');


/*Para uso de ambiente demo o prod*/
DELETE FROM TABLAS WHERE idtabla='voculares' AND idelemento='ambiente';
INSERT INTO TABLAS(idtabla,idelemento,dato1) VALUES('voculares','ambiente','demo');
