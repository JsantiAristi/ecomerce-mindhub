const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            plantas: [],
            plantas_filtadras: [],
            nombre: "",
            color: "",
            descripcion: "",
            stock: "",
            precio: "",
        }
    },
    created(){
        this.cargarDatos()
    },
    methods: {
        cargarDatos(){
            axios.get('/api/productos')
            .then(respuesta => {
                this.plantas = respuesta.data.filter(planta => planta.activo);
                console.log(this.plantas);
            })
            .catch(error => console.log(error))
        },
        changeData(id,tipo){
            this.plantas_filtadras = this.plantas.filter( planta => planta.id==id)[0]
            this.nombre = this.plantas_filtadras.nombre;
            this.color = this.plantas_filtadras.color;
            this.descripcion = this.plantas_filtadras.descripcion;
            this.stock = this.plantas_filtadras.stock;
            this.precio = this.plantas_filtadras.precio;
            Swal.fire({
                title: 'Modifica el contenido',
                input: 'text',
                showCancelButton: true,
                confirmButtonText: 'Edit',
                showLoaderOnConfirm: true,
                preConfirm: (edit) => {
                    if( this.nombre === tipo ){
                        this.nombre = edit;
                    } else if ( this.descripcion === tipo ){
                        this.descripcion = edit;
                    } else if( this.stock === tipo ){
                        this.stock = edit;
                    } else if( this.color === tipo ){
                        this.color = edit;
                    } else if ( this.precio === tipo ){
                        this.precio = edit;
                    }
                    axios.put('/api/plantas',
            {
                "id": this.plantas_filtadras.id,
                "nombre": this.nombre,
                "color": this.color,
                "descripcion": this.descripcion,
                "tipoPlanta": this.plantas_filtadras.tipoPlanta,
                "stock": this.stock,
                "foto": this.plantas_filtadras.foto,
                "precio": this.precio
            })
            .then(res => { Swal.fire({
                icon: 'success',
                text: 'Cambiaste correctamente el producto ' + this.nombre,
                showConfirmButton: false,
                timer: 2000
                }).then(() => window.location.href="/manager/paginas/editar_plantas.html")
            })
            .catch(error => { Swal.fire({
                icon: 'error',
                text: error.response.data   
                })
            })
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        },
    }
}).mount("#app")