const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            nombre: "",
            colorCard: "",
            typeCard: "",
            cvv: "",
            number1: "",
            number2: "",
            number3: "",
            number4: "",
            emailClient: "",
            cliente: "",
        }
    },
    created() {
        this.cargarDatos()
        this.cargarCliente()
    },
    //LOADER
    mounted() {
        window.onload = function () {
            var loader = document.getElementById('loader');
            loader.style.display = 'none'; // Ocultar el loader una vez que la página haya cargado completamente
        }
    },
    methods: {
        createNumberCard() {
            this.cardNumber = this.number1 + "-" + this.number2 + "-" + this.number3 + "-" + this.number4;
        },
        cargarDatos() {
            axios.get('/api/cliente/orden')
                .then(respuesta => {
                    this.ordenes = respuesta.data
                    console.log(this.ordenes[0]);
                })
                .catch(error => console.log(error))
        },
        cargarCliente() {
            axios.get('/api/clientes/actual')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                    console.log(this.cliente);
                })
                .catch(error => {
                    this.cliente = []
                })
        },
        payCard() {
            Swal.fire({
                title: '¿Está seguro de pagar con esta tarjeta? ' + this.cardNumber,
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Confirmar',
                confirmButtonColor: "#7c601893",
                preConfirm: () => {
                    console.log(this.ordenes[0].precioTotal);
                    return axios.post('/api/cliente/comprobante',
                        {
                            "type": this.typeCard,
                            "color": this.colorCard,
                            "number": this.cardNumber,
                            "cvv": this.cvv,
                            "email": this.cliente.email,
                            "amount": this.ordenes[0].precioTotal

                        })
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                text: 'Pago realizado',
                                showConfirmButton: false,
                                timer: 3000,
                            }).then(() => window.location.href = "/web/paginas/pedidos.html")
                        })
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                text: error.response.data,
                                confirmButtonColor: "#7c601893",
                            })
                            console.log(error);
                        })
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        }
    },
}).mount("#app");