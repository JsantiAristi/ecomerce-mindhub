const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            ordenes: [],
            productosSeleccionadosSet: [],
            carrito: [],
            totalCompra: "",
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            plantas: [],
        }
    },
    created() {
        this.cargarDatos()
        this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
    },
    methods: {
        cargarDatos() {
            axios.get('/api/cliente/orden')
                .then(respuesta => {
                    console.log(respuesta.data);
                    this.ordenes = respuesta.data
                    console.log(this.ordenes);
                    this.productosSeleccionadosSet = this.ordenes[0].productosSeleccionadosSet;
                    console.log(this.productosSeleccionadosSet);
                })
                .catch(error => console.log(error))
        },
        cargarDatosPlantas() {
            axios.get('/api/plantas')
                .then(respuesta => {
                    this.plantas = respuesta.data.filter(planta => planta.activo);
                    console.log(this.plantas)
                })
                .catch(error => console.log(error))
            // this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            //this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
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
            console.log(id);
            axios.put("/api/cliente/carrito/suma", `idProducto=${id}`)
                .then(response => {
                    Swal.fire({
                        title: 'Message Confirmation',
                        text: 'one unit has been added',
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
    }
}).mount("#app")