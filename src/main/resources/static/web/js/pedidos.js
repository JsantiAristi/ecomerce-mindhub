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
            cliente:''
        }
    },
    created() {
        this.cargarDatos()
        this.cargarDatosCliente()
        this.cargarComprobantes()
        this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
    },
    methods: {
        cargarDatos() {
            axios.get('/api/cliente/orden')
                .then(respuesta => {
                    this.ordenes = respuesta.data
                    this.ordenesProceso = this.ordenes.filter( orden => !orden.comprado )
                    this.productosProceso = this.ordenesProceso[0].productosSeleccionadosSet;

                    for( producto of this.productosProceso ){
                        this.totalProductosOrden += producto.cantidad;
                        this.totalPrecioOrden += producto.precio;
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
            // this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            //this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
        },
        cargarComprobantes() {
            axios.get('/api/cliente/comprobante')
                .then(respuesta => {
                    this.comprobantes = respuesta.data
                    console.log(this.comprobantes);
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



        // crearOrden() {
        //     axios.post("/api/cliente/orden", `idCliente=${1}`)
        //         .then(response => {
        //             for (producto of this.carrito) {
        //                 axios.post("/api/cliente/carrito",
        //                     {
        //                         "id": producto.id,
        //                         "idCliente": 1,
        //                         "unidadesSeleccionadas": producto.contador,
        //                     })
        //                     .then(respuesta => {
        //                         this.carrito = [];
        //                         this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
        //                         localStorage.setItem("carrito", JSON.stringify(this.carrito));
        //                         localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        //                         window.location.href = "/web/paginas/pedidos.html"
        //                     })
        //                     .catch(error => console.log(error))
        //             }
        //         })
        //         .catch(error => console.log(error))
        // },
        // sumarProducto(id) {
        //     axios.put("/api/cliente/carrito/suma", `idProducto=${id}`)
        //     .then(response => window.location.href="/web/paginas/pedidos.html")
        //     .catch(error => console.log(error))
        // },
        // restarProducto(id) {
        //     console.log(id);
        //     axios.put("/api/cliente/carrito/resta", `idProducto=${id}`)
                
        //     .then(response => window.location.href="/web/paginas/pedidos.html")
        //     .catch(error => console.log(error))
        // },
        // sumarProducto(id) {
        //     this.carrito.map(producto => {
        //       if (producto.id === id) {
        //         producto.contador += 1;
        //       }
        //     });
        //     this.actualizarTotalesEnBackend();
        //   },
        //   restarProducto(id) {
        //     this.carrito.map(producto => {
        //       if (producto.id === id) {
        //         if (producto.contador > 1) {
        //           producto.contador -= 1;
        //         }
        //       }
        //     });
        //     this.actualizarTotalesEnBackend();
        //   },
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


          
          //probando
          sumarProducto(id) {
            this.productosProceso.forEach(producto => {
              if (producto.id === id) {
                producto.cantidad += 1;
                this.totalProductosOrden += 1;
                this.totalPrecioOrden = this.productosProceso.reduce( (acomulador, producto) => acomulador + producto.precio * producto.cantidad,
                0)
              }
            });
            this.actualizarTotales();
          },
          restarProducto(id) {
            this.productosProceso.forEach(producto => {
              if (producto.id === id && producto.cantidad > 1) {
                producto.cantidad -= 1;
                this.totalProductosOrden += 1;
                this.totalPrecioOrden = this.productosProceso.reduce( (acomulador, producto) => acomulador + producto.precio * producto.cantidad,
                0)
              }
            });
            this.actualizarTotales();
          },
          actualizarTotales() {
            this.totalCompra = this.carrito.reduce(
              (acumulador, producto) => acumulador + producto.precio * producto.contador,
              0
            );
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra));
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

    }
}).mount("#app")