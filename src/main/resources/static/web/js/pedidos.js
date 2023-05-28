const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            ordenes:[],
            productosSeleccionadosSet:[],
            carrito: [],
            isCuentaInactivo: true,
            isCarritoInactivo: true,
        }
    },
    created(){
        this.cargarDatos()
    },
    methods: {
        cargarDatos(){
            axios.get('/api/cliente/orden/1')
            .then(respuesta => {
                this.ordenes=respuesta.data
                console.log(this.ordenes);
                this.productosSeleccionadosSet = this.ordenes[0].productosSeleccionadosSet;
                console.log(this.productosSeleccionadosSet);
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
    }
}).mount("#app")