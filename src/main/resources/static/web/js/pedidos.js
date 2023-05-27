const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            ordenes:[],
            productosSeleccionadosSet:[],
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
    }
}).mount("#app")