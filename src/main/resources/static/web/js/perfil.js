const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            cliente:'',
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            carrito: [],
        }
    },
    created(){
        this.cargarDatos()
    },
    methods: {
        cargarDatos(){
            axios.get('/api/clientes/actual/1')
            .then(respuesta => {
                this.cliente=respuesta.data
                console.log(this.cliente);
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