const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            plantas: [],
            plantas_filtradas: [],
            tipo_plantas: [],
            tipo_planta: "",
            cantidad_plantas: 0,
            rango_precio: 5000,
            filtros_arreglo: "",
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            carrito: [],
            contadorCarrito: 0,
            filtro_planta_carrito: [],
            cantidad: "",
            plantaId: [],
            totalCompra: 0,
        }
    },
    created() {
        this.cargarDatos()
    },
    methods: {
        cargarDatos() {
            axios.get('/api/productos')
                .then(respuesta => {
                    this.plantas = respuesta.data.filter(planta => planta.activo);
                    
                    for(planta of this.plantas){
                        planta.contador = 1
                    }

                    this.plantas_filtradas = this.plantas;
                    this.cantidad_plantas = this.plantas.length;
                    this.tipo_plantas = Array.from(new Set(this.plantas.map(planta => planta.tipoProducto)));
                })
                .catch(error => console.log(error))
            this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
        },
        filtro_tipo(planta) {
            return planta.tipoPlanta.includes(this.tipo_planta);
        },
        filtro_precio(planta) {
            return planta.precio <= this.rango_precio;
        },
        filtros() {
            this.plantas_filtradas = this.plantas.filter(planta => {
                return (this.filtro_tipo(planta) && this.filtro_precio(planta));
            })
        },
        filtro_plantas() {
            if (this.filtros_arreglo == 2) {
                this.plantas_filtradas.sort((planta1, planta2) => {
                    return (planta1.precio - planta2.precio);
                })
            } else if (this.filtros_arreglo == 1) {
                this.plantas_filtradas.sort((planta1, planta2) => {
                    return (planta2.precio - planta1.precio);
                })
            }
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
        añadirCarrito(id){
            this.filtro_planta_carrito = this.plantas.filter(planta => planta.id == id)[0];  
            if (!(this.carrito.some(planta => planta.id == id))) {
                this.carrito.push(this.filtro_planta_carrito);
                this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
            }                
        },
        sumar(id){
            this.carrito.map(planta => {
                if(planta.id == id){
                    if (planta.stock === planta.contador) {
                        planta.contador += 0
                    } else {
                        planta.contador += 1
                    }                    
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        resta(id){
            this.carrito.map(planta => {
                if(planta.id == id){
                    if (planta.contador === 1) {
                        planta.contador -= 0
                    } else {
                        planta.contador -= 1
                    }                 
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        eliminar(id){
            this.carrito.map(planta => {
                if(planta.id == id){
                    planta.contador = 1             
                }
            })
            this.carrito = this.carrito.filter(planta => !(planta.id === id))
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        crearOrden(){
            axios.post("/api/cliente/orden")
            .then(response => {
                for( producto of this.carrito ){
                    axios.post("/api/cliente/carrito",
                    {
                        "id": producto.id,
                        "idCliente": 1,
                        "unidadesSeleccionadas": producto.contador,
                    })
                    .then(respuesta => {
                        this.carrito = [];
                        this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
                        localStorage.setItem("carrito", JSON.stringify(this.carrito));
                        localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                        window.location.href="/web/paginas/pedidos.html"
                    })
                    .catch(error => console.log(error))
                }               
            })
            .catch(error => console.log(error))
        },
        logout(){
            axios.post('/api/logout')
            .then(response=> console.log('signed out!!!'), (window.location.href = '/index.html'))
            .catch(error => console.log(error))
        },
    }
}).mount("#app")