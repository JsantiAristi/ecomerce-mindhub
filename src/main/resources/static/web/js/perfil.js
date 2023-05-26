const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
           cliente:''
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
        
       
    }
}).mount("#app")