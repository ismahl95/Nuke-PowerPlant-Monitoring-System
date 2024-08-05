-- src/main/resources/schema.sql

CREATE VIEW material_supplier_view AS
SELECT
    m.name AS "MATERIAL NAME",
    m.type AS TYPE,
    m.quantity AS QUANTITY,
    m.unit_of_measure AS "MEASURE UNIT",
    s.name AS "SUPPLIER NAME",
    s.contact AS CONTACT,
    s.phone AS PHONE
FROM
    material m
JOIN
    supplier s
ON
    m.supplier_id = s.id;

-- Crear una vista para mostrar sensores y sus lecturas

CREATE VIEW sensor_readings_summary AS
SELECT
    s.type AS "TYPE",
    s.location AS "LOCATION",
    sr.measurement_value AS "VALUE",
    sr.date AS "DATE"
FROM
    sensor s
JOIN
    sensor_reading sr
ON
    s.id = sr.sensor_id;


CREATE VIEW reactor_overview AS
SELECT
    r.name AS "REACTOR NAME",
    np.location AS "PLANT LOCATION",
    r.type AS "TYPE",
    r.installation_date AS "INSTALLATION DATE",
    r.status AS "STATUS"
FROM
    reactor r
JOIN
    nuclear_plant np
ON
    r.nuclear_plant_id = np.id;

