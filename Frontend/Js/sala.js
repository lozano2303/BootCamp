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

    for (let i = 1; i <= cantidad; i++) {
        const inputDiv = document.createElement('div');
        inputDiv.className = 'mb-3';
        inputDiv.innerHTML = `
        <div class="inputLabel" >
            <label for="jugador${i}" class="form-label">Nombre del jugador ${i}:</label>
            <input type="text" class="form-control" id="jugador${i}" name="jugador${i}">
        <div>
        `;
        container.appendChild(inputDiv);
    }

    // Agrega el botón "Jugar" si hay al menos 2 jugadores
    if (cantidad >= 2) {
        const btnDiv = document.createElement('div');
        btnDiv.style.display = "flex";
        btnDiv.style.justifyContent = "center";
        btnDiv.style.marginTop = "20px";
        btnDiv.innerHTML = `
            <button id="btnJugar" style="
                background: #2EEF51;
                color: #222;
                font-weight: bold;
                border: 2px solid #222;
                border-radius: 8px;
                padding: 8px 32px;
                font-size: 1.2rem;
                box-shadow: 0 2px 8px rgba(0,0,0,0.15);
                cursor: pointer;
                transition: background 0.2s;
            ">Jugar</button>
        `;
        container.appendChild(btnDiv);
    }

    // Agrega el contenedor dentro de .salaJugadores
    document.querySelector('.salaJugadores').appendChild(container);
}
