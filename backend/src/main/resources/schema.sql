-- src/main/resources/schema.sql

-- Vista de los materiales y sus proveedores

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

-- Vista de los reactores

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

-- Vista de lecturas de los sensores por reactor y su planta asociada

CREATE VIEW plant_reactor_sensor_readings AS
SELECT
    np.name AS plant_name,
    r.name AS reactor_name,
    s.type AS sensor_type,
    s.location AS sensor_location,
    sr.measurement_value,
    sr.date AS reading_date
FROM
    nuclear_plant np
JOIN
    reactor r ON np.id = r.nuclear_plant_id
JOIN
    sensor s ON r.id = s.reactor_id
JOIN
    sensor_reading sr ON s.id = sr.sensor_id;


