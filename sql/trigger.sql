USE dbistuerCarroCompra;
DROP TRIGGER IF EXISTS carritoINSERT;
DELIMITER //
CREATE TRIGGER carritoINSERT AFTER INSERT ON carrito
FOR EACH ROW
BEGIN
	UPDATE producte SET disponibilitat=(disponibilitat-1) WHERE producte.id = NEW.idProducte;
END//

USE dbistuerCarroCompra;
DROP TRIGGER IF EXISTS carritoDELETE;
DELIMITER //
CREATE TRIGGER carritoDELETE AFTER DELETE ON carrito
FOR EACH ROW
BEGIN
	IF @trigger_on = 1 THEN
		UPDATE producte SET disponibilitat=(disponibilitat+OLD.cantitat) WHERE producte.id = OLD.idProducte;
	END IF;
END;
//

