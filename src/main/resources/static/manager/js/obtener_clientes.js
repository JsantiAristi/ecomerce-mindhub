const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            clientes: [],
            clientes_filtadras: [],
            email:"",
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
        logout() {
            axios.post('/api/logout')
              .then(response => {
                this.carrito = [];
                this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                window.location.href = '/index.html'
              })
              .catch(error => console.log(error))
          },
          eliminarCliente(email){
            axios.put('/api/clientes', `email=${email}`)
            .then(response => {
                Swal.fire({
                    title: 'Mensaje de confirmación',
                    text: 'Se ha eliminado un cliente',
                    icon: 'success',
                });
                this.cargarDatos();
                console.log(this.clientes);
            }).catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Error eliminando un cliente',
                    text: err.response.data,
                })
            })
          }
        
    }
}).mount("#app")