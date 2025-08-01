
//Obtención de cartas 
const cartas = document.querySelectorAll("#cartaSeleccion");


cartas.forEach(cartas =>{
    cartas.addEventListener('click' , function(){
        let cantidad  = parseInt(cartas.getAttribute('data-id'));
        localStorage.setItem('cantidadJugadores' , cantidad);
        crearInputsJugadores(cantidad)
    })
})

//Funcion para crear los inputs
function crearInputsJugadores(cantidad) {
    // Elimina inputs anteriores si existen
    let container = document.getElementById('inputs-jugadores');
    if (container) {
        container.remove();
    }

    // Crea un nuevo contenedor
    container = document.createElement('div');
    container.id = 'inputs-jugadores';
    container.className = 'container mt-4';

    for (let i = 1; i <= cantidad; i++) {
        const inputDiv = document.createElement('div');
        inputDiv.className = 'mb-3';
        inputDiv.innerHTML = `
            <label for="jugador${i}" class="form-label">Nombre del jugador ${i}:</label>
            <input type="text" class="form-control" id="jugador${i}" name="jugador${i}">
            
        `;
        container.appendChild(inputDiv);
    }

    // Agrega el contenedor al body (puedes cambiar la ubicación si lo prefieres)
    document.body.appendChild(container);
}   
