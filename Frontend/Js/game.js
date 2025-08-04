const statsNames = ['PAC', 'SHO', 'PAS', 'DRI', 'DEF', 'PHY'];
const statsLabels = {
  PAC: 'Pace',
  SHO: 'Shooting',
  PAS: 'Passing',
  DRI: 'Dribbling',
  DEF: 'Defense',
  PHY: 'Physical'
};

let jugadores = [
  // Se llenará luego con jugadores de la API
];

let rondaActual = 1;
let estadisticaRonda = '';
let cartasJugadas = [];
let turnoJugadorIndex = 0; // Índice del jugador que debe jugar ahora
let jugadoresEnDesempate = null; // null = juego normal, array = ronda extra con jugadores empatados

// --- INICIALIZACIÓN DEL JUEGO Y PETICIÓN DE JUGADORES ---

document.addEventListener('DOMContentLoaded', async () => {
  await inicializarJuego();
});

async function inicializarJuego() {
  const playerBar = document.getElementById('player-bar');
  playerBar.innerHTML = '';

  const gameId = localStorage.getItem('gameId');
  const playerIds = JSON.parse(localStorage.getItem('playerIds') || '[]');

  if (!gameId || playerIds.length === 0) {
    mostrarMensajeError('Sin partida activa o jugadores.');
    return;
  }

  try {
    await asignarJugadoresASala(gameId, playerIds);
    const jugadoresAPI = await obtenerJugadores(gameId);

    if (!jugadoresAPI || jugadoresAPI.length === 0) {
      mostrarMensajeError('No hay jugadores asignados.');
      return;
    }

    jugadores = jugadoresAPI.map((p) => ({
      id: p.id,
      nombre: p.playerName,
      cartas: generarCartasJugador(),
      rondasGanadas: 0,
      puntos: 0
    }));

    elegirEstadistica();
    render();
    mostrarJugadores(jugadores);

  } catch (error) {
    console.error('Error al iniciar el juego:', error);
    mostrarMensajeError('Error al cargar jugadores.');
  }
}

function mostrarMensajeError(mensaje) {
  const playerBar = document.getElementById('player-bar');
  playerBar.innerHTML = `<div class="player-slot">${mensaje}</div>`;
}

async function obtenerJugadores(gameId) {
  const response = await fetch(`http://localhost:8080/playergames/list/${gameId}`);
  if (!response.ok) throw new Error(`No se pudo obtener jugadores: ${response.statusText}`);
  return await response.json();
}

