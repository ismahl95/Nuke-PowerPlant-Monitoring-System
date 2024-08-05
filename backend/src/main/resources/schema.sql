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