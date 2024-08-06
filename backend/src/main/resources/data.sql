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
('Uranium Fuel Rod', 'FUEL', 100, 'KILOGRAMS', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Coolant Liquid', 'COOLANT', 5000, 'LITERS', (SELECT id FROM supplier WHERE name = 'EnergyTech Supplies Inc.')),
('Safety Helmet', 'TOOL', 150, 'PIECES', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Miscellaneous Tools', 'OTHER', 200, 'PIECES', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Hydrogen Fuel Cell', 'FUEL', 50, 'PIECES', (SELECT id FROM supplier WHERE name = 'Elite Energy Parts')),
('Graphite Moderator', 'FUEL', 1200, 'KILOGRAMS', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Control Rods', 'TOOL', 75, 'PIECES', (SELECT id FROM supplier WHERE name = 'ProTech Engineering')),
('Emergency Flashlights', 'TOOL', 80, 'PIECES', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Lubricating Oil', 'COOLANT', 3000, 'LITERS', (SELECT id FROM supplier WHERE name = 'Nuclear Solutions Ltd.')),
('De-ionized Water', 'COOLANT', 6000, 'LITERS', (SELECT id FROM supplier WHERE name = 'Nuclear Solutions Ltd.')),
('Radiation Detectors', 'TOOL', 25, 'PIECES', (SELECT id FROM supplier WHERE name = 'Precision Instruments Co.')),
('High Voltage Cables', 'OTHER', 200, 'METERS', (SELECT id FROM supplier WHERE name = 'Electrical Wiring Ltd.')),
('Heat Exchanger Parts', 'OTHER', 50, 'PIECES', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Cooling Fans', 'TOOL', 30, 'PIECES', (SELECT id FROM supplier WHERE name = 'Power Systems International')),
('Pump Seals', 'OTHER', 150, 'PIECES', (SELECT id FROM supplier WHERE name = 'Elite Energy Parts')),
('Heat Insulation Material', 'OTHER', 500, 'KILOGRAMS', (SELECT id FROM supplier WHERE name = 'Innovative Cooling Systems')),
('Welding Equipment', 'TOOL', 60, 'PIECES', (SELECT id FROM supplier WHERE name = 'ProTech Engineering')),
('Calibration Instruments', 'TOOL', 20, 'PIECES', (SELECT id FROM supplier WHERE name = 'Precision Instruments Co.')),
('Spare Parts for Generators', 'OTHER', 100, 'PIECES', (SELECT id FROM supplier WHERE name = 'Thermal Dynamics Corp.')),
('Chemical Reactants', 'FUEL', 200, 'KILOGRAMS', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Protective Gloves', 'TOOL', 300, 'PIECES', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('First Aid Kits', 'TOOL', 25, 'PIECES', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Storage Containers', 'OTHER', 100, 'PIECES', (SELECT id FROM supplier WHERE name = 'EnviroTech Materials')),
('Data Acquisition Systems', 'OTHER', 15, 'PIECES', (SELECT id FROM supplier WHERE name = 'Innovative Cooling Systems')),
('Power Transformers', 'OTHER', 10, 'PIECES', (SELECT id FROM supplier WHERE name = 'Thermal Dynamics Corp.')),
('Emergency Generators', 'FUEL', 5, 'PIECES', (SELECT id FROM supplier WHERE name = 'Elite Energy Parts')),
('Cryogenic Liquids', 'COOLANT', 2000, 'LITERS', (SELECT id FROM supplier WHERE name = 'HydroTech Solutions')),
('Radiation Shielding Material', 'FUEL', 800, 'KILOGRAMS', (SELECT id FROM supplier WHERE name = 'Global Energy Components')),
('Safety Barriers', 'TOOL', 50, 'PIECES', (SELECT id FROM supplier WHERE name = 'Advanced Safety Equipment')),
('Hydraulic Fluids', 'COOLANT', 1500, 'LITERS', (SELECT id FROM supplier WHERE name = 'HydroTech Solutions')),
('Electrical Wiring', 'OTHER', 5000, 'METERS', (SELECT id FROM supplier WHERE name = 'Electrical Wiring Ltd.')),
('Ventilation Systems', 'TOOL', 25, 'PIECES', (SELECT id FROM supplier WHERE name = 'Power Systems International')),
('Monitoring Systems', 'OTHER', 40, 'PIECES', (SELECT id FROM supplier WHERE name = 'Innovative Cooling Systems')),
('Chemical Storage Tanks', 'OTHER', 20, 'PIECES', (SELECT id FROM supplier WHERE name = 'EnviroTech Materials')),
('Precision Measurement Tools', 'TOOL', 30, 'PIECES', (SELECT id FROM supplier WHERE name = 'Precision Instruments Co.')),
('Explosion-Proof Fixtures', 'OTHER', 10, 'PIECES', (SELECT id FROM supplier WHERE name = 'Thermal Dynamics Corp.'));

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

-- Insertar datos en la tabla Sensor

INSERT INTO sensor (type, location, reactor_id) VALUES
    -- Sensores para Reactor ALM-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),

    -- Sensores para Reactor ALM-2
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),

    -- Sensores para Reactor ALM-3
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),

    -- Sensores para Reactor ALM-4
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),

    -- Sensores para Reactor ASC-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),

    -- Sensores para Reactor ASC-2
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),

    -- Sensores para Reactor COF-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),

    -- Sensores para Reactor VAN-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),

    -- Sensores para Reactor VAN-2
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),

    -- Sensores para Reactor VAN-3
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),

    -- Sensores para Reactor TRI-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),

    -- Sensores para Reactor SMG-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),

    -- Sensores para Reactor JCB-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),

    -- Sensores para Reactor JCB-2
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),

    -- Sensores para Reactor ZOR-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),

    -- Sensores para Reactor ZOR-2
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),

    -- Sensores para Reactor CAS-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),

    -- Sensores para Reactor NAV-1
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),

    -- Sensores para Reactor NAV-2
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),

    -- Sensores para Reactor NAV-3
    ('TEMPERATURE', 'Reactor Core', (SELECT id FROM reactor WHERE name = 'Reactor NAV-3')),
    ('PRESSURE', 'Main Steam Line', (SELECT id FROM reactor WHERE name = 'Reactor NAV-3')),
    ('FLOW', 'Coolant System', (SELECT id FROM reactor WHERE name = 'Reactor NAV-3')),
    ('LEVEL', 'Fuel Storage Tank', (SELECT id FROM reactor WHERE name = 'Reactor NAV-3')),
    ('HUMIDITY', 'Control Room', (SELECT id FROM reactor WHERE name = 'Reactor NAV-3')),
    ('RADIACTIVITY', 'Containment Building', (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'));

-- Insertar datos en la tabla SensorReading

INSERT INTO sensor_reading (measurement_value, date, sensor_id) VALUES
    -- Reactor ALM-1
    (320.5, '2024-07-01T10:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'))),
    (50.0, '2024-07-01T10:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'))),
    (150.2, '2024-07-01T10:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'))),
    (75.3, '2024-07-01T10:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'))),
    (45.0, '2024-07-01T10:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'))),
    (0.02, '2024-07-01T10:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'))),

    -- Reactor ALM-2
    (315.0, '2024-07-01T11:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-2'))),
    (52.5, '2024-07-01T11:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-2'))),
    (155.4, '2024-07-01T11:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-2'))),
    (78.0, '2024-07-01T11:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-2'))),
    (47.5, '2024-07-01T11:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-2'))),
    (0.03, '2024-07-01T11:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-2'))),

    -- Reactor ALM-3
    (319.8, '2024-07-01T12:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-3'))),
    (48.9, '2024-07-01T12:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-3'))),
    (148.9, '2024-07-01T12:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-3'))),
    (76.5, '2024-07-01T12:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-3'))),
    (44.0, '2024-07-01T12:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-3'))),
    (0.01, '2024-07-01T12:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-3'))),

    -- Reactor ALM-4
    (322.5, '2024-07-01T13:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'))),
    (53.5, '2024-07-01T13:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'))),
    (152.7, '2024-07-01T13:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'))),
    (77.0, '2024-07-01T13:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'))),
    (46.0, '2024-07-01T13:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'))),
    (0.04, '2024-07-01T13:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'))),

    -- Reactor ASC-1
    (325.0, '2024-07-01T14:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-1'))),
    (55.0, '2024-07-01T14:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-1'))),
    (160.2, '2024-07-01T14:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-1'))),
    (80.0, '2024-07-01T14:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-1'))),
    (48.0, '2024-07-01T14:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-1'))),
    (0.05, '2024-07-01T14:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-1'))),

    -- Reactor ASC-2
    (317.5, '2024-07-01T15:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'))),
    (54.0, '2024-07-01T15:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'))),
    (158.3, '2024-07-01T15:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'))),
    (82.5, '2024-07-01T15:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'))),
    (50.5, '2024-07-01T15:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'))),
    (0.06, '2024-07-01T15:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'))),

    -- Reactor COF-1
    (330.0, '2024-07-01T16:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor COF-1'))),
    (60.0, '2024-07-01T16:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor COF-1'))),
    (165.4, '2024-07-01T16:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor COF-1'))),
    (82.0, '2024-07-01T16:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor COF-1'))),
    (50.0, '2024-07-01T16:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor COF-1'))),
    (0.06, '2024-07-01T16:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor COF-1'))),

    -- Reactor VAN-1
    (310.5, '2024-07-01T17:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-1'))),
    (51.0, '2024-07-01T17:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-1'))),
    (140.0, '2024-07-01T17:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-1'))),
    (73.0, '2024-07-01T17:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-1'))),
    (42.0, '2024-07-01T17:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-1'))),
    (0.03, '2024-07-01T17:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-1'))),

    -- Reactor VAN-2
    (325.5, '2024-07-01T18:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-2'))),
    (56.0, '2024-07-01T18:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-2'))),
    (155.6, '2024-07-01T18:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-2'))),
    (80.0, '2024-07-01T18:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-2'))),
    (47.0, '2024-07-01T18:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-2'))),
    (0.05, '2024-07-01T18:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-2'))),

    -- Reactor VAN-3
    (310.0, '2024-07-01T19:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'))),
    (52.5, '2024-07-01T19:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'))),
    (145.0, '2024-07-01T19:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'))),
    (78.0, '2024-07-01T19:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'))),
    (45.0, '2024-07-01T19:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'))),
    (0.04, '2024-07-01T19:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'))),

    -- Reactor TRI-1
    (318.0, '2024-07-01T20:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'))),
    (53.0, '2024-07-01T20:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'))),
    (150.0, '2024-07-01T20:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'))),
    (76.0, '2024-07-01T20:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'))),
    (46.0, '2024-07-01T20:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'))),
    (0.05, '2024-07-01T20:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'))),

    -- Reactor SMG-1
    (310.0, '2024-07-01T21:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'))),
    (55.0, '2024-07-01T21:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'))),
    (160.0, '2024-07-01T21:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'))),
    (74.0, '2024-07-01T21:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'))),
    (48.0, '2024-07-01T21:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'))),
    (0.07, '2024-07-01T21:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'))),

    -- Reactor JCB-1
    (315.0, '2024-07-01T22:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-1'))),
    (51.5, '2024-07-01T22:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-1'))),
    (148.0, '2024-07-01T22:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-1'))),
    (77.5, '2024-07-01T22:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-1'))),
    (44.5, '2024-07-01T22:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-1'))),
    (0.03, '2024-07-01T22:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-1'))),

    -- Reactor JCB-2
    (325.0, '2024-07-01T23:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'))),
    (56.0, '2024-07-01T23:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'))),
    (155.0, '2024-07-01T23:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'))),
    (79.0, '2024-07-01T23:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'))),
    (45.0, '2024-07-01T23:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'))),
    (0.04, '2024-07-01T23:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'))),

    -- Reactor ZOR-1
    (308.0, '2024-07-02T00:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'))),
    (50.0, '2024-07-02T00:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'))),
    (140.0, '2024-07-02T00:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'))),
    (72.0, '2024-07-02T00:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'))),
    (42.5, '2024-07-02T00:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'))),
    (0.06, '2024-07-02T00:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'))),

    -- Reactor ZOR-2
    (320.0, '2024-07-02T01:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2'))),
    (51.0, '2024-07-02T01:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2'))),
    (145.0, '2024-07-02T01:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2'))),
    (75.0, '2024-07-02T01:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2'))),
    (44.5, '2024-07-02T01:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2'))),
    (0.03, '2024-07-02T01:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2'))),

    -- Reactor ZOR-3
    (315.5, '2024-07-02T02:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-3'))),
    (52.0, '2024-07-02T02:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-3'))),
    (150.0, '2024-07-02T02:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-3'))),
    (80.0, '2024-07-02T02:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-3'))),
    (46.0, '2024-07-02T02:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-3'))),
    (0.05, '2024-07-02T02:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-3'))),

    -- Reactor ZOR-4
    (328.0, '2024-07-02T03:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-4'))),
    (57.0, '2024-07-02T03:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-4'))),
    (160.0, '2024-07-02T03:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-4'))),
    (82.0, '2024-07-02T03:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-4'))),
    (48.0, '2024-07-02T03:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-4'))),
    (0.06, '2024-07-02T03:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-4'))),

    -- Reactor ZOR-5
    (305.0, '2024-07-02T04:00:00', (SELECT id FROM sensor WHERE type = 'TEMPERATURE' AND location = 'Reactor Core' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-5'))),
    (52.0, '2024-07-02T04:00:00', (SELECT id FROM sensor WHERE type = 'PRESSURE' AND location = 'Main Steam Line' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-5'))),
    (150.0, '2024-07-02T04:00:00', (SELECT id FROM sensor WHERE type = 'FLOW' AND location = 'Coolant System' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-5'))),
    (77.0, '2024-07-02T04:00:00', (SELECT id FROM sensor WHERE type = 'LEVEL' AND location = 'Fuel Storage Tank' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-5'))),
    (43.5, '2024-07-02T04:00:00', (SELECT id FROM sensor WHERE type = 'HUMIDITY' AND location = 'Control Room' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-5'))),
    (0.04, '2024-07-02T04:00:00', (SELECT id FROM sensor WHERE type = 'RADIACTIVITY' AND location = 'Containment Building' AND reactor_id = (SELECT id FROM reactor WHERE name = 'Reactor ZOR-5')));
