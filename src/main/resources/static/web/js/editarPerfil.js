const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            cliente: '',
            nombre: '',
            apellido: '',
            dni: '',
            telefono: '',
            fechaNacimiento: '',
            carrito: [],
            totalCompra: "",
            isCuentaInactivo: true,
            isCarritoInactivo: true,
        }
    },
    created() {
        this.cargarDatos()
        this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
    },
    //LOADER
  mounted() {
    window.onload = function() {
      var loader = document.getElementById('loader');
      loader.style.display = 'none'; // Ocultar el loader una vez que la página haya cargado completamente
    }
  },
    methods: {
        cargarDatos() {
            axios.get('/api/clientes/actual')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                    this.nombre = this.cliente.nombre;
                    this.apellido = this.cliente.apellido;
                    this.dni = this.cliente.dni;
                    this.telefono = this.cliente.telefono;
                    this.fechaNacimiento = this.cliente.fechaNacimiento;
                })
                .catch(error => console.log(error))
        },
        editarPerfil() {

            Swal.fire({
                title: 'Esta seguro?',
                text: "Usted quiere modificar su informacion personal?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si',
                cancelButtonText: 'No',
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.put('/api/clientes/actual',
                        {
                            "id": this.cliente.id,
                            "nombre": this.nombre,
                            "apellido": this.apellido,
                            "dni": this.dni,
                            "telefono": this.telefono,
                            "fechaNacimiento": this.fechaNacimiento
                        }
                    )
                        .then(response => {
                            console.log(dni)
                            console.log(telefono);
                            Swal.fire({
                                title: 'Modificado!',
                                text: 'Usted ha modificado su informacion',
                                icon: 'success',
                                didOpen: () => {
                                    document.querySelector('.swal2-confirm').addEventListener('click', () => { window.location.href = '/web/paginas/perfil.html' })
                                }
                            })
                        }).catch(function (error) {
                            console.log(error)
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: error.response.data,
                            })
                        })
                }
            })
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
                    Swal.fire({
                        icon: 'success',
                        title: 'Eliminar producto',
                        text: 'Se ha eliminado el producto de su carrito!',
                        
                      })                
                }
            })
            this.carrito = this.carrito.filter(planta => !(planta.id === id))
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        crearOrden(){
            if(this.cliente){

            }
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
                    .catch(error => {
                        Swal.fire({
                          icon: 'error',
                          text: error.response.data,
                          background:' rgb(238 243 236)',
                          confirmButtonColor: " #324545",
                          iconColor:"#324545",                  
                        })
                      })
                }               
            })
            .catch(error => console.log(error))
        },
        logout() {
            Swal.fire({
                title: 'Esta seguro de cerrar sesión?',
                text: "Confirmar",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#324545',
                cancelButtonColor: '#db3939',
                background:' rgb(238 243 236)',
                iconColor:"#324545",
                confirmButtonText: 'Si, cerrar sesión!',
                cancelButtonText: 'Cancelar'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/logout')
                    .then(response => {
                      this.carrito = [];
                      this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
                      localStorage.setItem("carrito", JSON.stringify(this.carrito));
                      localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                      window.location.href = '/index.html'
                    })
                    .catch(error => console.log(error))
                }
              })
          
          },     
    }
}).mount("#app")