async function asignarJugadoresASala(gameId, playerIds) {
  const res = await fetch(`http://localhost:8080/playergames/assign/${gameId}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(playerIds)
  });

  if (!res.ok) {
    const dto = await res.json().catch(() => null);
    const msg = dto?.message || 'Error al asignar jugadores a la sala';
    throw new Error(msg);
  }

  return await res.json();
}

function mostrarJugadores(jugadores) {
  const playerBar = document.getElementById('player-bar');
  playerBar.innerHTML = ''; // Limpiar

  const titulo = document.createElement('h3');
  titulo.textContent = `🎮 Jugadores en esta partida: ${jugadores.length}`;
  playerBar.appendChild(titulo);

  jugadores.forEach(jugador => {
    const slot = document.createElement('div');
    slot.className = 'player-slot';

    const nombre = document.createElement('span');
    nombre.className = 'player-name';
    nombre.textContent = jugador.nombre;

    slot.appendChild(nombre);
    playerBar.appendChild(slot);
  });
}

// --- FUNCIONES DEL JUEGO (render, elegirEstadistica, jugarCarta, etc) ---

function generarCartasJugador() {
  const nombresCartasEjemplo = ['Messi', 'Cristiano Ronaldo', 'Neymar', 'Mbappé', 'Haaland', 'Modric', 'Iniesta', 'Xavi'];
  return nombresCartasEjemplo.map(nombre => ({
    nombre,
    stats: {
      PAC: Math.floor(Math.random() * 40) + 60,
      SHO: Math.floor(Math.random() * 40) + 60,
      PAS: Math.floor(Math.random() * 40) + 60,
      DRI: Math.floor(Math.random() * 40) + 60,
      DEF: Math.floor(Math.random() * 40) + 30,
      PHY: Math.floor(Math.random() * 40) + 50
    }
  }));
}

function elegirEstadistica() {
  estadisticaRonda = statsNames[Math.floor(Math.random() * statsNames.length)];
}

function render() {
  document.getElementById('ronda').innerText = `Ronda ${rondaActual}/8`;
  document.getElementById('estadistica').innerText = `Estadística: ${statsLabels[estadisticaRonda]}`;

  const jugadoresParaRender = jugadoresEnDesempate ?? jugadores;

  const info = jugadoresParaRender.map((j, idx) => {
    const turno = (idx === turnoJugadorIndex) ? ' ← Turno' : '';
    return `<div><strong>${j.nombre}</strong> | Cartas: ${j.cartas.length} | Rondas ganadas: ${j.rondasGanadas} | Puntos: ${j.puntos}${turno}</div>`;
  }).join('');
  document.getElementById('jugadores-info').innerHTML = info;

  renderCartasJugador();
  renderCartasJugadas();
}

function renderCartasJugador() {
  const zona = document.getElementById('cartas-jugador');
  zona.innerHTML = ''; // Limpiar la zona antes de agregar nuevas cartas

  // El jugador que tiene el turno es el que podrá ver sus cartas interactivas
  const jugadorActual = jugadores[turnoJugadorIndex]; 

  // Crear un contenedor para las cartas del jugador actual
  const divJugador = document.createElement('div');
  divJugador.innerHTML = `<h3>${jugadorActual.nombre}</h3>`;

  jugadorActual.cartas.forEach(carta => {
    const div = document.createElement('div');
    div.className = 'carta';
    div.innerHTML = `<strong>${carta.nombre}</strong><br>` + Object.entries(carta.stats).map(([k, v]) => {
      const clase = (k === estadisticaRonda) ? 'stat-destacada' : '';
      return `<div class="${clase}">${k}: ${v}</div>`;
    }).join('');

    // Si no es el turno del jugador, deshabilitar la carta
    if (jugadorActual.id !== jugadores[turnoJugadorIndex].id) {
      div.style.pointerEvents = 'none'; // Deshabilitar la interacción con la carta
      div.classList.add('carta-deshabilitada'); // Añadir clase para estilizar la carta deshabilitada
    } else {
      div.onclick = () => jugarCarta(jugadorActual, carta); // Solo el jugador actual puede jugar su carta
    }

    divJugador.appendChild(div); // Añadir la carta al contenedor
  });

  zona.appendChild(divJugador); // Agregar las cartas del jugador actual al contenedor
}

function jugarCarta(jugador, carta) {
  const jugadoresParaJugar = jugadoresEnDesempate ?? jugadores;

  if (jugadoresParaJugar[turnoJugadorIndex].id !== jugador.id) {
    alert(`No es el turno de ${jugador.nombre}.`);
    return;
  }

  const indiceCarta = jugador.cartas.indexOf(carta);
  if (indiceCarta > -1) {
    jugador.cartas.splice(indiceCarta, 1);
  }

  cartasJugadas.push({ jugador, carta });

  turnoJugadorIndex++;

  if (cartasJugadas.length === jugadoresParaJugar.length) {
    determinarGanador();
  } else {
    if (turnoJugadorIndex >= jugadoresParaJugar.length) {
      turnoJugadorIndex = 0; // Reiniciar por seguridad
    }
    render();
  }
}

function determinarGanador() {
  const jugadoresParaJugar = jugadoresEnDesempate ?? jugadores;

  let mejor = cartasJugadas[0];
  for (let c of cartasJugadas) {
    if (c.carta.stats[estadisticaRonda] > mejor.carta.stats[estadisticaRonda]) {
      mejor = c;
    }
  }

  mejor.jugador.rondasGanadas++;

  const cartasGanadasRonda = cartasJugadas.length;
  const puntosGanados = (cartasGanadasRonda - 1) * 100;
  mejor.jugador.puntos += puntosGanados;

  document.getElementById('ganador-ronda').innerText = `${mejor.jugador.nombre} gana la ronda y obtiene ${puntosGanados} puntos!`;

  render();

  setTimeout(() => {
    avanzarRonda();
  }, 2000);
}

function renderCartasJugadas() {
  const zona = document.getElementById('cartas-jugadas');
  zona.innerHTML = cartasJugadas.map(({ jugador, carta }) => {
    return `<div class="carta"><strong>${jugador.nombre}</strong><br>${carta.nombre}<br>${estadisticaRonda}: ${carta.stats[estadisticaRonda]}</div>`;
  }).join('');
}

function avanzarRonda() {
  if (jugadoresEnDesempate) {
    // Ronda extra de desempate
    cartasJugadas = [];
    turnoJugadorIndex = 0;
    elegirEstadistica();

    if (jugadoresEnDesempate.length === 1) {
      mostrarGanadorFinal(jugadoresEnDesempate[0]);
      return;
    }

    render();
    return;
  }

  // Juego normal
  rondaActual++;
  cartasJugadas = [];
  turnoJugadorIndex = 0;
  document.getElementById('ganador-ronda').innerText = '';
  elegirEstadistica();

  if (rondaActual > 8) {
    // Al terminar ronda 8, revisar empate
    let maxPuntos = Math.max(...jugadores.map(j => j.puntos));
    let ganadores = jugadores.filter(j => j.puntos === maxPuntos);

    if (ganadores.length === 1) {
      mostrarGanadorFinal(ganadores[0]);
    } else {
      // Empate: iniciar ronda de desempate solo con los empatados
      iniciarRondaDesempate(ganadores);
    }
  } else {
    render();
  }
}

function iniciarRondaDesempate(empatados) {
  jugadoresEnDesempate = empatados;

  jugadoresEnDesempate.forEach(j => {
    j.cartas = generarCartasJugador();
  });

  cartasJugadas = [];
  turnoJugadorIndex = 0;
  estadisticaRonda = '';
  document.getElementById('ganador-ronda').innerText = 'Empate! Iniciando ronda de desempate entre: ' + jugadoresEnDesempate.map(j => j.nombre).join(', ');

  elegirEstadistica();
  render();
}

function mostrarGanadorFinal(ganador) {
  document.getElementById('final').style.display = 'block';
  document.getElementById('zona-cartas-jugador').style.display = 'none';

  let textoGanador = `${ganador.nombre} gana el juego con ${ganador.puntos} puntos!`;

  document.getElementById('ranking-final').innerHTML = `<li>${textoGanador}</li>`;
}

function reiniciarJuego() {
  location.reload();
}
