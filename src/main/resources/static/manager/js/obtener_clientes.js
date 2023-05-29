const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            clientes: [],
            clientes_filtadras: [],
        }
    },
    created(){
        this.cargarDatos()
    },
    methods: {
        cargarDatos(){
            axios.get('/api/clientes')
            .then(respuesta => {
                this.clientes = respuesta.data.filter(cliente => cliente.valido);
                console.log(this.clientes);
            })
            .catch(error => console.log(error))
        },
        
    }
}).mount("#app")