const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            accesorios: [],
            accesorios_filtradas: [],
            tipo_accesorios: [],
            tipo_accesorio: "",
            cantidad_accesorios: 0,
            rango_precio: 5000,
            filtros_arreglo: "",
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            carrito: [],
            contadorCarrito: 0,
            filtro_accesorio_carrito: [],
            cantidad: "",
            accesorioId: [],
            totalCompra: 0,
        }
    },
    created() {
        this.cargarDatos()
    },
    methods: {
        cargarDatos() {
            axios.get('/api/accesorios')
                .then(respuesta => {
                    this.accesorios = respuesta.data.filter(accesorio => accesorio.activo);
                    

                    for(accesorio of this.accesorios){
                        accesorio.contador = 1
                    }

                    this.accesorios_filtradas = this.accesorios;
                    this.cantidad_accesorios = this.accesorios.length;
                    this.tipo_accesorios = Array.from(new Set(this.accesorios.map(accesorio => accesorio.tipoAccesorio)));
                })
                .catch(error => console.log(error))
            this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
        },
        filtro_tipo(accesorio) {
            return accesorio.tipoAccesorio.includes(this.tipo_accesorio);
        },
        filtro_precio(accesorio) {
            return accesorio.precio <= this.rango_precio;
        },
        filtros() {
            this.accesorios_filtradas = this.accesorios.filter(accesorio => {
                return (this.filtro_tipo(accesorio) && this.filtro_precio(accesorio));
            })
        },
        filtro_accesorios() {
            if (this.filtros_arreglo == 2) {
                this.accesorios_filtradas.sort((accesorio1, accesorio2) => {
                    return (accesorio1.precio - accesorio2.precio);
                })
            } else if (this.filtros_arreglo == 1) {
                this.accesorios_filtradas.sort((accesorio1, accesorio2) => {
                    return (accesorio2.precio - accesorio1.precio);
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
            this.filtro_accesorio_carrito = this.accesorios.filter(accesorio => accesorio.id == id)[0];  
            if (!(this.carrito.some(accesorio => accesorio.id == id))) {
                this.carrito.push(this.filtro_accesorio_carrito);
                this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
            }                
        },
        sumar(id){
            this.carrito.map(accesorio => {
                if(accesorio.id == id){
                    if (accesorio.stock === accesorio.contador) {
                        accesorio.contador += 0
                    } else {
                        accesorio.contador += 1
                    }                    
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        resta(id){
            this.carrito.map(accesorio => {
                if(accesorio.id == id){
                    if (accesorio.contador === 1) {
                        accesorio.contador -= 0
                    } else {
                        accesorio.contador -= 1
                    }                 
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        eliminar(id){
            this.carrito.map(accesorio => {
                if(accesorio.id == id){
                    accesorio.contador = 1             
                }
            })
            this.carrito = this.carrito.filter(accesorio => !(accesorio.id === id))
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        crearOrden(){
            axios.post("/api/cliente/orden",`idCliente=${1}`)
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
    }
}).mount("#app")