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
            isCuentaInactivo: true,
            isCarritoInactivo: true,
        }
    },
    created() {
        this.cargarDatos()
        this.nombre = this.cliente.nombre
    },
    methods: {
        cargarDatos() {
            axios.get('/api/clientes/actual/1')
                .then(respuesta => {
                    this.cliente = respuesta.data
                    console.log(this.cliente);
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
                confirmButtonText: 'Yes, create it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    // console.log(this.cliente.id)
                    // console.log(this.cliente.nombre)
                    // console.log(this.cliente.apellido)
                    // console.log(this.cliente.cedulaCiudadania)
                    // console.log(this.cliente.telefono)
                    // console.log(this.cliente.genero)
                    // console.log(this.cliente.fechaNacimiento)
                    axios.put('/api/clientes/actual',
                        {
                            "id": this.cliente.id,
                            "nombre": this.cliente.nombre,
                            "apellido": this.cliente.apellido,
                            "cedulaCiudadania": this.cliente.cedulaCiudadania,
                            "telefono": this.cliente.telefono,
                            "genero": this.cliente.genero,
                            "fechaNacimiento": this.cliente.fechaNacimiento
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

    }
}).mount("#app")