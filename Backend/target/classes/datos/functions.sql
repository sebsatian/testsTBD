CREATE TABLE paid_orders (
                             order_id INT PRIMARY KEY
);
CREATE OR REPLACE FUNCTION update_order_status()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE orders
    SET status = 'Pagada'
    WHERE order_id = NEW.order_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_order_status
    AFTER INSERT ON paid_orders
    FOR EACH ROW
EXECUTE FUNCTION update_order_status();



CREATE OR REPLACE FUNCTION calculate_recent_order_total()
    RETURNS TRIGGER AS $$
DECLARE
    order_total DECIMAL(10, 2); -- Cambiamos el nombre de la variable
BEGIN
    -- Calcular el total de la orden actual (pasada desde el trigger)
    SELECT SUM(od.quantity * od.price)
    INTO order_total
    FROM order_detail od
    WHERE od.order_id = NEW.order_id;

    -- Actualizar el total en la tabla orders
    UPDATE orders
    SET total = order_total
    WHERE order_id = NEW.order_id;

    -- Mensaje de depuración (puedes quitarlo si no es necesario)
    RAISE NOTICE 'Total actualizado para order_id %: %', NEW.order_id, order_total;

    -- Retornar la fila modificada para cumplir con el requisito del trigger
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_update_order_total
    AFTER UPDATE OF status ON orders
    FOR EACH ROW
    WHEN (NEW.status IS NOT NULL)
EXECUTE FUNCTION calculate_recent_order_total();
