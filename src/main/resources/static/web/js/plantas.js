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
        }
    },
    created() {
        this.cargarDatos()
        this.cargarDatosCarrito()
    },
    methods: {
        cargarDatos() {
            axios.get('/api/plantas')
                .then(respuesta => {
                    this.plantas = respuesta.data.filter(planta => planta.activo);
                    this.plantas_filtradas = this.plantas;
                    this.cantidad_plantas = this.plantas.length;
                    console.log(this.plantas);
                    this.tipo_plantas = Array.from(new Set(this.plantas.map(planta => planta.tipoPlanta)));
                    console.log(this.tipo_plantas);
                })
                .catch(error => console.log(error))
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
        cargarDatosCarrito() {
            axios.get("/api/carrito")
                .then(response => {
                    this.carrito = response.data;
                    console.log(this.carrito);
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
        añadirCarrito(id){
            axios.post("/api/cliente/carrito",`idProducto=${id}`)
            .then(response => {
                window.location.href="../paginas/plantas.html"
            })
        }
    }
}).mount("#app")