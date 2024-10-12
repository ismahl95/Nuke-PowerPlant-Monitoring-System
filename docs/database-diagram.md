```mermaid
erDiagram
    supplier {
        int id PK
        string name
        string contact
        string phone
    }

    material {
        int id PK
        string name
        string type
        int quantity
        string unit_of_measure
        int supplier_id FK
    }

    report {
        int id PK
        string title
        string content
        string status
        datetime creation_date
    }

    nuclear_plant {
        int id PK
        string name
        string location
    }

    reactor {
        int id PK
        string name
        string type
        datetime installation_date
        string status
        int nuclear_plant_id FK
    }

    control_system {
        int id PK
        string name
        string type
        int reactor_id FK
    }

    sensor {
        int id PK
        string type
        string location
        int reactor_id FK
    }

    sensor_reading {
        int id PK
        float measurement_value
        datetime date
        int sensor_id FK
    }

    maintenance_plan {
        int id PK
        string name
        string description
        datetime start_date
        datetime end_date
        int nuclear_plant_id FK
        boolean is_completed
    }

    equipment {
        int id PK
        string name
        string manufacturer
        datetime installation_date
        string type
    }

    maintenance {
        int id PK
        string description
        datetime start_date
        datetime end_date
        string status
        int reactor_id FK
        int equipment_id FK
        int maintenance_plan_id FK
    }

    anomaly {
        int id PK
        string description
        datetime date
        string severity
        int reactor_id FK
    }

    emergency_plan {
        int id PK
        string name
        string description
        datetime creation_date
        int nuclear_plant_id FK
    }

    incident {
        int id PK
        string description
        datetime date
        string severity
        int nuclear_plant_id FK
    }

    operator {
        int id PK
        string name
        string position
        string experience
        int nuclear_plant_id FK
    }

    training {
        int id PK
        string title
        string description
        string type
        datetime training_date
        int operator_id FK
    }

    supplier ||--o{ material: "supplies"
    nuclear_plant ||--o{ reactor: "contains"
    reactor ||--o{ control_system: "monitors"
    reactor ||--o{ sensor: "has"
    sensor ||--o{ sensor_reading: "logs"
    nuclear_plant ||--o{ maintenance_plan: "schedules"
    reactor ||--o{ maintenance: "undergoes"
    equipment ||--o{ maintenance: "serviced in"
    maintenance_plan ||--o{ maintenance: "includes"
    reactor ||--o{ anomaly: "detects"
    nuclear_plant ||--o{ emergency_plan: "has"
    nuclear_plant ||--o{ incident: "reports"
    nuclear_plant ||--o{ operator: "employs"
    operator ||--o{ training: "undergoes"