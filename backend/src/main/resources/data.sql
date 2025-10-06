-- data.sql

INSERT INTO users (username, password, role) VALUES
('Admin', '$2a$12$3rDuLVZQrl62wEbhkg4QU.bJ5jC8mlyWmMwnkCrJjagQbm/QjnVRW', 'ADMIN');

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
('Navarra Nuclear Plant', 'Navarra, Navarra'),
('Central Prueba', 'Test Location');

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

-- Insertar sistemas de control con relación a todos los reactores y asegurando que cada tipo esté presente

-- Relacionar todos los reactores con un Distributed Control System
INSERT INTO control_system (name, type, reactor_id) VALUES
    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('Tertiary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('Quaternary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('Tertiary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),

    ('Main Distributed Control System', 'DISTRIBUTED_CONTROL_SYSTEM', 
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('Secondary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('Tertiary Control System', 'DISTRIBUTED_CONTROL_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'));

-- Relacionar todos los reactores con un SCADA
INSERT INTO control_system (name, type, reactor_id) VALUES
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),

    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('Plant-Wide SCADA Network', 'SUPERVISORY_CONTROL_AND_DATA_ACQUISITION', 
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'));

-- Relacionar todos los reactores con un PLC
INSERT INTO control_system (name, type, reactor_id) VALUES
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),

    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('Cooling System PLC', 'PROGRAMMABLE_LOGIC_CONTROLLER',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'));

-- Relacionar todos los reactores con un Safety Instrumented System
INSERT INTO control_system (name, type, reactor_id) VALUES
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),

    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('Backup Safety Instrumented System', 'SAFETY_INSTRUMENTED_SYSTEM',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'));

-- Relacionar todos los reactores con un HMI
INSERT INTO control_system (name, type, reactor_id) VALUES
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-4')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-2')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-1')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-3')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor SMG-1')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-2')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-2')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),

    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-2')),
    ('Operator HMI Console', 'HUMAN_MACHINE_INTERFACE',
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'));


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

-- Insertar planes de mantenimiento

INSERT INTO maintenance_plan (name, description, start_date, end_date, nuclear_plant_id, is_completed) VALUES
    ('Annual Inspection', 'Revisión anual de sistemas de seguridad y operatividad de equipos', '2024-01-10 08:00:00', '2024-01-20 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant'), false),
    ('Quarterly Maintenance', 'Mantenimiento trimestral de sistemas de control y refrigeración', '2024-04-01 08:00:00', '2024-04-10 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant'), false),
    ('Critical System Upgrade', 'Actualización crítica de sistemas de energía y control', '2024-06-15 08:00:00', '2024-07-01 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant'), false),
    ('Emergency Safety Check', 'Revisión de emergencia para sistemas de seguridad tras incidentes recientes', '2024-03-01 08:00:00', '2024-03-05 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant'), false),
    ('General Overhaul', 'Revisión general y mantenimiento de todos los sistemas operativos', '2024-09-01 08:00:00', '2024-09-30 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant'), false),
    ('Cooling System Upgrade', 'Actualización de sistemas de refrigeración y enfriamiento', '2024-05-01 08:00:00', '2024-05-15 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant'), false),
    ('Fuel Storage Maintenance', 'Mantenimiento y revisión de las instalaciones de almacenamiento de combustible', '2024-11-01 08:00:00', '2024-11-10 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant'), false),
    ('Structural Inspection', 'Inspección estructural de edificios y componentes críticos', '2024-02-01 08:00:00', '2024-02-20 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant'), false),
    ('Electrical System Audit', 'Auditoría y mantenimiento de sistemas eléctricos principales', '2024-07-01 08:00:00', '2024-07-20 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant'), false),
    ('Safety Systems Upgrade', 'Actualización de sistemas de seguridad y emergencia', '2024-08-01 08:00:00', '2024-08-15 17:00:00', 
        (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant'), false);

-- Insertar datos en la tabla Equipment

INSERT INTO equipment (name, manufacturer, installation_date, type) VALUES
    ('Main Cooling Pump', 'ABC Pumps Ltd.', '2018-05-15', 'PUMP'),
    ('High Pressure Valve', 'Valves Inc.', '2019-09-22', 'VALVE'),
    ('Emergency Generator', 'PowerGen Co.', '2017-03-11', 'GENERATOR'),
    ('Transformer Unit 1', 'ElectroTransformers', '2020-01-30', 'TRANSFORMER'),
    ('Heat Exchanger A', 'HeatTech Solutions', '2016-12-05', 'HEAT_EXCHANGER'),
    ('Compressor Series X', 'AirCompressor Ltd.', '2021-07-20', 'COMPRESSOR'),
    ('Steam Turbine 45', 'TurbineWorks', '2018-11-18', 'TURBINE'),

    ('Backup Cooling Pump', 'ABC Pumps Ltd.', '2021-08-14', 'PUMP'),
    ('Control Valve', 'Valves Inc.', '2020-06-10', 'VALVE'),
    ('Primary Generator', 'PowerGen Co.', '2015-10-02', 'GENERATOR'),
    ('Transformer Unit 2', 'ElectroTransformers', '2019-05-25', 'TRANSFORMER'),
    ('Heat Exchanger B', 'HeatTech Solutions', '2018-04-15', 'HEAT_EXCHANGER'),
    ('High Capacity Compressor', 'AirCompressor Ltd.', '2022-03-09', 'COMPRESSOR'),
    ('Gas Turbine 60', 'TurbineWorks', '2020-02-20', 'TURBINE'),

    ('Auxiliary Cooling Pump', 'ABC Pumps Ltd.', '2022-01-30', 'PUMP'),
    ('Safety Valve', 'Valves Inc.', '2019-11-05', 'VALVE'),
    ('Backup Generator', 'PowerGen Co.', '2016-06-22', 'GENERATOR'),
    ('Transformer Unit 3', 'ElectroTransformers', '2021-11-12', 'TRANSFORMER'),
    ('Heat Exchanger C', 'HeatTech Solutions', '2021-09-01', 'HEAT_EXCHANGER'),
    ('Industrial Compressor', 'AirCompressor Ltd.', '2023-02-28', 'COMPRESSOR'),
    ('High Pressure Turbine', 'TurbineWorks', '2021-07-09', 'TURBINE');

-- Insertar datos en la tabla Maintenance

INSERT INTO maintenance (description, start_date, end_date, status, reactor_id, equipment_id, maintenance_plan_id) VALUES
    -- Mantenimiento del Reactor ALM-1
    ('Revisión de rutina de sistema de refrigeración', '2024-01-15 08:00:00', '2024-01-17 17:00:00', 'IN_PROGRESS', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-1'), 
     (SELECT id FROM equipment WHERE name = 'Main Cooling Pump'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Annual Inspection')),

    -- Mantenimiento del Reactor ASC-2
    ('Inspección y ajuste de válvulas de seguridad', '2024-04-01 08:00:00', '2024-04-05 17:00:00', 'SCHEDULED', 
     (SELECT id FROM reactor WHERE name = 'Reactor ASC-2'), 
     (SELECT id FROM equipment WHERE name = 'Control Valve'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Quarterly Maintenance')),

    -- Mantenimiento del Reactor ALM-4
    ('Mantenimiento preventivo y revisión de sistemas críticos', '2024-02-10 08:00:00', '2024-02-12 17:00:00', 'SCHEDULED', 
     (SELECT id FROM reactor WHERE name = 'Reactor ALM-4'), 
     (SELECT id FROM equipment WHERE name = 'Heat Exchanger C'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Emergency Safety Check')),

    -- Mantenimiento del Reactor NAV-2
    ('Inspección de generador de respaldo', '2024-07-05 08:00:00', '2024-07-10 17:00:00', 'COMPLETED', 
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-2'), 
     (SELECT id FROM equipment WHERE name = 'Backup Generator'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Electrical System Audit')),

    -- Mantenimiento del Reactor COF-1
    ('Reemplazo de intercambiadores de calor y revisión general', '2024-05-01 08:00:00', '2024-05-15 17:00:00', 'IN_PROGRESS', 
     (SELECT id FROM reactor WHERE name = 'Reactor COF-1'), 
     (SELECT id FROM equipment WHERE name = 'Heat Exchanger B'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Cooling System Upgrade')),

    -- Mantenimiento del Reactor ZOR-1
    ('Revisión estructural y de seguridad', '2024-03-10 08:00:00', '2024-03-20 17:00:00', 'COMPLETED', 
     (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1'), 
     (SELECT id FROM equipment WHERE name = 'Transformer Unit 3'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Structural Inspection')),

    -- Mantenimiento del Reactor JCB-2
    ('Inspección y mantenimiento de turbina de gas', '2024-08-05 08:00:00', '2024-08-10 17:00:00', 'SCHEDULED', 
     (SELECT id FROM reactor WHERE name = 'Reactor JCB-2'), 
     (SELECT id FROM equipment WHERE name = 'Gas Turbine 60'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Safety Systems Upgrade')),

    -- Mantenimiento del Reactor VAN-3
    ('Revisión y calibración de bombas principales', '2024-04-15 08:00:00', '2024-04-20 17:00:00', 'IN_PROGRESS', 
     (SELECT id FROM reactor WHERE name = 'Reactor VAN-3'), 
     (SELECT id FROM equipment WHERE name = 'Main Cooling Pump'), 
     (SELECT id FROM maintenance_plan WHERE name = 'General Overhaul')),

    -- Mantenimiento del Reactor TRI-1
    ('Mantenimiento de transformadores y revisión de sistemas eléctricos', '2024-06-01 08:00:00', '2024-06-15 17:00:00', 'COMPLETED', 
     (SELECT id FROM reactor WHERE name = 'Reactor TRI-1'), 
     (SELECT id FROM equipment WHERE name = 'Transformer Unit 1'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Critical System Upgrade')),
     
    -- Mantenimiento del Reactor SMG-1
    ('Revisión y mantenimiento de sistemas de control', '2024-11-01 08:00:00', '2024-11-05 17:00:00', 'SCHEDULED', 
     (SELECT id FROM reactor WHERE name = 'Reactor SMG-1'), 
     (SELECT id FROM equipment WHERE name = 'Heat Exchanger A'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Fuel Storage Maintenance')),
     
    -- Mantenimiento del Reactor NAV-3
    ('Inspección de válvulas y bombas de emergencia', '2024-10-15 08:00:00', '2024-10-20 17:00:00', 'IN_PROGRESS', 
     (SELECT id FROM reactor WHERE name = 'Reactor NAV-3'), 
     (SELECT id FROM equipment WHERE name = 'High Pressure Valve'), 
     (SELECT id FROM maintenance_plan WHERE name = 'Cooling System Upgrade'));

-- Insertar anomalías en la base de datos

INSERT INTO anomaly (description, date, severity, reactor_id) VALUES
    ('High pressure in cooling system detected.', '2024-07-15 14:30:00', 'HIGH', (SELECT id FROM reactor WHERE name = 'Reactor ALM-1')),
    ('Unexpected vibration in turbine.', '2024-07-17 09:45:00', 'MEDIUM', (SELECT id FROM reactor WHERE name = 'Reactor ALM-2')),
    ('Leakage in the primary circuit.', '2024-07-20 11:00:00', 'LOW', (SELECT id FROM reactor WHERE name = 'Reactor ALM-3')),
    ('Control system failure during shutdown.', '2024-07-25 16:20:00', 'HIGH', (SELECT id FROM reactor WHERE name = 'Reactor ASC-1')),
    ('Minor electrical fault observed.', '2024-08-01 08:00:00', 'LOW', (SELECT id FROM reactor WHERE name = 'Reactor COF-1')),
    ('High temperature in reactor core.', '2024-08-03 14:15:00', 'MEDIUM', (SELECT id FROM reactor WHERE name = 'Reactor VAN-2')),
    ('Faulty sensors in safety system.', '2024-08-10 10:10:00', 'HIGH', (SELECT id FROM reactor WHERE name = 'Reactor TRI-1')),
    ('Pumping station failure.', '2024-08-12 13:25:00', 'MEDIUM', (SELECT id FROM reactor WHERE name = 'Reactor JCB-1')),
    ('Incorrect reactor pressure readings.', '2024-08-15 15:30:00', 'LOW', (SELECT id FROM reactor WHERE name = 'Reactor ZOR-1')),
    ('Emergency shutdown due to alarm.', '2024-08-18 17:45:00', 'HIGH', (SELECT id FROM reactor WHERE name = 'Reactor CAS-1')),
    ('Overheating in turbine generator.', '2024-08-20 12:00:00', 'MEDIUM', (SELECT id FROM reactor WHERE name = 'Reactor NAV-1')),
    ('Minor calibration error in HMI.', '2024-08-22 11:10:00', 'LOW', (SELECT id FROM reactor WHERE name = 'Reactor NAV-2'));

-- Insertar planes de emergencia para cada planta nuclear

INSERT INTO emergency_plan (name, description, creation_date, nuclear_plant_id) VALUES
    -- Planes para Almaraz Nuclear Plant
    ('Plan de Evacuación Almaraz', 'Plan detallado de evacuación para la Planta Nuclear de Almaraz.', '2024-01-10 08:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Plan de Contención de Fugas Almaraz', 'Procedimientos para la contención de fugas en la Planta Nuclear de Almaraz.', '2024-01-10 08:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Plan de Incendio Almaraz', 'Plan de respuesta a incendios en la Planta Nuclear de Almaraz.', '2024-01-10 08:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Almaraz', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Almaraz.', '2024-01-10 08:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Plan de Desastres Naturales Almaraz', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Almaraz.', '2024-01-10 08:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),

    -- Planes para Asco Nuclear Plant
    ('Plan de Evacuación Asco', 'Plan detallado de evacuación para la Planta Nuclear de Asco.', '2024-02-15 09:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Plan de Contención de Fugas Asco', 'Procedimientos para la contención de fugas en la Planta Nuclear de Asco.', '2024-02-15 09:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Plan de Incendio Asco', 'Plan de respuesta a incendios en la Planta Nuclear de Asco.', '2024-02-15 09:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Asco', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Asco.', '2024-02-15 09:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Plan de Desastres Naturales Asco', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Asco.', '2024-02-15 09:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),

    -- Planes para Cofrentes Nuclear Plant
    ('Plan de Evacuación Cofrentes', 'Plan detallado de evacuación para la Planta Nuclear de Cofrentes.', '2024-03-20 10:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Plan de Contención de Fugas Cofrentes', 'Procedimientos para la contención de fugas en la Planta Nuclear de Cofrentes.', '2024-03-20 10:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Plan de Incendio Cofrentes', 'Plan de respuesta a incendios en la Planta Nuclear de Cofrentes.', '2024-03-20 10:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Cofrentes', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Cofrentes.', '2024-03-20 10:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Plan de Desastres Naturales Cofrentes', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Cofrentes.', '2024-03-20 10:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),

    -- Planes para Vandellos Nuclear Plant
    ('Plan de Evacuación Vandellos', 'Plan detallado de evacuación para la Planta Nuclear de Vandellos.', '2024-04-25 11:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Plan de Contención de Fugas Vandellos', 'Procedimientos para la contención de fugas en la Planta Nuclear de Vandellos.', '2024-04-25 11:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Plan de Incendio Vandellos', 'Plan de respuesta a incendios en la Planta Nuclear de Vandellos.', '2024-04-25 11:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Vandellos', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Vandellos.', '2024-04-25 11:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Plan de Desastres Naturales Vandellos', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Vandellos.', '2024-04-25 11:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),

    -- Planes para Trillo Nuclear Plant
    ('Plan de Evacuación Trillo', 'Plan detallado de evacuación para la Planta Nuclear de Trillo.', '2024-05-30 12:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Plan de Contención de Fugas Trillo', 'Procedimientos para la contención de fugas en la Planta Nuclear de Trillo.', '2024-05-30 12:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Plan de Incendio Trillo', 'Plan de respuesta a incendios en la Planta Nuclear de Trillo.', '2024-05-30 12:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Trillo', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Trillo.', '2024-05-30 12:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Plan de Desastres Naturales Trillo', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Trillo.', '2024-05-30 12:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),

    -- Planes para Santa Maria de Garona Nuclear Plant
    ('Plan de Evacuación Santa Maria de Garona', 'Plan detallado de evacuación para la Planta Nuclear de Santa Maria de Garona.', '2024-06-15 13:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Plan de Contención de Fugas Santa Maria de Garona', 'Procedimientos para la contención de fugas en la Planta Nuclear de Santa Maria de Garona.', '2024-06-15 13:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Plan de Incendio Santa Maria de Garona', 'Plan de respuesta a incendios en la Planta Nuclear de Santa Maria de Garona.', '2024-06-15 13:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Santa Maria de Garona', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Santa Maria de Garona.', '2024-06-15 13:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Plan de Desastres Naturales Santa Maria de Garona', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Santa Maria de Garona.', '2024-06-15 13:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),

    -- Planes para Jose Cabrera Nuclear Plant
    ('Plan de Evacuación Jose Cabrera', 'Plan detallado de evacuación para la Planta Nuclear de Jose Cabrera.', '2024-07-20 14:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Plan de Contención de Fugas Jose Cabrera', 'Procedimientos para la contención de fugas en la Planta Nuclear de Jose Cabrera.', '2024-07-20 14:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Plan de Incendio Jose Cabrera', 'Plan de respuesta a incendios en la Planta Nuclear de Jose Cabrera.', '2024-07-20 14:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Jose Cabrera', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Jose Cabrera.', '2024-07-20 14:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Plan de Desastres Naturales Jose Cabrera', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Jose Cabrera.', '2024-07-20 14:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),

    -- Planes para Zorita Nuclear Plant
    ('Plan de Evacuación Zorita', 'Plan detallado de evacuación para la Planta Nuclear de Zorita.', '2024-08-25 15:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Plan de Contención de Fugas Zorita', 'Procedimientos para la contención de fugas en la Planta Nuclear de Zorita.', '2024-08-25 15:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Plan de Incendio Zorita', 'Plan de respuesta a incendios en la Planta Nuclear de Zorita.', '2024-08-25 15:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Zorita', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Zorita.', '2024-08-25 15:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Plan de Desastres Naturales Zorita', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Zorita.', '2024-08-25 15:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),

    -- Planes para Castellon Nuclear Plant
    ('Plan de Evacuación Castellon', 'Plan detallado de evacuación para la Planta Nuclear de Castellon.', '2024-09-30 16:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Plan de Contención de Fugas Castellon', 'Procedimientos para la contención de fugas en la Planta Nuclear de Castellon.', '2024-09-30 16:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Plan de Incendio Castellon', 'Plan de respuesta a incendios en la Planta Nuclear de Castellon.', '2024-09-30 16:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Castellon', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Castellon.', '2024-09-30 16:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Plan de Desastres Naturales Castellon', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Castellon.', '2024-09-30 16:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),

    -- Planes para Navarra Nuclear Plant
    ('Plan de Evacuación Navarra', 'Plan detallado de evacuación para la Planta Nuclear de Navarra.', '2024-10-15 17:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Plan de Contención de Fugas Navarra', 'Procedimientos para la contención de fugas en la Planta Nuclear de Navarra.', '2024-10-15 17:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Plan de Incendio Navarra', 'Plan de respuesta a incendios en la Planta Nuclear de Navarra.', '2024-10-15 17:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Plan de Fallo de Sistema Crítico Navarra', 'Plan para el manejo de fallos en sistemas críticos de la Planta Nuclear de Navarra.', '2024-10-15 17:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Plan de Desastres Naturales Navarra', 'Plan para enfrentar desastres naturales en la Planta Nuclear de Navarra.', '2024-10-15 17:00:00', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant'));

-- Insertar incidentes para cada planta nuclear

INSERT INTO incident (description, date, severity, nuclear_plant_id) VALUES
    -- Incidentes en Almaraz Nuclear Plant
    ('Fuga menor detectada en el sistema de refrigeración.', '2024-01-15 09:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Incendio controlado en el área de almacenamiento de residuos.', '2024-02-20 11:30:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Fallos críticos en el sistema de control de emergencia.', '2024-03-10 14:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),

    -- Incidentes en Asco Nuclear Plant
    ('Error en la lectura de datos del sensor de presión.', '2024-01-25 10:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Pequeño derrame de material radiactivo en la zona de mantenimiento.', '2024-02-18 12:45:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Problema grave con el sistema de enfriamiento principal.', '2024-03-22 16:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),

    -- Incidentes en Cofrentes Nuclear Plant
    ('Falla menor en el sistema de generación de energía.', '2024-01-30 09:30:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Malfuncionamiento en la válvula de emergencia.', '2024-02-25 14:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Desplome parcial de la estructura de soporte de reactor.', '2024-03-30 17:30:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),

    -- Incidentes en Vandellos Nuclear Plant
    ('Pequeño aumento en la radiación detectado.', '2024-02-05 10:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Pérdida de comunicación con el sistema de control remoto.', '2024-03-15 15:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Fuga de refrigerante crítico en el sistema principal.', '2024-04-10 18:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),

    -- Incidentes en Trillo Nuclear Plant
    ('Falla en el sistema de control de temperatura.', '2024-01-20 11:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Problemas con el sistema de ventilación del reactor.', '2024-02-15 13:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Desviación crítica en los niveles de presión del reactor.', '2024-03-20 14:30:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),

    -- Incidentes en Santa Maria de Garona Nuclear Plant
    ('Problemas menores en el sistema de monitorización.', '2024-02-10 08:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Derrame importante en la zona de tratamiento de residuos.', '2024-03-05 12:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Fallo crítico en el sistema de refrigeración de emergencia.', '2024-04-15 16:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),

    -- Incidentes en Jose Cabrera Nuclear Plant
    ('Falla en el sistema de alerta temprana.', '2024-03-01 09:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Problemas graves en el sistema de enfriamiento.', '2024-04-10 11:30:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Incidente crítico en la sala de control principal.', '2024-05-20 13:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),

    -- Incidentes en Zorita Nuclear Plant
    ('Anomalía menor en el sistema de distribución eléctrica.', '2024-03-10 10:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Incidente con el sistema de control de radiación.', '2024-04-15 14:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Fuga crítica en el sistema de refrigeración.', '2024-05-20 16:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),

    -- Incidentes en Castellon Nuclear Plant
    ('Error menor en el sistema de comunicación.', '2024-04-01 08:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Desviación en los niveles de presión del reactor.', '2024-05-10 11:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Fallo crítico en el sistema de seguridad del reactor.', '2024-06-20 13:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),

    -- Incidentes en Navarra Nuclear Plant
    ('Pequeña fuga detectada en el sistema de tuberías.', '2024-05-01 10:00:00', 'MINOR', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Problema grave con el sistema de enfriamiento.', '2024-06-15 12:00:00', 'MAJOR', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Fallo crítico en el sistema de seguridad de emergencia.', '2024-07-25 14:00:00', 'CRITICAL', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant'));

-- Insertar operadores para cada planta nuclear

INSERT INTO operator (name, position, experience, nuclear_plant_id) VALUES
    -- Operadores en Almaraz Nuclear Plant
    ('John Doe', 'Reactor Operator', '10 years', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    ('Jane Smith', 'Control Room Supervisor', '8 years', (SELECT id FROM nuclear_plant WHERE name = 'Almaraz Nuclear Plant')),
    
    -- Operadores en Asco Nuclear Plant
    ('Carlos Ruiz', 'Nuclear Engineer', '12 years', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),
    ('Marta Fernandez', 'Maintenance Technician', '6 years', (SELECT id FROM nuclear_plant WHERE name = 'Asco Nuclear Plant')),

    -- Operadores en Cofrentes Nuclear Plant
    ('Luis Gomez', 'Shift Supervisor', '15 years', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),
    ('Ana Martinez', 'Reactor Technician', '7 years', (SELECT id FROM nuclear_plant WHERE name = 'Cofrentes Nuclear Plant')),

    -- Operadores en Vandellos Nuclear Plant
    ('Francisco Moreno', 'Operations Manager', '20 years', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),
    ('Elena Torres', 'Safety Officer', '5 years', (SELECT id FROM nuclear_plant WHERE name = 'Vandellos Nuclear Plant')),

    -- Operadores en Trillo Nuclear Plant
    ('Ricardo Vargas', 'Nuclear Operator', '10 years', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),
    ('Isabel Castillo', 'Control Systems Engineer', '9 years', (SELECT id FROM nuclear_plant WHERE name = 'Trillo Nuclear Plant')),

    -- Operadores en Santa Maria de Garona Nuclear Plant
    ('Pablo Castro', 'Shift Engineer', '13 years', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),
    ('Laura Silva', 'Field Technician', '4 years', (SELECT id FROM nuclear_plant WHERE name = 'Santa Maria de Garona Nuclear Plant')),

    -- Operadores en Jose Cabrera Nuclear Plant
    ('Miguel Alvarez', 'Senior Reactor Operator', '18 years', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),
    ('Patricia Gomez', 'Electrical Engineer', '7 years', (SELECT id FROM nuclear_plant WHERE name = 'Jose Cabrera Nuclear Plant')),

    -- Operadores en Zorita Nuclear Plant
    ('Julian López', 'Maintenance Engineer', '11 years', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),
    ('Silvia Perez', 'Operations Technician', '6 years', (SELECT id FROM nuclear_plant WHERE name = 'Zorita Nuclear Plant')),

    -- Operadores en Castellon Nuclear Plant
    ('Manuel Delgado', 'Reactor Technician', '14 years', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),
    ('Raquel Jimenez', 'Control Room Operator', '8 years', (SELECT id FROM nuclear_plant WHERE name = 'Castellon Nuclear Plant')),

    -- Operadores en Navarra Nuclear Plant
    ('Victor Molina', 'Nuclear Systems Engineer', '9 years', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant')),
    ('Elena Ruiz', 'Safety Engineer', '10 years', (SELECT id FROM nuclear_plant WHERE name = 'Navarra Nuclear Plant'));

-- Insertar entrenamientos para cada operador

-- Operadores en Almaraz Nuclear Plant
INSERT INTO training (title, description, type, training_date, operator_id) VALUES
    ('Reactor Safety Training', 'Training on reactor safety protocols and emergency procedures.', 'SAFETY', '2024-01-15T08:00:00', (SELECT id FROM operator WHERE name = 'John Doe')),
    ('Advanced Control Systems', 'Training on advanced features of control systems used in reactors.', 'OPERATIONAL', '2024-02-20T08:00:00', (SELECT id FROM operator WHERE name = 'Jane Smith')),

-- Operadores en Asco Nuclear Plant
    ('Nuclear Plant Operations', 'Comprehensive training on daily operations of the nuclear plant.', 'OPERATIONAL', '2024-03-10T08:00:00', (SELECT id FROM operator WHERE name = 'Carlos Ruiz')),
    ('Emergency Procedures', 'Training on emergency procedures and response strategies.', 'SAFETY', '2024-04-05T08:00:00', (SELECT id FROM operator WHERE name = 'Marta Fernandez')),

-- Operadores en Cofrentes Nuclear Plant
    ('Preventive Maintenance', 'Training on preventive maintenance practices and schedules.', 'MAINTENANCE', '2024-05-15T08:00:00', (SELECT id FROM operator WHERE name = 'Luis Gomez')),
    ('Operational Safety', 'Training on operational safety measures and compliance.', 'SAFETY', '2024-06-10T08:00:00', (SELECT id FROM operator WHERE name = 'Ana Martinez')),

-- Operadores en Vandellos Nuclear Plant
    ('Reactor System Training', 'Training on reactor system operations and troubleshooting.', 'OPERATIONAL', '2024-07-20T08:00:00', (SELECT id FROM operator WHERE name = 'Francisco Moreno')),
    ('Safety Protocols', 'Training on safety protocols and hazard management.', 'SAFETY', '2024-08-25T08:00:00', (SELECT id FROM operator WHERE name = 'Elena Torres')),

-- Operadores en Trillo Nuclear Plant
    ('Control Systems Operation', 'Training on the operation of control systems.', 'OPERATIONAL', '2024-09-15T08:00:00', (SELECT id FROM operator WHERE name = 'Ricardo Vargas')),
    ('Maintenance Procedures', 'Training on maintenance procedures and practices.', 'MAINTENANCE', '2024-10-10T08:00:00', (SELECT id FROM operator WHERE name = 'Isabel Castillo')),

-- Operadores en Santa Maria de Garona Nuclear Plant
    ('Field Technician Skills', 'Training on field technician skills and procedures.', 'MAINTENANCE', '2024-11-15T08:00:00', (SELECT id FROM operator WHERE name = 'Pablo Castro')),
    ('Reactor Safety Operations', 'Training on reactor safety and operations.', 'SAFETY', '2024-12-10T08:00:00', (SELECT id FROM operator WHERE name = 'Laura Silva')),

-- Operadores en Jose Cabrera Nuclear Plant
    ('Electrical Systems Training', 'Training on electrical systems and their maintenance.', 'MAINTENANCE', '2024-01-20T08:00:00', (SELECT id FROM operator WHERE name = 'Miguel Alvarez')),
    ('Control Room Operations', 'Training on control room operations and emergency handling.', 'OPERATIONAL', '2024-02-25T08:00:00', (SELECT id FROM operator WHERE name = 'Patricia Gomez')),

-- Operadores en Zorita Nuclear Plant
    ('Safety Procedures', 'Training on safety procedures and regulatory compliance.', 'SAFETY', '2024-03-15T08:00:00', (SELECT id FROM operator WHERE name = 'Julian López')),
    ('Reactor Maintenance', 'Training on reactor maintenance and inspection.', 'MAINTENANCE', '2024-04-10T08:00:00', (SELECT id FROM operator WHERE name = 'Silvia Perez')),

-- Operadores en Castellon Nuclear Plant
    ('Operational Safety Training', 'Training on safety measures during operational processes.', 'SAFETY', '2024-05-20T08:00:00', (SELECT id FROM operator WHERE name = 'Manuel Delgado')),
    ('Maintenance Training', 'Training on maintenance tasks and schedules.', 'MAINTENANCE', '2024-06-15T08:00:00', (SELECT id FROM operator WHERE name = 'Raquel Jimenez')),

-- Operadores en Navarra Nuclear Plant
    ('Advanced Maintenance', 'Training on advanced maintenance techniques for nuclear reactors.', 'MAINTENANCE', '2024-07-25T08:00:00', (SELECT id FROM operator WHERE name = 'Victor Molina')),
    ('Safety Operations', 'Training on safety operations and risk management.', 'SAFETY', '2024-08-30T08:00:00', (SELECT id FROM operator WHERE name = 'Elena Ruiz'));