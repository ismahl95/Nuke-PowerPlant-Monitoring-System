-- data.sql

-- Insertar datos en la tabla de Supplier

INSERT INTO supplier (name, contact, phone) VALUES
('EnergyTech Supplies Inc.', 'James Smith', '+1-800-123-4567'),
('Nuclear Solutions Ltd.', 'Maria Garcia', '+1-800-234-5678'),
('Advanced Safety Equipment', 'John Doe', '+1-800-345-6789'),
('ProTech Engineering', 'Sarah Lee', '+1-800-456-7890'),
('Global Energy Components', 'Michael Brown', '+1-800-567-8901'),
('SafeGuard Technologies', 'Linda White', '+1-800-678-9012'),
('Power Systems International', 'David Green', '+1-800-789-0123'),
('EnviroTech Materials', 'Emily Clark', '+1-800-890-1234'),
('Precision Instruments Co.', 'Robert Miller', '+1-800-901-2345'),
('Thermal Dynamics Corp.', 'Patricia Taylor', '+1-800-012-3456'),
('HydroTech Solutions', 'Tom Harris', '+1-800-234-6789'),
('TechnoSafe Instruments', 'Jessica Martin', '+1-800-345-7890'),
('Elite Energy Parts', 'Daniel Allen', '+1-800-456-8901'),
('SafetyFirst Gear', 'Olivia Thompson', '+1-800-567-9012'),
('Innovative Cooling Systems', 'Laura Wilson', '+1-800-678-0123');

-- Insertar datos en la tabla de Material

