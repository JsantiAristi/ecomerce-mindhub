const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            ordenes: [],
            ordenesProceso: [],
            // ordenesRealizadas: [],
            // productosRealizados: "",
            productosProceso: [],
            totalProductosOrden: 0,
            totalPrecioOrden: 0,
            carrito: [],
            totalCompra: 0,
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            plantas: [],
            cliente: ''
        }
    },
    created() {
        this.cargarDatos()
        this.cargarDatosCliente()
        this.cargarComprobantes()
        this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
    },
    //LOADER
  mounted() {
    window.onload = function() {
      var loader = document.getElementById('loader');
      loader.style.display = 'none'; // Ocultar el loader una vez que la página haya cargado completamente
    }
  },
    methods: {
        cargarDatos() {
            axios.get('/api/cliente/orden')
                .then(respuesta => {
                    this.ordenes = respuesta.data
                    this.ordenesProceso = this.ordenes.filter(orden => !orden.comprado)
                    console.log(this.ordenesProceso);
                    this.productosProceso = this.ordenesProceso[0].productosSeleccionadosSet;

                    for (producto of this.productosProceso) {
                        this.totalProductosOrden += producto.cantidad;
                        this.totalPrecioOrden += (producto.precio * producto.cantidad);
                    }

                    console.log(this.productosProceso);
                })
                .catch(error => console.log(error))
        },
        cargarDatosPlantas() {
            axios.get('/api/plantas')
                .then(respuesta => {
                    this.plantas = respuesta.data.filter(planta => planta.activo);
                })
                .catch(error => console.log(error))
        },
        cargarComprobantes() {
            axios.get('/api/cliente/comprobante')
                .then(respuesta => {
                    this.comprobantes = respuesta.data
                    console.log(this.comprobantes);
                })
                .catch(error => console.log(error))
        },
        cargarDatosCliente() {
            axios.get('/api/clientes/actual')
                .then(respuesta => {
                    this.cliente = respuesta.data
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
        logout() {
            Swal.fire({
                title: 'Esta seguro de cerrar sesión?',
                text: "Confirmar",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#324545',
                cancelButtonColor: '#db3939',
                background: ' rgb(238 243 236)',
                iconColor: "#324545",
                confirmButtonText: 'Si, cerrar sesión!',
                cancelButtonText: 'Cancelar'
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
        //probando
        sumarProducto(id) {
            this.productosProceso.forEach(producto => {
                if (producto.id === id) {
                    producto.cantidad += 1;
                    producto.precioTotal = producto.precio * producto.cantidad;
                    this.totalProductosOrden += 1;
                    this.totalPrecioOrden = this.productosProceso.reduce((acomulador, producto) => acomulador + producto.precio * producto.cantidad,
                        0)
                }
            });
        },
        restarProducto(id) {
            this.productosProceso.forEach(producto => {
                if (producto.id === id && producto.cantidad > 1) {
                    producto.cantidad -= 1;
                    producto.precioTotal = producto.precio * producto.cantidad;
                    this.totalProductosOrden -= 1;
                    this.totalPrecioOrden = this.productosProceso.reduce((acomulador, producto) => acomulador + producto.precio * producto.cantidad,
                        0)
                }
            });
        },
        crearOrden() {
            const ordenData = {
                idCliente: 1,
                productos: this.carrito.map(producto => ({
                    id: producto.id,
                    unidadesSeleccionadas: producto.contador
                }))
            };

            axios
                .post('/api/cliente/orden', ordenData)
                .then(response => {
                    console.log('Compra realizada con éxito');

                    // Limpiar el carrito y el total de compra en el front-end
                    this.carrito = [];
                    this.totalCompra = 0;

                    // Limpiar el carrito y el total de compra en el almacenamiento local
                    localStorage.removeItem('carrito');
                    localStorage.removeItem('totalCompra');
                })
                .catch(error => {
                    console.log('Error al realizar la compra:', error);
                });
        },
        actualizarOrden() {
            for (producto of this.productosProceso) {
                axios.post("/api/orden/actualizacion",
                    {
                        "id": producto.id,
                        "unidadesSeleccionadas": producto.cantidad,
                    })
                    .then(response => { window.location.href = '/web/paginas/pagos.html' })
                    .catch(error => console.log(error))
            }
        },
        borrarProducto(id){
            Swal.fire({
                title: 'Esta seguro de cerrar sesión?',
                text: "Confirmar",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#324545',
                cancelButtonColor: '#db3939',
                background: ' rgb(238 243 236)',
                iconColor: "#324545",
                confirmButtonText: 'Si, eliminar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.delete("/api/cliente/carrito/"+id)
                    .then(response => {
                        Swal.fire({
                            icon: 'success',
                            text: 'Se elimino correctamente',
                            showConfirmButton: false,
                            timer: 2000,
                        }).then(() => window.location.href = "/web/paginas/pedidos.html")
                    })
                    .catch(error => {
                        Swal.fire({
                            icon: 'error',
                            text: error.response.data,
                            showConfirmButton: false,
                            timer: 2000,
                        })
                    })
                }
            })           
        }
    }
}).mount("#app")