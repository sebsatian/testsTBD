-- Crear la tabla de auditoría para registrar los cambios en las tablas
CREATE TABLE audit_log (
                           audit_id SERIAL PRIMARY KEY,      -- Identificador único para cada registro de auditoría
                           user_id BIGINT,                  -- ID del usuario que realizó la acción
                           action_type TEXT,                -- Tipo de acción: INSERT, UPDATE o DELETE
                           table_name TEXT,                 -- Nombre de la tabla afectada
                           executed_query TEXT,             -- Consulta SQL que se ejecutó
                           action_timestamp TIMESTAMP       -- Marca de tiempo de la acción
);

-- Crear la tabla "paid_orders" que registra las órdenes pagadas
CREATE TABLE paid_orders (
                             order_id INT PRIMARY KEY          -- ID de la orden pagada
);

-- Función que actualiza el estado de una orden a 'Pagada' cuando se inserta en "paid_orders"
CREATE OR REPLACE FUNCTION update_order_status()
    RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar el estado de la orden en la tabla "orders"
    UPDATE orders
    SET status = 'Pagada'
    WHERE order_id = NEW.order_id;

    -- Retornar la nueva fila insertada en "paid_orders"
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger que llama a la función "update_order_status" después de insertar en "paid_orders"
CREATE TRIGGER trg_update_order_status
    AFTER INSERT ON paid_orders
    FOR EACH ROW
EXECUTE FUNCTION update_order_status();

-- Función que calcula el total de una orden basada en los detalles de la misma
CREATE OR REPLACE FUNCTION calculate_recent_order_total()
    RETURNS TRIGGER AS $$
DECLARE
    order_total DECIMAL(10, 2); -- Variable para almacenar el total calculado
BEGIN
    -- Calcular el total de la orden actual
    SELECT SUM(od.quantity * od.price)
    INTO order_total
    FROM order_detail od
    WHERE od.order_id = NEW.order_id;

    -- Actualizar el total calculado en la tabla "orders"
    UPDATE orders
    SET total = order_total
    WHERE order_id = NEW.order_id;

    -- Mensaje de depuración (opcional, para verificar el proceso en logs)
    RAISE NOTICE 'Total actualizado para order_id %: %', NEW.order_id, order_total;

    -- Retornar la nueva fila para cumplir con el requisito del trigger
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger que llama a la función "calculate_recent_order_total" al actualizar el estado de una orden
CREATE TRIGGER trg_update_order_total
    AFTER UPDATE OF status ON orders
    FOR EACH ROW
    WHEN (NEW.status IS NOT NULL)
EXECUTE FUNCTION calculate_recent_order_total();

-- Función genérica para registrar cualquier cambio en las tablas auditadas
CREATE OR REPLACE FUNCTION log_table_changes()
    RETURNS TRIGGER AS $$
DECLARE
    last_user_id BIGINT; -- Variable para almacenar el ID del último usuario loggeado
BEGIN
    -- Obtener el último usuario loggeado desde la tabla "audit_log"
    SELECT al.user_id
    INTO last_user_id
    FROM audit_log al
    WHERE al.action_type = 'Login'
    ORDER BY al.action_timestamp DESC
    LIMIT 1;

    -- Registrar los cambios en la tabla de auditoría "audit_log"
    INSERT INTO audit_log (
        user_id,
        action_type,
        table_name,
        executed_query,
        action_timestamp
    )
    VALUES (
               COALESCE(last_user_id, 0), -- Usar 0 si no hay un usuario loggeado
               TG_OP,                    -- Tipo de operación (INSERT, UPDATE, DELETE)
               TG_TABLE_NAME,            -- Nombre de la tabla afectada
               current_query(),          -- Consulta SQL ejecutada
               NOW()                     -- Marca de tiempo actual
           );

    -- Retornar la nueva fila o la fila eliminada según corresponda
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear triggers para auditar cambios en tablas específicas

-- Trigger para la tabla "order_detail"
CREATE TRIGGER audit_order_detail
    AFTER INSERT OR UPDATE OR DELETE
    ON order_detail
    FOR EACH ROW
EXECUTE FUNCTION log_table_changes();

-- Trigger para la tabla "orders"
CREATE TRIGGER audit_orders
    AFTER INSERT OR UPDATE OR DELETE
    ON orders
    FOR EACH ROW
EXECUTE FUNCTION log_table_changes();

-- Trigger para la tabla "product"
CREATE TRIGGER audit_product
    AFTER INSERT OR UPDATE OR DELETE
    ON product
    FOR EACH ROW
EXECUTE FUNCTION log_table_changes();

//


CREATE OR REPLACE FUNCTION public.generate_user_action_report()
    RETURNS TABLE(user_id bigint, insert_count bigint, update_count bigint, delete_count bigint)
    LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
        SELECT
            al.user_id,
            COUNT(*) FILTER (WHERE al.action_type = 'INSERT') AS insert_count,
            COUNT(*) FILTER (WHERE al.action_type = 'UPDATE') AS update_count,
            COUNT(*) FILTER (WHERE al.action_type = 'DELETE') AS delete_count
        FROM audit_log al
        GROUP BY al.user_id
        ORDER BY insert_count DESC, update_count DESC, delete_count DESC;
END;
$function$

    //
CREATE OR REPLACE FUNCTION public.get_user_logs(user_id_input bigint)
    RETURNS TABLE(audit_id integer, action_type text, table_name text, executed_query text, action_timestamp timestamp without time zone)
    LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
        SELECT
            al.audit_id, -- Usar alias explícito
            al.action_type,
            al.table_name,
            al.executed_query,
            al.action_timestamp
        FROM audit_log al -- Asignar alias explícito
        WHERE al.user_id = user_id_input;
END;
$function$

GRANT EXECUTE ON FUNCTION generate_user_action_report() TO postgres;
GRANT EXECUTE ON FUNCTION get_user_logs(BIGINT) TO postgres;