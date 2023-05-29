const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            cliente: '',
            nombre: '',
            apellido: '',
            cedulaCiudadania: '',
            telefono: '',
            genero: '',
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
    methods: {
        cargarDatos() {
            axios.get('/api/clientes/actual/1')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                    this.nombre = this.cliente.nombre;
                    this.apellido = this.cliente.apellido;
                    this.cedulaCiudadania = this.cliente.cedulaCiudadania;
                    this.telefono = this.cliente.telefono;
                    this.genero = this.cliente.genero;
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
                            "cedulaCiudadania": this.cedulaCiudadania,
                            "telefono": this.telefono,
                            "genero": this.genero,
                            "fechaNacimiento": this.fechaNacimiento
                        }
                    )
                        .then(response => {
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
                }
            })
            this.carrito = this.carrito.filter(planta => !(planta.id === id))
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