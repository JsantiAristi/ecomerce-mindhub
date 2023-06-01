const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            ordenes: [],
            ordenesProceso: [],
            ordenesRealizadas: [],
            productosRealizados: "",
            productosProceso: "",
            carrito: [],
            totalCompra: "",
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            plantas: [],
            cliente:''
        }
    },
    created() {
        this.cargarDatos()
        this.cargarDatosCliente()
        this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
    },
    methods: {
        cargarDatos() {
            axios.get('/api/cliente/orden')
                .then(respuesta => {
                    this.ordenes = respuesta.data
                    this.ordenesProceso = this.ordenes.filter( orden => !orden.comprado )
                    this.ordenesRealizadas = this.ordenes.filter( orden => orden.comprado )

                    this.productosProceso = this.ordenesProceso[0].productosSeleccionadosSet;
                    this.productosRealizados = this.ordenesRealizadas[0].productosSeleccionadosSet;
                    console.log(this.productosRealizados);
                })
                .catch(error => console.log(error))
        },
        cargarDatosPlantas() {
            axios.get('/api/plantas')
                .then(respuesta => {
                    this.plantas = respuesta.data.filter(planta => planta.activo);
                })
                .catch(error => console.log(error))
            // this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            //this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
        },
        cargarDatosCliente(){
            axios.get('/api/clientes/actual')
            .then(respuesta => {
                this.cliente=respuesta.data
            })
            .catch(error => console.log(error))
        },
        aparecerCuenta() {
            if (this.isCarritoInactivo) {
                this.isCuentaInactivo = !this.isCuentaInactivo;
            } else {
                this.isCarritoInactivo = !this.isCarritoInactivo;
                this.isCuentaInactivo = !this.isCuentaInactivo;
            }
        },
        aparecerCarrito() {
            if (this.isCuentaInactivo) {
                this.isCarritoInactivo = !this.isCarritoInactivo;
            } else {
                this.isCuentaInactivo = !this.isCuentaInactivo;
                this.isCarritoInactivo = !this.isCarritoInactivo;
            }
        },
        sumar(nombre) {
            this.carrito.map(planta => {
                if (planta.nombre == nombre) {
                    if (planta.stock > 0) {
                        planta.contador += 0
                    } else {
                        planta.contador += 1
                    }
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        resta(nombre) {
            this.carrito.map(planta => {
                if (planta.nombre == nombre) {
                    if (planta.contador === 1) {
                        planta.contador -= 0
                    } else {
                        planta.contador -= 1
                    }
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        eliminar(id) {
            this.carrito.map(planta => {
                if (planta.id == id) {
                    planta.contador = 1
                }
            })
            this.carrito = this.carrito.filter(planta => !(planta.id === id))
            this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        crearOrden() {
            axios.post("/api/cliente/orden", `idCliente=${1}`)
                .then(response => {
                    for (producto of this.carrito) {
                        axios.post("/api/cliente/carrito",
                            {
                                "id": producto.id,
                                "idCliente": 1,
                                "unidadesSeleccionadas": producto.contador,
                            })
                            .then(respuesta => {
                                this.carrito = [];
                                this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
                                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                                window.location.href = "/web/paginas/pedidos.html"
                            })
                            .catch(error => console.log(error))
                    }
                })
                .catch(error => console.log(error))
        },
        sumarProducto(id) {
            axios.put("/api/cliente/carrito/suma", `idProducto=${id}`)
                .then(response => {
                    Swal.fire({
                        title: 'Mensaje de confirmación',
                        text: 'Se ha añadido una unidad',
                        icon: 'success',
                        didOpen: () => {
                            document.querySelector('.swal2-confirm').addEventListener('click', () => { location.reload(true) })
                        },
                    })
                }).catch(err => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error adding a product unit',
                        text: err.response.data,
                    })
                })
            /*.then(response => window.location.href="/web/paginas/pedidos.html")
            .catch(error => console.log(error))*/
        },
        restarProducto(id) {
            console.log(id);
            axios.put("/api/cliente/carrito/resta", `idProducto=${id}`)
                .then(response => {
                    Swal.fire({
                        title: 'Mensaje de confirmación',
                        text: 'Se ha eliminado una unidad',
                        icon: 'success',
                        didOpen: () => {
                            document.querySelector('.swal2-confirm').addEventListener('click', () => { location.reload(true) })
                        },
                    })
                }).catch(err => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error eliminando una unidad',
                        text: err.response.data,
                    })
                })
            /*.then(response => window.location.href="/web/paginas/pedidos.html")
            .catch(error => console.log(error))*/
        },
        logout() {
            Swal.fire({
                title: 'Esta seguro de cerrar sesión?',
                text: "Confirmar",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, log out!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/logout')
                    .then(response => {
                      this.carrito = [];
                      this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
                      localStorage.setItem("carrito", JSON.stringify(this.carrito));
                      localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                      window.location.href = '/index.html'
                    })
                    .catch(error => console.log(error))
                }
              })
          
          }, 
    }
}).mount("#app")