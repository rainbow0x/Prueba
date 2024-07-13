CREATE TABLE empleados (
    ID VARCHAR(10),
    nombre VARCHAR(100),
    empresa VARCHAR(100)
);

INSERT INTO empleados (ID, nombre, empresa)
VALUES
    ('0021', 'Joel Marquinez', 'Assa'),
    ('0011', 'Manuel Martinez', 'Profuturo'),
    ('01231', 'Julian Rodriguez', 'Banco General');

CREATE OR REPLACE FUNCTION obtener_informacion_empleados()
RETURNS TABLE (
    ID varchar(10),
    nombre varchar(100),
    empresa varchar(100)
)
AS $$
BEGIN
    RETURN QUERY
    SELECT empleados.ID, empleados.nombre, empleados.empresa
    FROM empleados;
END;
$$
LANGUAGE plpgsql;

SELECT * FROM obtener_informacion_empleados();