INSERT INTO material (name, type, quantity, unit_of_measure, supplier_id) VALUES
('Uranium Fuel Rod', 'FUEL', 100, 'kg', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Coolant Liquid', 'COOLANT', 5000, 'liters', (SELECT id FROM supplier WHERE name = 'EnergyTech Supplies Inc.')),
('Safety Helmet', 'TOOL', 150, 'units', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Miscellaneous Tools', 'OTHER', 200, 'units', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Hydrogen Fuel Cell', 'FUEL', 50, 'units', (SELECT id FROM supplier WHERE name = 'Elite Energy Parts')),
('Graphite Moderator', 'FUEL', 1200, 'kg', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Control Rods', 'TOOL', 75, 'units', (SELECT id FROM supplier WHERE name = 'ProTech Engineering')),
('Emergency Flashlights', 'TOOL', 80, 'units', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Lubricating Oil', 'COOLANT', 3000, 'liters', (SELECT id FROM supplier WHERE name = 'Nuclear Solutions Ltd.')),
('De-ionized Water', 'COOLANT', 6000, 'liters', (SELECT id FROM supplier WHERE name = 'Nuclear Solutions Ltd.')),
('Radiation Detectors', 'TOOL', 25, 'units', (SELECT id FROM supplier WHERE name = 'Precision Instruments Co.')),
('High Voltage Cables', 'OTHER', 200, 'meters', (SELECT id FROM supplier WHERE name = 'Electrical Wiring Ltd.')),
('Heat Exchanger Parts', 'OTHER', 50, 'units', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Cooling Fans', 'TOOL', 30, 'units', (SELECT id FROM supplier WHERE name = 'Power Systems International')),
('Pump Seals', 'OTHER', 150, 'units', (SELECT id FROM supplier WHERE name = 'Elite Energy Parts')),
('Heat Insulation Material', 'OTHER', 500, 'kg', (SELECT id FROM supplier WHERE name = 'Innovative Cooling Systems')),
('Welding Equipment', 'TOOL', 60, 'units', (SELECT id FROM supplier WHERE name = 'ProTech Engineering')),
('Calibration Instruments', 'TOOL', 20, 'units', (SELECT id FROM supplier WHERE name = 'Precision Instruments Co.')),
('Spare Parts for Generators', 'OTHER', 100, 'units', (SELECT id FROM supplier WHERE name = 'Thermal Dynamics Corp.')),
('Chemical Reactants', 'FUEL', 200, 'kg', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Protective Gloves', 'TOOL', 300, 'units', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('First Aid Kits', 'TOOL', 25, 'units', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Storage Containers', 'OTHER', 100, 'units', (SELECT id FROM supplier WHERE name = 'EnviroTech Materials')),
('Data Acquisition Systems', 'OTHER', 15, 'units', (SELECT id FROM supplier WHERE name = 'Innovative Cooling Systems')),
('Power Transformers', 'OTHER', 10, 'units', (SELECT id FROM supplier WHERE name = 'Thermal Dynamics Corp.')),
('Emergency Generators', 'FUEL', 5, 'units', (SELECT id FROM supplier WHERE name = 'Elite Energy Parts')),
('Cryogenic Liquids', 'COOLANT', 2000, 'liters', (SELECT id FROM supplier WHERE name = 'HydroTech Solutions')),
('Radiation Shielding Material', 'FUEL', 800, 'kg', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Safety Barriers', 'TOOL', 50, 'units', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Hydraulic Fluids', 'COOLANT', 1500, 'liters', (SELECT id FROM supplier WHERE name = 'HydroTech Solutions')),
('Electrical Wiring', 'OTHER', 5000, 'meters', (SELECT id FROM supplier WHERE name = 'Electrical Wiring Ltd.')),
('Ventilation Systems', 'TOOL', 25, 'units', (SELECT id FROM supplier WHERE name = 'Power Systems International')),
('Monitoring Systems', 'OTHER', 40, 'units', (SELECT id FROM supplier WHERE name = 'Innovative Cooling Systems')),
('Chemical Storage Tanks', 'OTHER', 20, 'units', (SELECT id FROM supplier WHERE name = 'EnviroTech Materials')),
('Precision Measurement Tools', 'TOOL', 30, 'units', (SELECT id FROM supplier WHERE name = 'Precision Instruments Co.')),
('Explosion-Proof Fixtures', 'OTHER', 10, 'units', (SELECT id FROM supplier WHERE name = 'Thermal Dynamics Corp.'));

-- Insertar datos en la tabla de ControlSystem

INSERT INTO control_system (name, type) VALUES
('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM'),
('Emergency SCADA System', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION'),
('Generator PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER'),
('Safety Instrumented System for Reactor', 'SAFETY_INSTRUMENTED_SYSTEM'),
('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE'),
('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM'),
('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION'),
('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER'),
('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM'),
('Maintenance HMI Panel', 'HUMAN_MACHINE_INTERFACE');

-- Insertar datos en la tabla de Report

INSERT INTO report (title, content, status, creation_date) VALUES
('Annual Safety Inspection Report', 'Comprehensive review of the plant’s safety systems and protocols.', 'FINALIZED', '2024-07-15T09:00:00'),
('Monthly Reactor Performance Report', 'Detailed analysis of reactor performance metrics and efficiency.', 'FINALIZED', '2024-07-01T08:30:00'),
('Emergency Drill Feedback Report', 'Assessment of the recent emergency drill including strengths and areas for improvement.', 'FINALIZED', '2024-06-20T10:00:00'),
('Maintenance Schedule Proposal', 'Proposed schedule for upcoming maintenance tasks and shutdowns.', 'DRAFT', '2024-07-10T14:45:00'),
('System Upgrade Documentation', 'Documentation for the planned upgrade of control systems.', 'DRAFT', '2024-07-05T11:20:00'),
('Incident Report - Cooling System Failure', 'Report on the recent cooling system failure incident and corrective actions taken.', 'ARCHIVED', '2024-06-25T13:30:00'),
('Audit Report on Supplier Compliance', 'Audit report evaluating the compliance of suppliers with safety and quality standards.', 'FINALIZED', '2024-06-30T15:00:00');

-- Insertar datos en la tabla Sensor
-- TODO: Relacionar el sensor con su reactor correspondiente

INSERT INTO sensor (type, location) VALUES
('TEMPERATURE', 'Reactor Core'),
('PRESSURE', 'Main Steam Line'),
('FLOW', 'Coolant System'),
('LEVEL', 'Fuel Storage Tank'),
('HUMIDITY', 'Control Room'),
('RADIACTIVITY', 'Containment Building');

-- Insertar datos en la tabla SensorReading

-- Lecturas para el sensor de temperatura
INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
(320.5, '2024-07-01T10:00:00', 1),  -- Sensor ID 1 (Reactor Core)
(315.0, '2024-07-01T11:00:00', 1),
(319.8, '2024-07-01T12:00:00', 1);
-- Lecturas para el sensor de presión
INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
(50.0, '2024-07-01T10:00:00', 2),  -- Sensor ID 2 (Main Steam Line)
(52.5, '2024-07-01T11:00:00', 2),
(48.9, '2024-07-01T12:00:00', 2);
-- Lecturas para el sensor de flujo
INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
(150.2, '2024-07-01T10:00:00', 3),  -- Sensor ID 3 (Coolant System)
(155.4, '2024-07-01T11:00:00', 3),
(148.9, '2024-07-01T12:00:00', 3);
-- Lecturas para el sensor de nivel
INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
(75.3, '2024-07-01T10:00:00', 4),  -- Sensor ID 4 (Fuel Storage Tank)
(78.0, '2024-07-01T11:00:00', 4),
(76.5, '2024-07-01T12:00:00', 4);
-- Lecturas para el sensor de humedad
INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
(45.0, '2024-07-01T10:00:00', 5),  -- Sensor ID 5 (Control Room)
(47.5, '2024-07-01T11:00:00', 5),
(44.0, '2024-07-01T12:00:00', 5);
-- Lecturas para el sensor de radiactividad
INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
(0.02, '2024-07-01T10:00:00', 6),  -- Sensor ID 6 (Containment Building)
(0.03, '2024-07-01T11:00:00', 6),
(0.01, '2024-07-01T12:00:00', 6);

-- Insertar datos en la tabla NuclearPlant

INSERT INTO nuclear_plant (name, location) VALUES
('Almaraz Nuclear Plant', 'Almaraz, Caceres'),
('Asco Nuclear Plant', 'Asco, Tarragona'),
('Cofrentes Nuclear Plant', 'Cofrentes, Valencia'),
('Vandellos Nuclear Plant', 'Vandellos, Tarragona'),
('Trillo Nuclear Plant', 'Trillo, Guadalajara'),
('Santa Maria de Garona Nuclear Plant', 'Santa Maria de Garona, Burgos'),
('Jose Cabrera Nuclear Plant', 'Jose Cabrera, Madrid'),
('Zorita Nuclear Plant', 'Zorita, Guadalajara'),
('Castellon Nuclear Plant', 'Castellon de la Plana, Castellon'),
('Navarra Nuclear Plant', 'Navarra, Navarra');

-- Insertar datos en la tabla Reactor

INSERT INTO reactor (name, type, installation_date, status, nuclear_plant_id) VALUES
('Reactor ALM-1', 'BOILING_WATER', '1980-01-15 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
('Reactor ALM-2', 'PRESSURIZED_WATER', '1985-03-22 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
('Reactor ALM-3', 'CANDU', '1992-06-18 00:00:00', 'MAINTENANCE', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
('Reactor ALM-4', 'OTHER', '2000-11-11 00:00:00', 'SHUTDOWN', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),

('Reactor ASC-1', 'PRESSURIZED_WATER', '1982-05-30 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
('Reactor ASC-2', 'BOILING_WATER', '1988-07-10 00:00:00', 'MAINTENANCE', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),

('Reactor COF-1', 'CANDU', '1995-09-25 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),

('Reactor VAN-1', 'BOILING_WATER', '1981-04-12 00:00:00', 'SHUTDOWN', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
('Reactor VAN-2', 'PRESSURIZED_WATER', '1987-08-29 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
('Reactor VAN-3', 'OTHER', '1994-02-03 00:00:00', 'MAINTENANCE', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),

('Reactor TRI-1', 'PRESSURIZED_WATER', '1990-01-19 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),

('Reactor SMG-1', 'BOILING_WATER', '1986-06-01 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),

('Reactor JCB-1', 'CANDU', '1993-03-15 00:00:00', 'SHUTDOWN', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
('Reactor JCB-2', 'PRESSURIZED_WATER', '1998-12-23 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),

('Reactor ZOR-1', 'OTHER', '2005-07-17 00:00:00', 'MAINTENANCE', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
('Reactor ZOR-2', 'BOILING_WATER', '2010-10-08 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),

('Reactor CAS-1', 'PRESSURIZED_WATER', '1991-05-20 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),

('Reactor NAV-1', 'CANDU', '2000-02-15 00:00:00', 'SHUTDOWN', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
('Reactor NAV-2', 'PRESSURIZED_WATER', '2005-09-30 00:00:00', 'OPERATIONAL', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
('Reactor NAV-3', 'BOILING_WATER', '2010-11-10 00:00:00', 'MAINTENANCE', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant'));



