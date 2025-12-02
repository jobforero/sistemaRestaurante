# 🍽️ Sistema de Gestión de Restaurante

Sistema completo de gestión para restaurantes desarrollado en Java con interfaz gráfica Swing. Permite administrar productos, pedidos y facturas, además de ofrecer una interfaz dedicada para que los clientes realicen sus pedidos.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura del Sistema](#-arquitectura-del-sistema)
- [Requisitos](#-requisitos)
- [Instalación y Compilación](#-instalación-y-compilación)
- [Ejecución](#-ejecución)
- [Guía de Uso](#-guía-de-uso)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Flujo de Trabajo](#-flujo-de-trabajo)
- [Modelo de Datos](#-modelo-de-datos)
- [Capturas de Pantalla](#-capturas-de-pantalla)

---

## ✨ Características

### Sistema de Administración

- ✅ **Gestión de Productos**: Crear y administrar comidas, bebidas y combos
- ✅ **Gestión de Pedidos**: Crear pedidos, agregar productos y cambiar estados
- ✅ **Generación de Facturas**: Facturar pedidos completados con información del cliente
- ✅ **Estadísticas en Tiempo Real**: Ver productos disponibles, pedidos activos y facturas generadas
- ✅ **Interfaz Intuitiva**: Navegación por pestañas con acceso rápido a todas las funciones

### Sistema de Clientes

- ✅ **Interfaz Dedicada**: Ventana separada para que los clientes hagan pedidos
- ✅ **Carrito de Compras**: Agregar/quitar productos con visualización en tiempo real
- ✅ **Cálculo Automático**: Total actualizado automáticamente al agregar productos
- ✅ **Integración Completa**: Los pedidos de clientes aparecen en el sistema de administración
- ✅ **Validaciones**: Nombre obligatorio, carrito no vacío, confirmaciones

---

## 🏗️ Arquitectura del Sistema

El sistema sigue el patrón **MVC (Modelo-Vista-Controlador)** y principios de **Programación Orientada a Objetos**:

```
┌─────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                 │
│  ┌──────────────────────┐  ┌──────────────────────┐    │
│  │ SistemaRestauranteGUI│  │  ClientePedidoGUI    │    │
│  │   (Administración)   │  │    (Clientes)        │    │
│  └──────────────────────┘  └──────────────────────┘    │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                   CAPA DE SERVICIOS                     │
│  ┌─────────────┐  ┌─────────────┐  ┌──────────────┐   │
│  │   Gestor    │  │   Gestor    │  │   Gestor     │   │
│  │  Productos  │  │   Pedidos   │  │  Facturas    │   │
│  └─────────────┘  └─────────────┘  └──────────────┘   │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                     CAPA DE MODELO                      │
│  ┌────────┐  ┌────────┐  ┌────────┐  ┌────────┐       │
│  │Producto│  │ Pedido │  │Factura │  │ Combo  │       │
│  └────────┘  └────────┘  └────────┘  └────────┘       │
│  ┌────────┐  ┌────────┐                                │
│  │ Comida │  │ Bebida │                                │
│  └────────┘  └────────┘                                │
└─────────────────────────────────────────────────────────┘
```

### Principios de Diseño

- **Encapsulamiento**: Cada clase protege sus datos internos
- **Herencia**: Comida y Bebida heredan de Producto
- **Polimorfismo**: Tratamiento uniforme de diferentes tipos de productos
- **Separación de Responsabilidades**: Modelo, lógica de negocio y presentación separados
- **Inyección de Dependencias**: Los gestores se pasan como parámetros

---

## 💻 Requisitos

- **Java JDK**: 8 o superior
- **Sistema Operativo**: Windows, Linux o macOS
- **Memoria RAM**: Mínimo 512 MB
- **Espacio en Disco**: 50 MB

---

## 🔧 Instalación y Compilación

### 1. Clonar o Descargar el Proyecto

```bash
cd sistemaRestaurante-main
```

### 2. Compilar el Proyecto

**Opción A - Usando el script incluido (Windows):**
```bash
cd src
run.bat
```

**Opción B - Compilación manual:**
```bash
javac -encoding UTF-8 -d bin -sourcepath src src/sistemaRestaurante/*.java src/modelo/*.java src/servicio/*.java
```

**Nota**: El parámetro `-encoding UTF-8` es importante para manejar caracteres especiales correctamente.

---

## 🚀 Ejecución

### Ejecutar el Sistema de Administración

```bash
java -cp bin sistemaRestaurante.SistemaRestauranteGUI
```

Esto abrirá la ventana principal del sistema con todas las funcionalidades de administración.

---

## 📖 Guía de Uso

### Para Administradores

#### 1. Panel de Inicio

Al abrir el sistema, verás:
- **Estadísticas del Sistema**: Productos disponibles, pedidos activos, facturas generadas
- **Botones de Acceso Rápido**:
  - `Actualizar Estadísticas`: Refresca los contadores
  - `Gestionar Productos`: Va a la pestaña de productos
  - `Gestionar Pedidos`: Va a la pestaña de pedidos
  - `Modo Cliente`: Abre la interfaz para clientes

#### 2. Gestión de Productos

**Agregar Comida:**
1. Ir a pestaña "Productos"
2. Clic en "Agregar Comida"
3. Ingresar: Nombre, Precio, Tipo (entrada/principal/postre)
4. Marcar si es vegetariano
5. Clic en "OK"

**Agregar Bebida:**
1. Clic en "Agregar Bebida"
2. Ingresar: Nombre, Precio, Tamaño (pequeño/mediano/grande)
3. Marcar si contiene alcohol
4. Clic en "OK"

**Agregar Combo:**
1. Clic en "Agregar Combo"
2. Ingresar: Nombre del combo, Descuento (%)
3. Clic en "OK"

#### 3. Gestión de Pedidos

**Crear Pedido (Como Admin):**
1. Ir a pestaña "Pedidos"
2. Clic en "Nuevo Pedido"
3. Seleccionar productos de la lista
4. Confirmar cada producto
5. Cuando termine, el pedido se crea automáticamente

**Generar Factura:**
1. Seleccionar un pedido de la tabla
2. Clic en "Generar Factura"
3. Si el pedido tiene cliente, confirmar
4. Si no, ingresar nombre del cliente
5. La factura se genera y el pedido cambia a "completado"

#### 4. Ver Facturas

1. Ir a pestaña "Facturas"
2. Clic en "Mostrar Facturas"
3. Se muestra la lista de todas las facturas generadas

### Para Clientes

#### 1. Abrir Interfaz de Cliente

Desde el sistema de administración:
1. Ir a pestaña "Inicio"
2. Clic en "Modo Cliente"
3. Se abre una nueva ventana

#### 2. Realizar Pedido

**Paso 1 - Ingresar Nombre:**
- Escribir tu nombre en el campo "Tu Nombre"

**Paso 2 - Seleccionar Productos:**
1. En la tabla izquierda, hacer clic en un producto
2. Clic en "+ Agregar al Carrito"
3. El producto aparece en la tabla derecha (carrito)
4. El total se actualiza automáticamente
5. Repetir para agregar más productos

**Paso 3 - Quitar Productos (Opcional):**
1. Seleccionar producto en el carrito
2. Clic en "- Quitar del Carrito"

**Paso 4 - Finalizar Pedido:**
1. Verificar que el nombre esté ingresado
2. Verificar que el carrito tenga productos
3. Clic en "Realizar Pedido"
4. Aparece confirmación con número de pedido
5. El carrito se limpia automáticamente

#### 3. Múltiples Clientes

- Puedes abrir varias ventanas de cliente simultáneamente
- Cada cliente puede hacer su pedido independientemente
- Todos los pedidos aparecen en el sistema de administración

---

## 📁 Estructura del Proyecto

```
sistemaRestaurante-main/
│
├── src/
│   ├── modelo/                      # Capa de Modelo
│   │   ├── Producto.java           # Clase abstracta base
│   │   ├── Comida.java             # Productos tipo comida
│   │   ├── Bebida.java             # Productos tipo bebida
│   │   ├── Combo.java              # Combos con descuento
│   │   ├── Pedido.java             # Pedidos del sistema
│   │   └── Factura.java            # Facturas generadas
│   │
│   ├── servicio/                    # Capa de Servicios
│   │   ├── GestorProductos.java    # Lógica de productos
│   │   ├── GestorPedidos.java      # Lógica de pedidos
│   │   └── GestorFacturas.java     # Lógica de facturas
│   │
│   ├── sistemaRestaurante/          # Capa de Presentación
│   │   ├── SistemaRestauranteGUI.java  # Interfaz admin
│   │   └── ClientePedidoGUI.java       # Interfaz cliente
│   │
│   ├── utilidades/                  # Utilidades
│   │   ├── Utiles.java
│   │   └── Validaciones.java
│   │
│   ├── docs/                        # Documentación JavaDoc
│   └── run.bat                      # Script de compilación
│
├── bin/                             # Archivos compilados (.class)
│
└── README.md                        # Este archivo
```

---

## 🔄 Flujo de Trabajo

### Flujo Completo del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                    INICIO DEL SISTEMA                       │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│              INICIALIZACIÓN DE GESTORES                     │
│  • GestorProductos (administra productos)                   │
│  • GestorPedidos (administra pedidos)                       │
│  • GestorFacturas (administra facturas)                     │
└─────────────────────────────────────────────────────────────┘
                          ↓
        ┌─────────────────┴─────────────────┐
        ↓                                   ↓
┌──────────────────┐              ┌──────────────────┐
│  ADMINISTRADOR   │              │    CLIENTE       │
└──────────────────┘              └──────────────────┘
        ↓                                   ↓
┌──────────────────┐              ┌──────────────────┐
│ Gestionar        │              │ Abrir Interfaz   │
│ Productos        │              │ de Cliente       │
└──────────────────┘              └──────────────────┘
        ↓                                   ↓
┌──────────────────┐              ┌──────────────────┐
│ Crear Pedidos    │              │ Ingresar Nombre  │
│ (Admin)          │              └──────────────────┘
└──────────────────┘                        ↓
        ↓                          ┌──────────────────┐
        │                          │ Seleccionar      │
        │                          │ Productos        │
        │                          └──────────────────┘
        │                                   ↓
        │                          ┌──────────────────┐
        │                          │ Realizar Pedido  │
        │                          └──────────────────┘
        │                                   ↓
        └───────────────┬───────────────────┘
                        ↓
              ┌──────────────────┐
              │ PEDIDO CREADO    │
              │ Estado: Pendiente│
              └──────────────────┘
                        ↓
              ┌──────────────────┐
              │ Aparece en Tabla │
              │ de Pedidos       │
              └──────────────────┘
                        ↓
              ┌──────────────────┐
              │ Admin Selecciona │
              │ Pedido           │
              └──────────────────┘
                        ↓
              ┌──────────────────┐
              │ Generar Factura  │
              └──────────────────┘
                        ↓
              ┌──────────────────┐
              │ FACTURA GENERADA │
              │ Pedido: Completado│
              └──────────────────┘
                        ↓
              ┌──────────────────┐
              │ Actualizar       │
              │ Estadísticas     │
              └──────────────────┘
```

### Ciclo de Vida de un Pedido

```
┌──────────────┐
│   CREADO     │ ← Pedido nuevo (admin o cliente)
│ (pendiente)  │
└──────────────┘
       ↓
┌──────────────┐
│  PRODUCTOS   │ ← Se agregan productos al pedido
│  AGREGADOS   │
└──────────────┘
       ↓
┌──────────────┐
│ FACTURADO    │ ← Se genera factura con nombre de cliente
│ (completado) │
└──────────────┘
```

---

## 🗃️ Modelo de Datos

### Clase Producto (Abstracta)

```java
abstract class Producto {
    - String nombre
    - double precio
    
    + abstract double calcularPrecio()
    + String getNombre()
    + void setNombre(String)
}
```

### Clase Comida extends Producto

```java
class Comida {
    - String tipo              // entrada, principal, postre
    - boolean esVegetariano
    
    + double calcularPrecio()  // Retorna precio base
    + String getTipo()
    + boolean isEsVegetariano()
}
```

### Clase Bebida extends Producto

```java
class Bebida {
    - String tamano            // pequeño, mediano, grande
    - boolean conAlcohol
    
    + double calcularPrecio()  // Retorna precio base
    + String getTamano()
    + boolean isConAlcohol()
}
```

### Clase Combo extends Producto

```java
class Combo {
    - List<Producto> productos
    - double descuento         // Porcentaje 0-100
    
    + double calcularPrecio()  // Aplica descuento
    + void agregarProducto(Producto)
    + double getDescuento()
}
```

### Clase Pedido

```java
class Pedido {
    - int id                   // Auto-generado
    - List<Producto> productos
    - LocalDateTime fecha
    - String estado            // pendiente, completado, cancelado
    - String nombreCliente     // Nombre del cliente
    - String origenPedido      // admin o cliente
    
    + void agregarProducto(Producto)
    + double calcularTotal()
    + String getEstado()
    + void setEstado(String)
}
```

### Clase Factura

```java
class Factura {
    - int numero               // Auto-generado
    - Pedido pedido
    - String cliente
    - LocalDateTime fecha
    
    + double getTotal()
    + void imprimirFactura()
    + String getCliente()
}
```

---

## 🎯 Características Técnicas

### Gestores (Servicios)

#### GestorProductos
- `agregarComida(nombre, precio, tipo, vegetariano)`
- `agregarBebida(nombre, precio, tamano, alcohol)`
- `agregarCombo(nombre, descuento)`
- `getProductosDisponibles()`
- `getTotalProductos()`

#### GestorPedidos
- `crearPedido()` - Crea pedido de admin
- `crearPedidoCliente(nombreCliente)` - Crea pedido de cliente
- `agregarProductoAPedido(idPedido, producto)`
- `buscarPedidoPorId(id)`
- `getPedidosPendientes()`
- `cambiarEstadoPedido(id, estado)`

#### GestorFacturas
- `generarFactura(idPedido, cliente)`
- `buscarFacturaPorNumero(numero)`
- `getTodasLasFacturas()`
- `getTotalFacturado()`
- `getTotalFacturas()`

---

## 🎨 Interfaz Gráfica

### SistemaRestauranteGUI (Administración)

**Pestañas:**
1. **Inicio**: Estadísticas y acceso rápido
2. **Productos**: Gestión de productos
3. **Pedidos**: Gestión de pedidos
4. **Facturas**: Visualización de facturas
5. **Salir**: Cierre controlado del sistema

**Colores:**
- Azul: Información (#3498db)
- Verde: Éxito (#2ecc71)
- Naranja: Advertencia (#f39c12)
- Rojo: Error/Salir (#e74c3c)

### ClientePedidoGUI (Clientes)

**Secciones:**
- **Header**: Título y campo de nombre (Azul #3498db)
- **Productos**: Lista de productos disponibles (Verde #2ecc71)
- **Carrito**: Productos seleccionados (Rojo #e74c3c)
- **Footer**: Total y botones de acción (Gris #ecf0f1)

---

## 🔐 Validaciones Implementadas

### En Productos
- ✅ Nombre no vacío
- ✅ Precio positivo
- ✅ Descuento entre 0-100%
- ✅ Tipo válido (entrada/principal/postre)
- ✅ Tamaño válido (pequeño/mediano/grande)

### En Pedidos
- ✅ Pedido no vacío antes de facturar
- ✅ Estado válido (pendiente/completado/cancelado)
- ✅ Productos válidos

### En Facturas
- ✅ Nombre de cliente no vacío
- ✅ Pedido existe
- ✅ Pedido está pendiente
- ✅ Pedido tiene productos

### En Interfaz de Cliente
- ✅ Nombre de cliente obligatorio
- ✅ Carrito no vacío al realizar pedido
- ✅ Confirmación antes de limpiar carrito
- ✅ Producto seleccionado antes de agregar

---

## 📊 Estadísticas del Sistema

El panel de inicio muestra en tiempo real:

- **Productos Disponibles**: Total de productos en el sistema
- **Pedidos Activos**: Pedidos en estado "pendiente"
- **Facturas Generadas**: Total de facturas creadas

Estas estadísticas se actualizan:
- Automáticamente al cargar el panel
- Al hacer clic en "Actualizar Estadísticas"

---

## 🐛 Solución de Problemas

### Error de Compilación: "unmappable character"

**Solución**: Usar `-encoding UTF-8` al compilar:
```bash
javac -encoding UTF-8 -d bin -sourcepath src src/**/*.java
```

### La interfaz no se ve correctamente

**Solución**: Asegurarse de tener Java 8 o superior instalado.

### Los pedidos de clientes no aparecen

**Solución**: Verificar que ambas interfaces usen los mismos gestores (compartidos).

---

## 👥 Autores

**Grupo 1 Desarrollo Software**
- Versión: 2.1
- Año: 2025

---

## 📝 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

---

## 🚀 Próximas Mejoras Sugeridas

- [ ] Persistencia de datos (base de datos o archivos)
- [ ] Reportes en PDF
- [ ] Búsqueda y filtros avanzados
- [ ] Historial de pedidos por cliente
- [ ] Sistema de usuarios y autenticación
- [ ] Notificaciones en tiempo real
- [ ] Integración con impresora de tickets
- [ ] Dashboard con gráficos estadísticos
- [ ] Exportación de datos a Excel
- [ ] Sistema de inventario

---

## 📞 Soporte

Para reportar problemas o sugerencias, por favor contactar al equipo de desarrollo.

---

**¡Gracias por usar el Sistema de Gestión de Restaurante!** 🍽️
