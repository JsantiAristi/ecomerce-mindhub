const { createApp } = Vue

createApp({
    data() {
        return {
            plantas: [],
            tipo_plantas: [],
            nombre: "",
            color: "",
            descripcion: "",
            foto: "../../assets/agregar-producto.png",
            tipo: "",
            precio: "",
            cantidad: "",
            permitExtension: /(.PNG|.JPG|.png|.jpg)$/i,

        }
    },
    created(){
        this.loadData()
    },
    methods: {
        loadData(){
            axios.get('/api/productos')
            .then(res => {
                this.plantas = res.data
                this.tipo_plantas = Array.from(new Set(this.plantas.map(planta => planta.tipoPlanta)));
            })
            .catch(error => console.log(error))
        },
        changePicture(e){
            if (!this.permitExtension.exec(e.target.files[0].name)) {
                alert("Selecciona solo archivos de imagen")
            }else if (e.target.files[0]) {
                this.foto = URL.createObjectURL(e.target.files[0])
            } else {
                this.foto = "../../assets/agregar-producto.png"
            }
        },
        add_product(){
            axios.post('/api/plantas',
            {
                "nombre": this.nombre,
                "color": this.color,
                "descripcion": this.descripcion,
                "tipoPlanta": this.tipo,
                "stock": this.stock,
                "foto": this.foto,
                "precio": this.precio
            })
            .then(res => { Swal.fire({
                icon: 'success',
                text: 'Añadiste un nuevo producto',
                showConfirmButton: false,
                timer: 2000
                }).then(() => window.location.href="/manager/paginas/editar_plantas.html")
            })
            .catch(error => { Swal.fire({
                icon: 'error',
                text: error.response.data   
                })
            })
        }
    }
}).mount('#app')