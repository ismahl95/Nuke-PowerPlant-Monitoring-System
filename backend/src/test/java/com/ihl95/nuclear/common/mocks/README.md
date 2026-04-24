# Test Data Mocks — Carpeta Reutilizable

> Este directorio contiene datos de prueba compartidos para todos los niveles de testing:
> - Tests Unitarios (Mockito puro)
> - Tests de Integración (@SpringBootTest)
> - Tests E2E (Cucumber)

## Estructura

```
src/test/java/com/ihl95/nuclear/common/mocks/
├── NuclearPlantTestData.java    ← Datos para módulo NuclearPlant
├── SupplierTestData.java        ← Datos para módulo Supplier
├── SecurityTestData.java        ← Usuarios, tokens JWT, credenciales
├── ReactorTestData.java         ← (próximamente)
├── SensorTestData.java          ← (próximamente)
└── README.md                    ← Este archivo
```

## Uso

### En Tests Unitarios

```java
@ExtendWith(MockitoExtension.class)
class NuclearPlantServiceTest {
    
    @Test
    void getName() {
        // Usar datos predefinidos
        NuclearPlant plant = NuclearPlantTestData.createNuclearPlantEntity();
        
        assertThat(plant.getName()).isEqualTo("Planta Nuclear Central");
    }
}
```

### En Tests de Integración

```java
@SpringBootTest
class NuclearPlantControllerIntegrationTest {
    
    @Autowired
    private NuclearPlantRepository repository;
    
    @BeforeEach
    void setUp() {
        // Prepopular con datos conocidos
        repository.save(NuclearPlantTestData.createNuclearPlantEntity());
    }
}
```

### En Tests E2E (Cucumber Steps)

```java
@Component
public class NuclearPlantSteps {
    
    @Given("existe una planta nuclear con nombre {string}")
    public void createPlant(String name) {
        NuclearPlantDTO dto = NuclearPlantTestData.createNuclearPlantDTO();
        // usar el DTO en RestAssured
    }
}
```

## Convenciones

1. **Un archivo = Un módulo/agregado**
   - `NuclearPlantTestData` para todo lo del módulo NuclearPlant
   - `SupplierTestData` para todo del módulo Supplier

2. **Métodos factory con sobrecarga**
   ```java
   // Sin parámetros: valores por defecto
   createNuclearPlantEntity()
   
   // Con parámetros: valores personalizados
   createNuclearPlantEntity(1L, "Nombre Custom", "Ubicación Custom")
   ```

3. **Constantes para validación**
   ```java
   VALID_PLANT_NAME = "Planta Nuclear Asc"
   INVALID_PLANT_NAME = ""
   ```

4. **Tanto entidades como DTOs**
   ```java
   createNuclearPlantEntity()   // para @DataJpaTest, servicios
   createNuclearPlantDTO()      // para controllers, E2E
   ```

## Ventajas

✅ **DRY principle**: No repites datos en cada test  
✅ **Mantenibilidad**: Cambias datos en un lugar, se actualiza en todos los tests  
✅ **Consistencia**: Todos los tests usan los mismos datos base  
✅ **Reutilización 100%**: Unitarios, integración y E2E comparten la misma carpeta  

## Agregar nuevos datos

Cuando crees un nuevo módulo (ej: Reactor, Sensor):

1. Crea `ReactorTestData.java` en esta carpeta
2. Define `createReactorEntity()`, `createReactorDTO()`
3. Agrega constantes de validación
4. Úsalos en todos tus tests

Ejemplo:
```java
public class ReactorTestData {
    public static Reactor createReactorEntity() {
        return Reactor.builder()
                .id(1L)
                .name("Reactor-A")
                .power(1200)
                .status(ReactorStatus.ACTIVE)
                .build();
    }
}
```

