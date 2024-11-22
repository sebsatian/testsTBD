-- Trigger para actualizar el stock del producto al crear una nueva orden
CREATE OR REPLACE FUNCTION update_product_stock() RETURNS TRIGGER AS $$
BEGIN
UPDATE product
SET stock = stock - NEW.quantity
WHERE product_id = NEW.product_id;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_product_stock
    AFTER INSERT ON order_detail
    FOR EACH ROW
    EXECUTE FUNCTION update_product_stock();

-- Trigger para almacenar las queries de inserción
CREATE OR REPLACE FUNCTION log_insert_operation() RETURNS TRIGGER AS $$
BEGIN
INSERT INTO log_insert (table_name, operation, user_id, executed_at)
VALUES (TG_TABLE_NAME, 'INSERT', current_setting('app.user_id')::INT, now());

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_log_insert
    AFTER INSERT ON product
    FOR EACH ROW
    EXECUTE FUNCTION log_insert_operation();

-- Trigger para almacenar las queries de actualización
CREATE OR REPLACE FUNCTION log_update_operation() RETURNS TRIGGER AS $$
BEGIN
INSERT INTO log_update (table_name, operation, user_id, executed_at)
VALUES (TG_TABLE_NAME, 'UPDATE', current_setting('app.user_id')::INT, now());

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_log_update
    AFTER UPDATE ON product
    FOR EACH ROW
    EXECUTE FUNCTION log_update_operation();

-- Trigger para almacenar las queries de eliminación
CREATE OR REPLACE FUNCTION log_delete_operation() RETURNS TRIGGER AS $$
BEGIN
INSERT INTO log_delete (table_name, operation, user_id, executed_at)
VALUES (TG_TABLE_NAME, 'DELETE', current_setting('app.user_id')::INT, now());

RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_log_delete
    AFTER DELETE ON product
    FOR EACH ROW
    EXECUTE FUNCTION log_delete_operation();

-- Trigger para cambiar el estado de una orden cuando el inventario esté actualizado
CREATE OR REPLACE FUNCTION trigger_update_order_status() RETURNS TRIGGER AS $$
BEGIN
    PERFORM update_order_status(NEW.order_id);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_order_status
    AFTER UPDATE ON product
    FOR EACH ROW
    WHEN (OLD.stock <> NEW.stock)
    EXECUTE FUNCTION trigger_update_order_status();

-- Trigger para calcular el total de una orden automáticamente
CREATE OR REPLACE FUNCTION trigger_calculate_order_total() RETURNS TRIGGER AS $$
BEGIN
    PERFORM calculate_order_total(NEW.order_id);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_calculate_order_total
    AFTER INSERT OR UPDATE ON order_detail
                        FOR EACH ROW
                        EXECUTE FUNCTION trigger_calculate_order_total();

-- Procedimiento almacenado para cambiar el estado de la orden
CREATE OR REPLACE FUNCTION update_order_status(order_id INT) RETURNS VOID AS $$
DECLARE
product_not_updated_count INT;
BEGIN
SELECT COUNT(*)
INTO product_not_updated_count
FROM order_detail od
         JOIN product p ON od.product_id = p.product_id
WHERE od.order_id = order_id
  AND p.stock < od.quantity;

IF product_not_updated_count = 0 THEN
UPDATE order
SET status = 'Enviada'
WHERE order_id = order_id;
END IF;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento almacenado para calcular el total de una orden
CREATE OR REPLACE FUNCTION calculate_order_total(order_id INT) RETURNS VOID AS $$
DECLARE
total_amount NUMERIC(10, 2);
BEGIN
SELECT SUM(od.quantity * p.price)
INTO total_amount
FROM order_detail od
         JOIN product p ON od.product_id = p.product_id
WHERE od.order_id = order_id;

UPDATE order
SET total = total_amount
WHERE order_id = order_id;
END;
$$ LANGUAGE plpgsql;
