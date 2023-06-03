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
            isCuentaInactivo: true,
            isCarritoInactivo: true,
        }
    },
    created() {
        this.cargarDatos()
    },
    //LOADER
    mounted() {
        window.onload = function () {
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
        logout() {
            Swal.fire({
                title: 'Esta seguro de cerrar sesión?',
                text: "Confirmar",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#324545',
                cancelButtonColor: '#db3939',
                background: ' rgb(238 243 236)',
                iconColor: "#324545",
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