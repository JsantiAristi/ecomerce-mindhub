const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            ordenes:[],
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
            })
            .catch(error => console.log(error))
        },
        
       
    }
}).mount("#app")